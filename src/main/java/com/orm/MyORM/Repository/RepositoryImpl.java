package com.orm.MyORM.Repository;

import com.orm.MyORM.Annotation.Column;
import com.orm.MyORM.Annotation.Id;
import com.orm.MyORM.Annotation.Table;
import com.orm.MyORM.Dialect.DbConnection.IDbConnection;
import com.orm.MyORM.Query.DeleteQuery;
import com.orm.MyORM.Query.InsertQuery;
import com.orm.MyORM.Query.SelectQuery;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;


public class RepositoryImpl<T, ID> implements Repository<T, ID> {
	protected Class<T> typeParameterClass;
	protected Class<ID> idParameterClass;
	private static IDbConnection dbConn = null;

	public RepositoryImpl(Class<T> typeParameterClass, Class<ID> idParameterClass) {
		this.typeParameterClass = typeParameterClass;
		this.idParameterClass = idParameterClass;
	}
	
	@Override
	public void setDbConn(IDbConnection dbConn) {
		RepositoryImpl.dbConn = dbConn;
	}

	protected T mapResultToEntity(ResultSet result, T t) throws Exception {
		Field[] fields = typeParameterClass.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			String columnName = getColumnName(field);
			setFields(field, result, t, columnName);
		}
		return t;
	}

	private String getColumnName(Field field) {
		Column columnAnnotation = field.getAnnotation(Column.class);
		String columnName = columnAnnotation.value() == null || columnAnnotation.value().length() == 0
				? field.getName()
				: columnAnnotation.value();
		return columnName;
	}

	@Override
	public Collection<T> findAll() throws Exception {
		//DatabaseConnection dbConn = DatabaseConnection.getConnection();
		// Query a SQL statement
		Connection conn = dbConn.getConnection();
		SelectQuery create = new SelectQuery();
		Table tableAnnotation = typeParameterClass.getAnnotation(Table.class);
		String query = create.select().from(tableAnnotation.value()).build();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		// Map ResultSet to Entities
		List<T> result = new ArrayList<>();

		while (resultSet.next()) {
			T t = typeParameterClass.getConstructor().newInstance();
			T mapped = mapResultToEntity(resultSet, t);
			result.add(mapped);
		}
		return result;
	}

	@Override
	public Optional<T> findById(ID id) throws Exception {
		Connection conn = dbConn.getConnection();
		Field[] fields = typeParameterClass.getDeclaredFields();
		Table tableAnnotation = typeParameterClass.getAnnotation(Table.class);
		List<String> columnNames = new ArrayList<>();

		String primaryKeyColumn = null;
		// Get list column names
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				primaryKeyColumn = getColumnName(field);
				columnNames.add(primaryKeyColumn);
			} else if (field.isAnnotationPresent(Column.class)) {
				String columnName = getColumnName(field);
				columnNames.add(columnName);
			}
		}

		if (primaryKeyColumn == null || primaryKeyColumn.length() == 0) {
			throw new IllegalStateException("Primary key in java class is not defined");
		}

		SelectQuery create = new SelectQuery();
		String sql = create.select().from(tableAnnotation.value()).where(String.join(" ",primaryKeyColumn, "= ?")).build();
		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		setPreparedStatement(idParameterClass, preparedStatement, 1, id);
		ResultSet resultSet = preparedStatement.executeQuery();

		Optional<T> result = Optional.empty();

		while (resultSet.next()) {
			T t = typeParameterClass.getConstructor().newInstance();

			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				setFields(fields[i], resultSet, t, columnNames.get(i));
			}
			result = Optional.ofNullable(t);
		}

		return result;
	}

	@Override
	public Optional<T> findBy(String... columns) {
		return Optional.empty();
	}


	@Override
	public boolean insertOne(T data) throws Exception {
		Table tableAnnotation = typeParameterClass.getAnnotation(Table.class);

		Field[] fields = typeParameterClass.getDeclaredFields();
		Connection conn = dbConn.getConnection();
		Statement statement = conn.createStatement();

		InsertQuery insertQuery = new InsertQuery();
		List<String> queryFields = new ArrayList<>();
		List<String> queryValues = new ArrayList<>();

		for (Field field : fields) {
			field.setAccessible(true);
			Column columnAnnotation = field.getAnnotation(Column.class);
			String columnName = columnAnnotation.value() == null || columnAnnotation.value().length() == 0
					? field.getName()
					: columnAnnotation.value();
			Object columnValue = field.get(data);
			Class t = field.getType();
			if ((t == boolean.class && Boolean.FALSE.equals(columnValue)) ||
					(t == char.class && ((Character) columnValue).charValue() == 0) ||
					(t.isPrimitive() && ((Number) columnValue).doubleValue() == 0) ||
					(columnValue == null)) {
				continue;
			}

			queryFields.add(columnName);
			queryValues.add("'" + columnValue + "'");
		}

		String[] tmp = {};
		String tx = insertQuery
				.insert_into(tableAnnotation.value(), queryFields.toArray(tmp))
				.values(queryValues.toArray(tmp))
				.build();

		int affectedRows = statement.executeUpdate(tx);
		return affectedRows != 0;
	}

	@Override
	public void deleteById(ID id) throws Exception {
		Field[] fields = typeParameterClass.getDeclaredFields();
		Connection conn = dbConn.getConnection();
		String primaryKeyColumn = null;
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				primaryKeyColumn = getColumnName(field);
				break;
			}
		}

		if (primaryKeyColumn == null || primaryKeyColumn.length() == 0) {
			throw new IllegalStateException("Primary key in java class is not defined");
		}

		Table tableAnnotation = typeParameterClass.getAnnotation(Table.class);
		DeleteQuery create = new DeleteQuery();
		String sql = create.delete().from(tableAnnotation.value()).where(String.join(" ", primaryKeyColumn, "= ?")).build();
		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		setPreparedStatement(idParameterClass, preparedStatement, 1, id);

		int rowsDeleted = preparedStatement.executeUpdate();
	}

	@Override
	public T save(T t) throws Exception {
		Connection conn = dbConn.getConnection();
		Field[] fields = typeParameterClass.getDeclaredFields();

		List<Field> columns = new ArrayList<>();
		StringJoiner columnsString = new StringJoiner(",");

		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Id.class)) {
				String primaryKeyColumn = getColumnName(field);
				columnsString.add(primaryKeyColumn);
			} else if (field.isAnnotationPresent(Column.class)) {
				Column columnAnnotation = field.getAnnotation(Column.class);
				String columnName = getColumnName(field);
				columnsString.add(columnName);
			}
			columns.add(field);
		}

		StringJoiner preparedStatementValueJoiner = new StringJoiner(",");
		for (int i = 0; i < columns.size(); i++) {
			preparedStatementValueJoiner.add("? ");
		}

		int preparedStatementIndex = 1;

		Table tableAnnotation = typeParameterClass.getAnnotation(Table.class);
		InsertQuery insertQuery = new InsertQuery();
		String sql = insertQuery
				.insert_into(tableAnnotation.value(), columnsString.toString())
				.values(preparedStatementValueJoiner.toString())
				.build();

		PreparedStatement preparedStatement = conn.prepareStatement(sql);

		for (Field field : columns) {
			setPreparedStatement(field.getType(), preparedStatement, preparedStatementIndex++, field.get(t));
//			if (int.class.equals(field.getType()) || Integer.class.equals(field.getType())) {
//				preparedStatement.setInt(preparedStatementIndex++, (Integer) field.get(t));
//			} else if (long.class.equals(field.getType()) || Long.class.equals(field.getType())) {
//				preparedStatement.setLong(preparedStatementIndex++, (Long) field.get(t));
//			} else if (String.class.equals(field.getType())) {
//				preparedStatement.setString(preparedStatementIndex++, (String) field.get(t));
//			} else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
//				preparedStatement.setBoolean(preparedStatementIndex++, (Boolean) field.get(t));
//			} else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
//				preparedStatement.setDouble(preparedStatementIndex++, (Double) field.get(t));
//			} else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
//				preparedStatement.setFloat(preparedStatementIndex++, (Float) field.get(t));
//			} else {
//				throw new IllegalStateException("Unexpected value: " + field.getType());
//			}
		}

		int noOfRowsUpdated = preparedStatement.executeUpdate();
		return t;
	}

	private void setPreparedStatement(Class<?> clss, PreparedStatement preparedStatement, int index, Object value)
			throws Exception {
		if (int.class.equals(clss) || Integer.class.equals(clss)) {
			preparedStatement.setInt(index, (Integer) value);
		} else if (long.class.equals(clss) || Long.class.equals(clss)) {
			preparedStatement.setLong(index, (Long) value);
		} else if (String.class.equals(clss)) {
			preparedStatement.setString(index, (String) value);
		} else if (boolean.class.equals(clss) || Boolean.class.equals(clss)) {
			preparedStatement.setBoolean(index, (Boolean) value);
		} else if (double.class.equals(clss) || Double.class.equals(clss)) {
			preparedStatement.setDouble(index, (Double) value);
		} else if (float.class.equals(clss) || Float.class.equals(clss)) {
			preparedStatement.setFloat(index, (Float) value);
		} else {
			throw new IllegalStateException("Unexpected value: " + clss);
		}
	}



	@Override
	public Collection<T> executeList(String query) throws Exception {
		//DatabaseConnection dbConn = DatabaseConnection.getConnection();
		Connection conn = dbConn.getConnection();
		Field[] fields = typeParameterClass.getDeclaredFields();
		///String query = "select * from public." + typeParameterClass.getSimpleName() + " where age > 20";
		//String query = dbConn.findAllQueryString(typeParameterClass.getSimpleName());
		//Statement statement = dbConn.createStatement();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		List<T> result = new ArrayList<>();

		while (resultSet.next()) {
			T t = typeParameterClass.getConstructor().newInstance();
			for (Field field : fields) {
				field.setAccessible(true);

				Column columnAnnotation = field.getAnnotation(Column.class);
				String columnName = columnAnnotation.value() == null || columnAnnotation.value().length() == 0
						? field.getName()
						: columnAnnotation.value();

				setFields(field, resultSet, t, columnName);
			}
			result.add(t);
		}
		return result;
	}

	@Override
	public T execute(String query) throws Exception {
		//DatabaseConnection dbConn = DatabaseConnection.getConnection();
		Field[] fields = typeParameterClass.getDeclaredFields();
		///String query = "select * from public." + typeParameterClass.getSimpleName() + " where age > 20";
		//String query = dbConn.findAllQueryString(typeParameterClass.getSimpleName());
		//Statement statement = dbConn.createStatement();
		Connection conn = dbConn.getConnection();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(query);

		List<T> result = new ArrayList<>();

		while (resultSet.next()) {
			T t = typeParameterClass.getConstructor().newInstance();
			for (Field field : fields) {
				field.setAccessible(true);

				Column columnAnnotation = field.getAnnotation(Column.class);
				String columnName = columnAnnotation.value() == null || columnAnnotation.value().length() == 0
						? field.getName()
						: columnAnnotation.value();

				setFields(field, resultSet, t, columnName);
			}
			result.add(t);
		}
		return result.get(0);
	}
	
	private void setFields(Field field, ResultSet resultSet, T t, String columnName) throws Exception {
		if (int.class.equals(field.getType()) || Integer.class.equals(field.getType())) {
			try {
				field.set(t, resultSet.getInt(columnName));
			} catch (Exception e) {
				//System.out.println("Result does not contain column: " + columnName);
			}
		} else if (long.class.equals(field.getType()) || Long.class.equals(field.getType())) {
			field.set(t, resultSet.getLong(columnName));
		} else if (String.class.equals(field.getType())) {
			try {
				field.set(t, resultSet.getString(columnName));
			} catch (Exception e) {
				//System.out.println("Result does not contain column: " + columnName);
			}
		} else if (boolean.class.equals(field.getType()) || Boolean.class.equals(field.getType())) {
			try {
				field.set(t, resultSet.getBoolean(columnName));
			} catch (Exception e) {
			}
		} else if (double.class.equals(field.getType()) || Double.class.equals(field.getType())) {
			try {
				field.set(t, resultSet.getDouble(columnName));
			} catch (Exception e) {
			}
		} else if (float.class.equals(field.getType()) || Float.class.equals(field.getType())) {
			try {
				field.set(t, resultSet.getFloat(columnName));
			} catch (Exception e) {
			}
			
		} else {
			throw new IllegalStateException("Unexpected value: " + field.getType());
		}
	}
}
