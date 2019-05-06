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
	private static String url;
	private static String username;
	private static String password;
	private static String srcConfig;
	private static String driver;
	
	public static void initConnection(String runContext) throws ConnectionDBFailedException{
			switch(runContext) {
				case "main":
					Dao.srcConfig = "src/main/resources/config.properties";
					Dao.driver = "com.mysql.cj.jdbc.Driver";
					break;
				case "test":
					Dao.srcConfig = "src/test/resources/config.properties";
					Dao.driver = "org.h2.Driver";
					break;
				default:
					throw new ConnectionDBFailedException("Identifiants de connexion à la BDD introuvables");
			}
		}
	
	public static Connection connection() throws ConnectionDBFailedException {
		try {
			final Properties prop = new Properties();
			InputStream input = new FileInputStream(srcConfig);
			prop.load(input);
			
			Dao.url = prop.getProperty("db.url");
			Dao.username = prop.getProperty("db.username");
			Dao.password = prop.getProperty("db.password");

			Class.forName(driver);
						
			return DriverManager.getConnection(
					url,
					username,
					password);

		} catch (ClassNotFoundException | IOException | SQLException e) {
			throw new ConnectionDBFailedException("connexion à la DB échouée");
		}

	}
}
