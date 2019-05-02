package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;

import org.dbunit.DBTestCase;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import com.excilys.cdb.exception.ConnectionDBFailedException;
import com.excilys.cdb.exception.RequestFailedException;

public class DaoCompanyTest{
	/*
	//Methodes DBUnit
	
	@Override
	protected IDataSet getDataSet() throws Exception {
		return new FlatXmlDataSetBuilder().build(new FileInputStream("computer-database-db.xml"));
	}
	
    protected DatabaseOperation getSetUpOperation() throws Exception
    {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception
    {
        return DatabaseOperation.NONE;
    }
    
*/
    // Tests

	/*
	 * LV = Legal Valid data
	 * LI = Legal Invalid data
	 * I = Illegal
	 */

	@Test
	public final void testListCompaniesLV() throws RequestFailedException, ConnectionDBFailedException {
		//TODO recupérer la connexion dbunit et l'envoyer aux DAO. ils devront prendre en arg la connexion
		assertEquals(2,DaoCompany.getInstance().listCompanies(2, 5).size());
	}
	
	@Test (expected = RequestFailedException.class)
	public final void testListCompaniesLI() throws RequestFailedException, ConnectionDBFailedException {
		DaoCompany.getInstance().listCompanies(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}
	
	@Test (expected = RequestFailedException.class)
	public final void testListCompaniesI() throws RequestFailedException, ConnectionDBFailedException {		  
		DaoCompany.getInstance().listCompanies(-1, -1);
	}

}
