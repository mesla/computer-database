package com.excilys.cdb.mapper;

import java.sql.Timestamp;

import com.excilys.cdb.dto.DtoComputer;
import com.excilys.cdb.exception.BadEntryException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.ModelComputer;
import com.excilys.cdb.dto.DtoCompany;

public class MapperComputer {

	private static MapperComputer INSTANCE = null;
	
	private MapperComputer () { }
	
	public static MapperComputer getInstance() {
		if (INSTANCE == null)
		{   INSTANCE = new MapperComputer(); 
		}
		return INSTANCE;
	}
	
	public ModelComputer toModel(DtoComputer t) throws BadEntryException {
		
		Timestamp dateIntro = t.getIntroduced().isEmpty() || t.getIntroduced() == "null" ? null : Timestamp.valueOf(t.getIntroduced() + " 13:00:01");
		Timestamp dateDiscon = t.getDiscontinued().isEmpty() || t.getDiscontinued() == "null" ? null : Timestamp.valueOf(t.getDiscontinued() + " 13:00:01");
		if(dateIntro != null && dateDiscon != null && dateIntro.after(dateDiscon)) throw new BadEntryException("Veuillez rentrer un date de sortie plus ancienne que celle de retrait\n");
		return new ModelComputer(
				Integer.parseInt(t.getId()),
				t.getName(),
				dateIntro,
				dateDiscon,
				new ModelCompany(t.getCompany_id().isEmpty() ? null : Integer.valueOf(t.getCompany_id()),
				t.getCompany_name()));

	}

	public DtoComputer toDto(ModelComputer u) {
		return new DtoComputer(
				String.valueOf(u.getId()),
				String.valueOf(u.getName()),
				(u.getIntroduced() == null) ? null : String.valueOf(u.getIntroduced()).substring(0,10),
				(u.getDiscontinued() == null) ? null : String.valueOf(u.getDiscontinued()).substring(0,10),
				new DtoCompany(String.valueOf(u.getCompany_id()), String.valueOf(u.getCompany_name())));
	}
}
