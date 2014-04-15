package edu.uwec.cs355.group14.rulegeneration;

import junit.framework.TestCase;

public class TransactionSetTest extends TestCase {

	public void testContainsSingle() {
		Transaction transactionSingle = new Transaction("{Apples}");
		Transaction transactionFull = new Transaction("{Apples, Beer, Diapers");
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertTrue(transactionSingle != transactionFull);
	}

	public void testToString() {
		Transaction transaction = new Transaction("{Apples, Beer, Diapers}");
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);
		String toString = new String();
		transaction.toString();
		toString.toString();

		assertFalse(transaction.toString() == toString.toString());
	}

	public void testGetTransactionSet() {
		Transaction transaction1 = new Transaction("{Apples, Beer, Diapers}");
		Transaction transaction2 = new Transaction("{Beer, Diapers, Apples}");
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
