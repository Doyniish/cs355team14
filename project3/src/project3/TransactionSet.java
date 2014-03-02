package project3;

import java.util.ArrayList;

public class TransactionSet {
	private double minConfidenceLevel;
	private double minSupportLevel;
	private ArrayList<Transaction> transactionSet;
	
	public void add(Transaction transaction) {
		transactionSet.add(transaction);
	}

}
