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
	private static TransactionSet transactionSet = new TransactionSet();
		
	// methods
	public RuleServerResource () {
		System.out.println("RuleServerResource constructor");
	}

	public TransactionSet retrieve() {
		System.out.println("retrieve: " + RuleServerResource.transactionSet);
		return RuleServerResource.transactionSet;
	}

	public void store (TransactionSet transactionSet) {
		System.out.println("store: " + transactionSet);
		RuleServerResource.transactionSet = new TransactionSet(transactionSet);
	}
}