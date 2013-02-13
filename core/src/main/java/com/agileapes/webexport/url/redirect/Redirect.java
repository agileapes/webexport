package com.agileapes.webexport.url.redirect;

import com.agileapes.webexport.parse.Parser;
import com.agileapes.webexport.url.rule.Rule;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:47)
 */
public interface Redirect {

    Rule getRule();

    Parser resolve(String url);

}
