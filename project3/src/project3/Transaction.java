package project3;

import java.util.ArrayList;

public class Transaction {
	private ArrayList<String> items;
	private double minSupportLevel;
	
	public Transaction(String transactionLine) {
		items = new ArrayList<String>();
		this.addItems(transactionLine);
	}
	
	public void addItems(String transactionLine) {
		String modifiedLine = transactionLine.toLowerCase().replaceAll(" ", "");
		boolean noErrors = true;
		String errorLog = "";
		
		if(!modifiedLine.startsWith("{") && modifiedLine.endsWith("}")) {
			noErrors = false;
			errorLog = "Does not start with { or end with }";
			
		} else {
			modifiedLine = modifiedLine.substring(1, modifiedLine.length() - 1);
			String[] itemsFromString = modifiedLine.split(",");
			
			int i = 0;
			
			while(i < itemsFromString.length && i < 1001) {
				System.out.println(i + ": " + itemsFromString[i]);
				
				if(itemsFromString[i].equals("")) {
					errorLog = errorLog + "\nThere are two commas in the transaction file.";
					noErrors = false;
					
				} else if(items.contains(itemsFromString[i])) {
					errorLog = errorLog + "\nThe item " + itemsFromString[i] + " was already in the file.";
					noErrors = false;
					
				} else if(noErrors) {
					items.add(itemsFromString[i]);
				}
				
				i++;
			}
		}
		
		if(items.size() > 1000) {
			errorLog = errorLog + "\nThe transaction contains over 1000 items.";
		}
		
		if(!noErrors) {
			System.out.println(errorLog);
		}
		System.out.println("The list contains a total of " + items.size() + " items.");
	}
}