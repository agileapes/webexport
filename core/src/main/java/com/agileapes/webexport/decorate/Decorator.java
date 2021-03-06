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

package com.agileapes.webexport.decorate;

import com.agileapes.webexport.event.AfterParseEvent;
import com.agileapes.webexport.event.BeforeShutdownEvent;
import com.agileapes.webexport.event.CrawlerEvent;
import com.agileapes.webexport.model.PageModel;
import com.agileapes.webexport.model.PageModelSelector;
import org.springframework.context.ApplicationListener;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class denotes the concept of Decorator as described in the white paper.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:09)
 */
public abstract class Decorator implements ApplicationListener<CrawlerEvent> {

    private final PageModelSelector selector;

    protected Decorator(PageModelSelector selector) {
        this.selector = selector;
    }

    @Override
    public void onApplicationEvent(CrawlerEvent event) {
        if (event instanceof AfterParseEvent) {
            final PageModel model = ((AfterParseEvent) event).getModel();
            if (getSelector().matches(model)) {
                decorate(model);
            }
        } else if (event instanceof BeforeShutdownEvent) {
            final Collection<PageModel> models = ((BeforeShutdownEvent) event).getModels();
            final HashSet<PageModel> set = new HashSet<PageModel>();
            for (PageModel model : models) {
                if (getSelector().matches(model)) {
                    set.add(model);
                }
            }
            decorate(set);
        }
    }

    public PageModelSelector getSelector() {
        return selector;
    }

    /**
     * This method is called when a page has just been parsed by a parser
     * @param model    the model representing the outcome of the parser
     */
    public void decorate(PageModel model) {
        //this method is a stub and will be overridden if necessary
    }

    /**
     * This method is called when the process of parsing pages is completed
     * @param models    the models representing the outcome of the parser
     */
    public void decorate(Collection<PageModel> models) {
        //this method is a stub and will be overridden if necessary
    }

}
