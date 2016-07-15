package testsys.models;

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

public class Student extends User {
    /**
     * User Constructor
     *
     * @param id
     * @param username    login credential
     * @param firstName   personal details
     * @param lastName    personal details
     * @param description personal details
     * @param courses     list of course ids separated by commas in the DB
     */
    public Student(String id, String username, String firstName, String lastName, String description, List<String> courses, String stId) throws Exception {
        super(id, username, firstName, lastName, description, courses, Type.STUDENT.ordinal(), stId);
    }

    public Student(User user, String stId) {
        super(user.mId, user.mUsername, user.mFirstName, user.mLastName, user.mDescription, user.mCourses, Type.STUDENT, stId);
    }

    /**
     * Fetch all students from the database where CourseColumn contains courseId parameter
     *
     * @param courseId
     * @return List of Students if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Student> getStudentsByCourseId(String courseId) throws Exception {
        List<Student> studentList = new ArrayList<>();
        List<HashMap<String, Object>> students = Database.getInstance().executeListQuery(SqlStatements.STUDENT_GET_STUDENTS_BY_COURSE_ID, SqlColumns.USER_ALL_COLUMNS, "%"+courseId+"%");
        for (int i = 0; i < students.size(); i++) {
            Student st = new Student(hashMapToObject(students.get(i)), (String) students.get(i).get(SqlColumns.USER_ST_ID));
            studentList.add(st);
        }
        return studentList;
    }

    public List<Record> getExams() throws Exception {
        return Record.getRecordsByStudentId(this.mId);
    }

    public static List<Student> getAllStudents() throws Exception{
        List<Student> studentList = new ArrayList<>();
        try {
            List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.USER_GET_ALL_STUDENTS, SqlColumns.USER_ALL_COLUMNS);
            for (int i = 0; i < objectsList.size(); i++) {
                studentList.add(new Student(hashMapToObject(objectsList.get(i)), (String) objectsList.get(i).get(SqlColumns.USER_ST_ID)));
            }
        } catch (Exception e) {
            L.err(e);
        }
        return studentList;
    }

    public static Student getStudentByStudentId(String studentId) {
        try {
            HashMap<String, Object> data = Database.getInstance().executeSingleQuery(SqlStatements.USER_GET_USER_BY_USER_ID, SqlColumns.USER_ALL_COLUMNS, studentId);
            Student st = new Student(User.hashMapToObject(data), (String) data.get(SqlColumns.USER_ST_ID));
            return st;
        } catch (Exception e) {
            L.err(e);
            return null;
        }
    }

}
