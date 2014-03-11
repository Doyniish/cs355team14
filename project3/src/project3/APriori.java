package project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class APriori {
	public static void main(String[] args) throws IOException {
		TransactionSet set = algorithm(0, 0, "workingTransaction");
	}
	
	public static TransactionSet algorithm(double minSupportLevel, double minConfidenceLevel, String filePath) throws IOException {
		TransactionSet transactionsFromFile = readFile(filePath);
		TransactionSet singleItemSets = generateCandidateSingleItemSets(transactionsFromFile);
		
		return null;
		
		// incomplete
	}
	
	private static TransactionSet readFile(String filepath) throws IOException {
		TransactionSet transactionSet = new TransactionSet();
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		String currentLine;
		
		while((currentLine = in.readLine()) != null) {
			transactionSet.add(new Transaction(currentLine));
		}

		in.close();
		
		return transactionSet;
	}
	
	private static TransactionSet generateCandidateSingleItemSets(TransactionSet transactionsFromFile) {
		TransactionSet singleItemTransactions = new TransactionSet();
		
		for(int i = 0; i < transactionsFromFile.getSize(); i++) {
		
			for(int j = 0; j < transactionsFromFile.getTransaction(i).getSize(); j++) {
			
				Transaction singleItem = new Transaction();
				String currentItem = transactionsFromFile.getTransaction(i).getItem(j);
				singleItem.addItem(currentItem);

				int k = 0;
				boolean itemFound = false;
				while(k < transactionsFromFile.getSize() && !itemFound) {
					itemFound = transactionsFromFile.getTransaction(k).equals(singleItem);
					if(itemFound) {
						singleItemTransactions.add(singleItem);
					} else {
						k++;
					}
				}
			}
		}
		System.out.println(singleItemTransactions.getSize());
		return singleItemTransactions;
	}
}
