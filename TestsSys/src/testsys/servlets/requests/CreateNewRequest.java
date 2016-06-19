package testsys.servlets.requests;

import org.json.JSONObject;
import testsys.models.Request;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateNewRequest
 */
@WebServlet("/CreateNewRequest")
public class CreateNewRequest extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateNewRequest() {
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

            String id = Request.generateRequestId();
            String teacherId = user.mId;
            String examId = json.getJSONObject("exam").getString("id");
            Integer pending = Request.RequestStatus.PENDING.ordinal();
            String requestText = json.getString("requestText");
            Integer durationToAdd = json.getInt("duration");

            Request newRequest = new Request(id, teacherId, examId, pending, requestText, durationToAdd);
            newRequest.insert();

            response.setStatus(200);
            out.println("{\"success\":" + newRequest.toJSON().toString() + "}");
        } catch (Exception e) {
            L.err(e);
            response.setStatus(500);
            out.println("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.close();
        L.log("CreateNewRequest:  END");
    }

}
