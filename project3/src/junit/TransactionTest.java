package junit;

import project3.Transaction;
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
		fail("Not yet implemented");
	}

	public void testAddAll() {
		fail("Not yet implemented");
	}

	public void testRemove() {
		fail("Not yet implemented");
	}

	public void testContainsObject() {
		fail("Not yet implemented");
	}

	public void testIndexOf() {
		fail("Not yet implemented");
	}

	public void testAddItems() {
		fail("Not yet implemented");
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
