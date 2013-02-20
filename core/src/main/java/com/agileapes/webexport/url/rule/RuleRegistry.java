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

import com.agileapes.webexport.exception.RuleRegistryError;

/**
 * The rule registry will expose an interface to the developers giving them leave
 * to register new rules with the system
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/20, 17:38)
 */
public interface RuleRegistry {

    /**
     * Will register rules into the system
     * @param name     the assigned rule name
     * @param rule     the actual rule instance (with a definition intrinsic to its implementation)
     * @throws RuleRegistryError
     */
    void register(String name, Rule rule) throws RuleRegistryError;

}
