package project3;

import java.util.ArrayList;

public class Transaction {
	public ArrayList<String> items;
	private double minSupportLevel;
	
	public Transaction() {
		items = new ArrayList<String>();
	}
	
	public Transaction(String transactionLine) {
		items = new ArrayList<String>();
		this.addItems(transactionLine);
	}
	
	@SuppressWarnings("unchecked")
	public Transaction(Transaction ts) {
		this.minSupportLevel = ts.minSupportLevel;
		this.items = (ArrayList<String>) ts.items.clone();
	}
	
	public void addItem(String item) {
		items.add(item);
	}
	
	public void addAll(Transaction c) {
		items.addAll(c.getItems());
	}
	
	public void remove(String s) {
		items.remove(s);
	}
	
	public boolean contains(Object o) {
		return items.contains(o);
	}
	
	public int indexOf(Object o) {
		return items.indexOf(o);
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
//				System.out.println(i + ": " + itemsFromString[i]);
				
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
//		System.out.println("The list contains a total of " + items.size() + " items.");
	}
	
	public String getItem(int index) {
		return items.get(index);
	}
	
	public ArrayList<String> getItems() {
		return this.items; 
	}
	
	public int getSize() {
		return items.size();
	}
	
	public boolean equals(Transaction ts) {
		if(items.size() == ts.items.size() &&
				items.containsAll(ts.items) &&
				ts.items.containsAll(items)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean contains(Transaction ts) {
		return this.items.containsAll(ts.items);
		
	}
	
	public String toString() {
		String toPrint = "{";
		for(int i = 0; i < items.size(); i++) {
			toPrint = toPrint + items.get(i) + ", ";
		}
		toPrint = toPrint.substring(0, toPrint.length() - 2) + "}";
		return toPrint;
	}
}