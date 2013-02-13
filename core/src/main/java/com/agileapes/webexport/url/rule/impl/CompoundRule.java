package com.agileapes.webexport.url.rule.impl;

import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.state.State;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 16:36)
 */
public class CompoundRule implements Rule {

    public CompoundRule(String expression) {
    }

    @Override
    public boolean matches(State start, State origin, State state) {
        return false;
    }
}
