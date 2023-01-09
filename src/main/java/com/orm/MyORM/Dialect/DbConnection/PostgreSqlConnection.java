package com.orm.MyORM.Dialect.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSqlConnection implements IDbConnection{
    private static PostgreSqlConnection instance = null;
    private Connection conn = null;
    private String prefix;

    private PostgreSqlConnection() {
        this.prefix = "jdbc:postgresql://";
    }

    public static PostgreSqlConnection getInstance() {
        if (instance == null) {
            instance = new PostgreSqlConnection();
        }
        return instance;
    }

    public void connect(String host, int port, String dbName, String user, String password) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            disconnect();
        }
        String url = this.prefix + host + ":" + Integer.toString(port) + "/" + dbName;
        this.conn = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public Connection getConnection() {
        if (conn != null) {
            return this.conn;
        }
        return null;
    }
}

