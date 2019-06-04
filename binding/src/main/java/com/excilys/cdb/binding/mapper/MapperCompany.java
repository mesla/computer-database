package com.excilys.cdb.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.model.ModelCompany;

@Component
public class MapperCompany {
		
	public ModelCompany toModel(DtoCompany t) {
		return new ModelCompany(
				t.getId(),
				t.getName());
	}

	public DtoCompany toDto(ModelCompany u) {
		return new DtoCompany(
				u.getId(),
				String.valueOf(u.getName()));
	}
}
