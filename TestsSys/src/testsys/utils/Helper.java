package testsys.utils;

import testsys.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Helper {

    public static User validateSession(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(true);
            String userId = (String) session.getAttribute("userId");
            if (userId != null) {
                return User.getUserByUserId(userId);
            }
        } catch (Exception e) {
            L.err(e);
        }
        return null;
    }
}
