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

package com.agileapes.webexport.model.impl;

import com.agileapes.webexport.model.PageModel;
import com.agileapes.webexport.url.state.UrlState;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/24, 20:12)
 */
public class DefaultPageModel implements PageModel {

    private final UrlState state;
    private final Map<String, Object> properties = new ConcurrentHashMap<String, Object>();

    public DefaultPageModel(UrlState state) {
        this.state = state;
    }

    @Override
    public UrlState getState() {
        return state;
    }

    @Override
    public void setProperty(String name, Object value) {
        properties.put(name, value);
    }

    @Override
    public Object getProperty(String name) {
        if (!properties.containsKey(name)) {
            throw new IllegalArgumentException("No such property: " + name);
        }
        return properties.get(name);
    }

}
