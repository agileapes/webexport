package com.agileapes.webexport.url.redirect.impl;

import com.agileapes.webexport.url.redirect.Redirect;
import com.agileapes.webexport.url.rule.Rule;

/**
 * An ImmutableRedirect is a redirect that has an immutable rule.
 * The specification of the process leading to the selection of a
 * {@link com.agileapes.webexport.parse.Parser} is left to the developer
 *
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
