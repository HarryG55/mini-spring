package com.minis.jdbc.pool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PooledDataSource implements DataSource{

    private List<PooledConnection> connections = null;
    private String driveClassName;
    private String url;
    private String username;
    private String password;
    private int initialSize = 2;
    private Properties connectionProperties;

    private void initPool() {
        this.connections = new ArrayList<>(initialSize);
        for (int i = 0; i < initialSize; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, username, password);
                PooledConnection pooledConnection = new PooledConnection(connection, false);
                this.connections.add(pooledConnection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
