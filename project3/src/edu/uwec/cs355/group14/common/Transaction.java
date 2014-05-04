package edu.uwec.cs355.group14.common;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> items;
	private double supportLevel;
	private String date;
	private String time;
	private int count;
	
	/*		Constructors	*/
	public Transaction() {
		this(new ArrayList<String>(), "nil", "nil");
	}
	
	public Transaction(ArrayList<String> items, String date, String time) {
		this.items = new ArrayList<String>();
		for(String item : items) {
			this.items.add(item);
		}
		this.date = date;
		this.time = time;
		this.count = 0;
	}
	
	public Transaction(Transaction ts) {	// copy constructor
		this.items = new ArrayList<String>();
		for(String item : ts.items) {
			this.items.add(item);
		}
		this.date = ts.date;
		this.time = ts.time;
		this.count = ts.count;
	}

	/*			Original Methods		*/
	public boolean equals(Transaction ts) {
		return	items.size() == ts.items.size() &&
				items.containsAll(ts.items) &&
				ts.items.containsAll(items);
	}
	
	@Override
	public String toString() {
		String string = "{";
		for(int i = 0; i < items.size() - 1; i++) {
			string += items.get(i) + ", ";
		}
		if(items.size() > 0) {
			string += items.get(items.size() - 1);
		}
		string += "} (" + this.supportLevel + ")";
		return string;
	}
	
	public void incrementCount() {
		this.count += 1;
	}
	
	/*	Getters and Setters, etc.	*/
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
	
	public boolean contains(String item) {
		return items.contains(item);
	}
	
	public int indexOf(String item) {
		return items.indexOf(item);
	}
	
	public boolean containsAll(Transaction ts) {
		return this.items.containsAll(ts.items);
	}
	
	public double getSupportLevel() {
		return supportLevel;
	}

	public void setSupportLevel(double supportLevel) {
		this.supportLevel = supportLevel;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}