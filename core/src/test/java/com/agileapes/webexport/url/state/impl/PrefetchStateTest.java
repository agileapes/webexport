package com.agileapes.webexport.url.state.impl;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.net.MalformedURLException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 14:46)
 */
public class PrefetchStateTest {

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testContent() throws Exception {
        new PrefetchUrlState(null, "", 0).getContent();
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testTimestamp() throws Exception {
        new PrefetchUrlState(null, "", 0).getTimestamp();
    }

    @Test
    public void testAddressWithoutProtocol() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "google.com", 0);
        Assert.assertEquals(state.getAddress(), "http://google.com");
        Assert.assertEquals(state.getProtocol(), "http");
        Assert.assertEquals(state.getDomain(), "google.com");
    }

    @Test
    public void testAddressWithUsername() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "smtp://m.m.naseri@gmail.com", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
    }

    @Test
    public void testAddressWithUsernameAndPassword() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "smtp://m.m.naseri:somepass@gmail.com", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
    }

    @Test
    public void testAddressWithUsernameAndPasswordAndPortNumber() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "smtp://m.m.naseri:somepass@gmail.com:465", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
        Assert.assertEquals(state.getPort(), "465");
    }

    @Test
    public void testAddressWithUsernameAndPasswordAndPortNumberAndNoContextPath() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "smtp://m.m.naseri:somepass@gmail.com:465/?", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
        Assert.assertEquals(state.getPort(), "465");
        Assert.assertEquals(state.getContext(), "/");
    }

    @Test
    public void testAddressWithUsernameAndPasswordAndPortNumberAndContextPath() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "smtp://m.m.naseri:somepass@gmail.com:465/this/is/a/test/", 0);
        Assert.assertEquals(state.getProtocol(), "smtp");
        Assert.assertEquals(state.getDomain(), "gmail.com");
        Assert.assertEquals(state.getUsername(), "m.m.naseri");
        Assert.assertEquals(state.getPassword(), "somepass");
        Assert.assertEquals(state.getPort(), (Object) 465);
        Assert.assertEquals(state.getContext(), "/this/is/a/test");
    }

    @Test
    public void testAddressWithQueryString() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "images.google.com/result?q=123&data=hello&&x", 0);
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
        new PrefetchUrlState(null, "google.com?q=1=2", 0);
    }

    @Test
    public void testDirectory() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "images.google.com/search/new/view?testing.html", 1);
        Assert.assertEquals(state.getDirectory(), "/search/new");
        Assert.assertEquals(state.getFilename(), "view");
    }

    @Test
    public void testNoDirectory() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "images.google.com", 1);
        Assert.assertEquals(state.getDirectory(), "/");
        Assert.assertEquals(state.getFilename(), "");
    }

    @Test
    public void testDirectoryForFolder() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "images.google.com/a/b/c/", 1);
        Assert.assertEquals(state.getDirectory(), "/a/b");
        Assert.assertEquals(state.getFilename(), "c");
    }

    @Test
    public void testDirectoryForRootContent() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "images.google.com/a", 1);
        Assert.assertEquals(state.getDirectory(), "/");
        Assert.assertEquals(state.getFilename(), "a");
    }

    @Test
    public void testQueryWithAssignment() throws Exception {
        final PrefetchUrlState state = new PrefetchUrlState(null, "http://git.kernel.org/?p=linux/kernel/git/torvalds/linux.git;a=summary", 1);
        Assert.assertEquals(state.getParameters().size(), 1);
        Assert.assertTrue(state.getParameters().contains("p"));
        Assert.assertEquals(state.getParameter("p"), "linux/kernel/git/torvalds/linux.git;a=summary");
    }

}
