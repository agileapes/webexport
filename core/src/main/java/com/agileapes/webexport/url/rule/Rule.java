package com.agileapes.webexport.url.rule;

import com.agileapes.webexport.url.state.State;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:48)
 */
public interface Rule {

    public static final Rule ALL = new Rule() {
        @Override
        public boolean matches(State start, State origin, State state) {
            return false;
        }
    };

    boolean matches(State start, State origin, State state);

}
