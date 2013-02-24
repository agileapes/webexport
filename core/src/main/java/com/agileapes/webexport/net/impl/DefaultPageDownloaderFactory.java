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

import com.agileapes.webexport.net.PageDownloader;
import com.agileapes.webexport.net.PageDownloaderFactory;
import com.agileapes.webexport.net.ProxyDescriptor;
import com.agileapes.webexport.net.RobotsController;

import java.net.MalformedURLException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/24, 17:20)
 */
public class DefaultPageDownloaderFactory implements PageDownloaderFactory {

    private final RobotsController controller;
    private final ProxyDescriptor proxyDescriptor;
    private final String userAgent;

    public DefaultPageDownloaderFactory(RobotsController controller, ProxyDescriptor proxyDescriptor, String userAgent) {
        this.controller = controller;
        this.proxyDescriptor = proxyDescriptor;
        this.userAgent = userAgent;
    }

    @Override
    public PageDownloader getDownloader(String url) throws MalformedURLException {
        return new DefaultPageDownloader(controller, url, proxyDescriptor, userAgent);
    }

}
