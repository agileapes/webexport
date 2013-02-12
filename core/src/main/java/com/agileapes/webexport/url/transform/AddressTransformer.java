package com.agileapes.webexport.url.transform;

import java.net.MalformedURLException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 12:24)
 */
public interface AddressTransformer {

    String transform(String url) throws MalformedURLException;

}
