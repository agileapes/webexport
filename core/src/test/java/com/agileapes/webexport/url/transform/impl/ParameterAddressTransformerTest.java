package com.agileapes.webexport.url.transform.impl;

import com.agileapes.webexport.url.transform.AddressTransformer;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 12:20)
 */
public class ParameterAddressTransformerTest {

    private static final String PREFIX = "http://a.b.c.domain.com/hello/world/?query=hello";
    private static final String SUFFIX = "&test=/1/2/3";
    private static final String EXPECTED = PREFIX + SUFFIX;

    @Test(expectedExceptions = MalformedURLException.class)
    public void testRemovalTransformWithMultipleQuestionMarks() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("");
        final String url = "http://www.google.com/search?a?b";
        transformer.transform(url);
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void testRemovalTransformWithInvalidVariableName() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("");
        final String url = "http://www.google.com/search?a$b=123";
        transformer.transform(url);
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void testRemovalTransformWithInvalidVariableValue() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("");
        final String url = "http://www.google.com/search?abc=1 23";
        transformer.transform(url);
    }

    @Test
    public void testRemovalTransformWithEmptyAssignment() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("session");
        final String url = "http://www.google.com/search?abc=1&&&&&xyz=1";
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, url);
    }

    @Test
    public void testRemovalTransformWithNoQueryString() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("session");
        final String url = "http://www.google.com/search";
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, url);
    }

    @Test
    public void testRemovalTransformWithNoSessionId() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("session");
        final String url = PREFIX + SUFFIX + "&x=1";
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, EXPECTED + "&x=1");
    }

    @Test
    public void testRemovalOfSessionId() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer("session");
        final String url = PREFIX + "&session=SOME%20ID" + SUFFIX;
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, EXPECTED);
    }

    @Test
    public void testRemovalOfJavaSessionId() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer(".*(session|sessid).*");
        final String url = PREFIX + "&JSESSIONID=SOME%20ID" + SUFFIX;
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, EXPECTED);
    }

    @Test
    public void testRemovalOfPhpSessionId() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer(".*(session|sessid).*");
        final String url = PREFIX + "&PHPSESSID=SOME%20ID" + SUFFIX;
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, EXPECTED);
    }

    @Test
    public void testRemovalOfAspSessionId() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer(".*(session|sessid).*");
        final String url = PREFIX + "&ASPSESSIONID=SOME%20ID" + SUFFIX;
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, EXPECTED);
    }

    @Test
    public void testRemovalOfGenericSessionId() throws Exception {
        final AddressTransformer transformer = new ParameterAddressTransformer(".*(session|sessid).*");
        final String url = PREFIX + "&currentSession=SOME%20ID" + SUFFIX;
        final String transformed = transformer.transform(url);
        Assert.assertEquals(transformed, EXPECTED);
    }

}
