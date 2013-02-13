package com.agileapes.webexport.event;

import com.agileapes.webexport.url.state.State;

/**
 * This event is published before a transition has been made. Of course, this event occurs
 * regardless of whether that transition has been made. That is, this event is published for
 * all transitions, even those that are destined to be ignored based on the rules.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:32)
 */
public class BeforeParseEvent extends CrawlerEvent {

    private final State origin;
    private final State target;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param origin the state we are in at this moment
     * @param target the state that is to be assumed after the transition
     */
    public BeforeParseEvent(Object source, State origin, State target) {
        super(source);
        this.origin = origin;
        this.target = target;
    }

    public State getOrigin() {
        return origin;
    }

    public State getTarget() {
        return target;
    }

}
