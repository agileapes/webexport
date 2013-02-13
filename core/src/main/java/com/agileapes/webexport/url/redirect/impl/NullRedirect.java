package com.agileapes.webexport.url.redirect.impl;

import com.agileapes.webexport.parse.Parser;
import com.agileapes.webexport.url.rule.Rule;

/**
 * A NullRedirect is a redirect that results in the url being dropped in case the
 * specified rule applies to the URL
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:50)
 */
public final class NullRedirect extends ImmutableRedirect {

    public NullRedirect(Rule rule) {
        super(rule);
    }

    @Override
    public Parser resolve(String url) {
        return Parser.NULL;
    }

}
