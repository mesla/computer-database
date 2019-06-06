package com.excilys.cdb.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.model.ModelUser;
import com.excilys.cdb.persistence.DaoUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private DaoUser daoUser;

	@Override
	public UserDetailsImpl loadUserByUsername(String username) throws
		UsernameNotFoundException {
		ModelUser user = daoUser.findByUserName(username);
		if(null == user){
			throw new UsernameNotFoundException("No user named "+username);
		}else{
			return new UserDetailsImpl(user);
		}
	}

}
