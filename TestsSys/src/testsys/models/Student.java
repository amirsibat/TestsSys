package testsys.models;

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
    public Student(String id, String username, String firstName, String lastName, String description, List<String> courses) {
        super(id, username, firstName, lastName, description, courses, Type.STUDENT);
    }

    /**
     * Fetch all students from the database where CourseColumn contains courseId parameter
     *
     * @param courseId
     * @return List of Students if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Student> getStudentsByCourseId(String courseId) throws Exception {
        return null;
    }
}
