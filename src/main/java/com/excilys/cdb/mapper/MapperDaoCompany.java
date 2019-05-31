package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.ModelCompany;

@Component
public class MapperDaoCompany  implements RowMapper<ModelCompany>{

	@Override
	public ModelCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new ModelCompany(
				rs.getLong("company.id"),
				rs.getString("company.name"));
	}
}
