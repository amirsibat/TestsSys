package testsys.models;

import testsys.utils.Database;
import testsys.utils.L;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

public class User {
    public enum Type {MANAGER, TEACHER, STUDENT}

    public String mId;
    public String mUsername;
    public String mFirstName;
    public String mLastName;
    public String mDescription;
    public List<Course> mCourses;
    public Type mType;
    public String mStId;

    /**
     * User Constructor
     *
     * @param id
     * @param username    login credential
     * @param firstName   personal details
     * @param lastName    personal details
     * @param description personal details
     * @param courses     list of course ids separated by commas in the DB
     * @param type        the mType of the user
     */

    public User(String id, String username, String firstName, String lastName, String description, List<Course> courses, Type type, String stId) {
        this.mId = id;
        this.mUsername = username;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mDescription = description;
        this.mType = type;
        this.mCourses = courses;
        this.mStId = stId;
    }

    public User(String id, String username, String firstName, String lastName, String description, List<String> courses, int type, String stId) throws Exception {
        this.mId = id;
        this.mUsername = username;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mDescription = description;
        this.mType = Type.values()[type];
        this.mCourses = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            this.mCourses.add(Course.getCourseByCourseId(courses.get(i)));
        }
        this.mStId = stId;
    }

    public User(String id, String username, String firstName, String lastName, String description, String coursesString, int type, String stId) throws Exception {
        this.mId = id;
        this.mUsername = username;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mDescription = description;
        this.mType = Type.values()[type];
        this.mCourses = new ArrayList<>();
        if (!coursesString.isEmpty()) {
            String[] coursesArray = coursesString.split(",");
            for (int i = 0; i < coursesArray.length; i++) {
                this.mCourses.add(Course.getCourseByCourseId(coursesArray[i]));
            }
        }
        this.mStId = stId;
    }

    public User() {
        // TODO testing only!!!
    }


    /**
     * @param Username user name
     * @return User user if exist, null otherwise
     * @throws SQLException    SQL syntax error,NullPointer,...
     * @throws NamingException if constants name are not valid.
     **/
    public static User isUserExist(String username, String password) {
        try {
            return hashMapToObject(Database.getInstance().executeSingleQuery(SqlStatements.USER_LOGIN, SqlColumns.USER_ALL_COLUMNS, username, password));
        } catch (Exception e) {
            L.err(e);
            return null;
        }
    }


    public static User hashMapToObject(HashMap<String, Object> objectHashMap) throws Exception {
        String id = (String) objectHashMap.get(SqlColumns.USER_ID);
        String username = (String) objectHashMap.get(SqlColumns.USER_USERNAME);
        String firstName = (String) objectHashMap.get(SqlColumns.USER_FIRST_NAME);
        String lastName = (String) objectHashMap.get(SqlColumns.USER_LAST_NAME);
        String description = (String) objectHashMap.get(SqlColumns.USER_DESCRIPTION);
        String courses = (String) objectHashMap.get(SqlColumns.USER_COURSES);
        int type = (int) objectHashMap.get(SqlColumns.USER_TYPE);
        String stId = (String) objectHashMap.get(SqlColumns.USER_ST_ID);

        return new User(id, username, firstName, lastName, description, courses, type, stId);
    }

    public static User getUserByUserId(String userId) {
        try {
            return hashMapToObject(Database.getInstance().executeSingleQuery(SqlStatements.USER_GET_USER_BY_USER_ID, SqlColumns.USER_ALL_COLUMNS, userId));
        } catch (Exception e) {
            L.err(e);
            return null;
        }
    }

    public JSONObject toJSON() {
        try {
            JSONObject json = new JSONObject();
            json.put("id", mId);
            json.put("username", mUsername);
            json.put("firstName", mFirstName);
            json.put("lastName", mLastName);
            json.put("description", mDescription);
            json.put("courses", getCoursesJSONList());
            json.put("type", mType);
            json.put("stId", mStId);
            return json;
        } catch (Exception e) {
            L.err(e);
            return null;
        }
    }

    
    public JSONArray getCoursesJSONList() throws Exception{
    	JSONArray json = new JSONArray();
    	for(int i=0; i<mCourses.size(); i++){
    		json.put(mCourses.get(i).mId);
    	}
    	return json;
    }

}
