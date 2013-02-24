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

package com.agileapes.webexport.url.redirect.impl;

import com.agileapes.webexport.url.redirect.Redirect;
import com.agileapes.webexport.url.rule.Rule;

/**
 * An AbstractRedirect is a redirect that has an immutable rule.
 * The specification of the process leading to the selection of a
 * {@link com.agileapes.webexport.parse.Parser} is left to the developer
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:51)
 */
public abstract class AbstractRedirect implements Redirect {

    private final Rule rule;

    public AbstractRedirect(Rule rule) {
        this.rule = rule;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

}
