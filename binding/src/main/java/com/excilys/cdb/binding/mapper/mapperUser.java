package com.excilys.cdb.binding.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.DtoUser;
import com.excilys.cdb.core.model.ModelUser;

@Component
public class mapperUser {
	public ModelUser toModel(DtoUser dtoUser) {
		return new ModelUser(
				dtoUser.getId(),
				dtoUser.getUsername(),
				dtoUser.getPassword(),
				dtoUser.getRole()
			);
	}
	
	public DtoUser toDto(ModelUser modelUser) {
		return new DtoUser(
				modelUser.getId(),
				modelUser.getUsername(),
				modelUser.getPassword(),
				modelUser.getRole()
			);
	}
}
