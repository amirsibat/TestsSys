package testsys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import testsys.models.Question;
import testsys.models.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class GetAllProfessionQuestions
 */
@WebServlet("/GetAllProfessionQuestions")
public class GetAllProfessionQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllProfessionQuestions() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try{
			String uri = request.getRequestURI();
			User user = new User();
			
			
			ArrayList<Question> QuestsArray = new ArrayList<Question>();
			
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			
				HttpSession session = request.getSession(true);
				//get session variable
				user = (User) session.getAttribute("user");
				QuestsArray = null;
			
			
			Gson gson = new Gson();
	    	//convert from question ArrayList to json
	    	String userJsonResult = gson.toJson(QuestsArray, new TypeToken<ArrayList<Question>>() {}.getType());
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
