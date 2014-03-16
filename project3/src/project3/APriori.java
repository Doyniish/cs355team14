package project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

public class APriori {
	public static void main(String[] args) throws IOException {
		TransactionSet set = algorithm(2, 0, "workingTransaction");
	}
	
	public static TransactionSet algorithm(double minSupportLevel, double minConfidenceLevel, String filePath) throws IOException {
		TransactionSet transactionsFromFile = readFile(filePath);
		Transaction uniqueItems = generateUniqueItems(transactionsFromFile);
		
		TransactionSet singleItemCandidateSets = generateSingleItemCandidateSets(uniqueItems);
		int[] countSingleItems = countSingleItems(uniqueItems, transactionsFromFile);
		TransactionSet filteredSingleItems = filterItems(singleItemCandidateSets, countSingleItems, minSupportLevel);
		
		TransactionSet twoItemCandidateSets = generateTwoItemSets(filteredSingleItems);
		int[] countMultipleItems = countMultipleItems(twoItemCandidateSets, transactionsFromFile);
		TransactionSet filteredDoubleItems = filterItems(twoItemCandidateSets, countMultipleItems, minSupportLevel);
		
//		System.out.println(filteredDoubleItems);
		
//		for(int i = 0; i < countMultipleItems.length; i++) {
//			System.out.println(allPossibleTwoItemSets.getTransaction(i));
//			System.out.println(countMultipleItems[i]);
//		}
		
//		TransactionSet filteredTwoItemSets = filterMultipleItems(transactionsFromFile, );
		
		ArrayList<String> letters = new ArrayList<String>();
		letters.add("a");
		letters.add("b");
		letters.add("c");
		letters.add("d");
		generate("", letters, 3, 3);
		
		return null;
	}
	
	private static String generate(String string, ArrayList<String> letters, int depth, int count) {
		if(depth == 0) {
			return "";
		} else {
			for(int i = 0; i < letters.size(); i++) {
				if(!string.contains(letters.get(i))) {
					string = string + letters.get(i);
				}
				generate(string, letters, depth - 1, count - 1);
			}
		}
		System.out.println(string);
		return string;
	}

	private static TransactionSet generateTwoItemSets(TransactionSet filteredSingleItems) {
		TransactionSet twoItemSets = new TransactionSet();
		for(int i = 0; i < filteredSingleItems.getSize(); i++) {
			for(int j = i + 1; j < filteredSingleItems.getSize() - 1; j++) {
				Transaction set = new Transaction();
				set.addItem(filteredSingleItems.getTransaction(i).getItem(0));
				set.addItem(filteredSingleItems.getTransaction(j).getItem(0));
				twoItemSets.add(set);
			}
		}		
		return twoItemSets;
	}

	private static TransactionSet filterItems(TransactionSet singleItemCandidateSets, int[] counts, double minSupportLevel) {
		TransactionSet filteredItems = new TransactionSet();
		for(int i = 0; i < singleItemCandidateSets.getSize(); i++) {
			if(counts[i] >= minSupportLevel) {
				filteredItems.add(singleItemCandidateSets.getTransaction(i));
			}
		}
				
		return filteredItems;
	}

	private static TransactionSet readFile(String filepath) throws IOException {
		TransactionSet transactionsFromFile = new TransactionSet();
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		String currentLine;
		
		while((currentLine = in.readLine()) != null) {
			transactionsFromFile.add(new Transaction(currentLine));
		}
		in.close();
		return transactionsFromFile;
	}
	
	private static Transaction generateUniqueItems(TransactionSet transactionsFromFile) {
		Transaction uniqueItems = new Transaction();
		
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {
			for(int j = 0; j < transactionsFromFile.getTransaction(i).getSize(); j++) {
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

	private static int[] countSingleItems(Transaction uniqueItems, TransactionSet transactionsFromFile) {
		int[] countSingleItems = new int[uniqueItems.getSize()];
		for(int i = 0; i < countSingleItems.length; i++) {
			countSingleItems[i] = 0;
		}
		
		int indexToIncrement = 0;
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {
			for(int j = 0; j < transactionsFromFile.getTransaction(i).getSize(); j++) {
				indexToIncrement = uniqueItems.indexOf(transactionsFromFile.getTransaction(i).getItem(j));
				++countSingleItems[indexToIncrement];
			}
		}
			
		return countSingleItems;
	}
	
	private static int[] countMultipleItems(TransactionSet uniqueSets, TransactionSet transactionsFromFile) {
		int[] countMultipleItems = new int[uniqueSets.getSize()];
		for(int i = 0; i < countMultipleItems.length; i++) {
			countMultipleItems[i] = 0;
		}
		
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {
			for(int j = 0; j < uniqueSets.getSize(); j++) {
				if(transactionsFromFile.getTransaction(i).contains(uniqueSets.getTransaction(j))) {
					++countMultipleItems[j];
				}
			}
		}

		return countMultipleItems;
	}
}