package testsys.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.http.HTTPException;

import org.apache.jasper.tagplugins.jstl.core.Out;

import testsys.utils.L;


/**
 * Servlet implementation class LogoutUser
 */
@WebServlet("/LogoutUser")
public class LogoutUser extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutUser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
        try {
        	HttpSession session = request.getSession(true);
        	
            if(session.getAttribute("userId") != null){
            	session.invalidate(); // release session
            	response.setStatus(200);
            	out.println("{ \"result\": \"success\"}");	
            }
            else{
            	response.setStatus(500);
            	out.println("{ \"result\": \"fail\"}");            
            }
            
        } catch (HTTPException e) {  
            L.err(e);
            response.setStatus(500);
        	out.println("{ \"result\": \"fail\"}");
        }
        out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
