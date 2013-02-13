package com.agileapes.webexport.parse;

import com.agileapes.webexport.url.model.PageModel;
import com.agileapes.webexport.url.state.State;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:52)
 */
public interface Parser {

    public static final Parser NULL = new Parser() {
        @Override
        public PageModel parse(State state) {
            return null;
        }
    };

    PageModel parse(State state);

}
