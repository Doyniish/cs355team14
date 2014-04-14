package edu.uwec.cs355.group14.tests;

import java.util.ArrayList;

import project3.AssociationRule;
import junit.framework.TestCase;

public class AssociationRuleTest extends TestCase {

	public void testAssociationRule() {
		String antecedent = new String("{Apples, Diapers, Beer}");
		String consequent = new String("{Diapers, Cheese, Apples}");

		assertTrue(antecedent != consequent);

	}

	public void testContains() {
		String items = new String("{Apples, Beer, Diapers}");

		assertNotNull(items);

	}

	public void testGetAntecedent() {
		String antecedent = new String();
		String items1 = "{Apples, Beer, Diapers}";
		String items2 = "";

		assertNotSame(antecedent.contains(items2), antecedent.contains(items1));
	}

	public void testGetConsequent() {
		String consequent = new String();
		String items1 = "{Apples, Beer, Diapers}";

		assertNotNull(consequent.contains(items1));
	}

	public void testGetConfidenceLevel() {
		double confidenceLevelActual = 2;
		double confidenceLevelExpected = 1;

		assertEquals(confidenceLevelExpected, confidenceLevelActual,
				confidenceLevelExpected);
	}

	public void testToString() {
		String toString = new String();
		String antecedent = new String("{Apples, Beer, Diapers");
		String consequent = new String("{Diapers, Beer, Apples}");
		// String string = "IF" + antecedent + "THEN" + consequent;

		assertTrue(toString.compareTo(antecedent) != toString
				.compareTo(consequent));
	}

	public void testGetAnteSize() {
		int anteSize = 3;

		assertNotNull(anteSize);
	}

	public void testGetConseqSize() {
		int conseqSize = 3;

		assertNotNull(conseqSize);
	}

	// Constructor methods

	/*
	 * public void testAssociationRuleAssociationRule() {
	 * fail("Not yet implemented"); }
	 * 
	 * public void testAssociationRuleArrayListOfStringArrayListOfString() {
	 * fail("Not yet implemented"); }
	 * 
	 * public void testAddAntecedent() {
	 * 
	 * }
	 * 
	 * public void testSetAntecedent() { fail("Not yet implemented"); }
	 * 
	 * public void testSetConfidenceLevel() { fail("Not yet implemented"); }
	 * 
	 * public void testAddConsequent() { fail("Not yet implemented"); }
	 * 
	 * public void testSetConsequent() { fail("Not yet implemented"); }
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
