package testsys.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import testsys.models.Question;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;


/**
 * Servlet implementation class CreateQuestion
 */
@WebServlet("/CreateQuestion")
public class CreateQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuestion() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();


		User user = Helper.validateSession(request);
		if (user == null) {
			response.setStatus(403);
			out.println("{\"error\":\"Not authorized\"}");
			out.close();
			return;
		}

		if(user.mType != User.Type.TEACHER){
			response.setStatus(400);
			out.println("{\"error\":\"Bad request\"}");
			out.close();
			return;
		}


		//loop over inputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		StringBuilder jsonFileContent = new StringBuilder();

		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}
		try{
			JSONObject json = new JSONObject(jsonFileContent.toString());
			
			String professionId = json.getJSONObject("profession").getString("id");
			
			JSONArray clientCoursesList = json.getJSONArray("selectedCourses");
			String coursesList = "";
			for(int i=0; i<clientCoursesList.length(); i++){
				String courseId = clientCoursesList.getString(i);
				coursesList += courseId + (i < clientCoursesList.length() - 1 ? "," : "");
			}
			
			String id = Question.generateQuestionId(professionId);
			String authorId = user.mId;
			String questionText = json.getString("questionText");
			String firstAnswer = json.getString("firstAnswer");
			String secondAnswer = json.getString("secondAnswer");
			String thirdAnswer = json.getString("thirdAnswer");
			String fourthAnswer = json.getString("fourthAnswer");
			Integer correctAnswer = json.getInt("correctAnswer");

			
			
			 Question question = new Question(id, questionText, correctAnswer, authorId, professionId,
					 firstAnswer, secondAnswer, thirdAnswer, fourthAnswer, coursesList);
			 question.insert();

			
			response.setStatus(200);
			out.println("{\"success\":"+question.toJSON().toString()+"}");
		}catch(Exception e){
			L.err(e);
			response.setStatus(500);
			out.println("{\"error\":\""+e.getMessage()+"\"}");
		}
		out.close();
		
	}


}
