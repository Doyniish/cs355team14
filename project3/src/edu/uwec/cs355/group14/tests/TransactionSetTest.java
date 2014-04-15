package edu.uwec.cs355.group14.tests;

<<<<<<< HEAD
import edu.uwec.cs355.group14.rulegeneration.*;
=======
<<<<<<< HEAD
import edu.uwec.cs.cs355.group14.rulegeneration.TransactionSet;
=======
import project3.Transaction;
import project3.TransactionSet;
>>>>>>> 7cf677f51313ca889a1220102f27179ca17e3be2
>>>>>>> 9d3885b106cf5af43f532174a3c7f162b9cf16a8
import junit.framework.TestCase;

public class TransactionSetTest extends TestCase {

	public void testContainsSingle() {
		Transaction transactionSingle = new Transaction("{Apples}");
		Transaction transactionFull = new Transaction("{Apples, Beer, Diapers");
		double minSupportLevel = 1;
		double minConfidenceLevel = 1;
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertTrue(transactionSet.containsSingle(transactionSingle) != transactionSet
				.containsSingle(transactionFull));
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
		TransactionSet transactionSet = new TransactionSet(minSupportLevel,
				minConfidenceLevel);

		assertTrue(transactionSet.getTransactionSet() != transactionSet
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

		assertNotNull(transactionSet.getTransaction(index));
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

	/*
	 * 
	 * public void testTransactionSet() { // TransactionSet transactionSet = new
	 * TransactionSet (transaction, 1, // 2);
	 * 
	 * fail("Not yet implemented"); }
	 * 
	 * public void testTransactionSetTransaction() {
	 * fail("Not yet implemented"); }
	 * 
	 * public void testTransactionSetTransactionDouble() {
	 * fail("Not yet implemented"); } public void testSetTransactionSet() {
	 * fail("Not yet implemented"); } public void testAdd() {
	 * fail("Not yet implemented"); }
	 * 
	 * public void testAddAll() { fail("Not yet implemented"); }
	 * 
	 * public void testSetMinConfidenceLevel() { fail("Not yet implemented"); }
	 * 
	 * public void testSetMinSupportLevel() { fail("Not yet implemented"); }
	 * 
	 * public void testObject() { fail("Not yet implemented"); }
	 * 
	 * public void testGetClass() { fail("Not yet implemented"); }
	 * 
	 * public void testHashCode() { fail("Not yet implemented"); }
	 * 
	 * public void testEquals() { fail("Not yet implemented"); }
	 * 
	 * public void testClone() { fail("Not yet implemented"); }
	 * 
	 * public void testToString1() { fail("Not yet implemented"); }
	 * 
	 * public void testNotify() { fail("Not yet implemented"); }
	 * 
	 * public void testNotifyAll() { fail("Not yet implemented"); }
	 * 
	 * public void testWaitLong() { fail("Not yet implemented"); }
	 * 
	 * public void testWaitLongInt() { fail("Not yet implemented"); }
	 * 
	 * public void testWait() { fail("Not yet implemented"); }
	 * 
	 * public void testFinalize() { fail("Not yet implemented"); }
	 */

}
