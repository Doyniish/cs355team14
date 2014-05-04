package edu.uwec.cs355.group14.common;

import java.io.Serializable;
import java.util.ArrayList;

import static java.lang.Double.valueOf;
import static java.lang.String.format;
import static java.util.Locale.US;

public class Rule implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> antecedent;
	private ArrayList<String> consequent;
	private double confidenceLevel;
	
	/*		 	Constructors			*/
	public Rule() {				// empty
		this.antecedent = new ArrayList<String>();
		this.consequent = new ArrayList<String>();
		this.confidenceLevel = -1;
	}
	
	public Rule(ArrayList<String> antecedent, ArrayList<String> consequent) {
		this.antecedent = new ArrayList<String>();
		this.antecedent.addAll(antecedent);
		this.consequent = new ArrayList<String>();
		this.consequent.addAll(consequent);
		this.confidenceLevel = -1;
	}
	
	public Rule(Rule ar) {		// copy
		this.antecedent = new ArrayList<String>();
		for(String antecedent : ar.getAntecedent()) {
			this.antecedent.add(antecedent);
		}
		this.consequent = new ArrayList<String>();
		for(String consequent : ar.getConsequent()) {
			this.consequent.add(consequent);
		}
		this.confidenceLevel = ar.confidenceLevel;
	}
	/*			Other Methods				*/
	@Override
	public String toString() {
		String string = "If ";
		for(int i = 0; i < antecedent.size()-1; i++) {
			string += antecedent.get(i) + " and ";
		}
		if(antecedent.size() > 0) {
			string += antecedent.get(antecedent.size()-1) + " Then ";
		}
		for(int i = 0; i < consequent.size()-1; i++) {
			string += consequent.get(i) + " and ";
		}
		if(consequent.size() > 0) {
			string += consequent.get(consequent.size()-1) + " (" + confidenceLevel + ")";
		}
		return string;
	}
	
	public void addAntecedent(String string) {
		this.antecedent.add(string);
	}
	
	public void addConsequent(String string) {
		this.consequent.add(string);
	}
	
	public boolean contains(ArrayList<String> items) {
		return this.getAntecedent().containsAll(items) || this.getConsequent().containsAll(items);
	}
	
	/*		 	Getters and Setters			 */
	public ArrayList<String> getAntecedent() {
		return antecedent;
	}
	
	public void setAntecedent(ArrayList<String> antecedent) {
		this.antecedent = antecedent;
	}
	
	public ArrayList<String> getConsequent() {
		return consequent;
	}
	
	public void setConsequent(ArrayList<String> consequent) {
		this.consequent = consequent;
	}
	
	public double getConfidenceLevel() {
		return confidenceLevel;
	}
	
	public void setConfidenceLevel(double confidenceLevel) {
		this.confidenceLevel = valueOf(format(US, "%1$.4f", confidenceLevel));	// ensures decimal format
	}
	
	public int getAntecedentSize() {
		return this.antecedent.size();
	}
	
	public int getConsequentSize() {
		return this.consequent.size();
	}
}