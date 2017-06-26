package io.relution.jenkins.awssqs.model.matchers;

import io.relution.jenkins.awssqs.interfaces.EventTriggerMatcher;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventTriggerMatcher implements EventTriggerMatcher {

    protected final List<EventTriggerMatcher> matchers;

    public AbstractEventTriggerMatcher(EventTriggerMatcher... matchers) {
        this.matchers = new ArrayList<>();
        CollectionUtils.addAll(this.matchers, matchers);
    }

    public AbstractEventTriggerMatcher(List<EventTriggerMatcher> matchers) {
        this.matchers = new ArrayList<>(matchers);
    }

    public AbstractEventTriggerMatcher and(EventTriggerMatcher... matchers) {
        CollectionUtils.addAll(this.matchers, matchers);
        return new AndEventTriggerMatcher(this.matchers);
    }

    public AbstractEventTriggerMatcher or(EventTriggerMatcher... matchers) {
        CollectionUtils.addAll(this.matchers, matchers);
        return new OrEventTriggerMatcher(this.matchers);
    }
}
