package edu.uwec.cs355.group14.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class RuleSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Rule> ruleSet;
	private double minConfidenceLevel;

	/*			Constructors				*/
	public RuleSet(double minConfidenceLevel) {
		ruleSet = new ArrayList<Rule>();
		this.minConfidenceLevel = minConfidenceLevel;
	}
	
	public RuleSet(RuleSet ruleSet) {		// copy constructor
		this.ruleSet = new ArrayList<Rule>();
		for(Rule rule : ruleSet.getRules()) {
			Rule newRule = new Rule(rule);
			this.ruleSet.add(newRule);
		}
		this.minConfidenceLevel = ruleSet.getMinConfidenceLevel();
	}
	
	/*			Getters and Setters			*/
	public ArrayList<Rule> getRuleSet() {
		return ruleSet;
	}

	public void setRuleSet(ArrayList<Rule> ruleSet) {
		this.ruleSet = ruleSet;
	}
	
	public Rule getRule(int i) {
		return ruleSet.get(i);
	}
	
	public ArrayList<Rule> getRules() {
		return ruleSet;
	}
	
	public int getSize() {
		return ruleSet.size();
	}
	
	public double getMinConfidenceLevel() {
		return minConfidenceLevel;
	}

	public void setMinConfidenceLevel(double minConfidenceLevel) {
		this.minConfidenceLevel = minConfidenceLevel;
	}

	/*			Other Methods				*/
	
	public void add(Rule rule) {
		this.ruleSet.add(rule);
	}
	
	public void addAll(RuleSet ruleSet) {
		this.ruleSet.addAll(ruleSet.getRules());
	}
	
	public boolean containsRule(Rule ar) {
		Collections.sort(ar.getAntecedent());
		Collections.sort(ar.getConsequent());
		
		int i = 0;
		boolean foundMatch = false;
		if(ar.getAntecedentSize() > 0 || ar.getConsequentSize() > 0) {
			while(!foundMatch && i < this.getSize()) {
				foundMatch =	this.getRule(i).getAntecedent().equals(ar.getAntecedent()) &&
								this.getRule(i).getConsequent().equals(ar.getConsequent());
				i++;
			}
		}
		return foundMatch;
	}

	public String toString() {
		String string = "";
		for(int i = 0; i < ruleSet.size()-1; i++) {
			string += i + ": " + ruleSet.get(i) + "\n";
		}
		if(ruleSet.size() > 0) {
			string += ruleSet.get(ruleSet.size()-1);
		}
		return string;
	}	
}