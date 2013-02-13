package com.agileapes.webexport.url.state;

import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:16)
 */
public interface State {

    String getAddress();

    String getProtocol();

    String getDomain();

    String getContext();

    String getPort();

    String getUsername();

    String getPassword();

    Set<String> getParameters();

    String getParameter(String name);

    Long getTimestamp();

    String getContent();

    Integer getDepth();

}
