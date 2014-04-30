package edu.uwec.cs355.group14.client;

import org.restlet.resource.ClientResource;

import edu.uwec.cs355.group14.common.*;
import edu.uwec.cs355.group14.server.RuleResource;

public class RuleClient {
	public static void main(String[] args) throws Exception {
		
		Transaction transaction = new Transaction();
		transaction.add("apple");
		transaction.add("beer");
		
		TransactionSet set = new TransactionSet(0,0);
		set.add(transaction);
		
		ClientResource clientResource = new ClientResource("http://localhost:8111/");
		RuleResource proxy = clientResource.wrap(RuleResource.class);
				
		TransactionSet newSet = null;

		proxy.store(set);
		newSet = proxy.retrieve();
		
		if (newSet != null) {
            System.out.println("set: " + set);
            System.out.println("newSet: " + newSet);
		}
		else {
			System.out.println("got null set");
		}
	}
}
