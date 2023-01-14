package  com.orm.MyORM.Repository;

import com.orm.MyORM.Dialect.DbConnection.IDbConnection;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T, ID> {
	Collection<T> findAll() throws Exception;

	Optional<T> findById(ID id) throws Exception;

	Optional<T> findBy(String... columns);

	boolean insertOne(T data) throws Exception;

	void deleteById(ID id) throws Exception;

	T save(T t) throws Exception;

	Collection<T> executeList(String query) throws Exception;

	T execute(String query) throws Exception;

	void setDbConn(IDbConnection dbConn);
}
