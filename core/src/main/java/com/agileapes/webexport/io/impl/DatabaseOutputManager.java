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

package com.agileapes.webexport.io.impl;

import com.agileapes.webexport.io.OutputManager;
import com.agileapes.webexport.io.impl.address.DatabaseAddress;
import com.agileapes.webexport.io.impl.content.DatabaseContent;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/2/13, 21:33)
 */
public class DatabaseOutputManager implements OutputManager<DatabaseAddress, DatabaseContent> {

    private final Set<ConnectionDescriptor> descriptors = new HashSet<ConnectionDescriptor>();

    private Connection getConnection(DatabaseAddress address) throws IOException {
        ConnectionDescriptor descriptor = new ConnectionDescriptor(address.getConnectionString(), address.getUsername(), address.getPassword());
        if (address.isKeepAlive()) {
            for (ConnectionDescriptor connectionDescriptor : descriptors) {
                if (descriptor.equals(connectionDescriptor)) {
                    descriptor = connectionDescriptor;
                }
            }
        }
        if (descriptor.getConnection() != null) {
            return descriptor.getConnection();
        }
        //loading the driver
        try {
            ClassUtils.forName(address.getDriver(), getClass().getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new IOException("Driver not found: " + address.getDriver());
        }
        final Connection connection;
        try {
            if ((address.getUsername() == null || address.getUsername().isEmpty()) &&
                    (address.getPassword() == null || address.getPassword().isEmpty())) {
                connection = DriverManager.getConnection(address.getConnectionString());
            } else {
                connection = DriverManager.getConnection(address.getConnectionString(), address.getUsername(), address.getPassword());
            }
        } catch (SQLException e) {
            throw new IOException("Failed to get a connection to the database", e);
        }
        if (address.isKeepAlive()) {
            descriptors.remove(descriptor);
            descriptors.add(descriptor);
        }
        return connection;
    }

    @Override
    public void commit(DatabaseAddress address, DatabaseContent content) throws IOException {
        final Connection connection = getConnection(address);

        final Statement statement;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new IOException("Failed to create statement", e);
        }
        try {
            statement.executeUpdate(content.getStatement());
        } catch (SQLException e) {
            throw new IOException("Failed to execute given statement", e);
        }
    }

    private static class ConnectionDescriptor {

        private final String connectionString;
        private final String username;
        private final String password;
        private Connection connection = null;

        private ConnectionDescriptor(String connectionString, String username, String password) {
            this.connectionString = connectionString;
            this.username = username;
            this.password = password;
        }

        public String getConnectionString() {
            return connectionString;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ConnectionDescriptor that = (ConnectionDescriptor) o;
            return !(connectionString != null ? !connectionString.equals(that.connectionString) : that.connectionString != null)
                    && !(password != null ? !password.equals(that.password) : that.password != null) &&
                    !(username != null ? !username.equals(that.username) : that.username != null);

        }

        @Override
        public int hashCode() {
            int result = connectionString != null ? connectionString.hashCode() : 0;
            result = 31 * result + (username != null ? username.hashCode() : 0);
            result = 31 * result + (password != null ? password.hashCode() : 0);
            return result;
        }

        public Connection getConnection() {
            return connection;
        }

        public void setConnection(Connection connection) {
            this.connection = connection;
        }

    }

}
