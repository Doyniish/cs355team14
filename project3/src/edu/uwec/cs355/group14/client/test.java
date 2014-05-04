package edu.uwec.cs355.group14.client;

import java.util.ArrayList;

public class test {
	public static void main(String[] args) {
		create(new ArrayList<String>(), "", "");
	}
	
	public static void create(ArrayList<String> items, String date, String time) {
		ArrayList<String> c = new ArrayList<String>();
		for(String item : items) {
			System.out.println("lol");
		}
		System.out.println("done");
	}
}
