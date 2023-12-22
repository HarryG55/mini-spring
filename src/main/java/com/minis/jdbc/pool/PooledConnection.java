package com.minis.jdbc.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PooledConnection implements Connection {

    private Connection connection;
    private boolean active;

    public PooledConnection() {
    }

    public PooledConnection(Connection connection, boolean active) {
        this.connection = connection;
        this.active = active;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        connection.prepareStatement(sql);
    }

    public void close() {
        this.active = false;
    }

}
