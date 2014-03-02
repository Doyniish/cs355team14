package project3;

import java.util.ArrayList;

public class Transaction {
	private ArrayList<String> item;
	
	public Transaction(String transactionLine) {
		ArrayList<String> item = new ArrayList<String>();
		this.addItems(transactionLine);
	}
	
	
	public void addItems(String transactionLine) {
		if(!transactionLine.startsWith("{")) {
			// print out error
			System.out.println("Error: does not start with {");
		} else {
			transactionLine.replaceAll(" ", "");
			transactionLine.toLowerCase();
			
			String[] items = transactionLine.split("\\w*,");
			
			int i = 0;
//			boolean noErrors = true;
			while(i < items.length) {
				if(items[i].endsWith(",")) {
					// double commas
				} else if(item.contains(items[i])) {
					// item is already in list, ignore
				} else {
					item.add(items[i]);
				}
			}
		}
		
		if(item.size() > 1000) {
			// report error
		}
	}
}
