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

import java.util.Collection;

/**
 * This event is fired when the application's work is completed, and all the states have been
 * visited or decided to be irrelevant. In this case, the worker threads are all closed,
 * and the application is left with a pool of all the collected page meta data.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:38)
 */
public class BeforeShutdownEvent extends CrawlerEvent {

    private final Collection<PageModel> models;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the component that published the event (never {@code null})
     * @param models the models collected so far in the application
     */
    public BeforeShutdownEvent(Object source, Collection<PageModel> models) {
        super(source);
        this.models = models;
    }

    public Collection<PageModel> getModels() {
        return models;
    }

}
