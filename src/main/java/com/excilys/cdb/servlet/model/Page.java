package com.excilys.cdb.servlet.model;

import java.util.ArrayList;

import com.excilys.cdb.exception.BadArgumentException;
import com.excilys.cdb.servlet.enums.OrderBy;

public class Page {
	private int page;
	private int offset;
	private int limit;
	private long nbComputers;
	private long nbPages;
	private ArrayList<Long> availablePages;
	private String like;
	
	private OrderBy orderBy;


	public Page() {
		this.setDefault();
	}

	public void setDefault() {
		limit = 10;
		page = 1;
		offset = (page - 1) * limit;
		like = "";
		orderBy = OrderBy.ORDERBY_COMPUTER_ID_ASC;
	}

	public int getPage() {
		return page;
	}

	public void setCurrentPageAndUpdateOffset(int page){
		if (page <= nbPages && page > 0) {
			this.page = page;
			offset = (page - 1) * limit;
		}
		else throw new BadArgumentException("La page " + page + " n'existe pas ! Les pages disponibles sont comprises entre 1 et " + nbPages);
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		if(offset>0) this.offset = offset;
		else throw new BadArgumentException("l'offset doit être supérieur à 0");
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		if(limit == 10 || limit == 50 || limit == 100) {
			this.limit = limit;
		} else throw new BadArgumentException("La taille d'une page ne peut que être comprise entre 10, 50 et 100");
	}
	
	public void refreshNbPages() {
		setNbPages(nbComputers % limit == 0 ? nbComputers / limit : nbComputers / limit + 1);
	}

	public long getNbComputers() {
		return nbComputers;
	}

	public void setNbComputers(long l) {
		this.nbComputers = l;
	}

	public long getNbPages() {
		return nbPages;
	}

	public void setNbPages(long l) {
		this.nbPages = l;
	}

	public ArrayList<Long> getAvailablePages() {
		return availablePages;
	}

	public void setAvailablePages(ArrayList<Long> arrayList) {
		this.availablePages = arrayList;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderbyAndResetCurrentPage(OrderBy orderBy) {
		this.orderBy = orderBy;
		setCurrentPageAndUpdateOffset(1);
	}

	@Override
	public String toString() {
		return "Page [page=" + page + ", offset=" + offset + ", limit=" + limit + ", nbComputers=" + nbComputers
				+ ", nbPages=" + nbPages + ", availablePages=" + availablePages + ", like=" + like + ", orderBy=" + orderBy + "]";
	}

}
