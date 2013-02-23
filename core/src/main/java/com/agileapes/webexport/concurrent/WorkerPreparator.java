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

package com.agileapes.webexport.concurrent;

/**
 * A worker preparator is in essence a callback that will be called whenever we need to
 * prepare a given worker thread for proceeding with a certain task.
 *
 * @see com.agileapes.webexport.concurrent.impl.AbstractManager
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 15:14)
 */
public interface WorkerPreparator<W extends Worker> {

    /**
     * This method will be called once for each worker prior to its execution
     * @param worker    the worker thread
     */
    void prepare(W worker);

}
