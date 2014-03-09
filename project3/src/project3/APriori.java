package project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class APriori {
	public static void main(String[] args) throws IOException {
		TransactionSet set = algorithm(0, 0, "workingTransaction");
	}
	
	public static TransactionSet algorithm(double minSupportLevel, double minConfidenceLevel, String filepath) throws IOException {
		TransactionSet transactionSet = new TransactionSet();
		readFile(filepath, transactionSet);
		
		return transactionSet;
	}
	
	private static void readFile(String filepath, TransactionSet transactionSet) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filepath));
		String currentLine;
		
		while((currentLine = in.readLine()) != null) {
			System.out.println("currentLine: " + currentLine);
			transactionSet.add(new Transaction(currentLine));
		}

		in.close();
	}
}
