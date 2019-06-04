package com.excilys.cdb.binding.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.DtoCompany;
import com.excilys.cdb.core.model.ModelCompany;

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
