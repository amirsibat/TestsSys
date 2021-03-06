package testsys.servlets.records;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import testsys.models.Course;
import testsys.models.Exam;
import testsys.models.Record;
import testsys.models.Student;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;

/**
 * Servlet implementation class GetCourseDetails
 */
@WebServlet("/GetCourseDetails")
public class GetCourseDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCourseDetails() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
        String courseId = request.getParameter("courseId");
            

		User user = Helper.validateSession(request);
		if (user == null) {
			response.setStatus(403);
			out.println("{\"error\":\"Not authorized\"}");
			out.close();
			return;
		}

		if(user.mType != User.Type.STUDENT ){
			response.setStatus(400);
			out.println("{\"error\":\"Bad request\"}");
			out.close();
			return;
		}
    
        try {    	
        	
            List<Record> records = Record.getSubmittedExams(courseId, user.mId);
            JSONArray json = new JSONArray();
            
            for (int i = 0; i < records.size(); i++) {
            	json.put(records.get(i).toJSON());  
            }

            response.setStatus(200);
            out.println("{\"success\":"+json.toString()+"}");
        } catch (Exception e) {
            L.err(e);
            response.setStatus(500);
            out.println("{\"error\":\"" + e.getMessage() + "\"}");
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
