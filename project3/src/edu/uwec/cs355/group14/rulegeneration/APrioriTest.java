package edu.uwec.cs355.group14.rulegeneration;

import java.io.IOException;
import junit.framework.TestCase;

public class APrioriTest extends TestCase {

	public void testMain() {

	}

	public void testTest1() {

	}

	public void testAlgorithm() {
		APriori apriori = new APriori();
		String filepath = "sampleTransactionFile";
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.5;
		
				
		MySQL mysql = new MySQL();
		mysql.recreateTables();
	
		try {
			assertNotNull(APriori.algorithm(filepath, minSupportLevel, minConfidenceLevel));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void testIsValidDate() {
		APriori apriori = new APriori();
		String dateString = new String("yyyy-MM-dd");
		String dateStringIncorrect = new String("yy-MM-dd");
		int date;
		date = Integer.parseInt(dateString);
		int year = date / 10000;
		int month = (date % 10000) / 100;
		int day = date % 100;
		int daysInMonth = 31;

		boolean yearOk = (year >= 1581) && (year <= 2500);
		boolean monthOk = (month >= 1) && (month <= 12);
		//boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));
		
		assertTrue(APriori.isValidDate(dateString) != APriori.isValidDate(dateStringIncorrect));
	}
	
	public void testGenerateUniqueItems() {
		APriori apriori = new APriori();
		double minSupportLevel = 0.25;
		Transaction uniqueItems = new Transaction(minSupportLevel);
		
		assertNotNull(uniqueItems.contains(apriori));
	}
	
	public void testGenerateSingleItemCandidateSets() {
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.25;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel, minConfidenceLevel);
		Transaction singleItem = new Transaction(minSupportLevel);
		Transaction doubleItem = new Transaction(minConfidenceLevel);
		
		assertTrue(singleItem != doubleItem);
		
	}
	
	public void testGenerateTwoItemSets() {
		double minSupportLevel = 0.25;
		double minConfidenceLevel = 0.5;
		TransactionSet filteredSingleItemSets = new TransactionSet(minSupportLevel, minConfidenceLevel);
		TransactionSet twoItemSets = new TransactionSet(filteredSingleItemSets.getMinSupportLevel(), filteredSingleItemSets.getMinConfidenceLevel());
		
		assertTrue(filteredSingleItemSets != twoItemSets);
	}
	
	public void generateCandidates() {
		double minSupportLevel = 0.25;
		double minConfidenceLevel = 0.5;
		int itemsToAdd = 1;
		
		TransactionSet multipleItemSets = new TransactionSet(minSupportLevel, minConfidenceLevel);
		TransactionSet candidates = new TransactionSet(minSupportLevel, multipleItemSets.getMinConfidenceLevel());
		
		assertNotNull(candidates);
	}

	public void testObject() {
		fail("Not yet implemented");
	}

	public void testGetClass() {
		fail("Not yet implemented");
	}

	public void testHashCode() {
		fail("Not yet implemented");
	}

	public void testEquals() {
		fail("Not yet implemented");
	}

	public void testClone() {
		fail("Not yet implemented");
	}

	public void testToString() {
		fail("Not yet implemented");
	}

	public void testNotify() {
		fail("Not yet implemented");
	}

	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	public void testWaitLong() {
		fail("Not yet implemented");
	}

	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	public void testWait() {
		fail("Not yet implemented");
	}

	public void testFinalize() {
		fail("Not yet implemented");
	}

}
