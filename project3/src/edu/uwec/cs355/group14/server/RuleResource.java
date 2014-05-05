package edu.uwec.cs355.group14.server;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import edu.uwec.cs355.group14.common.*;

public interface RuleResource {
	@Get
	public TransactionSet retrieveTS();
	
	public RuleSet retrieveRS();

	@Put
	public void storeTS(TransactionSet transactionSet);
	
	public void storeMCL(double minimumConfidenceLevel);
	
	public void storeMSL(double minimumSupportLevel);
}
