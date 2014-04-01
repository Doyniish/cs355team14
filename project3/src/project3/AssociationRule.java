package project3;

import java.util.ArrayList;

public class AssociationRule {
	private ArrayList<String> antecedent;
	private ArrayList<String> consequent;
	private double minConfidenceLevel;
	
	public AssociationRule() {
		this.antecedent = new ArrayList<String>();
		this.consequent = new ArrayList<String>();
		this.minConfidenceLevel = 0;
	}
	
	@SuppressWarnings("unchecked")
	public AssociationRule(AssociationRule ar) {
		this.minConfidenceLevel = ar.minConfidenceLevel;
		this.antecedent = (ArrayList<String>) ar.antecedent.clone();
		this.consequent = (ArrayList<String>) ar.consequent.clone();
	}
	
	public AssociationRule(ArrayList<String> antecedent, ArrayList<String> consequent) {
		this.antecedent = new ArrayList<String>();
		this.antecedent.addAll(antecedent);
		this.consequent = new ArrayList<String>();
		this.consequent.addAll(consequent);
		minConfidenceLevel = 0;
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
	
	public double getMinConfidenceLevel() {
		return minConfidenceLevel;
	}
	
	public void setMinConfidenceLevel(double minConfidenceLevel) {
		this.minConfidenceLevel = minConfidenceLevel;
	}
	
	public String toString() {
		String string = "If ";
		for(int i = 0; i < antecedent.size(); i++) {
			string = string + antecedent.get(i);
			if(i == antecedent.size()-1) {
				string = string + " Then ";
			} else {
				string = string + ", ";
			}
		}
		for(int i = 0; i < consequent.size(); i++) {
			string = string + consequent.get(i);
			if(i != consequent.size()-1) {
				string = string + ", ";
			} else {
				string = string + " (" + minConfidenceLevel + ")";
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
