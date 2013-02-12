package com.agileapes.webexport.url.transform.impl;

import com.agileapes.webexport.url.transform.AddressTransformer;
import org.springframework.core.Ordered;

import java.net.MalformedURLException;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/12, 12:24)
 */
public class SessionIdTransformer implements AddressTransformer, Ordered {

    @Override
    public String transform(String url) throws MalformedURLException {
        if (!url.matches(".*?\\?.*?")) {
            return url;
        }
        String result = url.substring(0, url.indexOf("?") + 1);
        final int questionMarkIndex = url.indexOf("?", result.length());
        if (questionMarkIndex != -1) {
            throw new MalformedURLException(String.format("Unexpected '?' at %d in %s", questionMarkIndex, url));
        }
        for (int i = result.length(); i < url.length(); i ++) {
            if (url.charAt(i) == '&') {
                result += '&';
                i ++;
            }
            //we expect to see something of the form: [^=]*=[^&]*
            String variable = "";
            String value = "";
            boolean hasEquals = false;
            //moving along until we meet '=' or fall off the end
            while (i < url.length() && url.charAt(i) != '=' && url.charAt(i) != '&') {
                if (!String.valueOf(url.charAt(i)).matches("[a-zA-Z/%\\d\\.\\-_~,]")) {
                    throw new MalformedURLException(String.format("Invalid input character at %d in %s", i, url.substring(i)));
                }
                variable += url.charAt(i ++);
            }
            //check to see if a '=' has been encountered
            if (i < url.length() && url.charAt(i) == '=') {
                hasEquals = true;
                i ++;
            }
            //looking ahead to capture the value of the variable
            while (i < url.length() && url.charAt(i) != '&') {
                if (!String.valueOf(url.charAt(i)).matches("[a-zA-Z/%\\d\\.\\-_~,]")) {
                    throw new MalformedURLException(String.format("Invalid input character at %d in %s", i, url.substring(i)));
                }
                value += url.charAt(i ++);
            }
            //if there is more to go, we step back
            if (i < url.length() && url.charAt(i) == '&') {
                i --;
            }
            //assembling it all together
            if (i == url.length() || url.charAt(i + 1) == '&') {
                //if the variable is a session identifier, we don't append it
                if (!variable.toLowerCase().matches(".*(session|sessid).*")) {
                    result += variable;
                    if (hasEquals) {
                        result += '=';
                    }
                    result += value;
                } else {
                    if (result.endsWith("&")) {
                        result = result.substring(0, result.length() - 1);
                    }
                }
            } else {
                throw new MalformedURLException(String.format("Invalid input character at %d in %s", i, url.substring(i)));
            }
        }
        return result;
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

}
