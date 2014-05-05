package edu.uwec.cs355.group14.common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Result implements Serializable {
	private static final long serialVersionUID = 1L;
	private TransactionSet transactionSet = null;
	private ArrayList<String> errorLog = null;
	
	public Result() {
	}
	
	public Result(String filepath, double minSupportLevel, double minConfidenceLevel) {
		readFromFile(filepath, minSupportLevel, minConfidenceLevel);
	}
	
	private void readFromFile(String filepath, double minSupportLevel, double minConfidenceLevel) {
		ArrayList<String> errors = new ArrayList<String>();
		
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
				errors.add("ERROR: The transaction file is completely empty.");
			} else {
				if(!currentLine.equals("PaulMart")) {		// check for vendor
					errors.add("Line " + line + ": The transaction set does not contain the correct vendor information.");
				}
				
				currentLine = in.readLine();
				++line;
				
				// skips through blank lines to dates
				while(currentLine != null && currentLine.equals("")) {
					currentLine = in.readLine();
					++line;
				}
				
				String startDate = "00-00-00";
				String defaultTime = "12:00:00";

				if(isValidDate(currentLine)) {
					startDate = currentLine;
				} else {
					errors.add("Line " + line + ": The transacion set does not contain a valid starting date (YYYY-MM-DD).");	
				}
				
				++line;
				currentLine = in.readLine();
				// skips through blank lines to end date
				while(currentLine != null && currentLine.equals("")) {
					currentLine = in.readLine();
					++line;
				}
				
				if(!isValidDate(currentLine)) {
					errors.add("Line " + line + ": The transacion set does not contain a valid ending date (YYYY-MM-DD).");
				}
				
				++line;
				currentLine = in.readLine();
				
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
						String modifiedLine = currentLine.toLowerCase().replaceAll(" ", "");	// remove spaces
						
						if(!modifiedLine.startsWith("{")) {
							errors.add("Line " + line + ": Transaction line does not start with '{'.");
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
									errors.add("Line " + line + ": Transaction line does not start with '}'.");
								}
							} else {
								modifiedLine = modifiedLine.substring(0, modifiedLine.length() - 1);
							}
							
							String[] itemsFromString = modifiedLine.split(",");
							ArrayList<String> itemsInTransaction = new ArrayList<String>();

							int i = 0;
							while(i < itemsFromString.length) {
								if(itemsFromString[i].equals("")) {
									errors.add("Line " + line + ": There are two consecutive commas in the transaction file.");
								} else if(itemsInTransaction.size() > 1000) {
									errors.add("Line " + line + ": The transaction contains over 1000 items. (item: " + itemsFromString[i] + ")");
								} else if(itemsFromString[i].matches(".*\\W.*")) {
									errors.add("Line " + line + ": The transaction contains items with illegal characters (item: " + itemsFromString[i] + ")");
								} else if(!itemsInTransaction.contains(itemsFromString[i])) {
									itemsInTransaction.add(itemsFromString[i]);
								}
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
				this.setTransactionSet(transactionsFromFile);
			}
		} catch (FileNotFoundException e) {
			errors.add("ERROR: The file (" + filepath + ") does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(errors.size() > 0) {
			this.setErrorLog(errors);
		}
	}
	
	public TransactionSet getTransactionSet() {
		return transactionSet;
	}
	public void setTransactionSet(TransactionSet transactionSet) {
		this.transactionSet = new TransactionSet(transactionSet);
	}
	
	public ArrayList<String> getErrorLog() {
		return errorLog;
	}
	public void setErrorLog(ArrayList<String> errorLog) {
		this.errorLog = errorLog;
	}
	
	public String toString() {
		String string = "transactionSet:";
		if(transactionSet != null) {
			string += "\n" + transactionSet;
		} else {
			string += " none";
		}
		string += "\nerrorLog:";
		if(errorLog != null) {
			string += "\n" + printErrorLog();
		} else {
			string += " none";
		}
		return string;
	}
	
	public String printErrorLog() {
		String string = "";
		for(int i = 0; i < errorLog.size(); i++) {
			string += errorLog.get(i);
			if(i != errorLog.size() - 1) {
				string += "\n";
			}
		}
		return string;
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