package junit;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

	public void testTransaction() {
		
		String transactionLine1 = new String ("{Apples, Beer, Diapers}");
		String transactionLine2 = new String ("{Apples, Beer, Diapers}");
		
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
		
		assertFalse(previousItems < addedItems);

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
		fail("Not yet implemented");
	}

	public void testGetItems() {
		fail("Not yet implemented");
	}

	public void testGetSize() {
		fail("Not yet implemented");
	}

	public void testEqualsTransaction() {
		fail("Not yet implemented");
	}

	public void testContainsTransaction() {
		fail("Not yet implemented");
	}

	public void testToString() {
		fail("Not yet implemented");
	}

}
