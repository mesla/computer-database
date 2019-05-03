package com.excilys.cdb.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.excilys.cdb.exception.ConnectionDBFailedException;

public abstract class Dao {
	String url;
	String username;
	String password;
	protected Connection connection() throws ConnectionDBFailedException {

		try {

			final Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("config.properties");
			prop.load(input);
			
			this.url = prop.getProperty("db.url");
			this.username = prop.getProperty("db.username");
			this.password = prop.getProperty("db.password");

			Class.forName("com.mysql.cj.jdbc.Driver");
			
			return DriverManager.getConnection(
					url,
					username,
					password);
			
		} catch (ClassNotFoundException | IOException | SQLException e) {
			throw new ConnectionDBFailedException("connexion à la DB échouée");
		}

	}
}
