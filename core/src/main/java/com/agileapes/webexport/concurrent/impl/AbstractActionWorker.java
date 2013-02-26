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

package com.agileapes.webexport.concurrent.impl;

import com.agileapes.webexport.concurrent.ActionDescriptor;
import com.agileapes.webexport.concurrent.ActionScheduler;
import com.agileapes.webexport.concurrent.Worker;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/26, 14:17)
 */
public abstract class AbstractActionWorker<A extends ActionDescriptor> extends Worker {

    private ActionScheduler<A> scheduler;
    private A action;

    protected AbstractActionWorker(AbstractActionManager<A> manager, String name) {
        super(manager, name);
    }

    @Override
    public void initialize() {
        setAction(null);
        setScheduler(null);
    }

    public void setScheduler(ActionScheduler<A> scheduler) {
        this.scheduler = scheduler;
    }

    public void setAction(A action) {
        this.action = action;
    }

    public ActionScheduler<A> getScheduler() {
        return scheduler;
    }

    public A getAction() {
        return action;
    }

}
