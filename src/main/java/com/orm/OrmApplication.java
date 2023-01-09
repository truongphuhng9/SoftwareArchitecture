package com.orm;

import com.orm.MyORM.Dialect.DbConnection.IDbConnection;
import com.orm.MyORM.Dialect.DbConnection.MySqlConnection;
import com.orm.repository.TaskRepository;
import com.orm.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class OrmApplication {

	public static void main(String[] args) throws SQLException {
		IDbConnection db = MySqlConnection.getInstance();
		db.connect("localhost", 3306, "app", "root", "root");

		UserRepository userRepository = new UserRepository();
		userRepository.setDbConn(db);
		TaskRepository taskRepository = new TaskRepository();
		taskRepository.setDbConn(db);

		SpringApplication.run(OrmApplication.class, args);
	}

}
