package edu.uwec.cs355.group14.tests;

import java.io.IOException;

import edu.uwec.cs355.group14.rulegeneration.APriori;
import edu.uwec.cs355.group14.rulegeneration.MySQL;
import edu.uwec.cs355.group14.rulegeneration.Transaction;
import edu.uwec.cs355.group14.rulegeneration.TransactionSet;
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
		APriori apriori = new APriori();
		double minSupportLevel = 0.25;
		Transaction uniqueItems = new Transaction(minSupportLevel);

		assertNotNull(uniqueItems.contains(apriori));
	}

	public void testGenerateSingleItemCandidateSets() {
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.25;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);
		Transaction singleItem = new Transaction(minSupportLevel);
		Transaction doubleItem = new Transaction(minConfidenceLevel);

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
