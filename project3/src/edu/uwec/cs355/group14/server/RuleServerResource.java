package edu.uwec.cs355.group14.server;

import org.restlet.resource.ServerResource;

import edu.uwec.cs355.group14.common.*;

// receives the parameters, store the transaction set in MySQL, generate the
// appropriate rules, store those rules in MySQL, and return the rule set to
// the client system as a serializable object.

public class RuleServerResource extends ServerResource implements RuleResource {
	// data
	private double minSupportLevel = -1;
	private double minConfidenceLevel = -1;
	private TransactionSet transactionSet = null;
	private RuleSet ruleSet = null;
		
	// methods
	public RuleServerResource () {
		System.out.println("RuleServerResource constructor");
	}

	public TransactionSet retrieveTS() {
		System.out.println("retrieve: " + transactionSet);
		return transactionSet;
	}
	
	public void generateRules() {
		System.out.println(minSupportLevel);
		System.out.println(minConfidenceLevel);
	}

	public void store (TransactionSet transactionSet) {
		System.out.println("store: " + transactionSet);
		transactionSet = new TransactionSet(transactionSet);
	}

	public RuleSet retrieveRS() {
		return ruleSet;
	}

	@Override
	public void storeTS(TransactionSet transactionSet) {
		this.transactionSet = transactionSet;
	}

	@Override
	public void storeMCL(double minimumConfidenceLevel) {
		this.minConfidenceLevel = minimumConfidenceLevel;
	}

	@Override
	public void storeMSL(double minimumSupportLevel) {
		this.minSupportLevel = minimumSupportLevel;
	}
}