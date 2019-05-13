package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.DtoCompany;
import com.excilys.cdb.model.ModelCompany;

public class MapperCompany {
	
	private static MapperCompany INSTANCE = null;
	
	private MapperCompany() { }
	
    public static MapperCompany getInstance() {           
        if (INSTANCE == null)
        {   INSTANCE = new MapperCompany(); 
        }
        return INSTANCE;
    }
	
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
