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

import com.agileapes.webexport.concurrent.impl.AbstractActionManager;
import com.agileapes.webexport.concurrent.impl.AbstractActionWorker;
import com.agileapes.webexport.concurrent.impl.DefaultActionQueue;
import com.agileapes.webexport.net.impl.AutomatedRobotsController;
import com.agileapes.webexport.net.impl.DefaultPageDownloaderFactory;
import com.agileapes.webexport.parse.impl.SimpleParser;
import com.agileapes.webexport.url.redirect.impl.DefaultRedirectContext;
import com.agileapes.webexport.url.redirect.impl.ImmutableRedirect;
import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.rule.RuleRequirement;
import com.agileapes.webexport.url.state.UrlState;
import com.agileapes.webexport.url.state.impl.PrefetchUrlState;
import com.agileapes.webexport.url.transform.AddressTransformer;
import com.agileapes.webexport.url.transform.impl.AjaxContentTransformer;
import com.agileapes.webexport.url.transform.impl.SerialAddressTransformer;
import com.agileapes.webexport.url.transform.impl.SessionAddressTransformer;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/26, 14:47)
 */
public class TransitionManager extends AbstractActionManager<TransitionAction> {

    private AddressTransformer addressTransformer;

    @Override
    protected AbstractActionWorker<TransitionAction> newWorker(int index) {
        return new TransitionWorker(this, "transitionWorker." + index);
    }

    public AddressTransformer getAddressTransformer() {
        return addressTransformer;
    }

    public void setAddressTransformer(AddressTransformer addressTransformer) {
        this.addressTransformer = addressTransformer;
    }

    public static void main(String[] args) throws Exception {
        final TransitionManager manager = new TransitionManager();
        manager.setWorkers(15);
        final DefaultPageDownloaderFactory downloaderFactory = new DefaultPageDownloaderFactory(new AutomatedRobotsController(), null, null);
        final DefaultRedirectContext redirectContext = new DefaultRedirectContext();
        redirectContext.register(new ImmutableRedirect(new Rule() {
            @Override
            public boolean matches(UrlState start, UrlState origin, UrlState target) {
                final String[] extensions = {"bz2", "gz", "zip", "rar", "tar", "pkz", "bz", "7z"};
                if (!start.getDomain().equals(target.getDomain())) {
                    return false;
                }
                if (target.getContext().matches("/mirrors($|/.*)")) {
                    return false;
                }
                for (String extension : extensions) {
                    if (target.getFilename().toLowerCase().endsWith("." + extension)) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public RuleRequirement getRequirement() {
                return RuleRequirement.ADDRESS;
            }
        }, new SimpleParser()));
        final DefaultActionQueue<TransitionAction> queue = new DefaultActionQueue<TransitionAction>("transitionQueue");
        queue.schedule(new TransitionAction(new PrefetchUrlState(null, "http://www.kernel.org", 0), redirectContext, downloaderFactory));
        manager.setQueue(queue);
        manager.setAutoShutdown(true);
        final SerialAddressTransformer addressTransformer = new SerialAddressTransformer();
        addressTransformer.add(new AjaxContentTransformer());
        addressTransformer.add(new SessionAddressTransformer());
        manager.setAddressTransformer(addressTransformer);
        manager.afterPropertiesSet();
        new Thread(manager).start();
    }
}
