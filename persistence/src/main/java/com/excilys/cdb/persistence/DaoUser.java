package com.excilys.cdb.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.exception.RequestFailedException;
import com.excilys.cdb.core.model.ModelUser;
import com.excilys.cdb.core.model.QModelUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class DaoUser {
	
	JPAQueryFactory queryFactory;
	
	QModelUser qModelUser = QModelUser.modelUser;

	public DaoUser(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public ModelUser findByUserName(String username) {
		ModelUser result = queryFactory.selectFrom(qModelUser)
				.where(qModelUser.username.eq(username))
				.fetchOne();
		
		if(result != null)
			return result;
		else
			throw new RequestFailedException("Aucun utilisateur ayant le nom d'utilisateur " + username);
	}

}
