package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.model.ModelCompany;


public class DaoCompany extends Dao{
	
	private static DaoCompany INSTANCE = null;
	
	private final String SQL_GETLIST = "SELECT * FROM company ORDER BY company.id ASC LIMIT ? OFFSET ?";
	private final String SQL_GET = "SELECT * from company WHERE id = ?";
	
	
	private DaoCompany() { }
	
	public static DaoCompany getInstance()
    {           
        if (INSTANCE == null)
        {   INSTANCE = new DaoCompany(); 
        }
        return INSTANCE;
    }
	
	
	public ArrayList<ModelCompany> listCompanies(int limit, int offset) throws RequestFailedException, ConnectionDBFailedException {
		
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GETLIST);
			) {
			preparedStatement.setInt(1,limit);
			preparedStatement.setInt(2,offset);
			ResultSet r = preparedStatement.executeQuery();
			
			ArrayList<ModelCompany> listOfCompanies = new  ArrayList<ModelCompany>();
			while(r.next()) {
				listOfCompanies.add(new ModelCompany(r.getInt("id"), r.getString("name")));
			}
			
			return listOfCompanies;
			
		} catch (SQLException e) {
			throw new ConnectionDBFailedException("La connection à la bdd a échouée");
		}
	}

	public String getMatch(int id) throws BadEntryException, RequestFailedException, ConnectionDBFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GET);
			) {
			preparedStatement.setInt(1,id);
			ResultSet r = preparedStatement.executeQuery();
			if(r.next()) return r.getString("name");
			else throw new BadEntryException("Vous avez rentré un company_id invalide");
		} catch (SQLException e) {
			throw new RequestFailedException("Requête échouée");
		}
	}
}
