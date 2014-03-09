package project3;

import java.util.ArrayList;

public class TransactionSet {
	private double minConfidenceLevel;
	private double minSupportLevel;
	private ArrayList<Transaction> transactionSet;
	
	public TransactionSet(Transaction transaction) {
		transactionSet = new ArrayList<Transaction>();
		transactionSet.add(transaction);
	}
	
	public TransactionSet() {
		transactionSet = new ArrayList<Transaction>();
	}
	
	public void add(Transaction transaction) {
		transactionSet.add(transaction);
	}

}
