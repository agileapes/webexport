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

package com.agileapes.webexport.net.impl;

import com.agileapes.webexport.net.ProxyDescriptor;

import java.net.Proxy;

/**
 * This is an immutable proxy descriptor
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 22:36)
 */
public class ImmutableProxyDescriptor implements ProxyDescriptor {

    private final Proxy.Type type;
    private final String host;
    private final Integer port;

    public ImmutableProxyDescriptor(Proxy.Type type, String host, Integer port) {
        this.type = type;
        this.host = host;
        this.port = port;
    }

    @Override
    public Proxy.Type getType() {
        return type;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Integer getPort() {
        return port;
    }

}
