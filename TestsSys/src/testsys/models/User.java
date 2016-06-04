package testsys.models;

import java.util.List;

public class User {
    public enum Type{MANAGER,TEACHER,STUDENT}
    public String id;
    public String username;
    public String firstName;
    public String lastName;
    public String description;
    public String picture;
    public List<String> courses;
    public Type type;

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
    
	/**
	 * @param Username user name
	 * @return User user if exist, null otherwise
	 * @throws SQLException SQL syntax error,NullPointer,...
	 * @throws NamingException if constants name are not valid.
	 **/
    public User isUserExist(String Username){
   
    User tempUser=null;
    
    
    return tempUser;
    }
}
