package project3;

import java.util.ArrayList;

public class TransactionSet {
	private double minConfidenceLevel;
	private double minSupportLevel;
	public ArrayList<Transaction> transactionSet;
	
	public TransactionSet() {
		transactionSet = new ArrayList<Transaction>();
	}
	
	public TransactionSet(Transaction transaction) {
		transactionSet = new ArrayList<Transaction>();
		transactionSet.add(transaction);
	}
	
	public TransactionSet(Transaction transaction, int minSupportLevel) {
		transactionSet = new ArrayList<Transaction>();
		transactionSet.add(transaction);
		this.minSupportLevel = (double) minSupportLevel / 10;
	}
	
	
	public TransactionSet(Transaction transaction, double minSupportLevel) {
		transactionSet = new ArrayList<Transaction>();
		transactionSet.add(transaction);
		this.minSupportLevel = minSupportLevel;
	}
	
	public double getMinConfidenceLevel() {
		return minConfidenceLevel;
	}

	public void setMinConfidenceLevel(double minConfidenceLevel) {
		this.minConfidenceLevel = minConfidenceLevel;
	}

	public double getMinSupportLevel() {
		return minSupportLevel;
	}

	public void setMinSupportLevel(double minSupportLevel) {
		this.minSupportLevel = minSupportLevel;
	}

	public void add(Transaction transaction) {
		transactionSet.add(transaction);
	}

	public void addAll(TransactionSet ts) {
		transactionSet.addAll(ts.transactionSet);
	}
	
	public boolean containsSingle(Transaction ts) {
		int i = 0;
		boolean foundMatch = false;
		while(i < this.getSize() && ts.getSize() > 0 && !foundMatch) {
			foundMatch = this.getTransaction(i).equals(ts);
			i++;
		}
		return foundMatch;
	}
	
	public int getSize() {
		return transactionSet.size();
	}
	
	public Transaction getTransaction(int index) {
		return transactionSet.get(index);
	}
	
	public String toString() {
		String trset = "";
		for(int i = 0; i < transactionSet.size(); i++) {
			trset = trset + i + ": " + transactionSet.get(i);
			if(i != transactionSet.size()-1) {
				trset = trset + "\n";
			}
		}
		return trset;
	}

}
