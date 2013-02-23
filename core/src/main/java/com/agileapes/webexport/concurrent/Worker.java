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
 * A worker is a thread that is responsible for performing a task
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 16:00)
 */
public abstract class Worker extends Thread {

    private final Manager manager;

    protected Worker(Manager manager) {
        this.manager = manager;
    }

    /**
     * This method is called to reset the state of the worker thread so that
     * it can be assigned a new task
     */
    public abstract void initialize();

    /**
     * This method encapsulates the actual performing of the work by this worker
     * It should be designed as a run-once method
     */
    public abstract void perform();

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    manager.fail(this);
                    return;
                }
                try {
                    perform();
                } catch (Throwable e) {
                    manager.fail(this);
                }
                manager.done(this);
            }
        }
    }

}
