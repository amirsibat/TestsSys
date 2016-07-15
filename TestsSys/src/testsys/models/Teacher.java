package testsys.models;

import org.json.JSONArray;
import testsys.utils.Database;
import testsys.utils.L;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by david on 29/05/2016.
 */

public class Teacher extends User {
    /**
     * Teacher Constructor
     *
     * @param id
     * @param username    login credential
     * @param firstName   personal details
     * @param lastName    personal details
     * @param description personal details
     * @param courses     list of course ids separated by commas in the DB
     */
    public Teacher(String id, String username, String firstName, String lastName, String description, List<String> courses) throws Exception {
        super(id, username, firstName, lastName, description, courses, Type.TEACHER.ordinal(), null);
    }

    public Teacher(User user) {
        super(user.mId, user.mUsername, user.mFirstName, user.mLastName, user.mDescription, user.mCourses, Type.TEACHER, null);
	}

	public static Teacher getTeacherByTeacherId(String teacherId) {
        return new Teacher(User.getUserByUserId(teacherId));
    }

    public static List<Teacher> getAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();
        try {
            List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.USER_GET_ALL_TEACHERS, SqlColumns.USER_ALL_COLUMNS);
            for (int i = 0; i < objectsList.size(); i++) {
                teacherList.add(new Teacher(hashMapToObject(objectsList.get(i))));
            }
        } catch (Exception e) {
            L.err(e);
        }
        return teacherList;
    }


    public String getProfessions() {
        try {
            String preventDuplicate = "";
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < mCourses.size(); i++) {
                if(!preventDuplicate.contains(mCourses.get(i).mProfession.mId)) {
                	preventDuplicate += mCourses.get(i).mProfession.mId + ",";
                    jsonArray.put(mCourses.get(i).mProfession.toJSON());
                }
            }
            return jsonArray.toString();
        } catch (Exception e) {
            L.err(e);
            return "[]";
        }
    }
}
