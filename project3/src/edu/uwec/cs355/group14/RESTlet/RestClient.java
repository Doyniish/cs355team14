package edu.uwec.cs355.group14.RESTlet;

import org.restlet.resource.ClientResource;

public class RestClient {
	public static void main (String [] args) throws Exception {
		ClientResource clientResource = new ClientResource("http://localhost:8111/?name=Group14");
		clientResource.get().write(System.out);
	}
	
	// minimum support level, minimum confidence level and location of
	// transaction file from the user, convert the transaction file to a
	// serializable object, and pass the three parameters to the server system.
	// On return, the client must receive the rule set, display them within the
	// GUI and write them out to a text file.

}
