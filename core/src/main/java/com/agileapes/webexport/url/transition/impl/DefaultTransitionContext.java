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

package com.agileapes.webexport.url.transition.impl;

import com.agileapes.webexport.url.state.UrlState;
import com.agileapes.webexport.url.transition.TransitionContext;
import org.apache.log4j.Logger;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/24, 19:35)
 */
public class DefaultTransitionContext implements TransitionContext {

    private final static Logger logger = Logger.getLogger(TransitionContext.class);
    private final Set<UrlState> unvisited = new CopyOnWriteArraySet<UrlState>();
    private final Set<UrlState> visited = new CopyOnWriteArraySet<UrlState>();

    @Override
    public synchronized void add(UrlState target) {
        if (!visited.contains(target)) {
            logger.debug("Discovered new target: " + target);
            logger.debug("Old: " + visited.size());
            logger.debug("New: " + unvisited.size());
            unvisited.add(target);
        }
    }

    @Override
    public synchronized UrlState next() {
        if (unvisited.isEmpty()) {
            onEmpty();
            return null;
        }
        final UrlState state = unvisited.iterator().next();
        unvisited.remove(state);
        visited.add(state);
        logger.debug("Dispensing state: " + state);
        return state;
    }

    @Override
    public synchronized boolean isEmpty() {
        return unvisited.isEmpty();
    }

    public void onEmpty() {
        logger.debug("No more transitions to follow.");
    }

}
