package testsys.models;

import java.util.List;

public class User {
    public enum Type{MANAGER,TEACHER,STUDENT}
    public String mId;
    public String mUsername;
    public String mFirstName;
    public String mLastName;
    public String mDescription;
    public List<String> courses;
    public Type type;

    /**
     * User Constructor
     * @param id
     * @param username login credential
     * @param firstName personal details
     * @param lastName personal details
     * @param description personal details
     * @param courses list of course ids separated by commas in the DB
     * @param type the type of the user
     */
    public User(String id, String username, String firstName, String lastName, String description, List<String> courses, Type type) {
        this.mId = id;
        this.mUsername = username;
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mDescription = description;
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
    	// TODO DAVID 
    User tempUser=null;
    
    
    return tempUser;
    }
}
