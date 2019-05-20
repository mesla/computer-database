package com.excilys.cdb.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperDaoCompany;
import com.excilys.cdb.model.ModelCompany;

@Repository
public class DaoCompany {
	
	private final Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	private final String SQL_GETLIST = "SELECT * FROM company ORDER BY company.id";
	private final String SQL_GET = "SELECT * from company WHERE id = :id";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id= :id";
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE company_id= :id";
	private final DbConnector dbConnector;
	private final MapperDaoCompany mapperDaoCompany;
	
	public DaoCompany(DbConnector dao, MapperDaoCompany mapperDaoCompany) {
		this.dbConnector = dao;
		this.mapperDaoCompany = mapperDaoCompany;
	}
	
	public List<ModelCompany> listCompanies() throws RequestFailedException, ConnectionDBFailedException {
		try {
			JdbcTemplate vJdbcTemplate = new JdbcTemplate(dbConnector.getDataSource());
			List<ModelCompany> listOfCompanies = vJdbcTemplate.query(SQL_GETLIST, mapperDaoCompany);
			return listOfCompanies;
			
		} catch (DataAccessException e) {
			throw new RequestFailedException("Request listCompanies failed because of DataAccessException");
		}
	}

	public ModelCompany getCompany(int id) throws RequestFailedException, ConnectionDBFailedException, BadEntryException {
		try {
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dbConnector.getDataSource());
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("id", id);
			
			ModelCompany result = vJdbcTemplate.queryForObject(SQL_GET, vParams, mapperDaoCompany);
			
			if(result != null)
				return result;
			else
				throw new RequestFailedException("aucun résultat");
			
		} catch (DataAccessException e) {
			throw new RequestFailedException("Request getMatch failed because of DataAccessException");
		}
	}
	
	@Transactional
	public void delete(int id) throws RequestFailedException, ConnectionDBFailedException {
		try{
			NamedParameterJdbcTemplate vJdbcTemplate = new NamedParameterJdbcTemplate(dbConnector.getDataSource());
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("id", id);
			
			if(vJdbcTemplate.update(SQL_DELETE_COMPUTER, vParams) != 0)
				logger.info("Ordinateurs supprimés");
			
			if(vJdbcTemplate.update(SQL_DELETE_COMPANY, vParams) != 0)
				logger.info("Company supprimée");
			else
				throw new RequestFailedException("Aucune entreprise ne possède l'id : " + id);
			
		}catch(DataAccessException ex) {
			throw new RequestFailedException("Request delete (company) failed because of DataAccessException \n" + ex.getMessage());
		}
	}
}
