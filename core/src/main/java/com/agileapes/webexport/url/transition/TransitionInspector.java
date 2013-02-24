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

import com.agileapes.webexport.concurrent.Worker;
import com.agileapes.webexport.net.PageDownloader;
import com.agileapes.webexport.net.PageDownloaderFactory;
import com.agileapes.webexport.net.impl.AutomatedRobotsController;
import com.agileapes.webexport.net.impl.DefaultPageDownloaderFactory;
import com.agileapes.webexport.parse.Parser;
import com.agileapes.webexport.parse.impl.SimpleParser;
import com.agileapes.webexport.tools.CollectionDSL;
import com.agileapes.webexport.url.redirect.Redirect;
import com.agileapes.webexport.url.redirect.RedirectContext;
import com.agileapes.webexport.url.redirect.impl.DefaultRedirectContext;
import com.agileapes.webexport.url.redirect.impl.ImmutableRedirect;
import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.rule.RuleRequirement;
import com.agileapes.webexport.url.state.UrlState;
import com.agileapes.webexport.url.state.impl.ActiveUrlState;
import com.agileapes.webexport.url.state.impl.PrefetchUrlState;
import com.agileapes.webexport.url.transition.impl.DefaultTransitionContext;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The transition inspector will inspect a given situation and decide on the next move
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/21, 18:12)
 */
public class TransitionInspector extends Worker {

    private static final String MAIL_TO_PREFIX = "mailto:";
    private final TransitionManager manager;
    private UrlState target;
    private UrlState origin;
    private UrlState start;
    private PageDownloaderFactory downloaderFactory;
    private RedirectContext redirectContext;

    protected TransitionInspector(TransitionManager manager, String name) {
        super(manager, name);
        this.manager = manager;
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

    public void setRedirectContext(RedirectContext redirectContext) {
        this.redirectContext = redirectContext;
    }

    public void setDownloaderFactory(PageDownloaderFactory downloaderFactory) {
        this.downloaderFactory = downloaderFactory;
    }

    @Override
    public synchronized void initialize() {
        start = null;
        origin = null;
        target = null;
        downloaderFactory = null;
    }

    @Override
    public void perform() {
        final Logger logger = Logger.getLogger("com.agileapes.webexport.workers." + getId());
        PrefetchUrlState originalTarget = (PrefetchUrlState) target;
        logger.info("Evaluating: " + target);
        final Set<Redirect> redirects = redirectContext.getRedirects();
        final Set<Parser> parsers = new HashSet<Parser>();
        final RuleRequirement requirement = RuleRequirement.getRequirement(CollectionDSL.map(redirects, new CollectionDSL.Mapper<Redirect, RuleRequirement>() {
            @Override
            public RuleRequirement map(Redirect input) {
                return input.getRule().getRequirement();
            }
        }));
        final PageDownloader downloader;
        try {
            downloader = downloaderFactory.getDownloader(target.getAddress());
        } catch (MalformedURLException e) {
            logger.warn("Dropping malformed URL: " + target.getAddress());
            return;
        }
        final HashMap<String, String> headers = new HashMap<String, String>();
        if (RuleRequirement.HEADERS.getLevel() <= requirement.getLevel()) {
            if (!exchangeHeaders(originalTarget, headers, downloader, logger)) {
                return;
            }
        }
        if (RuleRequirement.CONTENT.getLevel() <= requirement.getLevel()) {
            if (!downloadContent(originalTarget, headers, downloader, logger)) {
                return;
            }
        }
        for (Redirect redirect : redirects) {
            if (redirect.getRule().matches(start, origin, target)) {
                final Parser parser = redirect.getParser();
                //if a drop command has been issued in the matching rules,
                //we will drop this transition no matter what
                if (Parser.NULL.equals(parser)) {
                    logger.info("Dropping URL by design: " + target.getAddress());
                    return;
                }
                parsers.add(parser);
            }
        }
        if (parsers.isEmpty()) {
            logger.info("URL out of context: " + target.getAddress());
            return;
        }
        //we exchange headers and open the connection if not already done.
        if (RuleRequirement.HEADERS.getLevel() > requirement.getLevel()) {
            if (!exchangeHeaders(originalTarget, headers, downloader, logger)) {
                return;
            }
        }
        //we download the content (if not already done)
        if (RuleRequirement.CONTENT.getLevel() > requirement.getLevel()) {
            if (!downloadContent(originalTarget, headers, downloader, logger)) {
                return;
            }
        }
        logger.info("Downloaded " + target.getContent().getBytes().length + " byte(s) from " + target.getAddress());
        //now that we have the content, it is time to dig out new links
        final Matcher matcher = Pattern.compile("<a\\s+[^>]*href=('|\")(.*?)\\1", Pattern.DOTALL | Pattern.MULTILINE | Pattern.CASE_INSENSITIVE).matcher(target.getContent());
        final List<String> links = new ArrayList<String>();
        while (matcher.find()) {
            links.add(matcher.group(2));
        }
        logger.info("Found " + links.size() + " link(s) in " + target);
        for (String link : links) {
            if (link.startsWith(MAIL_TO_PREFIX)) {
                continue;
            }
            if (link.contains("#")) {
                link = link.substring(0, link.indexOf("#"));
            }
            if (!link.contains("://")) {
                link =  target.getProtocol() + "://" + (target.getDomain() + "/" + target.getDirectory() + "/" + link).replaceAll("//+", "/");
            }
            try {
                manager.getTransitionContext().add(new PrefetchUrlState(target, link, target.getDepth() + 1));
            } catch (MalformedURLException ignored) {
                logger.warn("Ignoring malformed URL: " + link);
            }
        }
        //we now have to schedule parse actions and leave it at that
        for (Parser parser : parsers) {
            logger.info("New pair <" + target.getAddress() + "," + parser.getClass().getName() + "> assigned");
        }
    }

    private boolean exchangeHeaders(PrefetchUrlState originalTarget, HashMap<String, String> headers, PageDownloader downloader, Logger logger) {
        try {
            downloader.connect();
            final Map<String,List<String>> fields = downloader.getConnection().getHeaderFields();
            for (String header : fields.keySet()) {
                headers.put(header, CollectionDSL.collect(fields.get(header), new CollectionDSL.Collector<String, String>() {

                    private StringBuilder builder = new StringBuilder();

                    @Override
                    public void handle(String input) {
                        builder.append(input).append(";");
                    }

                    @Override
                    public String collect() {
                        String string = builder.toString();
                        if (!string.isEmpty()) {
                            string = string.substring(0, string.length() - 1);
                        }
                        return string;
                    }
                }));
            }
            target = new ActiveUrlState(originalTarget, new Date().getTime(), "", headers);
        } catch (Throwable e) {
            logger.error("Error occurred in: " + originalTarget.getAddress(), e);
            return false;
        }
        return true;
    }

    private boolean downloadContent(PrefetchUrlState originalTarget, HashMap<String, String> headers, PageDownloader downloader, Logger logger) {
        try {
            final StringWriter writer = new StringWriter();
            downloader.download(writer);
            target = new ActiveUrlState(originalTarget, target.getTimestamp(), writer.toString(), headers);
        } catch (IllegalAccessError e) {
            logger.error("Access denied to agent at " + originalTarget.getAddress());
            return false;
        } catch (FileNotFoundException e) {
            logger.error("Error 404: " + originalTarget.getAddress());
            return false;
        } catch (Throwable e) {
            logger.error("Failed to download content: " + originalTarget.getAddress(), e);
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        final TransitionManager manager = new TransitionManager(15);
        manager.setDownloaderFactory(new DefaultPageDownloaderFactory(new AutomatedRobotsController(), null, null));
        final DefaultRedirectContext redirectContext = new DefaultRedirectContext();
        redirectContext.register(new ImmutableRedirect(new Rule() {
            @Override
            public boolean matches(UrlState start, UrlState origin, UrlState target) {
                final String[] extensions = {"bz2", "gz", "zip", "rar", "tar", "pkz", "bz", "7z"};
                if (!origin.getDomain().endsWith(target.getDomain())) {
                    return false;
                }
                if (target.getDirectory().matches("/mirrors($|/.*)")) {
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
        manager.setRedirectContext(redirectContext);
        final DefaultTransitionContext transitionContext = new DefaultTransitionContext();
        transitionContext.add(new PrefetchUrlState(null, "http://www.kernel.org", 0));
        manager.setTransitionContext(transitionContext);
        new Thread(manager).start();
    }

}
