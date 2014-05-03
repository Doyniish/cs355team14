package edu.uwec.cs355.group14.RESTlet;

import org.restlet.Server;
import org.restlet.data.Protocol;

public class RestServer {
	public static void main (String [] args) throws Exception {
		Server restServer = new Server(Protocol.HTTP, 8111, RestServerResource.class);
		restServer.start();
	}

	// receives the parameters, store the transaction set in MySQL, generate the
	// appropriate rules, store those rules in MySQL, and return the rule set to
	// the client system as a serializable object.

}
