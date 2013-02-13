package com.agileapes.webexport.url.transform.impl;

/**
 * This is a special case of {@link ParameterAddressTransformer} that is designed to take out
 * variables representing session attributes (e.g. JSESSIONID, or PHPSESSID).
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 13:43)
 */
public class SessionAddressTransformer extends ParameterAddressTransformer {

    public SessionAddressTransformer() {
        super(".*sess(ion|id).*");
    }

}
