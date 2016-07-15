package testsys.servlets.requests;

import org.json.JSONObject;
import testsys.models.Record;
import testsys.models.Request;
import testsys.models.User;
import testsys.servlets.records.GetAllRecords;
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
 * Servlet implementation class CreateNewRequest
 */
@WebServlet("/AnswerRequest")
public class AnswerRequest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnswerRequest() {
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
        L.log("CreateNewRequest:  START");
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();


        User user = Helper.validateSession(request);
        if (user == null) {
            response.setStatus(403);
            out.println("{\"error\":\"Not authorized\"}");
            out.close();
            return;
        }

        if (user.mType != User.Type.MANAGER) {
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

            String id = json.getString("id");
            boolean accept = json.getBoolean("accept");
            Request request1 = Request.getRequestById(id);
            if(accept){
                request1.answerRequest(Request.RequestStatus.ACCEPTED);
            }else{
                request1.answerRequest(Request.RequestStatus.REJECTED);
            }

            if(accept){
                List<Record> records = Record.getCurrentRecordsByTeacher(request1.mTeacher.mId);
                for(int i=0; i<records.size(); i++){
                    records.get(i).mExtraData.put("addExtraMinutes", request1.mDurationToAdd);
                    records.get(i).update();
                }
            }
            response.setStatus(200);
            out.println("{\"success\":true}");
        } catch (Exception e) {
            L.err(e);
            response.setStatus(500);
            out.println("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.close();
        L.log("CreateNewRequest:  END");
    }

}
