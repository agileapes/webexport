package com.agileapes.webexport.url.transform;

import java.net.MalformedURLException;

/**
 * AddressTransformers are required for transforming the URL to a page to get a better, cleaner,
 * or less clutter (and generally improved) URL that will sufficiently represent the state gleaned
 * from that URL.
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 12:24)
 */
public interface AddressTransformer {

    /**
     * This method is expected to take a raw/transformed URL and return a URL that has undergone
     * a certain transformation implicit to the body of its implementation that correctly identifies
     * the given state.
     * @param url    the original URL that might have already undergone some sort of transformations
     * @return the transformed url
     * @throws MalformedURLException if the URL is not valid
     */
    String transform(String url) throws MalformedURLException;

}
