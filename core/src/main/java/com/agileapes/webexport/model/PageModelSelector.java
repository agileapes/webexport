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

package com.agileapes.webexport.model;

/**
 * This interface represents the concept of "for" in decorators; i.e. this interface
 * helps the system select all page models that are relevant to a given decorator
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 16:43)
 */
public interface PageModelSelector {

    /**
     * Decides whether the given model is a suitable match for the decorator
     * @param model    the model to be handed over
     * @return {@code true} if the model matches the requirements
     */
    boolean matches(PageModel model);

}
