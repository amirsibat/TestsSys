package testsys.servlets;

import java.io.IOException;
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

import testsys.models.Exam;
import testsys.models.Question;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;

/**
 * Servlet implementation class getAllExams
 */
@WebServlet("/getAllQuestions")
public class getAllQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getAllQuestions() {
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
			List<Question> questions = new ArrayList<>();
			if(user.mType == User.Type.MANAGER){
				questions = Question.getAllQuestions();
			}
			if(user.mType == User.Type.TEACHER){
				questions = Question.getAllQuestionByTeacherId(user.mId);
			}
			JSONArray json = new JSONArray();
			for(int i=0; i<questions.size(); i++){
				JSONObject jObject = questions.get(i).toJSON();
				JSONArray coursesList = new JSONArray();
				for(int j=0; j<questions.get(i).mCourses.size(); j++){
					coursesList.put(questions.get(i).mCourses.get(j).toJSON());
				}
				jObject.put("courses", coursesList);
				json.put(jObject);
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
