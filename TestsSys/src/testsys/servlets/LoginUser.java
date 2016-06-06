package testsys.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import testsys.models.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


/**
 * Servlet implementation class LoginUser
 */
@SuppressWarnings("deprecation")
public class LoginUser extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUser() {
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
	
		User exist = null;
		HttpSession session = null;
		//loop over inputStream
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
		StringBuilder jsonFileContent = new StringBuilder();
		//read line by line from file
		String nextLine = null;
		while ((nextLine = br.readLine()) != null){
			jsonFileContent.append(nextLine);
		}

		
		/*Gson gson = new GsonBuilder().create();*/
      /*  User u = gson.fromJson(jsonFileContent.toString(),User.class);*/
		
        
        JSONObject u = null;
		try {
			u = new JSONObject(jsonFileContent.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        //check if user already exists.
        try {
			exist = User.isUserExist(u.getString("Username"), u.getString("Password") );
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
       
        
        try {
        	
        	response.setContentType("application/json; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	
        	//login
            if(exist != null){
            	session = request.getSession();
    			session.setAttribute("user", exist);
    			out.println("{ \"result\": \"success\"}");
    			out.close();
            }
            else{ //if not fail to login
            	
            	out.println("{ \"result\": \"fail\"}");
            	out.close();
            
            }
            	
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        
		
	}

}