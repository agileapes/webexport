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

import com.agileapes.webexport.net.RobotsController;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * This is the default robots.txt controller. To initialize, you should use the
 * internal {@link Builder}
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 23:56)
 */
public class DefaultRobotsController implements RobotsController {

    protected final Map<String, Map<String, Set<String>>> locations = new ConcurrentHashMap<String, Map<String, Set<String>>>();

    @Override
    public boolean canAccess(String agent, URL path) {
        final String host = path.getHost().toLowerCase();
        if (!locations.containsKey(host)) {
            return true;
        }
        final Map<String, Set<String>> map = locations.get(host);
        if (map.containsKey("*")) {
            final Set<String> all = map.get("*");
            for (String location : all) {
                if (path.getPath().startsWith(location)) {
                    return false;
                }
            }
        }
        if (map.containsKey(agent.toLowerCase())) {
            final Set<String> current = map.get(agent);
            for (String location : current) {
                if (path.getPath().startsWith(location)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean hasHost(String host) {
        return locations.containsKey(host);
    }

    protected void merge(DefaultRobotsController controller) {
        for (String host : controller.locations.keySet()) {
            if (!hasHost(host)) {
                locations.put(host, controller.locations.get(host));
            } else {
                final Map<String, Set<String>> map = locations.get(host);
                for (String agent : controller.locations.get(host).keySet()) {
                    if (!map.containsKey(agent)) {
                        map.put(agent, controller.locations.get(host).get(agent));
                    } else {
                        final Set<String> current = map.get(agent);
                        for (String location : controller.locations.get(host).get(agent)) {
                            current.add(location);
                        }
                        map.put(agent, current);
                    }
                }
                locations.put(host, map);
            }
        }
    }

    public static final class Builder {

        private DefaultRobotsController controller = new DefaultRobotsController();
        private Set<String> agent = new HashSet<String>();
        private String host = null;

        public void switchAgent(String agent) {
            agent = agent.toLowerCase();
            if (host == null) {
                throw new IllegalStateException("No host specified");
            }
            if (!this.agent.isEmpty()) {
                final String next = this.agent.iterator().next();
                if (controller.locations.containsKey(host) && controller.locations.get(host).containsKey(agent)) {
                    final Set<String> locations = controller.locations.get(host).get(agent);
                    if (!locations.isEmpty()) {
                        this.agent.clear();
                    }
                }
            }
            this.agent.add(agent);
        }

        public void switchHost(String host) {
            this.host = host.toLowerCase();
        }

        public void addLocation(String location) {
            if (agent.isEmpty()) {
                throw new IllegalStateException("No agent specified");
            }
            if (host == null) {
                throw new IllegalStateException("No host specified");
            }
            final Map<String, Set<String>> hostLocations;
            if (controller.locations.containsKey(host)) {
                hostLocations = controller.locations.get(host);
            } else {
                hostLocations = new ConcurrentHashMap<String, Set<String>>();
                controller.locations.put(host, hostLocations);
            }
            for (String theAgent : agent) {
                final Set<String> locations;
                if (hostLocations.containsKey(theAgent)) {
                    locations = hostLocations.get(theAgent);
                } else {
                    locations = new CopyOnWriteArraySet<String>();
                    hostLocations.put(theAgent, locations);
                }
                locations.add(location);
            }
        }

        public DefaultRobotsController build() {
            return controller;
        }

    }

}
