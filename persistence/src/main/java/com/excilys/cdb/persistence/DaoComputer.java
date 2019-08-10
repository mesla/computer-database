package com.excilys.cdb.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.exception.RequestFailedException;
import com.excilys.cdb.core.model.QModelCompany;
import com.excilys.cdb.core.model.QModelComputer;
import com.excilys.cdb.core.model.ModelComputer;
import com.excilys.cdb.core.model.Page;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
@Transactional(propagation = Propagation.NESTED)
public class DaoComputer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	QModelComputer qModelComputer = QModelComputer.modelComputer;
	QModelCompany qModelCompany = QModelCompany.modelCompany;
	
	JPAQueryFactory queryFactory;
	
	@PersistenceContext
	EntityManager entityManager;
	
	public DaoComputer(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	public List<ModelComputer> listComputer(Page page) {
		
		String sql_like = page.getLike();

		List<ModelComputer> listOfComputers = queryFactory
				.selectFrom(qModelComputer)
				.leftJoin(qModelCompany).on(qModelCompany.id.eq(qModelComputer.modelCompany.id))
				.where(qModelComputer.name.like("%"+sql_like+"%").or(qModelCompany.name.like("%"+sql_like+"%")))
				.orderBy(page.getOrderBy().getOrderSpecifier())
				.limit(page.getLimit())
				.offset(page.getOffset())
				.fetch();
		return listOfComputers;
	}
	
	public ModelComputer read(Long id) {
		ModelComputer result = queryFactory.selectFrom(qModelComputer)
				.leftJoin(qModelCompany).on(qModelCompany.id.eq(qModelComputer.modelCompany.id))
				.where(qModelComputer.id.eq(id))
				.fetchOne();
		if(result != null)
			return result;
		else
			throw new RequestFailedException("Aucun ordinateur trouvé à l'id " + id);
	}
	
	public void create(ModelComputer modelComputer) {
		entityManager.persist(modelComputer);
		logger.info("Ordinateur correctement inséré");
	}
	
	public void delete(Long id) {
		long nbDeleted = queryFactory.delete(qModelComputer)
				.where(qModelComputer.id.eq(id))
				.execute();
		
		if(nbDeleted == 1)
			logger.info("Les données de l'ordinateur ont bien été supprimées.");
		else
			throw new RequestFailedException("Request delete failed because the given ID didn't match any computer.");
	}
	
	public void update(ModelComputer modelComputer) {
		long nbComputerUpdated = queryFactory.update(qModelComputer)
		.set(qModelComputer.name,modelComputer.getName())
		.set(qModelComputer.introduced, modelComputer.getIntroduced())
		.set(qModelComputer.discontinued, modelComputer.getDiscontinued())
		.set(qModelComputer.modelCompany.id, modelComputer.getCompanyId())
		.where(qModelComputer.id.eq(modelComputer.getId()))
		.execute();
		
		if(nbComputerUpdated == 1)
			logger.info("Les données de l'ordinateur ont bien été mises à jour.");
		else
			throw new RequestFailedException("Request update failed because the given ID didn't match any computer.");
	}
	
	public long getNbComputers(String sql_like) {
		long nbComputers = queryFactory.selectFrom(qModelComputer)
				.leftJoin(qModelCompany).on(qModelCompany.id.eq(qModelComputer.modelCompany.id))
				.where(qModelComputer.name.like("%"+sql_like+"%").or(qModelCompany.name.like("%"+sql_like+"%")))
				.fetchCount();
				
		return nbComputers;
		}

	public void deleteByCompanyId(Long id) {
		long nbComputerDeleted = queryFactory.delete(qModelComputer)
			.where(qModelComputer.modelCompany.id.eq(id))
			.execute();

		if(nbComputerDeleted == 1)
			logger.info("Ordinateur supprimé");
		else throw new RequestFailedException("Aucun ordinateur n'a été supprimé");
	}
}
