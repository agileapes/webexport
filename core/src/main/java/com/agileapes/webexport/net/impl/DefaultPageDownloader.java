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
import com.agileapes.webexport.net.ProxyDescriptor;
import com.agileapes.webexport.net.RobotsController;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.*;

/**
 * This is the default implementation of the page downloader
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 22:43)
 */
public class DefaultPageDownloader implements PageDownloader {

    public static final String DEFAULT_USER_AGENT = "AgileApes WebExport/1.0";
    private static final String ROBOTS_TXT = "/robots.txt";
    private final Proxy proxy;
    private final URL url;
    private URLConnection connection;
    private boolean connected;
    private String userAgent = DEFAULT_USER_AGENT;
    private final RobotsController controller;

    public DefaultPageDownloader(RobotsController controller, String url) throws MalformedURLException {
        this(controller, url, (String) null);
    }

    public DefaultPageDownloader(RobotsController controller, URL url) {
        this(controller, url, (String) null);
    }

    public DefaultPageDownloader(RobotsController controller, String url, String userAgent) throws MalformedURLException {
        this(controller, new URL(url), userAgent);
    }

    public DefaultPageDownloader(RobotsController controller, URL url, String userAgent) {
        this(controller, url, null, userAgent);
    }

    public DefaultPageDownloader(RobotsController controller, String url, ProxyDescriptor proxyDescriptor) throws MalformedURLException {
        this(controller, url, proxyDescriptor, null);
    }

    public DefaultPageDownloader(RobotsController controller, String url, ProxyDescriptor proxyDescriptor, String userAgent) throws MalformedURLException {
        this(controller, new URL(url), proxyDescriptor, userAgent);
    }

    public DefaultPageDownloader(RobotsController controller, URL url, ProxyDescriptor proxyDescriptor) {
        this(controller, url, proxyDescriptor, null);
    }

    public DefaultPageDownloader(RobotsController controller, URL url, ProxyDescriptor proxyDescriptor, String userAgent) {
        this.controller = controller;
        this.url = url;
        this.userAgent = userAgent == null ? DEFAULT_USER_AGENT : userAgent;
        if (proxyDescriptor == null) {
            proxy = Proxy.NO_PROXY;
        } else {
            proxy = new Proxy(proxyDescriptor.getType(), new InetSocketAddress(proxyDescriptor.getHost(), proxyDescriptor.getPort()));
        }
        connected = false;
    }

    private Proxy getProxy() {
        return proxy;
    }

    @Override
    public void connect() throws IOException {
        if (connected) {
            throw new IllegalStateException("Downloader is already connected");
        }
        URLConnection connection = url.openConnection(getProxy());
        if (connection instanceof HttpURLConnection) {
            final CookieManager cookieManager = new CookieManager();
            HttpURLConnection urlConnection = (HttpURLConnection) connection;
            urlConnection.setInstanceFollowRedirects(false);
            if (userAgent != null) {
                urlConnection.setRequestProperty("User-Agent", userAgent);
            }
            urlConnection.connect();
            cookieManager.storeCookies(urlConnection);
            while (urlConnection.getResponseCode() / 100 == 3) {
                //we are being redirected
                final String location = urlConnection.getHeaderField("Location");
                urlConnection = (HttpURLConnection) new URL(location).openConnection(getProxy());
                cookieManager.setCookies(urlConnection);
                urlConnection.setInstanceFollowRedirects(false);
                if (userAgent != null) {
                    urlConnection.setRequestProperty("User-Agent", userAgent);
                }
                urlConnection.connect();
                cookieManager.storeCookies(urlConnection);
            }
            connection = urlConnection;
        } else {
            connection.connect();
        }
        connected = true;
        this.connection = connection;
    }

    @Override
    public void download(Writer writer) throws IOException {
        if (!connected) {
            throw new IllegalStateException("Downloader is not connected");
        }
        if (controller != null && !url.getPath().equals(ROBOTS_TXT) && !controller.canAccess(userAgent, url)) {
            throw new IllegalAccessError("This user agent cannot access the content at " + url.toExternalForm());
        }
        final InputStream connectionInputStream = connection.getInputStream();
        int data;
        while ((data = connectionInputStream.read()) != -1) {
            writer.write(data);
        }
    }

    @Override
    public URLConnection getConnection() {
        return connection;
    }

    public static void main(String[] args) throws Exception {
        final DefaultPageDownloader downloader = new DefaultPageDownloader(new AutomatedRobotsController(), "http://en.wikipedia.org/wiki/Main_Page", new ImmutableProxyDescriptor(Proxy.Type.SOCKS, "127.0.0.1", 32201), null);
        final StringWriter writer = new StringWriter();
        downloader.connect();
        downloader.download(writer);
        System.out.println(writer.toString());
    }

}
