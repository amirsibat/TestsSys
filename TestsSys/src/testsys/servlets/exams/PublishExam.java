package testsys.servlets.exams;

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
 * Servlet implementation class PublishExam
 */
@WebServlet("/PublishExam")
public class PublishExam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PublishExam() {
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

	        if (user.mType != User.Type.TEACHER) {
	            response.setStatus(400);
	            out.println("{\"error\":\"Bad request\"}");
	            out.close();
	            return;
	        }
	        
	      //loop over inputStream
	        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
	        StringBuilder jsonFileContent = new StringBuilder();

	        //read line by line from file
	        String nextLine = null;
	        while ((nextLine = br.readLine()) != null) {
	            jsonFileContent.append(nextLine);
	        }
	        try {
	            JSONObject json = new JSONObject(jsonFileContent.toString());
	            
	            
	            String id = Record.generateRecordId();	        
	            Course course = Course.getCourseByCourseId(json.getString("courseId"));
	            List<Student> students = new ArrayList<>();      
	            students = Student.getStudentsByCourseId(json.getString("courseId"));
	            
	            Exam exam = Exam.getExamByExamId(json.getString("examId"));
	            JSONObject extraData = new JSONObject();
	            extraData.put("status", Record.RecordExamStatus.PUBLISHED.ordinal());
	            
	            for(int i=0; i<students.size(); i++){
	            	Student studentToAdd = students.get(i);
	            	Record record = new Record(id, studentToAdd, course, exam, extraData);
	            	record.insert();
	            }
	           

	            response.setStatus(200);
	            out.println("{\"success\":true}");
	        } catch (Exception e) {
	            L.err(e);
	            response.setStatus(500);
	            out.println("{\"error\":\"" + e.getMessage() + "\"}");
	        }
	        out.close();
	}

}
