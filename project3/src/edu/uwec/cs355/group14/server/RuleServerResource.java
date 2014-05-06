package edu.uwec.cs355.group14.server;

import org.restlet.resource.ServerResource;

import edu.uwec.cs355.group14.client.RuleResource;
import edu.uwec.cs355.group14.common.*;

// receives the parameters, store the transaction set in MySQL, generate the
// appropriate rules, store those rules in MySQL, and return the rule set to
// the client system as a serializable object.

public class RuleServerResource extends ServerResource implements RuleResource {
	// data
	private static double minSupportLevel = -1;
	private static double minConfidenceLevel = -1;
	private static TransactionSet transactionSet = null;
	private static RuleSet ruleSet = new RuleSet(0.0);
		
	// methods
	public RuleServerResource () {
		System.out.println("RuleServerResource constructor");
	}

	public RuleSet retrieve() {
		return RuleServerResource.ruleSet;
	}

	public void store(Result result) {
		RuleServerResource.minSupportLevel = result.getTransactionSet().getMinSupportLevel();
		RuleServerResource.minConfidenceLevel = result.getTransactionSet().getMinConfidenceLevel();
		RuleServerResource.transactionSet = result.getTransactionSet();
		RuleServerResource.ruleSet = APriori.generateRules(RuleServerResource.transactionSet, RuleServerResource.minSupportLevel, RuleServerResource.minConfidenceLevel);
		MySQL mysql = new MySQL();
		mysql.recreateTables();
		mysql.saveData(RuleServerResource.transactionSet, RuleServerResource.ruleSet);
	}
}