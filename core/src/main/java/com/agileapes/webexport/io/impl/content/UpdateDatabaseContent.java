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

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 21:23)
 */
public class UpdateDatabaseContent extends ValueDatabaseContent {

    private static final String WHERE_CLAUSE_PREFIX = " WHERE ";
    private final String condition;

    protected UpdateDatabaseContent(String table, String condition) {
        super(table);
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    private String getWhereClause() {
        if (getCondition() == null || getCondition().isEmpty()) {
            return "";
        } else {
            return WHERE_CLAUSE_PREFIX + getCondition();
        }
    }

    @Override
    public String getStatement() {
        if (getValues().isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(getTable()).append(" SET ");
        boolean first = true;
        for (String column : getValues().keySet()) {
            if (!first) {
                builder.append(", ");
            } else {
                first = false;
            }
            builder.append(column).append(" = ").append(getValues().get(column));
        }
        builder.append(getWhereClause());
        builder.append(";");
        return builder.toString();
    }

}
