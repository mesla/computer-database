package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;

@Component
public class MapperDaoComputer implements RowMapper<ModelComputer>{

	@Override
	public ModelComputer mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ModelComputer(
				rs.getInt("computer.id"),
				rs.getString("computer.name"),
				rs.getTimestamp("computer.introduced"),
				rs.getTimestamp("computer.discontinued"),
				new ModelCompany(
						rs.getInt("company.id") == 0 ? null : rs.getInt("company.id"),
						rs.getString("company.name")));
	}

}
