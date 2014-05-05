package edu.uwec.cs355.group14.client;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import edu.uwec.cs355.group14.common.*;

public interface RuleResource {
	@Get
	public RuleSet retrieve();

	@Put
	public void store(Result result);
}
