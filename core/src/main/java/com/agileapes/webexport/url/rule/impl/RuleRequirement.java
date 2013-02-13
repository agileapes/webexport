package com.agileapes.webexport.url.rule.impl;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 16:37)
 */
public enum RuleRequirement {

    //the rule does not need any interaction with the server
    NONE,
    //the rule requires HTTP headers to be sent
    HEADERS,
    //the rule requires the content to be fetched
    CONTENT

}
