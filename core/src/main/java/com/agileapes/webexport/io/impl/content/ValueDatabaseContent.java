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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 21:12)
 */
public abstract class ValueDatabaseContent extends DatabaseContent {

    private final Map<String, String> values = new HashMap<String, String>();
    private final String table;

    protected ValueDatabaseContent(String table) {
        this.table = table;
    }

    public void set(String column, Object value) {
        if (value == null) {
            values.put(column, null);
            return;
        }
        if (value instanceof CharSequence) {
            CharSequence sequence = (CharSequence) value;
            String result = "";
            for (int i = 0; i < sequence.length(); i ++) {
                if ("\"\\".contains(String.valueOf(sequence.charAt(i)))) {
                    result += "\\" + sequence.charAt(i);
                } else {
                    result += sequence.charAt(i);
                }
            }
            result = "\"" + result + "\"";
            values.put(column, result);
        } else if (value instanceof Character) {
            String result = String.valueOf(value);
            if ("\"\\".contains(result)) {
                result = "\\" + result;
            }
            result = '"' + result + '"';
            values.put(column, result);
        } else {
            values.put(column, value.toString());
        }
    }

    public Map<String, String> getValues() {
        return values;
    }

    public String getTable() {
        return table;
    }

}
