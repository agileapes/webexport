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

package com.agileapes.webexport.url.redirect;

import com.agileapes.webexport.parse.Parser;
import com.agileapes.webexport.url.rule.Rule;

/**
 * A "Redirect" is a resolver that would result in a valid parser if the given rule
 * applies to the specific URL at hand
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:47)
 */
public interface Redirect {

    /**
     * @return the rule that should be applied at any given state
     */
    Rule getRule();

    /**
     * @param url    the URL to be examined
     * @return the relevant parser, or {@code null} if none applies
     */
    Parser resolve(String url);

}
