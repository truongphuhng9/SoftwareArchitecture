package com.orm.MyORM.Dialect;

import java.sql.SQLException;

public interface IQueryStatement {
    public void execute(String sql) throws SQLException;
    public void executeUpdate(String sql) throws SQLException;
    public void executeQuery(String sql) throws SQLException;
}
