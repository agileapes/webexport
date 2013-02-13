/*
 * Copyright (c) 2013. AgileApes (http://www.agileapes.scom/), and
 * associated organizations.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

package com.agileapes.webexport.io.impl.content;

import com.agileapes.webexport.io.Content;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 20:53)
 */
public class ReaderContent extends Content {

    protected final Reader reader;

    public ReaderContent(Reader reader) {
        this.reader = reader;
    }

    public int read() throws IOException {
        return reader.read();
    }

    public int read(CharBuffer buffer) throws IOException {
        return reader.read(buffer);
    }

    public int read(char[] buffer) throws IOException {
        return reader.read(buffer);
    }

    public int read(char[] buffer, int offset, int length) throws IOException {
        return reader.read(buffer, offset, length);
    }

    public boolean ready() throws IOException {
        return reader.ready();
    }

    public long skip(long length) throws IOException {
        return reader.skip(length);
    }

    public void close() throws IOException {
        reader.close();
    }

}
