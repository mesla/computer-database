package com.excilys.cdb.dao;

public class DaoComputerTest {

//	private Logger logger = LoggerFactory.getLogger(ServiceComputer.class);
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
//	
//	/*
//	 * LV = Legal Valid data
//	 * LI = Legal Invalid data
//	 * I = Illegal
//	 */
//	
//	
//	//LIST
//	
//	@Test
//	public final void testListComputersLV() throws RequestFailedException, ConnectionDBFailedException {
//		assertEquals(2,DaoComputer.getInstance().listComputer(2, 5, "").size());
//	}
//	
//	@Test (expected = RequestFailedException.class)
//	public final void testListComputersLI() throws RequestFailedException, ConnectionDBFailedException {
//		DaoComputer.getInstance().listComputer(Integer.MAX_VALUE, Integer.MAX_VALUE, "");
//	}
//	
//	@Test (expected = RequestFailedException.class)
//	public final void testListComputersI() throws RequestFailedException, ConnectionDBFailedException {		  
//		DaoComputer.getInstance().listComputer(-1, -1, "");
//	}
//	
//	
//	//READ
//	
//	@Test
//	public final void testReadLV() throws SQLException, ConnectionDBFailedException, RequestFailedException {
//		assertTrue(DaoComputer.getInstance().read(3) instanceof ModelComputer);
//	}
//	
//	@Test (expected = RequestFailedException.class)
//	public final void testReadLI() throws SQLException, ConnectionDBFailedException, RequestFailedException {
//		DaoComputer.getInstance().read(Integer.MAX_VALUE);
//	}
//	
//	@Test (expected = RequestFailedException.class)
//	public final void testReadI() throws SQLException, ConnectionDBFailedException, RequestFailedException {
//		DaoComputer.getInstance().read(-1);
//	}
//	
//	
//	//CREATE
//	
//	@Test
//	public final void testCreateLV() throws SQLException, RequestFailedException, ConnectionDBFailedException {
//		DaoComputer.getInstance().create(new ModelComputer(
//				null,
//				"test sample LV",
//				Timestamp.valueOf("2010-10-10 00:05:06"),
//				Timestamp.valueOf("2011-10-10 05:05:06"),
//				new ModelCompany(3, DaoCompany.getInstance().getMatch(3))));
//	}
//	
//	
//	@Test (expected = RequestFailedException.class) //test condition on existence of a company
//	public final void testCreateLI() throws SQLException, RequestFailedException, ConnectionDBFailedException {
//		DaoComputer.getInstance().create(new ModelComputer(
//				null,
//				"test sample LI",
//				Timestamp.valueOf("2010-10-10 00:05:06"),
//				Timestamp.valueOf("2011-10-10 05:05:06"),
//				new ModelCompany(Integer.MAX_VALUE, "not a real company")));
//	}
//	
//	@Test (expected = IllegalArgumentException.class) //test condition on date invalid
//	public final void testCreateI() throws SQLException, RequestFailedException, ConnectionDBFailedException {
//		DaoComputer.getInstance().create(new ModelComputer(
//				null,
//				"test sample I",
//				Timestamp.valueOf("2010-10-10"),
//				Timestamp.valueOf("2009"),
//				new ModelCompany(3, DaoCompany.getInstance().getMatch(3))));
//	}
	
	
}
