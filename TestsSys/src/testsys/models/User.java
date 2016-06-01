package testsys.models;

import java.util.List;

public class User {
    public enum Type{MANAGER,TEACHER,STUDENT}
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String description;
    private String picture;
    private List<String> courses;
    private Type type;

    /**
     * User Constructor
     * @param id
     * @param username login credential
     * @param firstName personal details
     * @param lastName personal details
     * @param description personal details
     * @param picture profile picture file path
     * @param courses list of course ids separated by commas in the DB
     * @param type the type of the user
     */
    public User(String id, String username, String firstName, String lastName, String description, String picture, List<String> courses, Type type) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.picture = picture;
        this.courses = courses;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public Type getType() {
        return type;
    }

}
