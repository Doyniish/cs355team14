package project3;

import java.util.ArrayList;

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
}
