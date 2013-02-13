package com.agileapes.webexport.url.state.impl;

import com.agileapes.webexport.url.state.State;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:41)
 */
public class FinalState implements State {

    private final PrefetchState state;
    private final Long timestamp;
    private final String content;

    public FinalState(PrefetchState state, Long timestamp, String content) {
        this.state = state;
        this.timestamp = timestamp;
        this.content = content;
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
    public String getPort() {
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

}
