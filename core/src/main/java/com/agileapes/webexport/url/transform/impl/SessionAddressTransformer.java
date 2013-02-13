package com.agileapes.webexport.url.transform.impl;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 13:43)
 */
public class SessionAddressTransformer extends ParameterAddressTransformer {

    public SessionAddressTransformer() {
        super(".*sess(ion|id).*");
    }

}
