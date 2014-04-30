package edu.uwec.cs355.group14.server;

import org.restlet.Server;
import org.restlet.data.Protocol;

public class RuleServer {
	public static void main (String [] args) throws Exception {
		Server ruleServer = new Server(Protocol.HTTP, 8111, RuleServerResource.class);
		ruleServer.start();
	}
}