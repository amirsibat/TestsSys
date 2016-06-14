package testsys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.oracle.xmlns.internal.webservices.jaxws_databinding.XmlWebFault;

import testsys.models.Exam;
import testsys.utils.L;
import word.api.interfaces.IDocument;
import word.w2004.Document2004;
import word.w2004.elements.BreakLine;
import word.w2004.elements.Heading1;
import word.w2004.elements.Paragraph;

/**
 * Servlet implementation class GetExamWordFile
 */
@WebServlet("/GetExamWordFile")
public class GetExamWordFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetExamWordFile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter writer = response.getWriter();
		try {
			 
		
	        
			IDocument myDoc = new Document2004();
	        myDoc.addEle(Heading1.with("David antoon").create()); 
	 
	        String myWord = myDoc.getContent(); 
	        
	        
	        request.setAttribute("res", "@@@ " + new Date()); 
	        request.getSession().setAttribute("xml", myWord);
	        
	        response.setContentType("application/msword; charset=UTF-8"); 
            response.setHeader("Content-disposition", 
                    "inline;filename=wordDoc.doc"); 
 
            writer.println(myWord); 
            writer.flush();
			return;
		

		} catch (Exception e) {
			L.err(e);
			response.setStatus(500);
			writer.println("{ \"result\": \"fail\"}");	
		}finally{
//			writer.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
