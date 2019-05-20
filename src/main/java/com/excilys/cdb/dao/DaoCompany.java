package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.model.ModelCompany;

@Repository
public class DaoCompany {
	
	private final Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	private final String SQL_GETLIST = "SELECT * FROM company ORDER BY company.id";
	private final String SQL_GET = "SELECT * from company WHERE id = ?";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id= ?";
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE company_id= ?";
	private final DbConnector dao;
	
	public DaoCompany(DbConnector dao) {
		this.dao = dao;
	}
	
	public ArrayList<ModelCompany> listCompanies() throws RequestFailedException, ConnectionDBFailedException {
		
		try(
				Connection connection = dao.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GETLIST);
				ResultSet r = preparedStatement.executeQuery();
			) {
			ArrayList<ModelCompany> listOfCompanies = new  ArrayList<ModelCompany>();
			while(r.next()) {
				listOfCompanies.add(new ModelCompany(r.getInt("id"), r.getString("name")));
			}
			if (listOfCompanies.isEmpty()) throw new RequestFailedException("Aucun résultat");
			else return listOfCompanies;
			
		} catch (SQLException e) {
			throw new RequestFailedException("Request listCompanies failed because of SQLException");
		}
	}

	public String getMatch(int id) throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		try(
				Connection connection = dao.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GET);
			) {
			
			preparedStatement.setInt(1,id);
			try (ResultSet r = preparedStatement.executeQuery();) {
				if(r.next()) 
					return r.getString("name");					
				else
					throw new BadEntryException("Vous avez rentré un company_id invalide");
			}
			
		} catch (SQLException e) {
			throw new RequestFailedException("Request getMatch failed because of SQLException");
		}
	}
	
	public boolean delete(int id) throws RequestFailedException, ConnectionDBFailedException {
		try(Connection connection = dao.connection();){
			try(PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COMPANY);
					PreparedStatement preparedStatement2 = connection.prepareStatement(SQL_DELETE_COMPUTER)){
				
				connection.setAutoCommit(false);
				
				preparedStatement2.setInt(1, id);
				preparedStatement.setInt(1, id);
				
				preparedStatement2.executeUpdate();
				preparedStatement.executeUpdate();
				
				connection.commit();
				logger.info("Entreprise correctement supprimée");
			}catch(SQLException ex) {
				connection.rollback();
				throw new RequestFailedException("Request delete (company) failed because of SQLException");
			}
		}catch(SQLException ex) {
			throw new RequestFailedException("Request delete (company) failed because of SQLException");
		}
		return false;
	}
}
