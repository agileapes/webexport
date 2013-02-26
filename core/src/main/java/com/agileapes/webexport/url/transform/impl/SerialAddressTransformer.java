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

package com.agileapes.webexport.url.transform.impl;

import com.agileapes.webexport.url.transform.AddressTransformer;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/26, 21:53)
 */
public class SerialAddressTransformer implements AddressTransformer {

    private List<AddressTransformer> transformers = new ArrayList<AddressTransformer>();

    public void add(AddressTransformer transformer) {
        transformers.add(transformer);
    }

    @Override
    public String transform(String url) throws MalformedURLException {
        for (AddressTransformer transformer : transformers) {
            url = transformer.transform(url);
        }
        return url;
    }

}
