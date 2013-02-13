package com.agileapes.webexport.url.state.impl;

import com.agileapes.webexport.url.state.State;

import java.util.Set;

/**
 * An ActiveState is a state for which the content has been downloaded and is ready to be parsed
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:41)
 */
public class ActiveState implements State {

    private final PrefetchState state;
    private final Long timestamp;
    private final String content;

    public ActiveState(PrefetchState state, Long timestamp, String content) {
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

}
