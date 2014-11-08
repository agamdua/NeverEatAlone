package edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestHandler.services;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.contracts.IRequestDispatcher;
import edu.jhu.cs.oose.fall2014.group19.neverEatAlone.server.requestDispatcher.services.RequestDispatcher;

/**
 * Servlet implementation class RequestHandler.
 * This class is the entry point for our server side logic.
 * It is repsonsible for receiving HTTP requests from the client and forwarding them 
 * to the RequestDispatcher for processing.
 * It also is responsible for sending an HTTP response back to the client.
 */

@WebServlet("/RequestHandler")
public class RequestHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	//Inject the bean
	@Inject IRequestDispatcher IRequestDispatcherObject; 
	
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		// ********* LOGGING ********* 
		java.io.Writer w = response.getWriter();
		w.append("<html>");
		w.append("<body>");
		w.append("<h1>This is something cool.</h1>");
		w.append("</body>");
		w.append("</html>");
		// ********* LOGGING ********* 
		
	}

	/**
	 * 
	 * This method accepts post requests, calls the request dispatcher on the request, 
	 * obtains response and sends the response back to the client.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		Map<String, String[]> map = request.getParameterMap();
		
		//call the beans DispatchRequest method.
		List<Map<String, String>> result = IRequestDispatcherObject.DispatchRequest(map);
		
		// For now just print the status of the query (success or failed).		
		java.io.Writer w = response.getWriter();
		w.append(result.get(0).get("Status"));
				
	}

}