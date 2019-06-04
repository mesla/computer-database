package com.excilys.cdb.binding.mapper;

import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.DtoComputer;
import com.excilys.cdb.core.model.ModelCompany;
import com.excilys.cdb.core.model.ModelComputer;

@Component
public class MapperComputer {

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
				u.getCompanyName() == null ? null : String.valueOf(u.getCompanyName()));
	}
}
