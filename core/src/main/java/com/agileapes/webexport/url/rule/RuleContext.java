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

import com.agileapes.webexport.exception.RuleContextError;

import java.util.Collection;
import java.util.Set;

/**
 * The rule context will be able to answer general questions about rules available
 * to the system, and will have an application-wide knowledge of the rules
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/20, 17:36)
 */
public interface RuleContext {

    /**
     * @return all the rules registered in the system
     */
    Collection<Rule> getRules();

    /**
     * @return the names of the rules registered to the system
     */
    Set<String> getRuleNames();

    /**
     *
     * @param name    the name of the desired rule
     * @return the rule specification associated with the given name
     * @throws RuleContextError
     */
    Rule getRule(String name) throws RuleContextError;

}
