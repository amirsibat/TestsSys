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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import testsys.models.Question;
import testsys.models.Teacher;
import testsys.models.User;


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

		try{
			//wrap input stream with a buffered reader to allow reading the file line by line
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			StringBuilder jsonFileContent = new StringBuilder();
			//read line by line from file
			String nextLine = null;
			while ((nextLine = br.readLine()) != null){
				jsonFileContent.append(nextLine);
			}
			
			//Obtain the session object, create a new session if doesn't exist
			HttpSession session = request.getSession(true);
			//get session variable
			User user = (User) session.getAttribute("user");
			
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			Gson gson = new GsonBuilder().create();
			Question m = gson.fromJson(jsonFileContent.toString(),Question.class);
		
	        
	        Question.createQuestion(m.mText, m.mCorrectAnswer, Teacher.getTeacherByTeacherId(user.mId) , m.mOptions1, m.mOptions2, m.mOptions3, m.mOptions4, m.mCourses);
			
	      /*  ArrayList<Question> QuestsArray = new ArrayList<Question>();
	        QuestsArray = Question.getAllQuestions(); //
	        
	        Gson tgson = new Gson();
	    	//convert from Questions ArrayList to json
	    	String userJsonResult = tgson.toJson(QuestsArray, new TypeToken<ArrayList<Question>>() {}.getType());
	    	out.println(userJsonResult);
	    	out.close();*/
	    	
			}catch (IOException e) {  
		        e.printStackTrace();  
		    } catch (Exception e) {
				
				e.printStackTrace();
			}
			
	}

}
