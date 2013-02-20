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

import com.agileapes.webexport.exception.DuplicateRuleError;
import com.agileapes.webexport.exception.RuleRegistryError;
import com.agileapes.webexport.url.rule.Rule;
import com.agileapes.webexport.url.rule.RuleRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/20, 18:10)
 */
public class DefaultRuleRegistry implements RuleRegistry {

    private final Map<String, Rule> rules = new ConcurrentHashMap<String, Rule>();

    @Override
    public void register(String name, Rule rule) throws RuleRegistryError {
        if (rules.containsKey(name)) {
            throw new DuplicateRuleError(name);
        }
        try {
            rules.put(name, rule);
        } catch (Throwable e) {
            throw new RuleRegistryError(e);
        }
    }

    public Map<String, Rule> getRules() {
        return rules;
    }

}
