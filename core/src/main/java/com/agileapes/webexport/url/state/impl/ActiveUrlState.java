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

import java.util.Map;
import java.util.Set;

/**
 * An ActiveUrlState is a state for which the content has been downloaded and is ready to be parsed
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:41)
 */
public class ActiveUrlState implements UrlState {

    private final PrefetchUrlState state;
    private final Long timestamp;
    private final String content;
    private final Map<String, String> headers;

    public ActiveUrlState(PrefetchUrlState state, Long timestamp, String content, Map<String, String> headers) {
        this.state = state;
        this.timestamp = timestamp;
        this.content = content;
        this.headers = headers;
    }

    @Override
    public String getAddress() {
        return state.getAddress();
    }

    @Override
    public String getProtocol() {
        return state.getProtocol();
    }

    @Override
    public String getDomain() {
        return state.getDomain();
    }

    @Override
    public String getContext() {
        return state.getContext();
    }

    @Override
    public Integer getPort() {
        return state.getPort();
    }

    @Override
    public String getUsername() {
        return state.getUsername();
    }

    @Override
    public String getPassword() {
        return state.getPassword();
    }

    @Override
    public Set<String> getParameters() {
        return state.getParameters();
    }

    @Override
    public String getParameter(String name) {
        return state.getParameter(name);
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public Integer getDepth() {
        return state.getDepth();
    }

    @Override
    public Set<String> getHeaders() {
        return headers.keySet();
    }

    @Override
    public String getHeader(String header) {
        return headers.get(header);
    }

    @Override
    public UrlState getParent() {
        return state.getParent();
    }

    @Override
    public String getDirectory() {
        return state.getDirectory();
    }

    @Override
    public String getFilename() {
        return state.getFilename();
    }

    @Override
    public String toString() {
        return getAddress() + "[" + getTimestamp() + "]:" + getContent().substring(0, Math.min(50, getContent().length()));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UrlState)) {
            return false;
        }
        if (obj instanceof PrefetchUrlState && ((PrefetchUrlState) obj).getAddress().equals(getAddress())) {
            return true;
        }
        if (obj instanceof ActiveUrlState) {
            if (((ActiveUrlState) obj).getContent().equals(getContent())) {
                return true;
            }
            if (((ActiveUrlState) obj).getAddress().equals(getAddress())) {
                return true;
            }
        }
        return false;
    }

}
