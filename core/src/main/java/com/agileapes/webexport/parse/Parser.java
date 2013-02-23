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

package com.agileapes.webexport.parse;

import com.agileapes.webexport.model.PageModel;
import com.agileapes.webexport.url.state.UrlState;

/**
 * The Parser is expected to parse the contents of a given page and return a PageModel
 * descriptive of all the properties of that model
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:52)
 */
public interface Parser {

    public static final Parser NULL = new Parser() {
        @Override
        public PageModel parse(UrlState state) {
            return null;
        }
    };

    /**
     * This method is expected to encapsulate the body of the parser.
     * The parser should be able to consume a full document and create a model
     * for that document representative of all the properties that are relevant
     * to the context of the parser/decorator pairs
     * @param state    The state object representing the page and its various aspects.
     *                 As the parser has to work with the contents of the given page, this state
     *                 has to be a descendant of {@link com.agileapes.webexport.url.state.impl.ActiveUrlState}
     * @return the page model representing the current page
     */
    PageModel parse(UrlState state);

}
