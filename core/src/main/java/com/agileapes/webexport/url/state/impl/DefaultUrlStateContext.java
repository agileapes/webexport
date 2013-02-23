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

package com.agileapes.webexport.url.state.impl;

import com.agileapes.webexport.url.state.UrlState;
import com.agileapes.webexport.url.state.UrlStateContext;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 15:27)
 */
public class DefaultUrlStateContext implements UrlStateContext {

    private final Set<UrlState> states = new CopyOnWriteArraySet<UrlState>();

    @Override
    public synchronized UrlState next() {
        if (states.isEmpty()) {
            return null;
        }
        final UrlState state = states.iterator().next();
        states.remove(state);
        return state;
    }

    @Override
    public void add(UrlState state) {
        states.add(state);
    }

}
