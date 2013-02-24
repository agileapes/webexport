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

package com.agileapes.webexport.url.rule;

import com.agileapes.webexport.url.state.UrlState;

/**
 * A rule is a matches that will decide if a triple of {@code <start, origin, state>}
 * holds true for a certain condition explicitly defined in the body of that rule.
 * The "situation" can be described as the decision for going from the <em>origin</em>
 * state (the state we are currently in, which in itself is the result of zero or more
 * transitions from the given <em>start</em> state) to the <em>target</em> state, which
 * as of yet has not been visited.
 *
 * The rule might require more than just the address of the target state to be correctly
 * assessed. This is decided by {@link #getRequirement()} which returns a {@link RuleRequirement}
 * of one of the three requirement levels.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:48)
 */
public interface Rule {

    /**
     * This is a rule that applies to all states
     */
    public static final Rule ALL = new Rule() {
        @Override
        public boolean matches(UrlState start, UrlState origin, UrlState target) {
            return true;
        }

        @Override
        public RuleRequirement getRequirement() {
            return RuleRequirement.ADDRESS;
        }
    };

    /**
     * This method should decide whether the condition specified in its body holds for the
     * given triple
     * @param start     the start state for the state machine that has finally lead to this situation
     * @param origin    the state that we are currently in. this might actually be the start state.
     * @param target     the state that we are evaluating as the target of the given transition. Based
     *                  on the results of this method this transition might or might not be taken.
     * @return {@code true} if the given rule applies to the current situation
     */
    boolean matches(UrlState start, UrlState origin, UrlState target);

    /**
     * @return the requirements for correct evaluation of this rule
     */
    RuleRequirement getRequirement();

}
