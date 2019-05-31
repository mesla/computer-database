package com.excilys.cdb.servlet.enums;

import com.excilys.cdb.model.QModelCompany;
import com.excilys.cdb.model.QModelComputer;
import com.querydsl.core.types.OrderSpecifier;

public enum OrderBy {
	ORDERBY_COMPUTER_NAME_ASC("computer", QModelComputer.modelComputer.name.asc()),
	ORDERBY_COMPUTER_NAME_DESC("computer", QModelComputer.modelComputer.name.desc()),
	ORDERBY_INTRODUCED_ASC("computer", QModelComputer.modelComputer.introduced.asc()),
	ORDERBY_INTRODUCED_DESC("computer", QModelComputer.modelComputer.introduced.desc()),
	ORDERBY_DISCONTINUED_ASC("computer", QModelComputer.modelComputer.discontinued.asc()),
	ORDERBY_DISCONTINUED_DESC("computer", QModelComputer.modelComputer.discontinued.desc()),
	ORDERBY_COMPANY_NAME_ASC("company", QModelCompany.modelCompany.name.desc()),
	ORDERBY_COMPANY_NAME_DESC("company", QModelCompany.modelCompany.name.desc()),
	ORDERBY_COMPUTER_ID_ASC("computer", QModelComputer.modelComputer.id.asc());
	
	private String field;
	private OrderSpecifier<? extends Comparable<?>> orderSpecifier;
	
	OrderBy(String field, OrderSpecifier<? extends Comparable<?>> orderSpecifier) {
		this.field = field;
		this.orderSpecifier = orderSpecifier;
	}
	
	public String getField() {
		return field;
	}
	
	public OrderSpecifier<? extends Comparable<?>> getOrderSpecifier() {
		return orderSpecifier;
	}
		
}