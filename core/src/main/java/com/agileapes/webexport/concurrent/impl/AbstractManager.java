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

package com.agileapes.webexport.concurrent.impl;

import com.agileapes.webexport.concurrent.Manager;
import com.agileapes.webexport.concurrent.Worker;
import com.agileapes.webexport.concurrent.WorkerPreparator;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This manager will abstract all the main tasks common to a manager process.
 * The single-run implementation and assignment of tasks is left to be implemented
 * by subclasses.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 13:36)
 */
public abstract class AbstractManager<W extends Worker> implements Manager<W>, InitializingBean, ApplicationContextAware {

    private AtomicBoolean running = new AtomicBoolean(true);
    private final Set<W> idle = new CopyOnWriteArraySet<W>();
    private final Set<W> working = new CopyOnWriteArraySet<W>();
    private final Set<W> interrupted = new CopyOnWriteArraySet<W>();
    private boolean autoShutdown;
    private int workers;
    public static final Logger logger = Logger.getLogger(Manager.class);
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Initializing workers for " + getClass().getSimpleName());
        for (int i = 0; i < workers; i ++) {
            final W worker = newWorker(i);
            idle.add(worker);
            worker.start();
        }
    }

    @Override
    public void shutdown() {
        running = new AtomicBoolean(false);
    }

    public void onStartup() {
        logger.info("Starting up " + getClass().getSimpleName() + " ...");
    }

    public void onBeforeShutdown() {
        logger.info("Going to shutdown " + getClass().getSimpleName() + " ...");
    }

    public void onAfterShutdown() {
        logger.info("Shutdown for " + getClass().getSimpleName() + " completed.");
    }

    @Override
    public void run() {
        onStartup();
        try {
            synchronized (this) {
                //we will continue with the execution as long as a shutdown
                //signal has not been sent
                while (running.get()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        //if we are interrupted we will shutdown safely
                        shutdown();
                        continue;
                    }
                    //in each pass we call the run-once execute method
                    execute();
                }
            }
        } finally {
            onBeforeShutdown();
            //after the execution, we will clean after ourselves.
            //this is essentially the shutdown part.
            while (!working.isEmpty()) {
                //we will wait for all the threads currently working to finish their
                //jobs or otherwise be interrupted
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
            //now all the threads must be idle
            for (W worker : idle) {
                //we send an interrupt signal to each idle thread so that
                //it will finish its business
                interrupted.add(worker);
                worker.interrupt();
                try {
                    //we wait for the worker to acknowledge our interrupt signal
                    worker.join();
                } catch (InterruptedException ignored) {
                }
            }
            onAfterShutdown();
        }
    }

    private void markDone(W worker) {
        working.remove(worker);
        idle.add(worker);
    }

    @Override
    public void done(W worker) {
        if (!working.contains(worker)) {
            throw new IllegalStateException();
        }
        markDone(worker);
        logger.info("Finished evaluating worker: " + worker.getName());
        logger.debug("Auto shutdown is " + (autoShutdown ? "" : "not ") + "enabled");
        if (isAutoShutdown()) {
            logger.debug("Number of workers active: " + working.size());
            logger.debug("Completion criteria check: " + completed());
        }
        if (isAutoShutdown() && working.isEmpty() && completed()) {
            shutdown();
        }
    }

    @Override
    public void fail(W worker) {
        done(worker);
        handleFailure(worker);
    }

    @Override
    public void interrupted(W worker) {
        if (interrupted.contains(worker)) {
            markDone(worker);
            return;
        }
        fail(worker);
    }

    protected boolean completed() {
        return true;
    }

    /**
     * This method is supposed to handle cases in which a worker failed to
     * complete its task
     * @param worker    the worker
     */
    protected void handleFailure(W worker) {
    }

    /**
     * @param index     the index of the new worker
     * @return an initialized instance of the worker thread
     */
    protected abstract W newWorker(int index);

    /**
     * This will run the run-once pass over the tasks
     */
    protected abstract void execute();

    /**
     * This method will start a task by preparing some worker and then notifying it
     * @param preparators    the preparators to prepare the worker and initialize the task
     */
    protected void start(WorkerPreparator<W> ... preparators) {
        while (idle.isEmpty()) {
            //waiting for some worker to become available
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                //in case of interruption, schedule a clean shutdown
                shutdown();
                return;
            }
        }
        //this part is mainly concerned with assigning the task to the worker
        synchronized (this) {
            final W worker = idle.iterator().next();
            //noinspection SynchronizationOnLocalVariableOrMethodParameter
            synchronized (worker) {
                //we first mark the worker thread as in-use
                idle.remove(worker);
                working.add(worker);
                //then we prepare the worker
                worker.initialize();
                for (WorkerPreparator<W> preparator : preparators) {
                    preparator.prepare(worker);
                }
                //and finally we notify it that it should begin its processing
                worker.notify();
            }
        }
    }

    public void setWorkers(int workers) {
        this.workers = workers;
    }

    public void setAutoShutdown(boolean autoShutdown) {
        this.autoShutdown = autoShutdown;
    }

    public boolean isAutoShutdown() {
        return autoShutdown;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
