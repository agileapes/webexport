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

package com.agileapes.webexport.url.rule.impl;

import com.agileapes.webexport.exception.NoSuchRuleError;
import com.agileapes.webexport.exception.RuleContextError;
import com.agileapes.webexport.exception.RuleRegistryError;
import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.rule.RuleContext;
import com.agileapes.webexport.url.rule.RuleRegistry;

import java.util.Collection;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/20, 18:16)
 */
public class DefaultRuleContext implements RuleContext, RuleRegistry {

    private final DefaultRuleRegistry registry = new DefaultRuleRegistry();

    @Override
    public Collection<Rule> getRules() {
        return registry.getRules().values();
    }

    @Override
    public Set<String> getRuleNames() {
        return registry.getRules().keySet();
    }

    @Override
    public Rule getRule(String name) throws RuleContextError {
        if (!registry.getRules().containsKey(name)) {
            throw new NoSuchRuleError(name);
        }
        return registry.getRules().get(name);
    }

    @Override
    public void register(String name, Rule rule) throws RuleRegistryError {
        registry.register(name, rule);
    }

}
