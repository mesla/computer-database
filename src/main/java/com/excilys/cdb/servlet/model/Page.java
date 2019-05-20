package com.excilys.cdb.servlet.model;

import java.util.ArrayList;

import com.excilys.cdb.exception.BadArgumentException;
import com.excilys.cdb.servlet.enums.OrderBy;

public class Page {
	private int page;
	private int offset;
	private int limit;
	private int nbComputers;
	private int nbPages;
	private ArrayList<Integer> availablePages;
	private String like;
	
	private OrderBy orderBy;

	private static Page INSTANCE = null;

	private Page() {
		this.setDefault();
	}

	public void setDefault() {
		limit = 10;
		page = 1;
		offset = (page - 1) * limit;
		like = "";
		orderBy = OrderBy.ORDERBY_COMPUTER_ID_ASC;
	}

	public static Page getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Page();
		}
		return INSTANCE;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) throws BadArgumentException {
		if (page <= nbPages && page > 0) {
			this.page = page;
			offset = (page - 1) * limit;
		}
		else throw new BadArgumentException("La page " + page + " n'existe pas ! Les pages disponibles sont comprises entre 1 et " + nbPages);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) throws BadArgumentException {
		if(offset>0) this.offset = offset;
		else throw new BadArgumentException("l'offset doit être supérieur à 0");
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) throws BadArgumentException {
		if(limit == 10 || limit == 50 || limit == 100) {
			this.limit = limit;
		} else throw new BadArgumentException("La taille d'une page ne peut que être comprise entre 10, 50 et 100");
	}
	
	public void refreshNbPages() {
		setNbPages(nbComputers % limit == 0 ? nbComputers / limit : nbComputers / limit + 1);
	}

	public int getNbComputers() {
		return nbComputers;
	}

	public void setNbComputers(int nbComputers) {
		this.nbComputers = nbComputers;
	}

	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public ArrayList<Integer> getAvailablePages() {
		return availablePages;
	}

	public void setAvailablePages(ArrayList<Integer> availablePages) {
		this.availablePages = availablePages;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) throws BadArgumentException {
		this.like = like;
		setPage(1);
	}
	
	

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderby(OrderBy orderBy) throws BadArgumentException {
		this.orderBy = orderBy;
		setPage(1);
	}

	@Override
	public String toString() {
		return "Page [page=" + page + ", offset=" + offset + ", limit=" + limit + ", nbComputers=" + nbComputers
				+ ", nbPages=" + nbPages + ", availablePages=" + availablePages + ", like=" + like + ", orderBy=" + orderBy + "]";
	}

}
