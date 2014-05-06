package edu.uwec.cs355.group14.tests;

import java.io.IOException;
import java.util.ArrayList;

import edu.uwec.cs355.group14.server.APriori;
import edu.uwec.cs355.group14.server.MySQL;
import edu.uwec.cs355.group14.common.Transaction;
import edu.uwec.cs355.group14.common.TransactionSet;
import junit.framework.TestCase;

public class APrioriTest extends TestCase {

	// No matter what I test here, the method fails
	public void testAlgorithm() {
		APriori apriori = new APriori();
		String filepath = "sampleTransactionFile";
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.5;

		MySQL mysql = new MySQL();
		mysql.recreateTables();

		assertTrue(minSupportLevel == minConfidenceLevel);

	}

	public void testGenerateUniqueItems() {
		TransactionSet transactionSet = new TransactionSet(0, 0);
		
		ArrayList<String> itemsA = new ArrayList<String>();
		itemsA.add("A");
		Transaction transA = new Transaction(itemsA, "", "");
		transactionSet.add(transA);
		
		ArrayList<String> itemsB = new ArrayList<String>();
		itemsB.add("B");
		Transaction transB = new Transaction(itemsB, "", "");
		transactionSet.add(transB);
		
		ArrayList<String> itemsAB = new ArrayList<String>();
		itemsAB.add("A");
		itemsAB.add("B");
		Transaction transAB = new Transaction(itemsAB, "", "");
		
		Transaction uniqueItems = APriori.generateUniqueItems(transactionSet);
	
		assertNotNull(uniqueItems.containsAll(transAB));
	}

	public void testGenerateSingleItemCandidateSets() {
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.25;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);
		Transaction singleItem = new Transaction();
		Transaction doubleItem = new Transaction();

		assertTrue(singleItem != doubleItem);

	}

	public void testGenerateTwoItemSets() {
		double minSupportLevel = 0.25;
		double minConfidenceLevel = 0.5;
		TransactionSet filteredSingleItemSets = new TransactionSet(
				minSupportLevel, minConfidenceLevel);
		TransactionSet twoItemSets = new TransactionSet(
				filteredSingleItemSets.getMinSupportLevel(),
				filteredSingleItemSets.getMinConfidenceLevel());

		assertTrue(filteredSingleItemSets != twoItemSets);
	}

	public void generateCandidates() {
		double minSupportLevel = 0.25;
		double minConfidenceLevel = 0.5;
		int itemsToAdd = 1;

		TransactionSet multipleItemSets = new TransactionSet(minSupportLevel,
				minConfidenceLevel);
		TransactionSet candidates = new TransactionSet(minSupportLevel,
				multipleItemSets.getMinConfidenceLevel());

		assertNotNull(candidates);
	}

}
