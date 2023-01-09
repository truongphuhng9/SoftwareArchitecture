package com.orm.MyORM.Repository;

import com.orm.MyORM.Dialect.DbConnection.IDbConnection;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {
	public Collection<T> findAll() throws Exception;
	Optional<T> findById(ID id) throws Exception;
	public Collection<T> executeList(String query) throws Exception;
	public T query(String query) throws Exception;
	public boolean insertOne(T data) throws Exception;
	public boolean execute(String tx) throws Exception;
	public void setDbConn(IDbConnection dbConn);
}
