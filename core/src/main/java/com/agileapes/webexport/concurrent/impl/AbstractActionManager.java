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
import com.agileapes.webexport.concurrent.ActionQueue;
import com.agileapes.webexport.concurrent.WorkerPreparator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/26, 14:16)
 */
public abstract class AbstractActionManager<A extends ActionDescriptor> extends AbstractManager<AbstractActionWorker<A>> {

    private ActionQueue<A> queue;

    public void setQueue(ActionQueue<A> queue) {
        this.queue = queue;
    }

    @Override
    protected void execute() {
        final A action = queue.next();
        if (action == null) {
            return;
        }
        List<WorkerPreparator<AbstractActionWorker<A>>> preparators = getPreparators();
        if (preparators == null) {
            preparators = new ArrayList<WorkerPreparator<AbstractActionWorker<A>>>();
        }
        preparators.add(0, new WorkerPreparator<AbstractActionWorker<A>>() {
            @Override
            public void prepare(AbstractActionWorker<A> worker) {
                worker.setScheduler(queue);
                worker.setAction(action);
            }
        });
        //noinspection unchecked
        start(preparators.toArray(new WorkerPreparator[preparators.size()]));
    }

    @Override
    protected boolean completed() {
        return !queue.hasNext();
    }

    protected List<WorkerPreparator<AbstractActionWorker<A>>> getPreparators() {
        return null;
    }

}
