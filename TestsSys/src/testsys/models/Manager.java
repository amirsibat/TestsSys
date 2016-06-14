package testsys.models;

import java.util.List;

/**
 * Created by david on 29/05/2016.
 */

public class Manager extends User{
    /**
     * Manager Constructor
     *
     * @param id
     * @param username    login credential
     * @param firstName   personal details
     * @param lastName    personal details
     * @param description personal details
     * @param courses     list of course ids separated by commas in the DB
     */
    public Manager(String id, String username, String firstName, String lastName, String description, List<String> courses) throws Exception{
        super(id, username, firstName, lastName, description, courses, Type.MANAGER.ordinal(), null);
    }

}
