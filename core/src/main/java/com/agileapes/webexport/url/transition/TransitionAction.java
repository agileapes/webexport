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

import com.agileapes.webexport.concurrent.ActionDescriptor;
import com.agileapes.webexport.net.PageDownloaderFactory;
import com.agileapes.webexport.url.redirect.RedirectContext;
import com.agileapes.webexport.url.state.UrlState;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/26, 14:33)
 */
public class TransitionAction extends ActionDescriptor {

    private final UrlState start;
    private final UrlState origin;
    private final UrlState target;
    private final RedirectContext redirectContext;
    private final PageDownloaderFactory pageDownloaderFactory;

    public TransitionAction(UrlState target, RedirectContext redirectContext, PageDownloaderFactory pageDownloaderFactory) {
        UrlState start = target;
        while (start.getParent() != null) {
            start = start.getParent();
        }
        this.start = start;
        this.origin = target.getParent() == null ? target : target.getParent();
        this.target = target;
        this.redirectContext = redirectContext;
        this.pageDownloaderFactory = pageDownloaderFactory;
    }

    public UrlState getStart() {
        return start;
    }

    public UrlState getOrigin() {
        return origin;
    }

    public UrlState getTarget() {
        return target;
    }

    public RedirectContext getRedirectContext() {
        return redirectContext;
    }

    public PageDownloaderFactory getPageDownloaderFactory() {
        return pageDownloaderFactory;
    }

    @Override
    public String toString() {
        return target.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof TransitionAction && target.equals(((TransitionAction) obj).getTarget());
    }

}
