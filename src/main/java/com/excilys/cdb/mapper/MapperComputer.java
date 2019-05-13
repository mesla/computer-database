package com.excilys.cdb.mapper;

import java.sql.Timestamp;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;

public class MapperComputer {

	private static MapperComputer INSTANCE = null;

	private MapperComputer () { }

	public static MapperComputer getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new MapperComputer(); 
		}
		return INSTANCE;
	}
	
	public ModelComputer toModel(DtoComputer t) {
		
		Timestamp dateIntro = t.getIntroduced() == null ? null : Timestamp.valueOf(t.getIntroduced() + " 13:00:01");
		Timestamp dateDiscon = t.getDiscontinued() == null ? null : Timestamp.valueOf(t.getDiscontinued() + " 13:00:01");
		return new ModelComputer(
				t.getId(),
				t.getName(),
				dateIntro,
				dateDiscon,
				t.getCompanyId() == null ? null : new ModelCompany(t.getCompanyId(),
				t.getCompanyName()));

	}
	public DtoComputer toDto(ModelComputer u) {
		return new DtoComputer(
				u.getId(),
				String.valueOf(u.getName()),
				(u.getIntroduced() == null) ? null : u.getIntroduced().toLocalDateTime().toLocalDate(),
				(u.getDiscontinued() == null) ? null : u.getDiscontinued().toLocalDateTime().toLocalDate(),
				u.getCompanyId(),
				String.valueOf(u.getCompanyName()));
	}
}
