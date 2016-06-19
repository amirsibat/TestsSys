package testsys.servlets.records;

import org.json.JSONArray;
import testsys.models.Exam;
import testsys.models.Record;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetExamsToCheck
 */
@WebServlet("/GetExamsToCheck")
public class GetExamsToCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetExamsToCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();


		User user = Helper.validateSession(request);
		if (user == null) {
			response.setStatus(403);
			out.println("{\"error\":\"Not authorized\"}");
			out.close();
			return;
		}

		if(user.mType != User.Type.MANAGER && user.mType != User.Type.TEACHER){
			response.setStatus(400);
			out.println("{\"error\":\"Bad request\"}");
			out.close();
			return;
		}

		try {
			List<Record> records = Record.getExamsToCheckByTeacher(user.mId);
			JSONArray json = new JSONArray();
			for(int i=0; i<records.size(); i++){
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
