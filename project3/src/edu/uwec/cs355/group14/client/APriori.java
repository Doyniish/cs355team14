package edu.uwec.cs355.group14.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import edu.uwec.cs355.group14.common.*;

public class APriori {
	static ArrayList<String> generatedLines;
	public static void main(String[] args) {		
	}
	
	public static String generateRules(String filepath, double minSupportLevel, double minConfidenceLevel) {
		generatedLines = new ArrayList<String>();
		Result result = algorithm(filepath, minSupportLevel, minConfidenceLevel);
		if(result.getErrorLog() != null) {
			generatedLines.addAll(result.getErrorLog());
		} else {
			RuleSet rules = result.getRuleSet();
//			String returnString = "";
			for(int i = 0; i < rules.getSize(); i++) {
				generatedLines.add(rules.getRule(i).toString() + "\n");
			}
		}
		String results = "";
		for(String line : generatedLines) {
			results += line;
		}
		return results;
	}
	
	private static Result algorithm(String filePath, double minSupportLevel, double minConfidenceLevel) {
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
			RuleSet ruleSets = new RuleSet(minConfidenceLevel);
			ruleSets = generateAllPossibleAssociations(filteredSets);
			ruleSets = filterByConfidence(ruleSets, transactionResults, minConfidenceLevel, transactionsFromFile.getSize());

			result.setRuleSet(ruleSets);
			
		}
		
		return result;
	}

	private static Result readFromFile(String filepath, double minSupportLevel, double minConfidenceLevel) {
		Result result = new Result();
		ArrayList<String> errorLog = new ArrayList<String>();
		
		TransactionSet transactionsFromFile = new TransactionSet(minSupportLevel, minConfidenceLevel);
		BufferedReader in;
		
		try {
			int line = 1;
			in = new BufferedReader(new FileReader(filepath));
			String currentLine = in.readLine();
			
			// skips through blank lines that may be beginning of file
			while(currentLine != null && currentLine.equals("")) {
				currentLine = in.readLine();
				++line;
			}
			
			if(currentLine == null) {
				errorLog.add("ERROR: The transaction file is completely empty.");
			} else {
				if(!currentLine.equals("PaulMart")) {		// check for vendor
					errorLog.add("Line " + line + ": The transaction set does not contain the vendor information.");
					
					if(!isValidDate(currentLine)) {
						currentLine = in.readLine();
						++line;
					}
				} else {
					currentLine = in.readLine();
					++line;
				}
				
				// skips through blank lines to dates
				while(currentLine != null && currentLine.equals("")) {
					currentLine = in.readLine();
					++line;
				}
				
				String startDate = "00-00-00";
//				String endDate = "00-00-00";
				String defaultTime = "12:00:00";

				if(!isValidDate(currentLine)) {
					errorLog.add("Line " + line + ": The transacion set does not contain a valid starting date (YYYY-MM-DD).");
				} else {
					startDate = currentLine;
					++line;
					currentLine = in.readLine();
					// skips through blank lines to end date
					while(currentLine != null && currentLine.equals("")) {
						currentLine = in.readLine();
						++line;
					}
				}
				
				if(!isValidDate(currentLine)) {
					errorLog.add("Line " + line + ": The transacion set does not contain a valid ending date (YYYY-MM-DD).");
				} else {
//					endDate = currentLine;
					++line;
					currentLine = in.readLine();
				}
				
				// skips through blank lines to get transactions
				while(currentLine != null && currentLine.equals("")) {
					currentLine = in.readLine();
					++line;
				}
			
				while(currentLine != null) {
					if(currentLine.equals("")) {
						currentLine = in.readLine();
						++line;
					} else {
						String date = startDate;
						String time = defaultTime;
						String modifiedLine = currentLine.toLowerCase().replaceAll(" ", "");	// lower case ??!
						
						if(!modifiedLine.startsWith("{")) {
							errorLog.add("Line " + line + ": Transaction line does not start with '{'.");
						} else {
							modifiedLine = modifiedLine.substring(1, modifiedLine.length());
							if(!modifiedLine.endsWith("}")) {
								String possibleDateTime = modifiedLine.replaceAll(".*}(\\d)", "$1");
	
								if(possibleDateTime.length() > 9 && isValidDate(possibleDateTime.substring(0,10))) {
									date = possibleDateTime.substring(0,9);
									
									if(possibleDateTime.length() > 8 && isValidTime(possibleDateTime.substring(10))) {
										time = possibleDateTime.substring(10);
										modifiedLine = modifiedLine.substring(0, modifiedLine.length() - 20);
									}
								} else {
									errorLog.add("Line " + line + ": Transaction line does not start with '}'.");
								}
							} else {
								modifiedLine = modifiedLine.substring(0, modifiedLine.length() - 1);
							}
							
							String[] itemsFromString = modifiedLine.split(",");
							ArrayList<String> itemsInTransaction = new ArrayList<String>();

							int i = 0;
							while(i < itemsFromString.length) {
								if(itemsFromString[i].equals("")) {
									errorLog.add("Line " + line + ": There are two consecutive commas in the transaction file.");	
								} else if(itemsInTransaction.contains(itemsFromString[i])) {
									errorLog.add("Line " + line + ": The item " + itemsFromString[i] + " was already listed in the transaction.");		
								} else if(itemsInTransaction.size() > 1000) {
									errorLog.add("Line " + line + ": The transaction contains over 1000 items. (item: " + itemsFromString[i] + ")");
								}
								itemsInTransaction.add(itemsFromString[i]);
								++i;
							}
							Transaction currTransaction = new Transaction(itemsInTransaction, time, date);
							transactionsFromFile.add(currTransaction);
						}
						++line;
						currentLine = in.readLine();
					}
				}
				in.close();
				result.setTransactionSet(transactionsFromFile);
			}
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			errorLog.add("ERROR: The file (" + filepath + ") does not exist.");
		} catch (IOException e) {
				e.printStackTrace();
		}
		if(errorLog.size() > 0) {
			result.setErrorLog(errorLog);
			System.out.println(result.printErrorLog());
		}
		return result;
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

	private static RuleSet generateAllPossibleAssociations(TransactionSet results) {
		RuleSet ruleSetWithDuplicates = new RuleSet(results.getMinConfidenceLevel());
		for(int i = 0; i < results.getSize(); i++) {
			RuleSet rules = generatePossibleAntecedentsRecursive(results.getTransaction(i), new Rule(), results.getMinConfidenceLevel(), results.getTransaction(i).getSize() - 1); // wrapper method
			rules = generatePossibleConsequents(rules, results.getTransaction(i));

			for(int j = 0; j < rules.getSize(); j++) {
				Transaction unusedItems = new Transaction(results.getTransaction(i));
				for(int k = 0; k < rules.getRule(j).getAntecedentSize(); k++) {
					unusedItems.remove(rules.getRule(j).getAntecedent().get(k));
				}
			}
			
			ruleSetWithDuplicates.getRules().addAll(rules.getRules());
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

	private static RuleSet generatePossibleAntecedentsRecursive(Transaction transaction, Rule Rule, double minConfidenceLevel, int itemsToAdd) {
		RuleSet ruleSet = new RuleSet(minConfidenceLevel);
		if(itemsToAdd == 1) {
			for(int i = 0; i < transaction.getSize(); i++) {
				Rule Rule2 = new Rule(Rule);
				Rule2.addAntecedent(transaction.getItem(i));
				ruleSet.add(Rule2);
			}
			return ruleSet;
		} else {
			if(Rule.getAntecedent().size() > 0) {
				ruleSet.add(Rule);
			}
			for(int i = 0; i < transaction.getSize(); i++) {
				Rule Rule2 = new Rule(Rule);
				Rule2.addAntecedent(transaction.getItem(i));
				Transaction transaction2 = new Transaction(transaction);
				transaction2.remove(transaction.getItem(i));
				ruleSet.add(Rule2);
				RuleSet preResults = generatePossibleAntecedentsRecursive(transaction2, Rule2, minConfidenceLevel, itemsToAdd - 1);
				ruleSet.addAll(preResults);
			}
		}
		return ruleSet;
	}
	
	private static RuleSet generatePossibleConsequents(RuleSet rules, Transaction transaction) {
		RuleSet ruleSet = new RuleSet(rules.getMinConfidenceLevel());
		
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

	private static RuleSet filterByConfidence(RuleSet possibleRuleSets, TransactionSet transactionSet, double minConfidenceLevel, int transactionCount) {
		RuleSet finalRules = new RuleSet(possibleRuleSets.getMinConfidenceLevel());

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

	
	public static boolean isValidDate(String date) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}
	
	public static boolean isValidTime(String time) {
		try {
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			df.setLenient(false);
			df.parse(time);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}		
}