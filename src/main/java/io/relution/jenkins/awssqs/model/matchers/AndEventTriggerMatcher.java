package io.relution.jenkins.awssqs.model.matchers;

import hudson.model.AbstractProject;
import io.relution.jenkins.awssqs.interfaces.Event;
import io.relution.jenkins.awssqs.interfaces.EventTriggerMatcher;
import io.relution.jenkins.awssqs.logging.Log;

import java.util.List;

public class AndEventTriggerMatcher extends AbstractEventTriggerMatcher {

    public AndEventTriggerMatcher(EventTriggerMatcher... matchers) {
        super(matchers);
    }

    public AndEventTriggerMatcher(List<EventTriggerMatcher> matchers) {
        super(matchers);
    }

    @Override
    public boolean matches(List<Event> events, AbstractProject<?, ?> job) {
        for (EventTriggerMatcher matcher : matchers) {
            Log.info("Job '%s': test if any event not match by matcher '%s'...", job.getName(), matcher.getClass().getSimpleName());
            if (!matcher.matches(events, job)) {
                return false;
            }
        }
        Log.info("Job '%s': event(s) match all Matchers defined in '%s'.", job.getName(), this.getClass().getSimpleName());
        return true;
    }
}
