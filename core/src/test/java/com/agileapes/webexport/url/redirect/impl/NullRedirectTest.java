package com.agileapes.webexport.url.redirect.impl;

import com.agileapes.webexport.parse.Parser;
import com.agileapes.webexport.url.redirect.Redirect;
import com.agileapes.webexport.url.rule.Rule;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 13:44)
 */
public class NullRedirectTest {

    @Test
    public void testNullRedirect() throws Exception {
        final Redirect redirect = new NullRedirect(Rule.ALL);
        Parser parser = redirect.resolve("");
        Assert.assertEquals(parser, Parser.NULL);
    }

}
