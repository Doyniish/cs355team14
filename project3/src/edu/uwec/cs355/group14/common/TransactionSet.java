package edu.uwec.cs355.group14.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class TransactionSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Transaction> transactionSet;
	private double minSupportLevel;
	private double minConfidenceLevel;
		
	/*		Constructors	*/
	public TransactionSet(double minSupportLevel, double minConfidenceLevel) {
		this.transactionSet = new ArrayList<Transaction>();
		this.minSupportLevel = minSupportLevel;
		this.minConfidenceLevel = minConfidenceLevel;
	}
	
	public TransactionSet(TransactionSet transactionSet) {	// copy constructor
		this.transactionSet = new ArrayList<Transaction>();
		for(Transaction transaction : transactionSet.getTransactionSet()) {
			Transaction newTransaction = new Transaction(transaction);
			this.transactionSet.add(newTransaction);
		}
		this.minSupportLevel = transactionSet.getMinSupportLevel();
		this.minConfidenceLevel = transactionSet.getMinConfidenceLevel();
	}
	
	/*		Original Methods 	*/
	public boolean containsSingle(Transaction ts) {
		boolean foundMatch = false;
		if(ts.getSize() > 0) {	
			Collections.sort(ts.getItems());
			int i = 0;
			while(i < transactionSet.size() && !foundMatch) {
				foundMatch = this.getTransaction(i).equals(ts);
				i++;
			}
		}
		return foundMatch;
	}
	
	@Override
	public String toString() {
		String string = "";
		for(int i = 0; i < transactionSet.size()-1; i++) {
			string += transactionSet.get(i) + "\n";
		}
		if(transactionSet.size() > 0) {
			string += transactionSet.get(transactionSet.size()-1);
		}
		return string;
	}
	
	/*		Getters and Setters, etc. 	*/
	public ArrayList<Transaction> getTransactionSet() {
		return transactionSet;
	}
	
	public void setTransactionSet(ArrayList<Transaction> transactionSet) {
		this.transactionSet = transactionSet;
	}
	
	public int getSize() {
		return transactionSet.size();
	}
	
	public Transaction getTransaction(int index) {
		return transactionSet.get(index);
	}
	
	public void add(Transaction transaction) {
		transactionSet.add(transaction);
	}

	public void addAll(TransactionSet ts) {
		transactionSet.addAll(ts.transactionSet);
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
}
