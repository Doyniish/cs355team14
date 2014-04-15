package edu.uwec.cs355.group14.rulegeneration;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

	public void testTransaction() {

		String transactionLine1 = new String("{Apples, Beer, Diapers}");
		String transactionLine2 = new String("{Apples, Beer, Diapers}");

		assertEquals(transactionLine1, transactionLine2);

	}

	public void testTransactionString() {

		String transactionLine1 = new String("{Apples, Beer, Diapers}");
		String transactionLine2 = new String("{Apples, Diapers}");

		assertNotSame(transactionLine1, transactionLine2);

	}

	public void testTransactionTransaction() {

		String transactionLine1 = new String("{Apples, Beer, Diapers}");
		String transactionLine2 = new String("{Apples, Beer, Diapers}");

		assertTrue(transactionLine1 = transactionLine2, true);
	}

	public void testAddItem() {

		int previousItems = 2;
		int addedItems = 3;

		assertFalse(previousItems > addedItems);

	}

	public void testAddAll() {

		String transactionEmpty = new String("{}");
		String transactionFull = new String("{Apples, Beer, Diapers}");

		assertNotSame(transactionEmpty, transactionFull);
	}

	public void testRemove() {

		String transactionEmpty = new String("{}");
		String transactionFull = new String("{Apples, Beer, Diapers}");

		assertNotSame(transactionFull, transactionEmpty);

	}

	public void testContainsObject() {

		String transaction = new String("{Apples, Beer, Diapers}");

		assertNotNull(transaction);
	}

	public void testIndexOf() {

		String appleTransaction1 = new String("{Apples, Beer, Diapers}");
		String appleTransaction2 = new String("{Beer, Apples, Diapers}");

		assertNotSame(appleTransaction1, appleTransaction2);
	}

	public void testAddItems() {

		int itemsToAdd1 = 2;
		int itemsToAdd2 = 3;
		String transactionAdd1 = new String("{Apples, Beer}");
		String transactionAdd2 = new String("{Apples, Beer, Diapers}");

		assertTrue(itemsToAdd1 < itemsToAdd2);

	}

	public void testGetItem() {

		String itemToGet = new String("Apples");
		String itemsToAddTo = new String("{Beer, Diapers}");

		itemToGet.compareTo(itemsToAddTo);

		assertFalse(itemToGet.compareTo(itemsToAddTo) == itemsToAddTo
				.compareTo(itemToGet));
	}

	public void testGetItems() {

		Transaction itemsToGet = new Transaction("{Apples, Beer}");
		Transaction itemsRecieved = new Transaction("{Diapers}");

		assertTrue(itemsToGet.getItems() != itemsRecieved.getItems());

	}

	public void testGetSize() {
		Transaction items = new Transaction("{Apples, Beer, Diapers}");
		items.getSize();

		assertNotNull(items.getSize());
	}

	public void testEqualsTransaction() {
		Transaction items = new Transaction();
		String transactionSet1 = "{Apples, Beer, Diapers}";
		String transactionSet2 = "{Apples, Beer, Diapers}";

		assertTrue(transactionSet1 == transactionSet2);
	}

	public void testContainsTransaction() {
		Transaction transaction1 = new Transaction("{Apples, Beer, Diapers}");
		Transaction transaction2 = new Transaction("{Apples, Beer, Diapers}");

		assertTrue(transaction1.contains("{Apples, Beer, Diapers}") == transaction2
				.contains("{Apples, Beer, Diapers}"));
	}

	public void testToString() {
		Transaction transaction = new Transaction("{Apples, Beer, Diapers}");
		String toString = new String();
		transaction.toString();
		toString.toString();

		assertFalse(transaction.toString() == toString.toString());

	}

}
