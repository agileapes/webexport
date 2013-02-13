package com.agileapes.webexport.url.redirect.impl;

import com.agileapes.webexport.url.redirect.Redirect;
import com.agileapes.webexport.url.rule.Rule;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:51)
 */
public abstract class ImmutableRedirect implements Redirect {

    private final Rule rule;

    public ImmutableRedirect(Rule rule) {
        this.rule = rule;
    }

    @Override
    public Rule getRule() {
        return rule;
    }

}
