package  com.orm.MyORM.Dialect.DbConnection;

import  com.orm.MyORM.Dialect.DbConnection.IDbConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

public class PostgreSqlConnection implements IDbConnection {

    private static PostgreSqlConnection instance = null;
    private Connection conn = null;
    private String prefix;

    private PostgreSqlConnection() {
        this.prefix = "jdbc:postgresql://";
    }

    public static PostgreSqlConnection getInstance(){
        if(instance==null){
            instance=new PostgreSqlConnection();
        }
        return instance;
    }

    @Override
    public void connect(String host, int port, String dbName, String user, String password) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            disconnect();
        }
        String url = this.prefix + host + ":" + Integer.toString(port) + "/" + dbName;
        this.conn = DriverManager.getConnection(url, user, password);
    }

    @Override
    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    @Override
    public Connection getConnection() {
        if (conn != null) {
            return this.conn;
        }
        return null;
    }
}
