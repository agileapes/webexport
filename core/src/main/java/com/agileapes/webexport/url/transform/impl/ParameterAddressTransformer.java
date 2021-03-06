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
import java.util.regex.Pattern;

/**
 * This transformer takes in a pattern and removes any GET parameters from the URL whose
 * names' match the given pattern
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 12:24)
 */
public class ParameterAddressTransformer implements AddressTransformer {

    private final Pattern pattern;

    public ParameterAddressTransformer(Pattern pattern) {
        this.pattern = pattern;
    }

    public ParameterAddressTransformer(String pattern) {
        this.pattern = Pattern.compile(pattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
    }

    @Override
    public String transform(String url) throws MalformedURLException {
        if (!url.matches(".*?\\?.*?")) {
            return url;
        }
        String result = url.substring(0, url.indexOf("?") + 1);
        final int questionMarkIndex = url.indexOf("?", result.length());
        if (questionMarkIndex != -1) {
            throw new MalformedURLException(String.format("Unexpected '?' at %d in %s", questionMarkIndex, url));
        }
        for (int i = result.length(); i < url.length(); i ++) {
            if (url.charAt(i) == '&') {
                result += '&';
                i ++;
            }
            //we expect to see something of the form: [^=]*=[^&]*
            String variable = "";
            String value = "";
            boolean hasEquals = false;
            //moving along until we meet '=' or fall off the end
            while (i < url.length() && url.charAt(i) != '=' && url.charAt(i) != '&') {
                if (!String.valueOf(url.charAt(i)).matches("[a-zA-Z/%\\d\\.\\-_~,]")) {
                    throw new MalformedURLException(String.format("Invalid input character at %d in %s", i, url.substring(i)));
                }
                variable += url.charAt(i ++);
            }
            //check to see if a '=' has been encountered
            if (i < url.length() && url.charAt(i) == '=') {
                hasEquals = true;
                i ++;
            }
            //looking ahead to capture the value of the variable
            while (i < url.length() && url.charAt(i) != '&') {
                if (!String.valueOf(url.charAt(i)).matches("[a-zA-Z/%\\d\\.\\-_~,]")) {
                    throw new MalformedURLException(String.format("Invalid input character at %d in %s", i, url.substring(i)));
                }
                value += url.charAt(i ++);
            }
            //if there is more to go, we step back
            if (i < url.length() && url.charAt(i) == '&') {
                i --;
            }
            //assembling it all together
            if (i == url.length() || url.charAt(i + 1) == '&') {
                //if the variable is a session identifier, we don't append it
                if (!pattern.matcher(variable).matches()) {
                    result += variable;
                    if (hasEquals) {
                        result += '=';
                    }
                    result += value;
                } else {
                    if (result.endsWith("&")) {
                        result = result.substring(0, result.length() - 1);
                    }
                }
            } else {
                throw new MalformedURLException(String.format("Invalid input character at %d in %s", i, url.substring(i)));
            }
        }
        return result;
    }

}
