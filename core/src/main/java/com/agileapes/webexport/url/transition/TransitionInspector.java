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

package com.agileapes.webexport.url.transition;

import com.agileapes.webexport.url.state.UrlState;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * The transition inspector will inspect a given situation and decide on the next move
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/21, 18:12)
 */
public class TransitionInspector extends Thread {

    private UrlState state;
    private final TransitionManager manager;

    public TransitionInspector(TransitionManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        synchronized (this) {
            while (true) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println("Executing ...");
                try {
                    wait(5000);
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println("Done.");
                manager.done(this);
            }
        }
    }

    public void setState(UrlState state) {
        this.state = state;
    }

    private static interface Callback {

        void execute(Worker worker);

    }

    private static final class Worker extends Thread {

        private final long id;
        private final Callback finish;

        private Worker(long id, Callback finish) {
            this.id = id;
            this.finish = finish;
        }

        @Override
        public void run() {
            synchronized (this) {
                while (true) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                    System.out.printf("[%d] Starting to work ...%n", id);
                    try {
                        wait(5000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    System.out.printf("[%d] Done.%n", id);
                    finish.execute(this);
                }
            }
        }

    }

}
