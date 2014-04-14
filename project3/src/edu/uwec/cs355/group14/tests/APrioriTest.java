package edu.uwec.cs355.group14.tests;

import project3.APriori;
import project3.Transaction;
import project3.TransactionSet;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import junit.framework.TestCase;

public class APrioriTest extends TestCase {

	public void testMain() {

	}

	public void testTest1() {

	}

	public void testAlgorithm() {

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
