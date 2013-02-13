package com.agileapes.webexport.event;

import com.agileapes.webexport.model.PageModel;

/**
 * This event occurs right after a transition has been made and a parsed model
 * of the page is available.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:30)
 */
public class AfterParseEvent extends CrawlerEvent {

    private final PageModel model;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param model  the page that was the source of this event
     */
    public AfterParseEvent(Object source, PageModel model) {
        super(source);
        this.model = model;
    }

    public PageModel getModel() {
        return model;
    }

}
