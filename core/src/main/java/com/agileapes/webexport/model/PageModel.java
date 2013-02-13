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

import com.agileapes.webexport.url.state.State;

/**
 * This interface holds the description of a page as defined by a given parser
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:23)
 */
public interface PageModel {

    /**
     * @return the state that has resulted in the creation of the page model
     */
    State getState();

    /**
     * Sets a property of the page model
     * @param name     the name of the property
     * @param value    the actual value of the property
     */
    void setProperty(String name, Object value);

    /**
     * @param name    the name of the property
     * @return the value of the given property
     */
    Object getProperty(String name);

}
