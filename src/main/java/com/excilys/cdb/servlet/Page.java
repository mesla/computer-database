package com.excilys.cdb.servlet;

import java.util.ArrayList;

public class Page {
	private int page;
	private int offset;
	private int limit;
	private int nbComputers;
	private int nbPages;
	private ArrayList<Integer> availablePages;
	private String like;

	@Override
	public String toString() {
		return "Page [page=" + page + ", offset=" + offset + ", limit=" + limit + ", nbComputers="
				+ nbComputers + ", nbPages=" + nbPages + ", availablePages=" + availablePages + ", like=" + like +"]";
	}

	private static Page INSTANCE = null;
	
	private Page() {this.setDefault();}
	
	public void setDefault() {
		limit = 10;
		page = 1;
		offset = (page-1) * limit;
		like = "";
	}
	
	public static Page getInstance()
	   {           
	       if (INSTANCE == null)
	       {   INSTANCE = new Page(); 
	       }
	       return INSTANCE;
	   }

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		offset = (page-1)*limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
		setNbPages(nbComputers%limit == 0 ? nbComputers/limit : nbComputers/limit+1);
		setPage(1);
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

	public void setLike(String like) {
		this.like = like;
		setPage(1);
	}
	
	
}
