package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;


/**
 * 
 * Interface for login management services.
 * Accessible to the management request handler layers. 
 * 
 * @author tejasvamsingh
 *
 */
@Local
public interface ILoginDBRequestHandler {
	
	/**
	 * Method to check user credentials.
	 * Used during login.
	 *  
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> checkCredentials(Map<String,String[]> request);
}
