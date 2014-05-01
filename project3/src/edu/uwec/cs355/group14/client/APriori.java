package edu.uwec.cs355.group14.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uwec.cs355.group14.rulegeneration.AssociationRuleSet;
import edu.uwec.cs355.group14.rulegeneration.Result;
import edu.uwec.cs355.group14.rulegeneration.Transaction;
import edu.uwec.cs355.group14.rulegeneration.TransactionSet;

public class APriori {
	public static void main(String[] args) {		
	}
	
	public static ArrayList<String> generateRules(String filepath, double minSupportLevel, double minConfidenceLevel) {
		Result result = algorithm(filepath, minSupportLevel, minConfidenceLevel);
		
		
		return null;
	}

	private static Result algorithm(String filepath, double minSupportLevel, double minConfidenceLevel) {		
		/* Frequent item sets generation */
		
		// Read in transaction from file
		Result result = readFromFile(filepath, minSupportLevel, minConfidenceLevel);
		
//		TransactionSet transactionResults = new TransactionSet(minSupportLevel, minConfidenceLevel);
		
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