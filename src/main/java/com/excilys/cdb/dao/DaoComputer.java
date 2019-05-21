package com.excilys.cdb.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperDaoComputer;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.servlet.enums.OrderBy;
import com.excilys.cdb.servlet.model.Page;

@Repository
public class DaoComputer {
	
	private final Logger logger = LoggerFactory.getLogger(DaoComputer.class);
	
	private final String SQL_GETLIST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE :computerName OR company.name LIKE :companyName ORDER BY %s LIMIT :limit OFFSET :offset;";
	private final String SQL_GET = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = :id;";
	private final String SQL_CREATE = "INSERT INTO computer (name, introduced,discontinued,company_id) VALUES (:name, :introduced, :discontinued, :company_id);";
	private final String SQL_DELETE = "DELETE FROM computer WHERE id = :id;";
	private final String SQL_UPDATE = "UPDATE computer SET name=:name, introduced=:introduced, discontinued=:discontinued, company_id=:company_id WHERE id=:id;";
	private final String SQL_COUNT = "SELECT count(computer.id) as nbComputers FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE :sql_like OR company.name LIKE :sql_like;";
	
	private final DaoCompany daoCompany;
	private final MapperDaoComputer mapperDaoComputer;
	NamedParameterJdbcTemplate jdbcTemplate;
	private String orderBySQL;
	
	public DaoComputer(DaoCompany daoCompany, DbConnector dbConnector, MapperDaoComputer mapperDaoComputer) {
		this.daoCompany = daoCompany;
		this.mapperDaoComputer = mapperDaoComputer;
		this.jdbcTemplate = dbConnector.getJdbcTemplate();
	}

	public List<ModelComputer> listComputer(Page page) throws RequestFailedException, ConnectionDBFailedException {
		String sql_like = page.getLike();
		OrderBy orderBy = page.getOrderBy();
		int limit = page.getLimit();
		int offset = page.getOffset();
		
		if(sql_like!=null && !sql_like.isEmpty())
			sql_like = "%" + sql_like + "%";
		else
			sql_like = "%%";

		orderBySQL = String.format(SQL_GETLIST, orderBy.getField() + " " + orderBy.getDirection());

		try {
			MapSqlParameterSource vParams = new MapSqlParameterSource();

			vParams.addValue("computerName", sql_like);
			vParams.addValue("companyName", sql_like);
			vParams.addValue("limit", limit);
			vParams.addValue("offset", offset);		

			List<ModelComputer> listOfComputers = jdbcTemplate.query(orderBySQL, vParams, mapperDaoComputer);

			return listOfComputers;
		} catch (DataAccessException e){
			throw new RequestFailedException("Request listComputer failed because of DataAccessException");
		}
	}
	
	public ModelComputer read(int id) throws ConnectionDBFailedException, RequestFailedException {
		try {
			MapSqlParameterSource vParams = new MapSqlParameterSource();

			vParams.addValue("id", id);
			
			ModelComputer result = jdbcTemplate.queryForObject(SQL_GET, vParams, mapperDaoComputer);
			
			if(result != null)
				return result;
			else
				throw new RequestFailedException("aucun résultat");

		} catch (DataAccessException e){
			throw new RequestFailedException("Request read failed because of DataAccessException " + e.getMessage());
		}
	}
	
	public void create(ModelComputer modelComputer) throws RequestFailedException, BadEntryException, ConnectionDBFailedException {
		try {
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			
			vParams.addValue("name", modelComputer.getName());
			vParams.addValue("introduced", modelComputer.getIntroduced());
			vParams.addValue("discontinued", modelComputer.getDiscontinued());
			vParams.addValue("company_id", modelComputer.getCompanyId());
			
			if(jdbcTemplate.update(SQL_CREATE, vParams) == 1)
				logger.info("Les données de l'ordinateur ont bien été insérées.");

		} catch (DataAccessException e){
			throw new RequestFailedException("Request create failed because of DataAccessException");
		}
	}
	
	public void delete(int id) throws RequestFailedException, ConnectionDBFailedException {
		try {
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			
			vParams.addValue("id", id);
			
			if(jdbcTemplate.update(SQL_DELETE, vParams) == 1)
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
			
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			
			vParams.addValue("name", modelComputer.getName());
			vParams.addValue("introduced", modelComputer.getIntroduced());
			vParams.addValue("discontinued", modelComputer.getDiscontinued());
			vParams.addValue("company_id", company_id);
			vParams.addValue("company_name", company_id == null ? null : daoCompany.getCompany(company_id));
			vParams.addValue("id", modelComputer.getId());
			
			if(jdbcTemplate.update(SQL_UPDATE, vParams) == 1)
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
			
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("sql_like", sql_like);
			vParams.addValue("sql_like", sql_like);
			
			int vNbrTicket = jdbcTemplate.queryForObject(SQL_COUNT, vParams, Integer.class);
			
			return vNbrTicket;

		} catch (DataAccessException  e){
			throw new RequestFailedException("Request getNbComputers failed because of DataAccessException");
		}
	}
}
