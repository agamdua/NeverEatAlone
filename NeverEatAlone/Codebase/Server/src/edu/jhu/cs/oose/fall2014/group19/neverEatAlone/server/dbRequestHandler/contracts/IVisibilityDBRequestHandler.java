package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbRequestHandler.contracts;

import java.util.List;
import java.util.Map;


/**
 * 
 * Interface for visibility management services.
 * 
 * @author Xiaozhou Zhou
 *
 */
public interface IVisibilityDBRequestHandler {

	/**
	 * Method to set visible to some contacts
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> setVisibility(Map<String,String[]> request);

	/**
	 * method to set invisible to some contacts
	 * @param request
	 * @return
	 */
	public List<Map<String,String>> unsetVisibility(Map<String,String[]> request);


}