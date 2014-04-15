package edu.uwec.cs.cs355.group14.rulegeneration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class APriori {
	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();
		timer.startTimer();
		
		String filepath = "sampleTransactionFile";
		double minSupportLevel = 0.5;
		double minConfidenceLevel = 0.5;
		Result result = algorithm(filepath, minSupportLevel, minConfidenceLevel);
		
		timer.stopTimer();
		
		System.out.println(filepath);
		System.out.println("Timer: " + timer.getTotal() + " ms");
		System.out.println("Final result set:\n" + result.getAssociationRuleSet());
		
		test1();
				
		MySQL mysql = new MySQL();
		mysql.recreateTables();
		int ts_id = mysql.saveTransactions(result.getTransactionSet());
		mysql.saveRules(result.getAssociationRuleSet(), ts_id);
	}
	
	public static void test1() throws IOException {
		Timer timer = new Timer();
		timer.startTimer();
		
		String filepath = "transactions1";
		double minSupportLevel = 0.25;
		double minConfidenceLevel = 0.5;
		Result result = algorithm(filepath, minSupportLevel, minConfidenceLevel);
		
		timer.stopTimer();
		System.out.println("Timer: " + timer.getTotal() + " ms");
		System.out.println("Final result set:\n" + result.getAssociationRuleSet());
	}
	
	public static Result algorithm(String filePath, double minSupportLevel, double minConfidenceLevel) throws IOException {
		Result result = new Result();
		/* Frequent item sets generation */
		TransactionSet transactionResults = new TransactionSet(minSupportLevel, minConfidenceLevel);
		
		// read in transaction from file
		result = readFromFile(filePath, minSupportLevel, minConfidenceLevel);
		
		if(result.getErrorLog() == null) {
			TransactionSet transactionsFromFile = result.getTransactionSet();
		
			// create and filter out single items not meeting minSupportLevel
			TransactionSet candidateSets = generateSingleItemCandidateSets(transactionsFromFile, minSupportLevel);
			TransactionSet filteredSets = filterItems(candidateSets, transactionsFromFile);
			transactionResults.addAll(filteredSets);
		
			// create and filter two-item sets
			candidateSets = generateTwoItemSets(filteredSets, minSupportLevel);
			filteredSets = filterItems(candidateSets, transactionsFromFile);
			transactionResults.addAll(filteredSets);
			
			TransactionSet previousSets = filteredSets;
			
			int count = 3;
			while(filteredSets.getSize() > 1) {		// continue cycle until there are 0-1 elements
				previousSets = filteredSets;
		
				candidateSets = generateCandidates(filteredSets, minSupportLevel, count);
				filteredSets = removeImpossibleCandidates(candidateSets, previousSets, minSupportLevel, count - 1);
				filteredSets = filterItems(filteredSets, transactionsFromFile);
				
				transactionResults.addAll(filteredSets);
				++count;
			}
			
			if(filteredSets.getSize() < 1) {
				filteredSets = previousSets;
			}

			/* Association rule sets */
			AssociationRuleSet ruleSets = new AssociationRuleSet(minConfidenceLevel);
			ruleSets = generateAllPossibleAssociations(filteredSets);
			ruleSets = filterByConfidence(ruleSets, transactionResults, minConfidenceLevel, transactionsFromFile.getSize());

			result.setAssociationRuleSet(ruleSets);
			
		} else {
			// error log
		}
		
		return result;
	}

	private static Result readFromFile(String filepath, double minSupportLevel, double minConfidenceLevel) throws IOException {
		ArrayList<String> errorLog = new ArrayList<String>();
		Result result = new Result();
		
		TransactionSet transactionsFromFile = new TransactionSet(minSupportLevel, minConfidenceLevel);
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		String currentLine = in.readLine();
		
		if(!currentLine.equals("PaulMart")) {		// check for vendor
			errorLog.add("Error: The transaction set does not contain the vendor information on line 1.");
		}
		currentLine = in.readLine();
		
		for(int i = 0; i < 2; i++) {
			if(isValidDate(currentLine)) {
				errorLog.add("Error: The transacion set does not contain a valid date on line " + (i+2) + ".");
			}
			currentLine = in.readLine();
		}

		while(currentLine != null) {
			transactionsFromFile.add(new Transaction(currentLine));
			currentLine = in.readLine();
		}
		
		in.close();
		
		result.setTransactionSet(transactionsFromFile);
		return result;
	}
	
	public static boolean isValidDate(String dateString) {
	    if (dateString == null || dateString.length() != "yyyy-MM-dd".length()) {
	        return false;
	    }

	    int date;
	    try {
	        date = Integer.parseInt(dateString);
	    } catch (NumberFormatException e) {
	        return false;
	    }

	    int year = date / 10000;
	    int month = (date % 10000) / 100;
	    int day = date % 100;

	    // leap years calculation not valid before 1581
	    boolean yearOk = (year >= 1581) && (year <= 2500);
	    boolean monthOk = (month >= 1) && (month <= 12);
	    boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));

	    return (yearOk && monthOk && dayOk);
	}

	private static int daysInMonth(int year, int month) {
	    int daysInMonth;
	    switch (month) {
	        case 1: // fall through
	        case 3: // fall through
	        case 5: // fall through
	        case 7: // fall through
	        case 8: // fall through
	        case 10: // fall through
	        case 12:
	            daysInMonth = 31;
	            break;
	        case 2:
	            if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
	                daysInMonth = 29;
	            } else {
	                daysInMonth = 28;
	            }
	            break;
	        default:
	            // returns 30 even for nonexistant months 
	            daysInMonth = 30;
	    }
	    return daysInMonth;
	}

	private static Transaction generateUniqueItems(TransactionSet transactionsFromFile, double minSupportLevel) {
		Transaction uniqueItems = new Transaction(minSupportLevel);
		
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {	// for each transaction in transactionSet
			for(int j = 0; j < transactionsFromFile.getTransaction(i).getSize(); j++) { 	// for each item in transaction
				String itemSearched = transactionsFromFile.getTransaction(i).getItem(j);
				if(!uniqueItems.contains(itemSearched)) {
					uniqueItems.add(itemSearched);
				}
			}
		}
		Collections.sort(uniqueItems.getItems());
		return uniqueItems;
	}
	
	private static TransactionSet generateSingleItemCandidateSets(TransactionSet transactionSet, double minSupportLevel) {
		Transaction uniqueItems = generateUniqueItems(transactionSet, minSupportLevel);

		TransactionSet singleItemCandidateSets = new TransactionSet(transactionSet.getMinSupportLevel(), transactionSet.getMinConfidenceLevel());
		for(int i = 0; i < uniqueItems.getSize(); i++) {
			Transaction singleItem = new Transaction(minSupportLevel);
			singleItem.add(uniqueItems.getItem(i));
			singleItemCandidateSets.add(singleItem);
		}
		return singleItemCandidateSets;
	}
	
	private static TransactionSet generateTwoItemSets(TransactionSet filteredSingleItemSets, double minSupportLevel) {
		TransactionSet twoItemSets = new TransactionSet(filteredSingleItemSets.getMinSupportLevel(), filteredSingleItemSets.getMinConfidenceLevel());
		for(int i = 0; i < filteredSingleItemSets.getSize(); i++) {
			for(int j = i + 1; j < filteredSingleItemSets.getSize(); j++) {
				Transaction set = new Transaction(minSupportLevel);
				set.add(filteredSingleItemSets.getTransaction(i).getItem(0));
				set.add(filteredSingleItemSets.getTransaction(j).getItem(0));
				twoItemSets.add(set);
			}
		}
		return twoItemSets;
	}
	
	private static TransactionSet generateCandidates(TransactionSet multipleItemSets, double minSupportLevel, int itemsToAdd) {
		Transaction uniqueItems = generateUniqueItems(multipleItemSets, minSupportLevel);
		
		TransactionSet candidatesWithDuplicates = generateCandidatesRecursive(new Transaction(minSupportLevel), uniqueItems, multipleItemSets.getMinConfidenceLevel(), itemsToAdd); // wrapper method
		TransactionSet candidates = new TransactionSet(minSupportLevel, multipleItemSets.getMinConfidenceLevel());
		for(int i = 0; i < candidatesWithDuplicates.getSize(); i++) {
			Transaction transaction = candidatesWithDuplicates.getTransaction(i);
			
			if(!candidates.containsSingle(transaction)) {
				candidates.add(transaction);
			}
		}
		return candidates;
	}
	
	private static TransactionSet generateCandidatesRecursive(Transaction transaction, Transaction uniqueItems, double minConfidenceLevel, int itemsToAdd) {
		TransactionSet results = new TransactionSet(transaction.getMinSupportLevel(), minConfidenceLevel);
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
				TransactionSet preResults = generateCandidatesRecursive(transaction2, uniqueItems2, minConfidenceLevel, itemsToAdd - 1);
				
				results.addAll(preResults);
			}
		}
		return results;
	}
	
	private static TransactionSet filterItems(TransactionSet itemCandidateSets, TransactionSet transactionSet) {
		countItems(itemCandidateSets, transactionSet);
		setActualSupportLevels(itemCandidateSets, transactionSet.getSize());
				
		TransactionSet filteredItems = new TransactionSet(itemCandidateSets.getMinSupportLevel(), itemCandidateSets.getMinConfidenceLevel());
		for(int i = 0; i < itemCandidateSets.getSize(); i++) {
			Transaction transaction = itemCandidateSets.getTransaction(i);
			if(transaction.getActualSupportLevel() >= transactionSet.getMinSupportLevel()) {
				filteredItems.add(transaction);
			}
		}
		return filteredItems;
	}
	
	private static void setActualSupportLevels(TransactionSet transactionSet, int transactionTotal) {
		for(int i = 0; i < transactionSet.getSize(); i++) {
			int count = transactionSet.getTransaction(i).getCount();
			transactionSet.getTransaction(i).setActualSupportLevel((double) count / (double) transactionTotal);
		}
	}

	private static TransactionSet removeImpossibleCandidates(TransactionSet multipleItemSets, TransactionSet previousItemSets, double minSupportLevel, int count) {
		TransactionSet filteredItems = new TransactionSet(minSupportLevel, multipleItemSets.getMinConfidenceLevel());
		for(int i = 0; i < multipleItemSets.getSize(); i++) {
			TransactionSet requiredItems = generateCandidates(multipleItemSets, minSupportLevel, count);
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

	private static AssociationRuleSet generateAllPossibleAssociations(TransactionSet results) {
		AssociationRuleSet ruleSetWithDuplicates = new AssociationRuleSet(results.getMinConfidenceLevel());
		for(int i = 0; i < results.getSize(); i++) {
			AssociationRuleSet rules = generatePossibleAntecedentsRecursive(results.getTransaction(i), new AssociationRule(), results.getMinConfidenceLevel(), results.getTransaction(i).getSize() - 1); // wrapper method
			rules = generatePossibleConsequents(rules, results.getTransaction(i));

			for(int j = 0; j < rules.getSize(); j++) {
				Transaction unusedItems = new Transaction(results.getTransaction(i));
				for(int k = 0; k < rules.getRule(j).getAnteSize(); k++) {
					unusedItems.remove(rules.getRule(j).getAntecedent().get(k));
				}
			}
			
			ruleSetWithDuplicates.getRules().addAll(rules.getRules());
		}
		AssociationRuleSet ruleSets = new AssociationRuleSet(results.getMinConfidenceLevel());
		for(int i = 0; i < ruleSetWithDuplicates.getSize(); i++) {
			AssociationRule rule = ruleSetWithDuplicates.getRule(i);

			if(!ruleSets.containsRule(rule)) {
				ruleSets.add(rule);
			}
		}
		return ruleSets;
	}

	private static AssociationRuleSet generatePossibleAntecedentsRecursive(Transaction transaction, AssociationRule associationRule, double minConfidenceLevel, int itemsToAdd) {
		AssociationRuleSet ruleSet = new AssociationRuleSet(minConfidenceLevel);
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
				AssociationRuleSet preResults = generatePossibleAntecedentsRecursive(transaction2, associationRule2, minConfidenceLevel, itemsToAdd - 1);
				ruleSet.addAll(preResults);
			}
		}
		return ruleSet;
	}
	
	private static AssociationRuleSet generatePossibleConsequents(AssociationRuleSet rules, Transaction transaction) {
		AssociationRuleSet ruleSet = new AssociationRuleSet(rules.getMinConfidenceLevel());
		
		for(int i = 0; i < rules.getSize(); i++) {
			for(int j = 0; j < transaction.getSize(); j++) {
				String item = transaction.getItem(j);
				if(!rules.getRule(i).getAntecedent().contains((String) item)) {
					rules.getRule(i).addConsequent(item);
					ruleSet.add(rules.getRule(i));
				}
			}
		}
		return ruleSet;
	}

	private static AssociationRuleSet filterByConfidence(AssociationRuleSet possibleRuleSets, TransactionSet transactionSet, double minConfidenceLevel, int transactionCount) {
		AssociationRuleSet finalRules = new AssociationRuleSet(possibleRuleSets.getMinConfidenceLevel());

		for(int i = 0; i < possibleRuleSets.getSize(); i++) {
			double supportCountX = 0;
			double supportCountXUY = 0;
			for(int j = 0; j < transactionSet.getSize(); j++) {
				if(transactionSet.getTransaction(j).getItems().equals(possibleRuleSets.getRule(i).getAntecedent())) {
					supportCountX = transactionSet.getTransaction(j).getActualSupportLevel();
				}
				if(transactionSet.getTransaction(j).getItems().containsAll(possibleRuleSets.getRule(i).getAntecedent()) &&
					transactionSet.getTransaction(j).getItems().containsAll(possibleRuleSets.getRule(i).getConsequent())) {
					supportCountXUY = transactionSet.getTransaction(j).getActualSupportLevel();
				}
			}
			double confidenceLevel = supportCountXUY / supportCountX;
			if(confidenceLevel >= minConfidenceLevel) {
				possibleRuleSets.getRule(i).setConfidenceLevel(confidenceLevel);
				finalRules.add(possibleRuleSets.getRule(i));
			}
		}
		return finalRules;
	}
}