/*
 * Copyright 2017 Ribose Inc. <https://www.ribose.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.relution.jenkins.awssqs.it;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.FreeStyleProject;
import hudson.util.OneShotEvent;
import io.relution.jenkins.awssqs.SQSTrigger;
import io.relution.jenkins.awssqs.SQSTriggerQueue;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.TestBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Starts an embedded servlet container so that the test code can exercise user interaction through HTTP and assert based on the outcome.
 *
 * https://wiki.jenkins-ci.org/display/JENKINS/Unit+Test#UnitTest-Overview
 * */
public class JenkinsIT {

    @Rule
    public JenkinsRule jenkinsRule = new JenkinsRule();

    private MockAwsSqs mockAwsSqs;

    private static final Long TIMEOUT = 1_000L;//in milliseconds, e.g: 30_000 ~ 5 mins

    @Before
    public void before() throws Exception {//https://sqs.us-west-2.amazonaws.com/239062223385/testjenkinssqs
        this.mockAwsSqs = MockAwsSqs.get();

        //TODO refactor not to use func HtmlForm.submit
        final HtmlForm configForm = jenkinsRule.createWebClient().goTo("configure").getFormByName("config");
        configForm.getInputByName("_.nameOrUrl").setValueAttribute(this.mockAwsSqs.getSqsUrl());
        jenkinsRule.submit(configForm);

        initQueue();
    }

    private void initQueue() {
        SQSTriggerQueue queue = SQSTrigger.DescriptorImpl.get().getSqsQueues().get(0);
        queue.setFactory(new MockSQSFactory());
    }

    @After
    public void after() {
        this.mockAwsSqs.shutdown();
    }

    private void createFreestyleProject(List<OneShotEvent> eventStorage, String branches) throws IOException {
        final FreeStyleProject masterProject = jenkinsRule.createFreeStyleProject(UUID.randomUUID().toString());

        SQSTriggerQueue queue = SQSTrigger.DescriptorImpl.get().getSqsQueues().get(0);

        final String uuid = queue.getUuid();
        final SQSTrigger trigger = new SQSTrigger(uuid, branches);
        masterProject.addTrigger(trigger);

        final OneShotEvent buildStarted = new OneShotEvent();
        masterProject.getBuildersList().add(new TestBuilder() {

            @Override
            public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
                buildStarted.signal();
                return true;
            }
        });

        trigger.start(masterProject, false);

        eventStorage.add(buildStarted);
    }

    private void createRandomFreestyleProjects(List<OneShotEvent> eventStorage, int nop) throws IOException {// number of projects should be created
        for (int i = 0; i < nop; i++) {
            createFreestyleProject(eventStorage, UUID.randomUUID().toString());
        }
    }

    private void listenEvents(List<OneShotEvent> eventStorage) {
        for (OneShotEvent event : eventStorage) {
            try {
                event.block(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void shouldTriggerOnBranchWithoutWildcard() throws Exception {
        int nop = 10;

        this.mockAwsSqs.sendRandom(nop - 1);
        this.mockAwsSqs.send("master");

        List<OneShotEvent> eventStorage = new ArrayList<>();
        createFreestyleProject(eventStorage, "master");
        createRandomFreestyleProjects(eventStorage, nop);

        listenEvents(eventStorage);

        final OneShotEvent branchFoo = eventStorage.get(0);
        Assertions.assertThat(branchFoo.isSignaled()).isTrue();

        for (int i = 1; i < nop; i++) {
            Assertions.assertThat(eventStorage.get(i).isSignaled()).isFalse();
        }
    }

    @Test
    public void shouldTriggerOnBranchWithPrefixWildcard() throws Exception {
        //TODO implementing
    }

    @Test
    public void shouldTriggerOnBranchWithSuffixWildcard() throws Exception {
        //TODO implementing
    }

    @Test
    public void shouldTriggerOnAllBranches() throws Exception {
        //TODO implementing
    }

    @Test
    public void shouldTriggerOnRegexBranch() throws Exception {
        //TODO implementing
    }
}
