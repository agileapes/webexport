package com.agileapes.webexport.url.state.impl;

import com.agileapes.webexport.url.state.State;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:27)
 */
public class PrefetchState implements State {

    private static final String DEFAULT_ADDRESS_PREFIX = "http://";
    private final String address;
    private final String protocol;
    private final Integer depth;
    private String domain;
    private String context;
    private final Map<String, String> parameters = new HashMap<String, String>();
    private String port;
    private String username;
    private String password;

    public PrefetchState(String address, Integer depth) throws MalformedURLException {
        this.depth = depth;
        String url = address;
        if (!url.contains("://")) {
            url = DEFAULT_ADDRESS_PREFIX + url;
        }
        this.address = url;
        domain = "";
        context = "";
        port = "";
        username = "";
        password = "";
        int i = 0;
        while (i < url.length()) {
            if (url.charAt(i ++) == ':' && (i < url.length() && url.charAt(i ++) == '/')) {
                break;
            }
        }
        protocol = url.substring(0, ++ i - 3);
        while (i < url.length()) {
            if ("/?".contains(String.valueOf(url.charAt(i ++)))) {
                i --;
                break;
            }
        }
        domain = url.substring(protocol.length() + 3, i);
        if (domain.contains("@")) {
            username = domain.substring(0, domain.indexOf('@'));
            domain = domain.substring(username.length() + 1);
        }
        if (username.contains(":")) {
            password = username.substring(username.indexOf(':') + 1);
            username = username.substring(0, username.indexOf(':'));
        }
        if (domain.contains(":")) {
            port = domain.substring(domain.indexOf(':') + 1);
            domain = domain.substring(0, domain.indexOf(':'));
        }
        int start = i;
        while (i < url.length()) {
            if (url.charAt(i ++) == '?') {
                i --;
                break;
            }
        }
        context = url.substring(start, i);
        if (context.isEmpty()) {
            context = "/";
        }
        if (i >= url.length()) {
            return;
        }
        i ++;
        final String[] queries = url.substring(i).split("&");
        for (String query : queries) {
            final String[] split = query.split("=");
            if (split.length == 1) {
                parameters.put(split[0], "");
            } else if (split.length == 2) {
                parameters.put(split[0], split[1]);
            } else {
                throw new MalformedURLException("Malformed query key-value: " + query);
            }
        }
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getContext() {
        return context;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Set<String> getParameters() {
        return parameters.keySet();
    }

    @Override
    public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override
    public String getContent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Integer getDepth() {
        return depth;
    }

    @Override
    public Long getTimestamp() {
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {
        System.out.println("ftp".matches("(?i:https?)"));
    }

}
