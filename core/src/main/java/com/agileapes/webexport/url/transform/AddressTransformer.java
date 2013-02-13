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

package com.agileapes.webexport.url.transform;

import java.net.MalformedURLException;

/**
 * AddressTransformers are required for transforming the URL to a page to get a better, cleaner,
 * or less clutter (and generally improved) URL that will sufficiently represent the state gleaned
 * from that URL.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 12:24)
 */
public interface AddressTransformer {

    /**
     * This method is expected to take a raw/transformed URL and return a URL that has undergone
     * a certain transformation implicit to the body of its implementation that correctly identifies
     * the given state.
     * @param url    the original URL that might have already undergone some sort of transformations
     * @return the transformed url
     * @throws MalformedURLException if the URL is not valid
     */
    String transform(String url) throws MalformedURLException;

}
