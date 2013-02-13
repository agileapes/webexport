package com.agileapes.webexport.url.rule.impl;

import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.rule.RuleRequirement;
import com.agileapes.webexport.url.state.State;

/**
 * This class encapsulates the concept of compound rules.
 * Compound rules are rules that cannot be assessed simply by the evaluation of a single
 * property of the current situation (see {@link Rule}).
 *
 * todo Should be implemented
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 16:36)
 */
public class CompoundRule implements Rule {

    /**
     * @param expression    the expression that will determine the body of the rule
     */
    public CompoundRule(String expression) {
    }

    @Override
    public boolean matches(State start, State origin, State target) {
        return false;
    }

    @Override
    public RuleRequirement getRequirement() {
        return RuleRequirement.ADDRESS;
    }

}
