package testsys.models;

import testsys.utils.Database;
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
    public Teacher(String id, String username, String firstName, String lastName, String description, List<String> courses) throws Exception{
        super(id, username, firstName, lastName, description, courses, Type.TEACHER.ordinal());
    }

    public static Teacher getTeacherByTeacherId(String teacherId) {
        return (Teacher) User.getUserByUserId(teacherId);
    }
    
    public static List<Teacher> getAllTeachers(){
        List<Teacher> teacherList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.USER_GET_ALL_TEACHERS, SqlColumns.USER_ALL_COLUMNS);
        for (int i = 0; i < objectsList.size(); i++) {
            teacherList.add((Teacher) hashMapToObject(objectsList.get(i)));
        }
        return teacherList;
    }
}
