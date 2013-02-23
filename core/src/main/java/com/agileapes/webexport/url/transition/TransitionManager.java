package com.agileapes.webexport.url.transition;

import com.agileapes.webexport.concurrent.WorkerPreparator;
import com.agileapes.webexport.concurrent.impl.AbstractManager;
import com.agileapes.webexport.url.state.UrlState;
import com.agileapes.webexport.url.state.UrlStateContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The transition manager is an entity initialized from the outside
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 12:36)
 */
public class TransitionManager extends AbstractManager<TransitionInspector> {

    @Autowired
    private UrlStateContext stateContext;

    public TransitionManager(int workers) {
        super(workers);
    }

    @Override
    protected TransitionInspector initializeWorker() {
        return new TransitionInspector(this);
    }

    @Override
    protected void execute() {
        //we first ask the state context to give us an unhandled state
        final UrlState state = stateContext.next();
        //if no such state exists we happily pretend nothing has happened
        if (state == null) {
            return;
        }
        //otherwise we assign this state to a worker thread and start it
        //noinspection unchecked
        start(new WorkerPreparator<TransitionInspector>() {
            @Override
            public void prepare(TransitionInspector worker) {
                worker.setState(state);
            }
        });
    }

}
