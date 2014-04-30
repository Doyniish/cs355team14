package edu.uwec.cs355.group14.server;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import edu.uwec.cs355.group14.common.*;

public interface RuleResource {
	@Get
	public TransactionSet retrieve();

	@Put
	public void store(TransactionSet transaction);
}
