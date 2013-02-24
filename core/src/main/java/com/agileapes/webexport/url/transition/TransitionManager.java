package com.agileapes.webexport.url.transition;

import com.agileapes.webexport.concurrent.WorkerPreparator;
import com.agileapes.webexport.concurrent.impl.AbstractManager;
import com.agileapes.webexport.net.impl.DefaultPageDownloaderFactory;
import com.agileapes.webexport.url.redirect.RedirectContext;
import com.agileapes.webexport.url.state.UrlState;

/**
 * The transition manager is an entity initialized from the outside
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/23, 12:36)
 */
public class TransitionManager extends AbstractManager<TransitionInspector> {

    private RedirectContext redirectContext;
    private TransitionContext transitionContext;
    private DefaultPageDownloaderFactory downloaderFactory;

    public TransitionManager(int workers) {
        super(workers, true);
    }

    @Override
    protected TransitionInspector newWorker(int index) {
        return new TransitionInspector(this, "Inspector-" + index);
    }

    @Override
    protected void execute() {
        //we first ask the state context to give us an unhandled state
        final UrlState state = transitionContext.next();
        //if no such state exists we happily pretend nothing has happened
        if (state == null) {
            return;
        }
        //otherwise we assign this state to a worker thread and start it
        //noinspection unchecked
        start(new WorkerPreparator<TransitionInspector>() {
            @Override
            public void prepare(TransitionInspector worker) {
                UrlState start = state;
                while (start.getParent() != null) {
                    start = start.getParent();
                }
                UrlState origin = state.getParent() == null ? state : state.getParent();
                worker.setStartState(start);
                worker.setOriginState(origin);
                worker.setTargetState(state);
                worker.setRedirectContext(redirectContext);
                worker.setDownloaderFactory(downloaderFactory);
            }
        });
    }

    public void setRedirectContext(RedirectContext redirectContext) {
        this.redirectContext = redirectContext;
    }

    public void setTransitionContext(TransitionContext transitionContext) {
        this.transitionContext = transitionContext;
    }

    public void setDownloaderFactory(DefaultPageDownloaderFactory downloaderFactory) {
        this.downloaderFactory = downloaderFactory;
    }

    public TransitionContext getTransitionContext() {
        return transitionContext;
    }

    @Override
    protected boolean done() {
        return getTransitionContext().isEmpty();
    }

}
