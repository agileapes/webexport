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

package com.agileapes.webexport.url.state;

import java.util.Set;

/**
 * The state is the centerpiece around which all the action takes place.
 * The "State" encapsulates the concept of a valid move within the web graph.
 * States are considered different if they result in the exact same content. On the other hand,
 * two states with the same {@code <address, timestamp>} pair are considered to be exactly the same,
 * even if their content -- by some chance (e.g., two worker threads hitting the exact same address
 * at the exact same time, and that address resulting in a dynamically generated content that is
 * different for the two hits) -- are not the same.
 *
 * This distinction is specially important and has been created by design. To make it somewhat clearer,
 * let's have a scenario. We call going from state {@code A} to address {@code xyz} transition 1.
 * Now, let's assume that we are in state {@code B} -- which is entirely distinct from the two pages
 * above -- and we encounter a link to {@code xyz}. It is immediately apparent that we should not
 * discard this page as already visited (even though we <em>have</em> visited it). The reason might
 * require some explanation.
 *
 * Suppose that page {@code xyz} is designed to generate a random news item. Now, it is completely
 * clear that going from {@code A} to this page and going from {@code B} to this page are entirely
 * different.
 *
 * To remedy this, the system will at first assume the two transitions to be different, until it is
 * proven wrong. There are multiple ways of achieving this. One is two examine the last update date
 * specified in the HTTP header. Another is to make certain assumptions (e.g. images with the same
 * URL being the same, pages with {@code .html} extension being static, etc.). Another, more reliable
 * -- and therefore more resource-consuming -- way is to actually compare the contents (or at least a
 * hash of the contents) of the two target states to make sure that they are the same.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:16)
 */
public interface State {

    /**
     * @return the actual, fully qualified address leading to this state
     */
    String getAddress();

    /**
     * @return the communications protocol used in the address
     */
    String getProtocol();

    /**
     * @return the qualified name of the domain hosting the content associated with this address
     */
    String getDomain();

    /**
     * @return the context path leading to this state in the specified domain
     */
    String getContext();

    /**
     * @return the port number for this communication
     */
    Integer getPort();

    /**
     * @return the username specified in the URL
     */
    String getUsername();

    /**
     * @return the password specified in the URL
     */
    String getPassword();

    /**
     * @return the names of the GET parameters in the HTTP request
     */
    Set<String> getParameters();

    /**
     * @param name    the name of the desired parameter
     * @return the value of the parameter or {@code null} if it has not been specified
     */
    String getParameter(String name);

    /**
     * @return the timestamp for the moment the contents of this page was touched by the system
     */
    Long getTimestamp();

    /**
     * @return the contents of this page
     */
    String getContent();

    /**
     * @return the distance of this page in the state machine from the starting state
     */
    Integer getDepth();

}
