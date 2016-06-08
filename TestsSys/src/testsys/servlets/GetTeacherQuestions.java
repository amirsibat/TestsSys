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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import testsys.models.Question;
import testsys.models.Teacher;
import testsys.models.User;

/**
 * Servlet implementation class GetTeacherQuestions
 */
@WebServlet("/GetTeacherQuestions")
public class GetTeacherQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTeacherQuestions() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{
			
			User user = new User();
			
			List<Question> QuestsArray = new ArrayList<Question>();
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			
				HttpSession session = request.getSession(true);
				//get session variable
				user = (User) session.getAttribute("user");
				QuestsArray =  Question.getAllQuestionByTeacherId(user.mId);
			
			
			Gson gson = new Gson();
	    	//convert from question ArrayList to json
	    	String userJsonResult = gson.toJson(QuestsArray, new TypeToken<List<Question>>() {}.getType());
	    	out.println(userJsonResult);
	    	out.close();
			
			
	}catch (IOException e) {  
        e.printStackTrace();  
    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
