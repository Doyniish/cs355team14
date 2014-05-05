package edu.uwec.cs355.group14.server;

import java.util.Collections;
import edu.uwec.cs355.group14.common.Rule;
import edu.uwec.cs355.group14.common.RuleSet;
import edu.uwec.cs355.group14.common.Timer;
import edu.uwec.cs355.group14.common.Transaction;
import edu.uwec.cs355.group14.common.TransactionSet;

public class APriori {
	public static RuleSet generateRules(TransactionSet transactionSet, double minSupportLevel, double minConfidenceLevel) {
		Timer timer = new Timer();
		timer.startTimer();

		RuleSet ruleSet = algorithm(transactionSet, minSupportLevel, minConfidenceLevel);
		
		timer.stopTimer();
		System.out.println("Timer: " + timer.getTotal() + " ms");
		return ruleSet;
	}
	
	private static RuleSet algorithm(TransactionSet transactionsFromFile, double minSupportLevel, double minConfidenceLevel) {
		/* Frequent item sets generation */
		TransactionSet transactionResults = new TransactionSet(minSupportLevel, minConfidenceLevel);
		
		// create and filter out single items not meeting minSupportLevel
		TransactionSet candidateSets = generateSingleItemCandidateSets(transactionsFromFile);		
		TransactionSet filteredSets = filterItems(candidateSets, transactionsFromFile);
		
		// create and filter two-item sets
		candidateSets = generateTwoItemSets(filteredSets);
		filteredSets = removeImpossibleCandidates(candidateSets, filteredSets, 1);
		filteredSets = filterItems(candidateSets, transactionsFromFile);
		transactionResults.addAll(filteredSets);
			
		int count = 3;
		TransactionSet previousSets = null;
		
		while(filteredSets.getSize() > 1) {		// continue cycle until there are 0-1 elements
			previousSets = filteredSets;
			candidateSets = generateCandidates(filteredSets, count);
			filteredSets = removeImpossibleCandidates(candidateSets, previousSets, count - 1);
			filteredSets = filterItems(filteredSets, transactionsFromFile);
			
			transactionResults.addAll(filteredSets);
			++count;
		}
		
		/* Association rule sets */
		RuleSet ruleSets = new RuleSet(minConfidenceLevel);
		ruleSets = generateAllPossibleRules(transactionResults);
		ruleSets = filterByConfidence(ruleSets, transactionsFromFile, minConfidenceLevel, transactionsFromFile.getSize());
		return ruleSets;
	}

	private static TransactionSet generateSingleItemCandidateSets(TransactionSet transactionSet) {
		Transaction uniqueItems = generateUniqueItems(transactionSet);

		TransactionSet singleItemCandidateSets = new TransactionSet(transactionSet.getMinSupportLevel(), transactionSet.getMinConfidenceLevel());
		for(int i = 0; i < uniqueItems.getSize(); i++) {
			Transaction singleItem = new Transaction();
			singleItem.add(uniqueItems.getItem(i));
			singleItemCandidateSets.add(singleItem);
		}
		return singleItemCandidateSets;
	}
	
	private static Transaction generateUniqueItems(TransactionSet transactions) {
		Transaction uniqueItems = new Transaction();
		
		for(int i = 0; i < transactions.getSize(); i++) {	// for each transaction in transactionSet
			for(int j = 0; j < transactions.getTransaction(i).getSize(); j++) { 	// for each item in transaction
				String itemSearched = transactions.getTransaction(i).getItem(j);
				if(!uniqueItems.contains(itemSearched)) {
					uniqueItems.add(itemSearched);
				}
			}
		}
		Collections.sort(uniqueItems.getItems());
		return uniqueItems;
	}
	
	private static TransactionSet generateTwoItemSets(TransactionSet filteredSingleItemSets) {
		TransactionSet twoItemSets = new TransactionSet(filteredSingleItemSets.getMinSupportLevel(), filteredSingleItemSets.getMinConfidenceLevel());
		for(int i = 0; i < filteredSingleItemSets.getSize(); i++) {
			for(int j = i + 1; j < filteredSingleItemSets.getSize(); j++) {
				Transaction set = new Transaction();
				set.add(filteredSingleItemSets.getTransaction(i).getItem(0));
				set.add(filteredSingleItemSets.getTransaction(j).getItem(0));
				twoItemSets.add(set);
			}
		}
		return twoItemSets;
	}
	
	private static TransactionSet generateCandidates(TransactionSet multipleItemSets, int itemsToAdd) {
		Transaction uniqueItems = generateUniqueItems(multipleItemSets);
		TransactionSet candidatesWithDuplicates = generateCandidatesRecursive(new Transaction(), uniqueItems, multipleItemSets.getMinSupportLevel(), multipleItemSets.getMinConfidenceLevel(), itemsToAdd); // wrapper method
		TransactionSet candidates = new TransactionSet(multipleItemSets.getMinSupportLevel(), multipleItemSets.getMinConfidenceLevel());

		for(int i = 0; i < candidatesWithDuplicates.getSize(); i++) {
			Transaction transaction = candidatesWithDuplicates.getTransaction(i);
			
			if(!candidates.containsSingle(transaction)) {
				candidates.add(transaction);
			}
		}
		return candidates;
	}
	
	private static TransactionSet generateCandidatesRecursive(Transaction transaction, Transaction uniqueItems, double minSupportLevel, double minConfidenceLevel, int itemsToAdd) {
		TransactionSet results = new TransactionSet(minSupportLevel, minConfidenceLevel);
		if(itemsToAdd == 1) {
			for(int i = 0; i < uniqueItems.getSize(); i++) {
				Transaction transaction2 = new Transaction(transaction);
				transaction2.add(uniqueItems.getItem(i));
				results.add(transaction2);
			}
			return results;
		} else {
			for(int i = 0; i < uniqueItems.getSize(); i++) {
				Transaction transaction2 = new Transaction(transaction);
				transaction2.add(uniqueItems.getItem(i));
				Transaction uniqueItems2 = new Transaction(uniqueItems);
				uniqueItems2.remove(uniqueItems.getItem(i));
				TransactionSet preResults = generateCandidatesRecursive(transaction2, uniqueItems2, minSupportLevel, minConfidenceLevel, itemsToAdd - 1);
				results.addAll(preResults);
			}
		}
		return results;
	}
	
	private static TransactionSet filterItems(TransactionSet itemCandidateSets, TransactionSet transactionSet) {
		countItems(itemCandidateSets, transactionSet);
		
		TransactionSet filteredItems = new TransactionSet(itemCandidateSets.getMinSupportLevel(), itemCandidateSets.getMinConfidenceLevel());
		for(int i = 0; i < itemCandidateSets.getSize(); i++) {
			Transaction transaction = itemCandidateSets.getTransaction(i);
			transaction.setSupportLevel((double) transaction.getCount() / (double) transactionSet.getSize());

			if(transaction.getSupportLevel() >= transactionSet.getMinSupportLevel()) {
				filteredItems.add(transaction);
			}
		}
		return filteredItems;
	}

	private static TransactionSet removeImpossibleCandidates(TransactionSet multipleItemSets, TransactionSet previousItemSets, int count) {
		TransactionSet filteredItems = new TransactionSet(multipleItemSets.getMinSupportLevel(), multipleItemSets.getMinConfidenceLevel());
		for(int i = 0; i < multipleItemSets.getSize(); i++) {
			TransactionSet singleSet = new TransactionSet(multipleItemSets.getMinSupportLevel(), multipleItemSets.getMinConfidenceLevel());
			singleSet.add(multipleItemSets.getTransaction(i));
			TransactionSet requiredItems = generateCandidates(singleSet, count);
			
			boolean missedMatch = false;
			for(int j = 0; j < requiredItems.getSize(); j++) {
				if(!previousItemSets.containsSingle(requiredItems.getTransaction(j))) {
					missedMatch = true;
				}
			}
			if(!missedMatch) {
				filteredItems.add(multipleItemSets.getTransaction(i));
			}
		}
		return filteredItems;
	}
	
	private static void countItems(TransactionSet uniqueSets, TransactionSet transactionsFromFile) {
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {
			for(int j = 0; j < uniqueSets.getSize(); j++) {
				if(transactionsFromFile.getTransaction(i).containsAll(uniqueSets.getTransaction(j))) {
					uniqueSets.getTransaction(j).incrementCount();
				}
			}
		}
	}

	private static RuleSet generateAllPossibleRules(TransactionSet results) {
		RuleSet ruleSetWithDuplicates = new RuleSet(results.getMinConfidenceLevel());
		for(int i = 0; i < results.getSize(); i++) {
			RuleSet rules = generatePossibleRulesRecursive(results.getTransaction(i), new Rule(), results.getMinConfidenceLevel(), results.getTransaction(i).getSize() - 1); // wrapper method
			ruleSetWithDuplicates.addAll(rules);
		}
		RuleSet ruleSets = new RuleSet(results.getMinConfidenceLevel());
		for(int i = 0; i < ruleSetWithDuplicates.getSize(); i++) {
			Rule rule = ruleSetWithDuplicates.getRule(i);
			if(!ruleSets.containsRule(rule)) {
				ruleSets.add(rule);
			}
		}
		return ruleSets;
	}

	private static RuleSet generatePossibleRulesRecursive(Transaction transaction, Rule rule, double minConfidenceLevel, int itemsToAdd) {
		RuleSet ruleSet = new RuleSet(minConfidenceLevel);
		if(itemsToAdd == 1) {
			for(int i = 0; i < transaction.getSize(); i++) {
				Rule rule2 = new Rule(rule);
				String item = transaction.getItem(i);
				rule2.addAntecedent(item);
				Transaction transaction2 = new Transaction(transaction);
				transaction2.remove(item);
				ruleSet.addAll(generatePossibleConsequentsRecursive(transaction2, rule2, minConfidenceLevel));
			}
			return ruleSet;
		} else {
			if(rule.getAntecedent().size() > 0) {
				ruleSet.add(rule);
			}
			for(int i = 0; i < transaction.getSize(); i++) {
				Rule rule2 = new Rule(rule);
				rule2.addAntecedent(transaction.getItem(i));
				Transaction transaction2 = new Transaction(transaction);
				transaction2.remove(transaction.getItem(i));
				ruleSet.addAll(generatePossibleConsequentsRecursive(transaction2, rule2, minConfidenceLevel));
				RuleSet preResults = generatePossibleRulesRecursive(transaction2, rule2, minConfidenceLevel, itemsToAdd - 1);
				ruleSet.addAll(preResults);
			}
		}
		return ruleSet;
	}
	
	private static RuleSet generatePossibleConsequentsRecursive(Transaction transaction, Rule rule, double minConfidenceLevel) {
		RuleSet ruleSet = new RuleSet(minConfidenceLevel);
		if(transaction.getSize() == 1) {
			Rule rule2 = new Rule(rule);
			rule2.addConsequent(transaction.getItem(0));
			ruleSet.add(rule2);
		} else {
			for(int i = 0; i < transaction.getSize(); i++) {
				Rule rule2 = new Rule(rule);
				rule2.addConsequent(transaction.getItem(i));
				Transaction transaction2 = new Transaction(transaction);
				transaction2.remove(transaction.getItem(i));
				ruleSet.add(rule2);
				RuleSet preResults = generatePossibleConsequentsRecursive(transaction2, rule2, minConfidenceLevel);
				ruleSet.addAll(preResults);
			}
		}
		return ruleSet;
	}

	private static RuleSet filterByConfidence(RuleSet possibleRuleSets, TransactionSet transactionSet, double minConfidenceLevel, int transactionCount) {
		RuleSet finalRules = new RuleSet(possibleRuleSets.getMinConfidenceLevel());

		for(int i = 0; i < possibleRuleSets.getSize(); i++) {
			Rule testRule = possibleRuleSets.getRule(i);
			double supportCountX = 0;
			double supportCountXUY = 0;
			for(int j = 0; j < transactionSet.getSize(); j++) {
				Transaction transaction = transactionSet.getTransaction(j);
				if(transaction.getItems().containsAll(testRule.getAntecedent())) {
					supportCountX += 1;
					
					if(transaction.getItems().containsAll(testRule.getConsequent())) {
						supportCountXUY += 1;
					}
				}
			}
			double confidenceLevel = supportCountXUY / supportCountX;
			possibleRuleSets.getRule(i).setConfidenceLevel(confidenceLevel);

			if(confidenceLevel >= minConfidenceLevel) {
				finalRules.add(possibleRuleSets.getRule(i));
			}
		}
		return finalRules;
	}
}