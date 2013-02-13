package com.agileapes.webexport.url.rule.impl;

import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.state.State;

import java.util.regex.Pattern;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:09)
 */
public class PatternRule implements Rule {

    private final Pattern pattern;

    public PatternRule(Pattern pattern) {
        this.pattern = pattern;
    }

    public PatternRule(String pattern) {
        this.pattern = Pattern.compile("^" + pattern + "$", Pattern.DOTALL);
    }

    @Override
    public boolean matches(State start, State origin, State state) {
        return pattern.matcher(state.getAddress()).matches();
    }
}
