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

package com.agileapes.webexport.url.transition;

import com.agileapes.webexport.concurrent.Manager;
import com.agileapes.webexport.concurrent.Worker;
import com.agileapes.webexport.parse.Parser;
import com.agileapes.webexport.url.redirect.Redirect;
import com.agileapes.webexport.url.redirect.RedirectContext;
import com.agileapes.webexport.url.state.UrlState;

import java.util.HashSet;
import java.util.Set;

/**
 * The transition inspector will inspect a given situation and decide on the next move
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/21, 18:12)
 */
public class TransitionInspector extends Worker {

    private UrlState target;
    private UrlState origin;
    private UrlState start;
    private RedirectContext redirectContext;

    protected TransitionInspector(Manager manager) {
        super(manager);
    }

    public void setTargetState(UrlState target) {
        this.target = target;
    }

    public void setOriginState(UrlState origin) {
        this.origin = origin;
    }

    public void setStartState(UrlState start) {
        this.start = start;
    }

    @Override
    public synchronized void initialize() {
        start = null;
        origin = null;
        target = null;
    }

    @Override
    public void perform() {
        final Set<Redirect> redirects = redirectContext.getRedirects();
        final Set<Parser> parsers = new HashSet<Parser>();
        for (Redirect redirect : redirects) {
            if (redirect.getRule().matches(start, origin, target)) {
                final Parser parser = redirect.getParser();
                //if a drop command has been issued in the matching rules,
                //we will drop this transition no matter what
                if (Parser.NULL.equals(parser)) {
                    return;
                }
                parsers.add(parser);
            }
        }
        //we now have to schedule parse actions and leave it at that
    }

    public void setRedirectContext(RedirectContext redirectContext) {
        this.redirectContext = redirectContext;
    }
}
