package  com.orm.MyORM.Dialect.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDbConnection {
    public void connect(String host, int port, String dbName, String user, String password) throws SQLException;
    public void disconnect() throws SQLException;
    public Connection getConnection();
}
