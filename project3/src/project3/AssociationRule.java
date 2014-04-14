package project3;

import java.util.ArrayList;
import static java.lang.Double.valueOf;
import static java.util.Locale.US;
import static java.lang.String.format;

public class AssociationRule {
	private ArrayList<String> antecedent;
	private ArrayList<String> consequent;
	private double confidenceLevel;
	
	public AssociationRule() {
		this.antecedent = new ArrayList<String>();
		this.consequent = new ArrayList<String>();
		this.confidenceLevel = 0;
	}
	
	public AssociationRule(AssociationRule ar) {
		this.antecedent = ar.antecedent;
		this.consequent = ar.consequent;
		this.confidenceLevel = ar.confidenceLevel;
		this.antecedent = new ArrayList<String>();
		for(String antecedent : ar.getAntecedent()) {
			this.antecedent.add(antecedent);
		}
		this.consequent = new ArrayList<String>();
		for(String consequent : ar.getConsequent()) {
			this.consequent.add(consequent);
		}
	}
	
	public AssociationRule(ArrayList<String> antecedent, ArrayList<String> consequent) {
		this.antecedent = new ArrayList<String>();
		this.antecedent.addAll(antecedent);
		this.consequent = new ArrayList<String>();
		this.consequent.addAll(consequent);
		this.confidenceLevel = 0;
	}
	
	public boolean contains(ArrayList<String> items) {
		return this.getAntecedent().containsAll(items) || this.getConsequent().containsAll(items);
	}
	
	
	
	public ArrayList<String> getAntecedent() {
		return antecedent;
	}
	
	public void addAntecedent(String string) {
		this.antecedent.add(string);
	}
	
	public void setAntecedent(ArrayList<String> antecedent) {
		this.antecedent = antecedent;
	}
	
	public ArrayList<String> getConsequent() {
		return consequent;
	}
	
	public void addConsequent(String string) {
		this.consequent.add(string);
	}
	
	public void setConsequent(ArrayList<String> consequent) {
		this.consequent = consequent;
	}
	
	public double getConfidenceLevel() {
		return confidenceLevel;
	}
	
	public void setConfidenceLevel(double confidenceLevel) {
		this.confidenceLevel = valueOf(format(US, "%1$.5f", confidenceLevel));
	}
	
	public String toString() {
		String string = "IF ";
		for(int i = 0; i < antecedent.size(); i++) {
			string = string + antecedent.get(i);
			if(i == antecedent.size()-1) {
				string = string + " THEN ";
			} else {
				string = string + ", ";
			}
		}
		for(int i = 0; i < consequent.size(); i++) {
			string = string + consequent.get(i);
			if(i != consequent.size()-1) {
				string = string + ", ";
			} else {
				string = string + " (" + confidenceLevel + ")";
			}
		}
		return string;
	}

	public int getAnteSize() {
		return this.antecedent.size();
	}
	
	public int getConseqSize() {
		return this.consequent.size();
	}
}
