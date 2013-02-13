package com.agileapes.webexport.model;

import com.agileapes.webexport.url.state.State;

import java.util.Properties;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:23)
 */
public interface PageModel {

    State getState();

    String getTitle();

    Properties getProperties();

}
