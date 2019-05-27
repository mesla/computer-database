package com.excilys.cdb.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.mapper.MapperDaoCompany;
import com.excilys.cdb.model.ModelCompany;

@Repository
public class DaoCompany {
	
	private final Logger logger = LoggerFactory.getLogger(DaoCompany.class);
	
	private final String SQL_GETLIST = "SELECT * FROM company ORDER BY company.id";
	private final String SQL_GET = "SELECT * from company WHERE id = :id";
	private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id= :id";
	private final MapperDaoCompany mapperDaoCompany;
	NamedParameterJdbcTemplate jdbcTemplate;
	
	public DaoCompany(DbConnector dbConnector, MapperDaoCompany mapperDaoCompany) {
		this.mapperDaoCompany = mapperDaoCompany;
		this.jdbcTemplate = dbConnector.getJdbcTemplate();
	}

	public List<ModelCompany> listCompanies() {
		try {
			List<ModelCompany> listOfCompanies = jdbcTemplate.query(SQL_GETLIST, mapperDaoCompany);
			return listOfCompanies;
			
		} catch (DataAccessException e) {
			throw new RequestFailedException("Request listCompanies failed because of DataAccessException");
		}
	}

	public ModelCompany getCompany(int id) {
		try {
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("id", id);
			
			ModelCompany result = jdbcTemplate.queryForObject(SQL_GET, vParams, mapperDaoCompany);
			
			if(result != null)
				return result;
			else
				throw new RequestFailedException("aucun résultat");
			
		} catch (DataAccessException e) {
			throw new RequestFailedException("Request getMatch failed because of DataAccessException");
		}
	}
	
	
	public void delete(int id) {
		try{
			MapSqlParameterSource vParams = new MapSqlParameterSource();
			vParams.addValue("id", id);
			
			if(jdbcTemplate.update(SQL_DELETE_COMPANY, vParams) != 0)
				logger.info("Company supprimée");
			else
				throw new RequestFailedException("Aucune entreprise ne possède l'id : " + id);
			
		}catch(DataAccessException ex) {
			throw new RequestFailedException("Request delete (company) failed because of DataAccessException \n" + ex.getMessage());
		}
	}
}
