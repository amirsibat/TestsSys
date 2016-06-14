package testsys.servlets;

//import java.io.IOException;
//import java.io.PrintWriter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import testsys.models.User;

/**
 * Servlet implementation class SessionAuth
 */
@SuppressWarnings("deprecation")
public class SessionAuth extends HttpServlet implements SingleThreadModel {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SessionAuth() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");

        //Obtain the session object, create a new session if doesn't exist
        HttpSession session = request.getSession(true);

        //get session variable
        String userId = (String) session.getAttribute("userId");


        if (userId != null) {
            String userJson = User.getUserByUserId(userId).toJSON().toString();
            if(userJson != null) {
                response.setStatus(200);
                out.println("{\"success\":" + userJson + "}");
            }else{
                response.setStatus(403);
                out.println("{\"error\":\"Not authorized\"}");
            }
        } else {
            response.setStatus(403);
            out.println("{\"error\":\"Not authorized\"}");
        }
        out.close();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(500);
        out.println("{\"error\":\"Not supported method\"}");
        out.close();
    }

}
