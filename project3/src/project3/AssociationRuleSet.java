package project3;

import java.util.ArrayList;
import java.util.Collections;

public class AssociationRuleSet {
	public ArrayList<AssociationRule> associationRuleSet;

	public AssociationRuleSet() {
		associationRuleSet = new ArrayList<AssociationRule>();
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
	
	@Override
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
}