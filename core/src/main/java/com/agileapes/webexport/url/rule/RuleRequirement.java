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

import java.util.Collection;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 16:37)
 */
public enum RuleRequirement {

    /**
     * the rule does not need any interaction with the server and can be evaluated
     * by only using the address of the target state
     */
    ADDRESS(1),
    /**
     * the rule requires HTTP headers to be sent
     */
    HEADERS(2),
    /**
     * the rule requires the content to be fetched
     */
    CONTENT(3);

    private final int level;

    private RuleRequirement(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static RuleRequirement getRequirement(Collection<RuleRequirement> requirements) {
        if (requirements == null || requirements.isEmpty()) {
            return ADDRESS;
        }
        RuleRequirement requirement = ADDRESS;
        for (RuleRequirement ruleRequirement : requirements) {
            if (ruleRequirement.getLevel() > requirement.getLevel()) {
                requirement = ruleRequirement;
            }
        }
        return requirement;
    }

}
