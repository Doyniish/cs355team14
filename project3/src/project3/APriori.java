package project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

public class APriori {
	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();
		timer.startTimer();
		AssociationRuleSet set = algorithm(2, 0, "powerpointTransaction");
		timer.stopTimer();
		System.out.println("Timer: " + timer.getTotal() + " ms");
		System.out.println(set);
	}
	
	public static AssociationRuleSet algorithm(double minSupportLevel, double minConfidenceLevel, String filePath) throws IOException {
		/* Frequent item sets generation */
		TransactionSet results = new TransactionSet();
		
		// read in transaction from file and create transaction & transactionSets
		TransactionSet transactionsFromFile = readFromFile(filePath);
		
		// filter out single items not meeting minSupportLevel
		Transaction uniqueItems = generateUniqueItems(transactionsFromFile);
		TransactionSet singleItemCandidateSets = generateSingleItemCandidateSets(uniqueItems);
		int[] countSingleItems = countItems(singleItemCandidateSets, transactionsFromFile);
		TransactionSet filteredSingleItemSets = filterItems(singleItemCandidateSets, countSingleItems, minSupportLevel);

		// create and filter two-item sets
		TransactionSet twoItemCandidateSets = generateTwoItemSets(filteredSingleItemSets);
		int[] countMultipleItems = countItems(twoItemCandidateSets, transactionsFromFile);
		TransactionSet multipleItemSets = filterItems(twoItemCandidateSets, countMultipleItems, minSupportLevel);
		TransactionSet previousItemSets = filteredSingleItemSets;
		
		results.addAll(multipleItemSets);
		
		int count = 3;
		while(multipleItemSets.getSize() > 1) {		// continue cycle until there are 0-1 elements
			uniqueItems = generateUniqueItems(multipleItemSets);
			previousItemSets = multipleItemSets;
	
			multipleItemSets = generateCandidates(uniqueItems, count);
			multipleItemSets = removeImpossibleCandidates(multipleItemSets, previousItemSets, count - 1); // not working yet
			countMultipleItems = countItems(multipleItemSets, transactionsFromFile);
			multipleItemSets = filterItems(multipleItemSets, countMultipleItems, minSupportLevel);
			
			results.addAll(multipleItemSets);
			++count;
		}
		
//		System.out.println("Final frequent item sets:");
//		System.out.println(results);
		
		/* Association rule sets */
		AssociationRuleSet possibleRuleSets = new AssociationRuleSet();
		possibleRuleSets = generateAllPossibleAssociations(results);
		
		return possibleRuleSets;
	}

	private static TransactionSet readFromFile(String filepath) throws IOException {
		TransactionSet transactionsFromFile = new TransactionSet();
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		String currentLine = in.readLine();
		
		while(currentLine != null) {
			transactionsFromFile.add(new Transaction(currentLine));
			currentLine = in.readLine();
		}
		in.close();
		return transactionsFromFile;
	}

	private static Transaction generateUniqueItems(TransactionSet transactionsFromFile) {
		Transaction uniqueItems = new Transaction();
		
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {	// for each transaction in transactionSet
			for(int j = 0; j < transactionsFromFile.getTransaction(i).getSize(); j++) { 	// for each item in transaction
				String itemSearched = transactionsFromFile.getTransaction(i).getItem(j);
				if(!uniqueItems.contains(itemSearched)) {
					uniqueItems.addItem(itemSearched);
				}
			}
		}
		Collections.sort(uniqueItems.items);
		return uniqueItems;
	}
	
	private static TransactionSet generateSingleItemCandidateSets(Transaction uniqueItems) {
		TransactionSet singleItemCandidateSets = new TransactionSet();
		for(int i = 0; i < uniqueItems.getSize(); i++) {
			Transaction singleItem = new Transaction();
			singleItem.addItem(uniqueItems.getItem(i));
			singleItemCandidateSets.add(singleItem);
		}
		return singleItemCandidateSets;
	}
	
	private static TransactionSet generateTwoItemSets(TransactionSet filteredSingleItemSets) {
		TransactionSet twoItemSets = new TransactionSet();
		for(int i = 0; i < filteredSingleItemSets.getSize(); i++) {
			for(int j = i + 1; j < filteredSingleItemSets.getSize(); j++) {
				Transaction set = new Transaction();
				set.addItem(filteredSingleItemSets.getTransaction(i).getItem(0));
				set.addItem(filteredSingleItemSets.getTransaction(j).getItem(0));
				twoItemSets.add(set);
			}
		}
		return twoItemSets;
	}
	
	private static TransactionSet generateCandidates(Transaction uniqueItems, int itemsToAdd) {
		TransactionSet candidatesWithDuplicates = generateCandidatesRecursive(new Transaction(), uniqueItems, itemsToAdd); // wrapper method
		TransactionSet candidates = new TransactionSet();
		for(int i = 0; i < candidatesWithDuplicates.getSize(); i++) {
			Transaction transaction = candidatesWithDuplicates.getTransaction(i);
			
			if(!candidates.containsSingle(transaction)) {
				candidates.add(transaction);
			}
		}
		return candidates;
	}
	
	private static TransactionSet generateCandidatesRecursive(Transaction transaction, Transaction uniqueItems, int itemsToAdd) {
		TransactionSet results = new TransactionSet();
		if(itemsToAdd == 1) {
			for(int i = 0; i < uniqueItems.getSize(); i++) {
				Transaction transaction2 = new Transaction(transaction);
				transaction2.addItem(uniqueItems.getItem(i));
				results.add(transaction2);
			}
			return results;
		} else {
			for(int i = 0; i < uniqueItems.getSize(); i++) {
				Transaction transaction2 = new Transaction(transaction);
				transaction2.addItem(uniqueItems.getItem(i));
				Transaction uniqueItems2 = new Transaction(uniqueItems);
				uniqueItems2.remove(uniqueItems.getItem(i));
				TransactionSet preResults = generateCandidatesRecursive(transaction2, uniqueItems2, itemsToAdd - 1);
				
				results.addAll(preResults);
			}
		}
		return results;
	}
	
	private static TransactionSet filterItems(TransactionSet itemCandidateSets, int[] counts, double minSupportLevel) {
		TransactionSet filteredItems = new TransactionSet();
		for(int i = 0; i < itemCandidateSets.getSize(); i++) {
			if(counts[i] >= minSupportLevel) {
				filteredItems.add(itemCandidateSets.getTransaction(i));
			}
		}
		return filteredItems;
	}

	private static TransactionSet removeImpossibleCandidates(TransactionSet multipleItemSets, TransactionSet previousItemSets, int count) {
		TransactionSet filteredItems = new TransactionSet();
		for(int i = 0; i < multipleItemSets.getSize(); i++) {
			TransactionSet requiredItems = generateCandidates(multipleItemSets.getTransaction(i), count);
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
	
	private static int[] countItems(TransactionSet uniqueSets, TransactionSet transactionsFromFile) {
		int[] countItems = new int[uniqueSets.getSize()];
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {
			for(int j = 0; j < uniqueSets.getSize(); j++) {
				if(transactionsFromFile.getTransaction(i).contains(uniqueSets.getTransaction(j))) {
					++countItems[j];
				}
			}
		}
		return countItems;
	}

	private static AssociationRuleSet generateAllPossibleAssociations(TransactionSet results) {
		AssociationRuleSet ruleSetWithDuplicates = new AssociationRuleSet();
		for(int i = 0; i < results.getSize(); i++) {
			AssociationRuleSet rules = generatePossibleAntecedentsRecursive(results.getTransaction(i), new AssociationRule(), results.getTransaction(i).getSize() - 1); // wrapper method
//			System.out.println(rules);
//			System.out.println(results.getTransaction(i));
			rules = generatePossibleConsequents(rules, results.getTransaction(i));

			for(int j = 0; j < rules.getSize(); j++) {
				Transaction unusedItems = new Transaction(results.getTransaction(i));
				for(int k = 0; k < rules.getRule(j).getAnteSize(); k++) {
					unusedItems.remove(rules.getRule(j).getAntecedent().get(k));
				}
//				System.out.println(rules.getRule(j) + " " + unusedItems);
			}
			
			ruleSetWithDuplicates.associationRuleSet.addAll(rules.associationRuleSet);
		}
		AssociationRuleSet ruleSet = new AssociationRuleSet();
		for(int i = 0; i < ruleSetWithDuplicates.getSize(); i++) {
			AssociationRule rule = ruleSetWithDuplicates.getRule(i);

			if(!ruleSet.containsRule(rule)) {
				ruleSet.add(rule);
			} else {
				System.out.println("Ruleset contains \"" + rule + "\"");
				System.out.println(ruleSet + "\n");
			}
		}
		return ruleSet;
	}

	private static AssociationRuleSet generatePossibleAntecedentsRecursive(Transaction transaction, AssociationRule associationRule, int itemsToAdd) {
		AssociationRuleSet ruleSet = new AssociationRuleSet();

		if(itemsToAdd == 1) {
			for(int i = 0; i < transaction.getSize(); i++) {
				AssociationRule associationRule2 = new AssociationRule(associationRule);
				associationRule2.addAntecedent(transaction.getItem(i));
				ruleSet.add(associationRule2);
			}
			return ruleSet;
		} else {
			if(associationRule.getAntecedent().size() > 0) {
				ruleSet.add(associationRule);
			}
			for(int i = 0; i < transaction.getSize(); i++) {
				AssociationRule associationRule2 = new AssociationRule(associationRule);
				associationRule2.addAntecedent(transaction.getItem(i));
				Transaction transaction2 = new Transaction(transaction);
				transaction2.remove(transaction.getItem(i));
				ruleSet.add(associationRule2);
				AssociationRuleSet preResults = generatePossibleAntecedentsRecursive(transaction2, associationRule2, itemsToAdd - 1);
				ruleSet.addAll(preResults);
			}
		}
		return ruleSet;
	}
	
	private static AssociationRuleSet generatePossibleConsequents(AssociationRuleSet rules, Transaction transaction) {
		AssociationRuleSet ruleSet = new AssociationRuleSet();
		
		for(int i = 0; i < rules.getSize(); i++) {
			for(int j = 0; j < transaction.getSize(); j++) {
				String item = transaction.getItem(j);
				if(!rules.getRule(i).getAntecedent().contains(item)) {
					rules.getRule(i).addConsequent(item);
					ruleSet.add(rules.getRule(i));
				}
			}
		}
		return ruleSet;
	}
}