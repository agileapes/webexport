package com.agileapes.webexport.url.state.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:46)
 */
public class PrefetchStateTest {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testContent() throws Exception {
        new PrefetchState("", 0).getContent();
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testTimestamp() throws Exception {
        new PrefetchState("", 0).getTimestamp();
    }

    @Test
    public void testAddressWithoutProtocol() throws Exception {
        final PrefetchState state = new PrefetchState("google.com", 0);
        Assert.assertEquals(state.getAddress(), "http://google.com");
        Assert.assertEquals(state.getProtocol(), "http");
        Assert.assertEquals(state.getDomain(), "google.com");
    }

    @Test
    public void testAddressWithUsername() throws Exception {
        final PrefetchState state = new PrefetchState("smtp://m.m.naseri@gmail.com", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
    }

    @Test
    public void testAddressWithUsernameAndPassword() throws Exception {
        final PrefetchState state = new PrefetchState("smtp://m.m.naseri:somepass@gmail.com", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
    }

    @Test
    public void testAddressWithUsernameAndPasswordAndPortNumber() throws Exception {
        final PrefetchState state = new PrefetchState("smtp://m.m.naseri:somepass@gmail.com:465", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
        Assert.assertEquals(state.getPort(), "465");
    }

    @Test
    public void testAddressWithUsernameAndPasswordAndPortNumberAndNoContextPath() throws Exception {
        final PrefetchState state = new PrefetchState("smtp://m.m.naseri:somepass@gmail.com:465/?", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
        Assert.assertEquals(state.getPort(), "465");
        Assert.assertEquals(state.getContext(), "/");
    }

    @Test
    public void testAddressWithUsernameAndPasswordAndPortNumberAndContextPath() throws Exception {
        final PrefetchState state = new PrefetchState("smtp://m.m.naseri:somepass@gmail.com:465/this/is/a/test", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
        Assert.assertEquals(state.getPort(), "465");
        Assert.assertEquals(state.getContext(), "/this/is/a/test");
    }

    @Test
    public void testAddressWithQueryString() throws Exception {
        final PrefetchState state = new PrefetchState("images.google.com/result?q=123&data=hello&&x", 0);
        Assert.assertEquals(state.getProtocol(), "http");
        Assert.assertEquals(state.getDomain(), "images.google.com");
        Assert.assertEquals(state.getContext(), "/result");
        Assert.assertEquals(state.getParameters().size(), 4);
        Assert.assertTrue(state.getParameters().contains("q"));
        Assert.assertTrue(state.getParameters().contains("data"));
        Assert.assertTrue(state.getParameters().contains("x"));
        Assert.assertEquals(state.getParameter("q"), "123");
        Assert.assertEquals(state.getParameter("data"), "hello");
        Assert.assertEquals(state.getParameter(""), "");
        Assert.assertEquals(state.getParameter("x"), "");
    }

    @Test(expectedExceptions = MalformedURLException.class)
    public void testMalformedKeyValue() throws Exception {
        new PrefetchState("google.com?q=1=2", 0);
    }
}
