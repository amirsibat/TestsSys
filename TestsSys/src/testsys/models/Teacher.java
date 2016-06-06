package testsys.models;

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
    public Teacher(String id, String username, String firstName, String lastName, String description, List<String> courses, Type type) {
        super(id, username, firstName, lastName, description, courses, Type.TEACHER);
    }

    public static Teacher getTeacherByTeacherId(String teacherId) {
        return null;
    }
}
