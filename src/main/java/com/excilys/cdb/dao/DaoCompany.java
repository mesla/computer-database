package com.excilys.cdb.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.model.ModelCompany;
import com.excilys.cdb.model.QModelCompany;
import com.excilys.cdb.model.QModelComputer;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class DaoCompany {
	
	private final Logger logger = LoggerFactory.getLogger(DaoCompany.class);

	JPAQueryFactory queryFactory;
	
	QModelComputer qModelComputer = QModelComputer.modelComputer;
	QModelCompany qModelCompany = QModelCompany.modelCompany;
	
	public DaoCompany(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public List<ModelCompany> listCompanies() {
		return queryFactory.selectFrom(qModelCompany)
			.fetch();
	}

	public ModelCompany getCompany(Long id) {
		ModelCompany result = queryFactory.selectFrom(qModelCompany)
				.where(qModelCompany.id.eq(id))
				.fetchOne();
		if(result != null)
			return result;
		else
			throw new RequestFailedException("Aucune entreprise trouvé à l'id " + id);
	}
	
	public void delete(Long id) {
		long nbDeleted = queryFactory.delete(qModelCompany)
				.where(qModelCompany.id.eq(id))
				.execute();
		
		if(nbDeleted == 1)
			logger.info("Les données de l'entreprise ont bien été supprimées.");
		else
			throw new RequestFailedException("Request delete failed because the given ID didn't match any computer.");
	}
	
}
