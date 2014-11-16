package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.kernel.impl.util.StringLogger;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.dbManager.contracts.IContactDBManager;


/**
 * This class handles contact management related database transactions.
 * 
 * @author tejasvamsingh
 *
 */
@Stateless
public class ContactDBManager implements IContactDBManager {


	GraphDatabaseService GraphDBInstance;


	/**
	 * Constructor gets a database handle.
	 * @author tejasvamsingh
	 */
	public ContactDBManager(){
		GraphDBInstance = DBManager.GetGraphDBInstance();
	}


	/**
	 * This method is responsible for adding a contact in the database.
	 * @author tejasvamsingh
	 */
	@Override
	public List<Map<String,String>> AddContact(Map<String,String[]> request) {

		// ********* LOGGING ********* 
		System.out.println("Reached AddContact in ContactDBManager");
		System.out.flush();
		// ********* LOGGING ********* 

		//create a duplicate map.
		Map<String,String[]> modifiableRequestMap = new HashMap<String,String[]>(request);
		modifiableRequestMap.remove("RequestType");
		modifiableRequestMap.remove("RequestID");


		//format the parameters for the query.		
		Map<String, String> queryParamterMap = 
				DBManager.GetQueryParameterMap(modifiableRequestMap);


		// ************************ LOGGING ************************

		System.out.println("USERNAME :"+queryParamterMap.get("Username"));
		System.out.println("CONTACT :"+queryParamterMap.get("ContactUsername"));


		// set up paramters to execute and store the result of query
		ExecutionEngine executionEngine = new ExecutionEngine(GraphDBInstance,
				StringLogger.SYSTEM);				
		ExecutionResult result;
		List<Map<String,String>> resultMapList;


		try ( Transaction tx = GraphDBInstance.beginTx() )
		{
			//create a params map.
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("Username",queryParamterMap.get("Username"));
			parameters.put("ContactUsername",queryParamterMap.get("ContactUsername"));

			//create cypher query to add a relation in the dataase.
			String query = "MATCH (a:User),(b:User)"
					+ " WHERE "
					+ "a.Username = {Username} AND "
					+ "b.Username = {ContactUsername}"
					+ "CREATE UNIQUE (a)-[n:KNOWS]->(b)"
					+ "RETURN n";

			// Check for uniqueness constraint violation.
			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
			}catch(Exception e){
				System.out.println("Constraint violation in add contact. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}


			// Fetch the contact via query

			query = "MATCH (a:User)-[r]->(n:User)"
					+ " WHERE "
					+ "a.Username = {Username} AND "
					+ "n.Username = {ContactUsername}"
					+ "RETURN n ";

			try{
				//execute the query
				result = executionEngine.execute(query,parameters);
				tx.success();
			}catch(Exception e){
				System.out.println("Constraint violation in add contact. :: ");
				System.out.println(e.getMessage());
				result = null;
				tx.failure();
			}

			// This is the data returned.
			resultMapList = DBManager.GetResultMapList(result);

			// Sucessful transaction.


		}

		return resultMapList;

	}






}
