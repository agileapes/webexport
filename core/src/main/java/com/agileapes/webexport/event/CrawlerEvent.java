package com.agileapes.webexport.event;

import org.springframework.context.ApplicationEvent;

/**
 * This is the base class for all the events in this eco-system. This class is just to add
 * another level of indirection.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:39)
 */
public abstract class CrawlerEvent extends ApplicationEvent {

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     */
    public CrawlerEvent(Object source) {
        super(source);
    }

}
