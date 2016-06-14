package testsys.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import testsys.models.Profession;
import testsys.models.Teacher;
import testsys.models.User;
import testsys.utils.Helper;
import testsys.utils.L;

/**
 * Servlet implementation class GetTeacherProfessions
 */
@WebServlet("/GetTeacherProfessions")
public class GetTeacherProfessions extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTeacherProfessions() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");

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

        try {
            Teacher teacher = new Teacher(user);
            response.setStatus(200);
            out.println("{\"success\":"+teacher.getProfessions()+"}");
        } catch (Exception e) {
            L.err(e);
            response.setStatus(500);
            out.println("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.close();

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(500);
        out.println("{\"error\":\"Not supported method\"}");
        out.close();
    }

}
