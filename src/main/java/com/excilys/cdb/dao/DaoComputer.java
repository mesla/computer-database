package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.servlet.enums.OrderBy;

@Component
public class DaoComputer {
	
	private final Logger logger = LoggerFactory.getLogger(DaoComputer.class);
	
	private final String SQL_GETLIST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY %s LIMIT ? OFFSET ?;";
	private final String SQL_GET = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?;";
	private final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private final String SQL_DELETE = "DELETE FROM computer WHERE id = :id;";
	private final String SQL_UPDATE = "UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:company_id WHERE id=:id;";
	private final String SQL_COUNT = "SELECT count(computer.id) as nbComputers FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE :sql_like OR company.name LIKE :sql_like;";
	
	private final DaoCompany daoCompany;
	private final Dao dao;
	
	private String orderBySQL;
	
	public DaoComputer(DaoCompany daoCompany, Dao dao) {
		this.daoCompany = daoCompany;
		this.dao = dao;
	}

	public ArrayList<ModelComputer> listComputer(int limit, int offset, String sql_like, OrderBy orderBy) throws RequestFailedException, ConnectionDBFailedException {
		if(sql_like!=null && !sql_like.isEmpty())
			sql_like = "%" + sql_like + "%";
		else
			sql_like = "%%";

		orderBySQL = String.format(SQL_GETLIST, orderBy.getField() + " " + orderBy.getDirection());

		try(
				Connection connection = dao.connection();
				PreparedStatement preparedStatement = connection.prepareStatement(orderBySQL);
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
		} catch (SQLException e){
			throw new RequestFailedException("Request listComputer failed because of SQLException");
		}
	}
	
	public ModelComputer read(int id) throws ConnectionDBFailedException, RequestFailedException {
		try(
				Connection connection = dao.connection();
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
		} catch (SQLException e){
			throw new RequestFailedException("Request read failed because of SQLException");
		}
	}
	
	public void create(ModelComputer modelComputer) throws RequestFailedException, BadEntryException, ConnectionDBFailedException {
		try(
				Connection connection = dao.connection();
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
		} catch (SQLException e){
			throw new RequestFailedException("Request create failed because of SQLException");
		}
	}
	
	public void delete(int id) throws RequestFailedException, ConnectionDBFailedException {
		try {
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dao.getDataSource());
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			
			vParams.addValue("id", id);
			
			if(vJdbcTemplate.update(SQL_DELETE, vParams) == 1)
				logger.info("Les données de l'ordinateur ont bien été supprimées.");
			else
				throw new RequestFailedException("Request delete failed because the given ID didn't match any computer.");
				
		}  catch (DataAccessException e){
			throw new RequestFailedException("Request delete failed because of DataAccessException");
		}
	}
	
	public void update(ModelComputer modelComputer) throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		try {
			Integer company_id = modelComputer.getCompanyId();
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dao.getDataSource());
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			
			vParams.addValue("name", modelComputer.getName());
			vParams.addValue("introduced", modelComputer.getIntroduced());
			vParams.addValue("discontinued", modelComputer.getDiscontinued());
			vParams.addValue("company_id", company_id);
			vParams.addValue("company_name", company_id == null ? null : daoCompany.getMatch(company_id));
			vParams.addValue("id", modelComputer.getId());
			
			if(vJdbcTemplate.update(SQL_UPDATE, vParams) == 1)
				logger.info("Les données de l'ordinateur ont bien été mises à jour.");
			else
				throw new RequestFailedException("Request update failed because the given ID didn't match any computer.");
		}  catch (DataAccessException e){
			throw new RequestFailedException("Request update failed because of DataAccessException");
		}
	}
	
	public int getNbComputers(String sql_like) throws ConnectionDBFailedException, RequestFailedException {
		try {
			if(sql_like!=null && !sql_like.isEmpty())
				sql_like = "%" + sql_like + "%";
			else
				sql_like = "%%";
			
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dao.getDataSource());
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("sql_like", sql_like);
			vParams.addValue("sql_like", sql_like);
			
			int vNbrTicket = vJdbcTemplate.queryForObject(SQL_COUNT, vParams, Integer.class);
			
			return vNbrTicket;

		} catch (DataAccessException  e){
			throw new RequestFailedException("Request getNbComputers failed because of DataAccessException");
		}
		
	}

}
