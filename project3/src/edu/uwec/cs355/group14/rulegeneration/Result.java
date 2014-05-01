package edu.uwec.cs355.group14.rulegeneration;

import java.util.ArrayList;

public class Result {
	private TransactionSet transactionSet = null;
	private AssociationRuleSet associationRuleSet = null;
	private ArrayList<String> errorLog = null;
	
	public TransactionSet getTransactionSet() {
		return transactionSet;
	}
	public void setTransactionSet(TransactionSet transactionSet) {
		this.transactionSet = new TransactionSet(transactionSet);
	}
	public AssociationRuleSet getAssociationRuleSet() {
		return associationRuleSet;
	}
	public void setAssociationRuleSet(AssociationRuleSet associationRuleSet) {
		this.associationRuleSet = new AssociationRuleSet(associationRuleSet);
	}
	public ArrayList<String> getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(ArrayList<String> errorLog) {
		this.errorLog = errorLog;
	}
	public String printErrorLog() {
		String errorString = "";
		for(int i = 0; i < errorLog.size(); i++) {
			errorString = errorString + errorLog.get(i);
			if(i != errorLog.size() - 1) {
				errorString = errorString + "\n";
			}
		}
		return errorString;
	}
}