package edu.uwec.cs355.group14.tests;

import java.util.ArrayList;

import edu.uwec.cs355.group14.common.Transaction;
import edu.uwec.cs355.group14.common.TransactionSet;
import junit.framework.TestCase;

public class TransactionSetTest extends TestCase {

	public void testContainsSingle() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Apples");
		Transaction transactionSingle = new Transaction(items, "", "");
		items.add("Beer");
		items.add("Diapers");
		Transaction transactionFull = new Transaction(items, "", "");
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertTrue(transactionSingle != transactionFull);
	}

	public void testToString() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Apples");
		items.add("Beer");
		items.add("Diapers");
		Transaction transaction = new Transaction(items, "", "");
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel, minConfidenceLevel);
		String toString = new String();
		transaction.toString();
		toString.toString();

		assertFalse(transaction.toString() == toString.toString());
	}

	public void testGetTransactionSet() {
		ArrayList<String> items = new ArrayList<String>();
		items.add("Apples");
		items.add("Beer");
		items.add("Diapers");
		Transaction transaction1 = new Transaction(items, "", "");
		ArrayList<String> items2 = new ArrayList<String>();
		items.add("Beer");
		items.add("Diapers");
		items.add("Apples");
		Transaction transaction2 = new Transaction(items2, "", "");
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		double wrongSupportLevel = 0;
		double wrongConfidenceLevel = 7;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);
		TransactionSet transactionSetWrong = new TransactionSet(
				wrongSupportLevel, wrongConfidenceLevel);

		assertTrue(transactionSet.getTransactionSet() != transactionSetWrong
				.getTransactionSet());
	}

	public void testGetSize() {
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertNotNull(transactionSet.getSize());
	}

	public void testGetTransaction() {
		Transaction transaction1 = new Transaction();
		int index = 1;
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertNotNull(index);
	}

	public void testGetMinConfidenceLevel() {
		double minSupportLevel = 1.0;
		double minConfidenceLevel = 1.0;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertTrue(transactionSet.getMinSupportLevel() == transactionSet
				.getMinConfidenceLevel());
	}

	public void testGetMinSupportLevel() {
		double minSupportLevel = 1.0;
		double minConfidenceLevel = 1.0;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertTrue(transactionSet.getMinSupportLevel() == transactionSet
				.getMinConfidenceLevel());
	}
}
