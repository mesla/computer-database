package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.excilys.cdb.exception.ConnectionDBFailedException;

public class DBManager {
	private static DBManager instance = new DBManager();

    private DBManager() {
    }

    public static DBManager getInstance() {
        return instance;
    }

    private static void executeScript(String filename) throws ConnectionDBFailedException{
    	
        try (final Connection connection = Dao.connection();
             final Statement statement = connection.createStatement();
             final InputStream resourceAsStream = DBManager.class.getClassLoader().getResourceAsStream(filename);
             final Scanner scanner = new Scanner(resourceAsStream)) {
        	
            StringBuilder sb = new StringBuilder();
            while (scanner.hasNextLine()) {
                final String nextLine = scanner.nextLine();
                sb.append(nextLine);
            }
            
            final StringTokenizer stringTokenizer = new StringTokenizer(sb.toString(), ";");

            while (stringTokenizer.hasMoreTokens()) {
                final String nextToken = stringTokenizer.nextToken();
                statement.execute(nextToken);
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }

    public void reload() throws IOException, SQLException, ConnectionDBFailedException {
        executeScript("schema.sql");
        executeScript("entries.sql");
    }
}
