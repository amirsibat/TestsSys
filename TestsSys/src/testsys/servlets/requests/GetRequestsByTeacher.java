package testsys.servlets.requests;

import org.json.JSONArray;
import org.json.JSONObject;
import testsys.models.Request;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetRequestsByTeacher
 */
@WebServlet("/GetRequestsByTeacher")
public class GetRequestsByTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetRequestsByTeacher() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		L.log("GetRequestsByTeacher:  START");
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();


		User user = Helper.validateSession(request);
		if (user == null) {
			response.setStatus(403);
			out.println("{\"error\":\"Not authorized\"}");
			out.close();
			return;
		}

		if (user.mType != User.Type.TEACHER) {
			response.setStatus(400);
			out.println("{\"error\":\"Bad request\"}");
			out.close();
			return;
		}

		try {
			List<Request> requests = Request.getRequestByTeacherId(user.mId);
			JSONArray jsonArray = new JSONArray();
			for(int i=0; i<requests.size(); i++){
				jsonArray.put(requests.get(i).toJSON());
			}
			response.setStatus(200);
			out.println("{\"success\":"+jsonArray.toString()+"}");
		} catch (Exception e) {
			L.err(e);
			response.setStatus(500);
			out.println("{\"error\":\"" + e.getMessage() + "\"}");
		}
		out.close();
		L.log("GetRequestsByTeacher:  END");
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
