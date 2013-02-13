package com.agileapes.webexport.parse;

import com.agileapes.webexport.model.PageModel;
import com.agileapes.webexport.url.state.State;

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
        public PageModel parse(State state) {
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
     *                 has to be a descendant of {@link com.agileapes.webexport.url.state.impl.ActiveState}
     * @return the page model representing the current page
     */
    PageModel parse(State state);

}
