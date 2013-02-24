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

package com.agileapes.webexport.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/24, 16:59)
 */
public abstract class CollectionDSL {

    public static interface Mapper<I, O> {

        O map(I input);

    }

    public static interface Collector<I, O> {

        void handle(I input);

        O collect();

    }

    public static <I, O> Set<O> map(Set<I> input, Mapper<I, O> mapper) {
        final HashSet<O> set = new HashSet<O>();
        for (I item : input) {
            set.add(mapper.map(item));
        }
        return set;
    }

    public static <I, O> O collect(Collection<I> input, Collector<I, O> collector) {
        for (I item : input) {
            collector.handle(item);
        }
        return collector.collect();
    }

}
