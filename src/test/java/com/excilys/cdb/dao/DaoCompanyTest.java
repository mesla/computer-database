package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;
import com.excilys.cdb.service.ServiceComputer;

public class DaoCompanyTest{
//	private Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
//    // Tests
//
//	/*
//	 * LV = Legal Valid data
//	 * LI = Legal Invalid data
//	 * I = Illegal
//	 */
//	
//	@Before
//	public final void initBdd() {
//		try {
//			DBManager.getInstance().reload();
//		} catch (IOException | SQLException | ConnectionDBFailedException e) {
//			logger.error(e.getMessage());
//		}
//	}	
//
//	@Test
//	public final void testListCompaniesLV() throws RequestFailedException, ConnectionDBFailedException {
//		//TODO recupérer la connexion dbunit et l'envoyer aux DAO. ils devront prendre en arg la connexion
//		assertEquals(2,DaoCompany.getInstance().listCompanies(2, 5).size());
//	}
//	
//	@Test (expected = RequestFailedException.class)
//	public final void testListCompaniesLI() throws RequestFailedException, ConnectionDBFailedException {
//		DaoCompany.getInstance().listCompanies(Integer.MAX_VALUE, Integer.MAX_VALUE);
//	}
//	
//	@Test (expected = RequestFailedException.class)
//	public final void testListCompaniesI() throws RequestFailedException, ConnectionDBFailedException {		  
//		DaoCompany.getInstance().listCompanies(-1, -1);
//	}

}
