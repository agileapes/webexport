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
 * A manager is a concurrent process handler that is in charge of initializing, starting,
 * preparing, and populating worker threads and assigning tasks to them and then taking care
 * of their eventual death.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 13:33)
 */
public interface Manager<W extends Worker> extends Runnable {

    /**
     * This method should be called whenever we expect this manager to shut itself and
     * all its spawned processes down
     */
    void shutdown();

    /**
     * This method is called by a worker within its bounds to signify that
     * the given worker has done its work and can now be assigned new tasks
     * @param worker    the worker
     */
    void done(W worker);

    /**
     * Works just like {@link #done(Worker)} but signals that the assigned task was not
     * successfully performed
     * @param worker    the worker
     */
    void fail(W worker);

    /**
     * This will signal the manager that a worker was interrupted. It is up to the manager to
     * decide whether this was intentional or by mistake
     * @param worker    the worker that was interrupted
     */
    void interrupted(W worker);

}
