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
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/26, 14:56)
 */
public class DefaultActionQueue<A extends ActionDescriptor> implements ActionQueue<A> {

    private final Set<A> scheduled = new CopyOnWriteArraySet<A>();
    private final Set<A> done = new CopyOnWriteArraySet<A>();
    private final String name;

    public DefaultActionQueue(String name) {
        this.name = name;
    }

    @Override
    public synchronized boolean hasNext() {
        return !scheduled.isEmpty();
    }

    @Override
    public synchronized A next() {
        if (hasNext()) {
            final Logger logger = Logger.getLogger(DefaultActionQueue.class.getName() + "." + name);
            final A action = scheduled.iterator().next();
            scheduled.remove(action);
            done.add(action);
            logger.info("Dispensing action: " + action);
            logger.info("Scheduled: " + scheduled.size());
            logger.info("Done: " + done.size());
            return action;
        }
        return null;
    }

    @Override
    public synchronized void schedule(A action) {
        if (done.contains(action)) {
            return;
        }
        scheduled.add(action);
    }
}
