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
    public Student(String id, String username, String firstName, String lastName, String description, List<String> courses) throws Exception {
        super(id, username, firstName, lastName, description, courses, Type.STUDENT.ordinal());
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
        List<HashMap<String, Object>> students = Database.getInstance().executeListQuery(SqlStatements.STUDENT_GET_STUDENTS_BY_COURSE_ID, SqlColumns.USER_ALL_COLUMNS, courseId);
        for (int i = 0; i < students.size(); i++) {
            studentList.add((Student) hashMapToObject(students.get(i)));
        }
        return studentList;
    }

    public List<Record> getExams() throws Exception {
        return Record.getRecordsByStudentId(this.mId);
    }

    public static Student getStudentByStudentId(String studentId) {
        return (Student) User.getUserByUserId(studentId);
    }

}
