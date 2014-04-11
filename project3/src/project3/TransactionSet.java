package project3;

import java.util.ArrayList;
import java.util.Collections;

public class TransactionSet {
	private ArrayList<Transaction> transactionSet;
	private double minConfidenceLevel;
	private double minSupportLevel;
	
	/*		Constructors	*/
	public TransactionSet() {
		this.transactionSet = new ArrayList<Transaction>();
	}
	
	public TransactionSet(Transaction transaction) {
		this.transactionSet = new ArrayList<Transaction>();
		this.transactionSet.add(transaction);
		this.minSupportLevel = 0;
	}
	
	public TransactionSet(Transaction transaction, double minSupportLevel) {
		this.transactionSet = new ArrayList<Transaction>();
		this.transactionSet.add(transaction);
		this.minConfidenceLevel = 0;
		this.minSupportLevel = minSupportLevel;
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
		String trSet = "";
		for(int i = 0; i < transactionSet.size(); i++) {
			trSet = trSet + i + ": " + transactionSet.get(i);
			if(i != transactionSet.size()-1) {
				trSet = trSet + "\n";
			}
		}
		return trSet;
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
