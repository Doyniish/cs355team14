package edu.uwec.cs355.group14.rulegeneration;

import java.util.ArrayList;

public class Transaction {
	private ArrayList<String> items;
	private double minSupportLevel;
	private double actualSupportLevel;
	private int count;
	
	/*		Constructors	*/
	public Transaction() {													// no parameters
		this("", 0.0);
	}
	
	public Transaction(double minSupportLevel) {							// minSupportLevel only
		this("", minSupportLevel);
	}
	
	public Transaction(String transactionLine) {							// transactionLine only
		this(transactionLine, 0.0);
	}

	public Transaction(String transactionLine, double minSupportLevel) {	// complete constructor
		this.items = new ArrayList<String>();
		this.count = 0;
		this.minSupportLevel = minSupportLevel;
		this.actualSupportLevel = 0;
		if(!transactionLine.equals("")) {
			this.addItems(transactionLine);
		}
	}
	
	public Transaction(Transaction ts) {									// copy constructor
		this.items = new ArrayList<String>();
		for(String string : ts.items) {
			this.items.add(string);
		}
//		this.items = ts.getItems();
		this.minSupportLevel = ts.minSupportLevel;
		this.count = ts.count;
		this.actualSupportLevel = ts.actualSupportLevel;
	}

	/*			Original Methods		*/
	public void addItems(String transactionLine) {
		String modifiedLine = transactionLine.toLowerCase().replaceAll(" ", "");
		ArrayList<String> errorLog = new ArrayList<String>();
		
		if(!modifiedLine.startsWith("{") && modifiedLine.endsWith("}")) {
			errorLog.add("Transaction line does not start with '{' or end with }");	
		} else {
			modifiedLine = modifiedLine.substring(1, modifiedLine.length() - 1);
			String[] itemsFromString = modifiedLine.split(",");
			
			int i = 0;
			while(i < itemsFromString.length && i < 1001) {
				if(itemsFromString[i].equals("")) {
					errorLog.add("There are two commas in the transaction file.");	
					
				} else if(items.contains(itemsFromString[i])) {
					errorLog.add("The item " + itemsFromString[i] + " was already in the file.");		
					
				} else if(errorLog.size() == 0) {
					items.add(itemsFromString[i]);
				}
				
				i++;
			}
		}
		
		if(items.size() > 1000) {
			errorLog.add("The transaction contains over 1000 items.");
		}
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
	
	@Override
	public String toString() {
		String toPrint = "{";
		for(int i = 0; i < items.size(); i++) {
			toPrint = toPrint + items.get(i);
			if(i != items.size() - 1) {
				toPrint = toPrint + ", ";
			} else {
				toPrint = toPrint + "} (" + this.actualSupportLevel + ")";
			}
		}
		return toPrint;
	}
	
	public void incrementCount() {
		this.count += 1;
	}
	
	/* Getters and Setters, etc.	*/
	public ArrayList<String> getItems() {
		return this.items; 
	}
	
	public void setItems(ArrayList<String> items) {
		this.items = items;
	}
	
	public String getItem(int index) {
		return items.get(index);
	}
	
	public int getSize() {
		return items.size();
	}
	
	public void add(String item) {
		items.add(item);
	}
	
	public void addAll(Transaction transaction) {
		items.addAll(transaction.getItems());
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
	
	public boolean containsAll(Transaction ts) {
		return this.items.containsAll(ts.items);
	}
	
	public double getMinSupportLevel() {
		return minSupportLevel;
	}

	public void setMinSupportLevel(double minSupportLevel) {
		this.minSupportLevel = minSupportLevel;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public double getActualSupportLevel() {
		return actualSupportLevel;
	}

	public void setActualSupportLevel(double actualSupportLevel) {
		this.actualSupportLevel = actualSupportLevel;
	}
}