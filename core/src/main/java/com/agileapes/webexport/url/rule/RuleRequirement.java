package com.agileapes.webexport.url.rule;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 16:37)
 */
public enum RuleRequirement {

    /**
     * the rule does not need any interaction with the server and can be evaluated
     * by only using the address of the target state
     */
    ADDRESS,
    /**
     * the rule requires HTTP headers to be sent
     */
    HEADERS,
    /**
     * the rule requires the content to be fetched
     */
    CONTENT

}
