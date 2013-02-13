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
