package project3;

import java.util.ArrayList;
import java.util.Collections;

public class AssociationRuleSet {
	private ArrayList<AssociationRule> associationRuleSet;
	private double minConfidenceLevel;

	public AssociationRuleSet() {
		associationRuleSet = new ArrayList<AssociationRule>();
		this.setMinConfidenceLevel(0);
	}
	
	public AssociationRuleSet(double minConfidenceLevel) {
		associationRuleSet = new ArrayList<AssociationRule>();
		this.setMinConfidenceLevel(minConfidenceLevel);
	}
	
	public AssociationRuleSet(AssociationRuleSet associationRuleSet) {	// copy constructor
		this.associationRuleSet = new ArrayList<AssociationRule>();
		for(AssociationRule associationRule : associationRuleSet.getRules()) {
			AssociationRule newAssociationRule = new AssociationRule(associationRule);
			this.associationRuleSet.add(newAssociationRule);
		}
	}
	
	public void add(AssociationRule associationRule) {
		associationRuleSet.add(associationRule);
	}
	
	public void addAll(AssociationRuleSet ars) {
		associationRuleSet.addAll(ars.associationRuleSet);
	}
	
	public AssociationRule getRule(int i) {
		return associationRuleSet.get(i);
	}
	
	public ArrayList<AssociationRule> getRules() {
		return associationRuleSet;
	}
	
	public String toString() {
		String arset = "";
		for(int i = 0; i < associationRuleSet.size(); i++) {
			arset = arset + i + ": " + associationRuleSet.get(i);
			if(i != associationRuleSet.size()-1) {
				arset = arset + "\n";
			}
		}
		return arset;
	}

	public int getSize() {
		return associationRuleSet.size();
	}

	public boolean containsRule(AssociationRule ar) {
		Collections.sort(ar.getAntecedent());
		Collections.sort(ar.getConsequent());
		
		int i = 0;
		boolean foundMatch = false;
		if(ar.getAnteSize() > 0 || ar.getConseqSize() > 0) {
			while(i < this.getSize() && !foundMatch) {
				foundMatch = this.getRule(i).getAntecedent().equals(ar.getAntecedent()) && this.getRule(i).getConsequent().equals(ar.getConsequent()) ;
				i++;
			}
		}
		return foundMatch;
	}

	public double getMinConfidenceLevel() {
		return minConfidenceLevel;
	}

	public void setMinConfidenceLevel(double minConfidenceLevel) {
		this.minConfidenceLevel = minConfidenceLevel;
	}
}