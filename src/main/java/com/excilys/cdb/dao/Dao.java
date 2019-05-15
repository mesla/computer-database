package com.excilys.cdb.dao;

import java.sql.Connection;

import java.sql.SQLException;
import java.util.ResourceBundle;

import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Dao {

	private static  HikariConfig config;
	private	static  HikariDataSource dataSource;
	//C://Users/kutt9/Desktop/maven/computer-database/src/main/resources/
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("config");
		config = new HikariConfig();
		config.setDriverClassName(bundle.getString("driverClassName"));
        config.setJdbcUrl(bundle.getString("jdbcUrl"));
        config.setUsername(bundle.getString("username"));
        config.setPassword(bundle.getString("password"));
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        dataSource = new HikariDataSource( config );
	}
	
	private Dao() { }

	public static Connection connection() throws ConnectionDBFailedException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new ConnectionDBFailedException("connexion à la DB échouée");
		}

	}
}
