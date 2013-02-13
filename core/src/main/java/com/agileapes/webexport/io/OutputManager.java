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

package com.agileapes.webexport.io;

import java.io.IOException;

/**
 * The output manager is an abstraction over the content of outputting content
 * into a specific address
 *
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 20:42)
 */
public interface OutputManager<A extends Address, C extends Content> {

    /**
     * This method will commit the given content into the specified address
     * @param address    the descriptor denoting the target of this transaction
     * @param content    the content to be committed during this transaction
     * @throws IOException should any errors occur, preventing a correct completion of this
     * transaction
     */
    void commit(A address, C content) throws IOException;

}
