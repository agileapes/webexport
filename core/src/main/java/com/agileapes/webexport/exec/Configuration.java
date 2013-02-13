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

package com.agileapes.webexport.exec;

import com.agileapes.webexport.url.transform.AddressTransformer;

import java.util.List;
import java.util.Set;

/**
 * The configuration for running the application is represented by this interface and its
 * implementers.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 19:55)
 */
public interface Configuration {

    /**
     * @return the number of transition workers for this execution
     */
    Integer getTransitionWorkers();

    /**
     * @return the number of worker threads handling {@code <parser, state>} pairs
     */
    Integer getParserWorkers();

    /**
     * @return the number of worker threads handling scheduled tasks
     */
    Integer getTaskWorkers();

    /**
     * @return a sequential list of address transformers that will work on all URLs during
     * transitions
     */
    List<AddressTransformer> getAddressTransformers();

    /**
     * @return the root of the output directory designated upon execution
     */
    String getOutputDirectory();

    /**
     * @return a set of starting points for the crawler
     */
    Set<String> getStartStates();

}
