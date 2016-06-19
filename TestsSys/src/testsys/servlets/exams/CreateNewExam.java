package testsys.servlets.exams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import testsys.models.Exam;
import testsys.models.User;
import testsys.models.Exam.ExamStatus;
import testsys.models.Exam.ExamType;
import testsys.models.QuestionsGrade;
import testsys.utils.Helper;
import testsys.utils.L;

/**
 * Servlet implementation class CreateNewExam
 */
@WebServlet("/CreateNewExam")
public class CreateNewExam extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewExam() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(500);
        out.println("{\"error\":\"Not supported method\"}");
        out.close();
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
			String courseId = json.getJSONObject("course").getString("id");
			String id = Exam.generateExamId(professionId, courseId);
			String authorId = user.mId;
			String description = json.getString("studentsNote");
			String descriptionTeacher = json.getString("teacherNote");
			Date date = new Date();
			Integer duration = json.getInt("duration");
			ExamStatus status = ExamStatus.NEW;
			ExamType type = json.getString("type") == "ONLINE" ? ExamType.ONLINE : ExamType.MANUAL;
			String code = json.getString("code");
			JSONArray clientQuestionsList = json.getJSONArray("questions");
			JSONArray questionsList = new JSONArray();
			for(int i=0; i<clientQuestionsList.length(); i++){
				String questionId = clientQuestionsList.getJSONObject(i).getString("id");
				int grade = clientQuestionsList.getJSONObject(i).getInt("grade");
				QuestionsGrade questionsGrade = new QuestionsGrade(questionId, grade);
				questionsList.put(questionsGrade.toJSON());
			}
			
			Exam exam = new Exam(id, authorId, description, descriptionTeacher, date, duration, questionsList.toString(), courseId, professionId, status.ordinal(), type.ordinal(), code);
			exam.insert();
			
			response.setStatus(200);
			out.println("{\"success\":"+exam.toJSON().toString()+"}");
		}catch(Exception e){
			L.err(e);
			response.setStatus(500);
			out.println("{\"error\":\""+e.getMessage()+"\"}");
		}
		out.close();
		
	}

}
