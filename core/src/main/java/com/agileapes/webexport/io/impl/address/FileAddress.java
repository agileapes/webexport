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

package com.agileapes.webexport.io.impl.address;

import com.agileapes.webexport.io.Address;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 20:46)
 */
@SuppressWarnings("UnusedDeclaration")
public class FileAddress extends Address {

    private final File file;

    public FileAddress(File file) {
        this.file = file;
    }

    public FileAddress(String path) {
        this(new File(path));
    }

    public FileAddress(String parent, String path) {
        this(new File(parent, path));
    }

    public FileAddress(File parent, String path) {
        this(new File(parent, path));
    }

    public FileAddress(FileAddress parent, String path) {
        this(new File(parent.getFile(), path));
    }

    public FileAddress(URI uri) {
        this(new File(uri));
    }

    public FileAddress(URL url) throws URISyntaxException {
        this(new File(url.toURI()));
    }

    public File getFile() {
        return file;
    }

}
