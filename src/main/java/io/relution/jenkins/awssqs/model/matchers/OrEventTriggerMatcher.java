package io.relution.jenkins.awssqs.model.matchers;

import hudson.model.AbstractProject;
import io.relution.jenkins.awssqs.interfaces.Event;
import io.relution.jenkins.awssqs.interfaces.EventTriggerMatcher;
import io.relution.jenkins.awssqs.logging.Log;

import java.util.List;

public class OrEventTriggerMatcher extends AbstractEventTriggerMatcher {

    public OrEventTriggerMatcher(EventTriggerMatcher... matchers) {
        super(matchers);
    }

    public OrEventTriggerMatcher(List<EventTriggerMatcher> matchers) {
        super(matchers);
    }

    @Override
    public boolean matches(List<Event> events, AbstractProject<?, ?> job) {
        for (EventTriggerMatcher matcher : matchers) {
            Log.info("Job '%s': test if any event match by matcher '%s'...", job.getName(), matcher.getClass().getSimpleName());
            if (matcher.matches(events, job)) {
                return true;
            }
        }
        Log.info("Job '%s': event(s) not match all matchers defined in '%s'.", job.getName(), this.getClass().getSimpleName());
        return false;
    }
}
