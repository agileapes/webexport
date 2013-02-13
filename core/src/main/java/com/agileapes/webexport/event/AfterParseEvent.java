/*
 * Copyright (c) 2013. AgileApes (http://www.agileapes.scom/), and
 * associated organizations.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

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
