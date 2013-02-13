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

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This controller will automatically update its database according to the URL at hand,
 * when asked {@link #canAccess(String, java.net.URL)}. This obviously needs a connection
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/14, 0:12)
 */
public class AutomatedRobotsController extends DefaultRobotsController {

    private synchronized void prepare(URL url) throws IOException {
        final String host = url.getHost();
        if (hasHost(host)) {
            return;
        }
        final Builder builder = new Builder();
        builder.switchHost(host);
        final DefaultPageDownloader downloader = new DefaultPageDownloader(null, "http://" + host + "/robots.txt");
        downloader.connect();
        final StringWriter writer = new StringWriter();
        downloader.download(writer);
        final String[] lines = writer.toString().split("\n");
        for (String line : lines) {
            line = line.replaceFirst("#.*", "").trim();
            if (line.isEmpty()) {
                continue;
            }
            final String[] info = line.split(":", 2);
            if (info.length != 2) {
                continue;
            }
            info[0] = info[0].trim();
            info[1] = info[1].trim();
            if (info[0].equalsIgnoreCase("User-agent")) {
                builder.switchAgent(info[1]);
            } else if (info[0].equalsIgnoreCase("Disallow")) {
                builder.addLocation(info[1]);
            }
        }
        merge(builder.build());
    }

    @Override
    public boolean canAccess(String agent, URL path) {
        try {
            prepare(path);
        } catch (IOException ignored) {
            //if we cannot get the actual policies, we will assume that the robot is allowed to do
            //as it pleases
            locations.put(path.getHost().toLowerCase(), new ConcurrentHashMap<String, Set<String>>());
        }
        return super.canAccess(agent, path);
    }

}
