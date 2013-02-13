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
