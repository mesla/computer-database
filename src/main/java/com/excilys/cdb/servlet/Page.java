package com.excilys.cdb.servlet;

import java.util.ArrayList;

public class Page {
	private int page;
	private int offset;
	private int limit;
	private int nbComputers;
	private int nbPages;
	private ArrayList<Integer> availablePages;	

	@Override
	public String toString() {
		return "Page [page=" + page + ", offset=" + offset + ", limit=" + limit + ", nbComputers="
				+ nbComputers + ", nbPages=" + nbPages + ", availablePages=" + availablePages + "]";
	}

	private static Page INSTANCE = null;
	
	private Page() {this.setDefault();}
	
	public void setDefault() {
		this.limit = 10;
		this.page = 1;
		this.offset = (page-1) * limit;
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
		if((page-1)*limit < nbComputers) {
			this.limit = limit;
			offset = (page-1)*limit;
		}
		else this.setDefault();
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
	
	
}
