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

package com.agileapes.webexport.exec.impl;

import com.agileapes.webexport.exec.Configuration;
import com.agileapes.webexport.net.impl.DefaultPageDownloader;
import com.agileapes.webexport.url.transform.AddressTransformer;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 20:10)
 */
public class ImmutableConfiguration implements Configuration {

    private static final int DEFAULT_TASK_WORKERS = 4;
    private static final int DEFAULT_PARSER_WORKERS = 10;
    private static final int DEFAULT_TRANSITION_WORKERS = 4;
    private final Integer transitionWorkers;
    private final Integer parserWorkers;
    private final Integer taskWorkers;
    private final List<AddressTransformer> addressTransformers;
    private final String outputDirectory;
    private final Set<String> startingStates;

    private ImmutableConfiguration(String outputDirectory) {
        this(outputDirectory, Collections.<String>emptySet());
    }

    private ImmutableConfiguration(String outputDirectory, Set<String> startingStates) {
        this(outputDirectory, startingStates, Collections.<AddressTransformer>emptyList());
    }

    private ImmutableConfiguration(String outputDirectory, Set<String> startingStates, List<AddressTransformer> addressTransformers) {
        this(outputDirectory, startingStates, addressTransformers, DEFAULT_TRANSITION_WORKERS);
    }

    private ImmutableConfiguration(String outputDirectory, Set<String> startingStates, List<AddressTransformer> addressTransformers, Integer transitionWorkers) {
        this(outputDirectory, startingStates, addressTransformers, transitionWorkers, DEFAULT_PARSER_WORKERS);
    }

    private ImmutableConfiguration(String outputDirectory, Set<String> startingStates, List<AddressTransformer> addressTransformers, Integer transitionWorkers, Integer parserWorkers) {
        this(outputDirectory, startingStates, addressTransformers, transitionWorkers, parserWorkers, DEFAULT_TASK_WORKERS);
    }

    private ImmutableConfiguration(String outputDirectory, Set<String> startingStates, List<AddressTransformer> addressTransformers, Integer transitionWorkers, Integer parserWorkers, Integer taskWorkers) {
        this.transitionWorkers = transitionWorkers;
        this.parserWorkers = parserWorkers;
        this.taskWorkers = taskWorkers;
        this.addressTransformers = addressTransformers;
        this.outputDirectory = outputDirectory;
        this.startingStates = startingStates;
    }

    @Override
    public Integer getTransitionWorkers() {
        return transitionWorkers;
    }

    @Override
    public Integer getParserWorkers() {
        return parserWorkers;
    }

    @Override
    public Integer getTaskWorkers() {
        return taskWorkers;
    }

    @Override
    public List<AddressTransformer> getAddressTransformers() {
        return Collections.unmodifiableList(addressTransformers);
    }

    @Override
    public Set<String> getStartStates() {
        return Collections.unmodifiableSet(startingStates);
    }

    @Override
    public String getUserAgent() {
        return DefaultPageDownloader.DEFAULT_USER_AGENT;
    }

    @Override
    public String getOutputDirectory() {
        return outputDirectory;
    }

}
