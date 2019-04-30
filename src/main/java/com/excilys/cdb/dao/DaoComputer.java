package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;

public class DaoComputer extends Dao{
	
	private static DaoComputer INSTANCE = null;
	
	private final String SQL_GETLIST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY computer.id ASC LIMIT ? OFFSET ?;";
	private final String SQL_GET = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;";
	private final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private final String SQL_DELETE = "DELETE FROM computer WHERE id = ?;";
	private final String SQL_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	
	
	private DaoComputer() {}
	
	
	public static DaoComputer getInstance()
	   {           
	       if (INSTANCE == null)
	       {   INSTANCE = new DaoComputer(); 
	       }
	       return INSTANCE;
	   }
	
	
	public ArrayList<ModelComputer> listComputer(int limit, int offset) throws RequestFailedException, ConnectionDBFailedException {
		
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GETLIST);
			) {
						
			preparedStatement.setInt(1,limit);
			preparedStatement.setInt(2,offset);
			ResultSet r = preparedStatement.executeQuery();
			
			ArrayList<ModelComputer> listOfComputers = new ArrayList<ModelComputer>();
			while(r.next()) {
			listOfComputers.add(
					new ModelComputer(
							r.getInt("computer.id"),
							r.getString("computer.name"),
							r.getTimestamp("computer.introduced"),
							r.getTimestamp("computer.discontinued"),
							new ModelCompany(r.getInt("company.id"), r.getString("company.name"))));
			
			}
			
			if (listOfComputers.isEmpty()) throw new RequestFailedException("Aucun résultat");
			else return listOfComputers;
			
		} catch (SQLException e) {
			throw new RequestFailedException("Il y a un soucis au niveau de la requête SQL");
		}
	}
	
	public ModelComputer read(int id) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GET);
			) {
			
			preparedStatement.setInt(1,id);
			ResultSet r = preparedStatement.executeQuery();
			
			if(r.next()) {
				return new ModelComputer(r.getInt("computer.id"),
							r.getString("computer.name"),
							r.getTimestamp("computer.introduced"),
							r.getTimestamp("computer.discontinued"),
							new ModelCompany(r.getInt("company.id"), r.getString("company.name")));
			}
			else throw new RequestFailedException("Vous avez rentré un ID invalide");
		}
	}
	
	public void create(ModelComputer modelComputer) throws SQLException, RequestFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_CREATE);
			) {
			preparedStatement.setString(1,modelComputer.getName());
			preparedStatement.setTimestamp(2, modelComputer.getIntroduced());
			preparedStatement.setTimestamp(3, modelComputer.getDiscontinued());
			if (modelComputer.getCompany_id() == null)
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			else if(!DaoCompany.getInstance().getMatch(modelComputer.getCompany_id()).isEmpty())
				preparedStatement.setInt(4, modelComputer.getCompany_id());
			else
				throw new RequestFailedException("Vous avez rentré un company_id invalide");
				
			preparedStatement.executeUpdate();
		}
		catch (ConnectionDBFailedException e) {
			Logger logger = LoggerFactory.getLogger(DaoComputer.class);
		    logger.info(e.getMessage());
		}
	}
	
	public void delete(int id) throws SQLException, RequestFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_DELETE);
			) {
			preparedStatement.setInt(1,id);
			
			try {
				if (preparedStatement.executeUpdate() == 0)
					throw new RequestFailedException("Vous avez rentré un ID invalide");
			}
			catch (SQLException e){throw new RequestFailedException("Vous avez rentré un ID invalide");}
		}
		catch (ConnectionDBFailedException e) {
			Logger logger = LoggerFactory.getLogger(DaoComputer.class);
		    logger.info(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	
	public void update(ModelComputer modelComputer, ModelComputer oldComputer) throws SQLException, RequestFailedException, ConnectionDBFailedException {

		String name = (modelComputer.getName() == null) ? oldComputer.getName() : modelComputer.getName();
		Timestamp intro = (modelComputer.getIntroduced() == null) ? oldComputer.getIntroduced() : modelComputer.getIntroduced();
		Timestamp discon = (modelComputer.getDiscontinued() == null) ? oldComputer.getDiscontinued() : modelComputer.getDiscontinued();
		Integer company_id = (modelComputer.getCompany_id() == null) ? oldComputer.getCompany_id() : modelComputer.getCompany_id();

		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_UPDATE);
			) {
			preparedStatement.setString(1,name);
			preparedStatement.setTimestamp(2, intro);
			preparedStatement.setTimestamp(3, discon);
			if (company_id == null)
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			else if(!DaoCompany.getInstance().getMatch(company_id).isEmpty())
				preparedStatement.setInt(4, company_id);
			else
				throw new RequestFailedException("Vous avez rentré un company_id invalide");
			preparedStatement.setInt(5, oldComputer.getId());
				
			preparedStatement.executeUpdate();
		} 
		catch (ConnectionDBFailedException e) {
			Logger logger = LoggerFactory.getLogger(DaoComputer.class);
		    logger.info(e.getMessage());
			System.out.println(e.getMessage());
		}
	}


}
