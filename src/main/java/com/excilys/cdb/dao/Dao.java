package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.excilys.cdb.exception.ConnectionDBFailedException;

public abstract class Dao {
	protected final static String DBACCESS = "jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC&zeroDateTimeBehavior=convertToNull";
	protected final static String DBUSER = "admincdb";
	protected final static String DBPASS = "qwerty1234";
	
	protected Connection connection() throws ConnectionDBFailedException {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			throw new ConnectionDBFailedException("connexion à la DB échouée");
		}
		
		try {
			return DriverManager.getConnection(Dao.DBACCESS, Dao.DBUSER, Dao.DBPASS);
		} catch (SQLException e) {
			throw new ConnectionDBFailedException("connexion à la DB échouée");
		}

	}
}
