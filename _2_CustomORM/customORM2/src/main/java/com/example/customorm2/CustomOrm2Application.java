package com.example.customorm2;

import com.example.customorm2.entitties.User;
import com.example.customorm2.orm.Connector;
import com.example.customorm2.orm.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;

@SpringBootApplication
public class CustomOrm2Application {

	public static void main(String[] args) {
	//	SpringApplication.run(CustomOrm2Application.class, args);

		Connection connection = Connector.getConnection();

		String username = System.getenv("username");
		String password = System.getenv("password");
		String dbName = "orm";

		try {
			Connector.createConnection(username, password, dbName);

			EntityManager<User> entityManager = new EntityManager<>(connection);

			User user = new User("Pesho", "123", 25, new Date());

			entityManager.persist(user);

		} catch (SQLException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
