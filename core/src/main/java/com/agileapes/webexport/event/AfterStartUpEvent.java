package com.agileapes.webexport.event;

/**
 * This event is published right after the startup process has been completed,
 * and when the configuration of the application has been decided
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:36)
 */
public class AfterStartUpEvent extends CrawlerEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public AfterStartUpEvent(Object source) {
        super(source);
    }

}
