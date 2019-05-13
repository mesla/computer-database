package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;

public class DaoComputer extends Dao{
	
	private static DaoComputer INSTANCE = null;
	private static DaoCompany daoCompany = DaoCompany.getInstance();
	private final Logger logger = LoggerFactory.getLogger(DaoComputer.class);
	
	private final String SQL_GETLIST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY computer.name ASC LIMIT ? OFFSET ?;";
	private final String SQL_GET = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;";
	private final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private final String SQL_DELETE = "DELETE FROM computer WHERE id = ?;";
	private final String SQL_UPDATE = "UPDATE computer SET name=?, introduced=?, discontinued=?, company_id=? WHERE id=?;";
	private final String SQL_COUNT = "SELECT count(*) as nbComputers FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?;";
	
	private DaoComputer() {}
	
	
	public static DaoComputer getInstance()
	   {           
	       if (INSTANCE == null)
	       {   INSTANCE = new DaoComputer(); 
	       }
	       return INSTANCE;
	   }
	
	
	public ArrayList<ModelComputer> listComputer(int limit, int offset, String sql_like) throws RequestFailedException, ConnectionDBFailedException {
		if(sql_like!=null && sql_like.isEmpty())
			sql_like = "%" + sql_like + "%";
		else
			sql_like = "%%";
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GETLIST);
			) {
			preparedStatement.setString(1, sql_like);
			preparedStatement.setString(2, sql_like);
			preparedStatement.setInt(3,limit);
			preparedStatement.setInt(4,offset);
			
			try(ResultSet r = preparedStatement.executeQuery();) {
				ArrayList<ModelComputer> listOfComputers = new ArrayList<ModelComputer>();
				while(r.next()) {
				listOfComputers.add(
						new ModelComputer(
								r.getInt("computer.id"),
								r.getString("computer.name"),
								r.getTimestamp("computer.introduced"),
								r.getTimestamp("computer.discontinued"),
								new ModelCompany(
										r.getInt("company.id") == 0 ? null : r.getInt("company.id"),
										r.getString("company.name"))));
				
				}
				if (limit < 0 || offset < 0) throw new RequestFailedException("Veuillez entrer des nombres positifs");
				else return listOfComputers;
			}
		} catch (SQLException e) {
			throw new RequestFailedException("Il y a un soucis au niveau de la requête SQL");
		}
	}
	
	public ModelComputer read(int id) throws ConnectionDBFailedException, RequestFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_GET);
			) {
			
			preparedStatement.setInt(1,id);
			
				try(ResultSet r = preparedStatement.executeQuery();)
				{
				if(r.next()) {
					return new ModelComputer(r.getInt("computer.id"),
								r.getString("computer.name"),
								r.getTimestamp("computer.introduced"),
								r.getTimestamp("computer.discontinued"),
								new ModelCompany(
										r.getInt("company.id") == 0 ? null : r.getInt("company.id"),
										r.getString("company.name")));
				}
				else throw new RequestFailedException("Vous avez rentré un ID invalide");
			}
		} catch (SQLException e) {
			throw new RequestFailedException("Il y a un soucis au niveau de la requête SQL");
		}
	}
	
	public void create(ModelComputer modelComputer) throws SQLException, RequestFailedException, BadEntryException, ConnectionDBFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_CREATE);
			) {
			preparedStatement.setString(1,modelComputer.getName());
			preparedStatement.setTimestamp(2, modelComputer.getIntroduced());
			preparedStatement.setTimestamp(3, modelComputer.getDiscontinued());
			if (modelComputer.getCompanyId() == null)
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			else if(!daoCompany.getMatch(modelComputer.getCompanyId()).isEmpty())
				preparedStatement.setInt(4, modelComputer.getCompanyId());
			else
				throw new RequestFailedException("Vous avez rentré un company_id invalide");
				
			preparedStatement.executeUpdate();
			logger.info("Ordinateur bien créé.");
		}
	}
	
	public void delete(int id) throws SQLException, RequestFailedException, ConnectionDBFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_DELETE);
			) {
			
			preparedStatement.setInt(1,id);
			
			if (preparedStatement.executeUpdate() == 0)
				throw new RequestFailedException("Il n'y a aucun ordinateur à cet ID.");
			else logger.info("Ordinateur bien supprimé");
				
		} catch (SQLException e) {
			throw new RequestFailedException("Vous avez rentré un ID invalide");
		}
	}
	
	public void update(ModelComputer modelComputer, ModelComputer oldComputer) throws SQLException, RequestFailedException, ConnectionDBFailedException, BadEntryException {

		String name = (modelComputer.getName() == null) ? oldComputer.getName() : modelComputer.getName();
		Timestamp intro = (modelComputer.getIntroduced() == null) ? oldComputer.getIntroduced() : modelComputer.getIntroduced();
		Timestamp discon = (modelComputer.getDiscontinued() == null) ? oldComputer.getDiscontinued() : modelComputer.getDiscontinued();
		Integer company_id = (modelComputer.getCompanyId() == null) ? oldComputer.getCompanyId() : modelComputer.getCompanyId();

		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_UPDATE);
			) {
			preparedStatement.setString(1,name);
			preparedStatement.setTimestamp(2, intro);
			preparedStatement.setTimestamp(3, discon);
			
			if (company_id == null)
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			else if(!daoCompany.getMatch(company_id).isEmpty())
				preparedStatement.setInt(4, company_id);
			else
				throw new RequestFailedException("Vous avez rentré un company_id invalide");
			
			preparedStatement.setInt(5, oldComputer.getId());
				
			preparedStatement.executeUpdate();
			logger.info("Les données de l'ordinateur ont bien été mises à jour.");
		}
	}
	
	public int getNbComputers(String sql_like) throws SQLException, ConnectionDBFailedException, RequestFailedException {
		try(
				Connection connection = super.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(this.SQL_COUNT);
			){
			if(sql_like!=null && sql_like.isEmpty())
				sql_like = "%" + sql_like + "%";
			else
				sql_like = "%%";
			preparedStatement.setString(1,sql_like);
			preparedStatement.setString(2,sql_like);
			try(ResultSet r = preparedStatement.executeQuery();) {
				if(r.next())
					return r.getInt("nbComputers");
				else
					throw new RequestFailedException("Il n'y a aucun ordinateur.");
			}
		}
		
	}

}
