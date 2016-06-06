package testsys.models;

import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import testsys.utils.Database;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

/**
 * Profession class required for each Course class
 */
public class Profession {
    public String mId;
    public String mName;

    /**
     * Profession Constructor
     *
     * @param id
     * @param name
     */
    public Profession(String id, String name) {
        this.mId = id;
        this.mName = name;
    }


    /**
     * Fetch profession from Database where professionId parameter
     *
     * @param professionId
     * @return Profession instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public static Profession getProfessionByProfessionId(String professionId) throws Exception {
        return hashMapToObject(Database.getInstance().executeSingleQuery(
                SqlStatements.PROFESSION_GET_PROFESSION_BY_PROFESSION_ID, SqlColumns.PROFESSION_ALL_COLUMNS,
                professionId));
    }

    public static JSONObject getProfessionByProfessionIdAsJSON(String professionId) throws Exception {
        return Database.getInstance().executeSingleQueryAsJSON(SqlStatements.PROFESSION_GET_PROFESSION_BY_PROFESSION_ID,
                SqlColumns.PROFESSION_ALL_COLUMNS, SqlColumns.PROFESSION_ALL_COLUMNS_TYPES, professionId);
    }

    /**
     * Fetch all Courses from the database where professionColumn equals to this
     * profession Id
     *
     * @return profession associated courses list, else empty list
     * @throws Exception failed to execute SQL query
     */
    public List<Course> getCoursesList() throws Exception {
        return Course.getCoursesByProfessionId(mId);
    }

    public JSONArray getCoursesListAsJSON() throws Exception {
        return Database.getInstance().executeListQueryAsJSON(SqlStatements.COURSE_GET_PROFESSION_COURSES,
                SqlColumns.PROFESSION_ALL_COLUMNS, SqlColumns.PROFESSION_ALL_COLUMNS_TYPES, this.mId);
    }

    public static Profession hashMapToObject(HashMap<String, Object> hashMapProfession) throws Exception{
        String id = (String) hashMapProfession.get(SqlColumns.PROFESSION_ID);
        String name = (String) hashMapProfession.get(SqlColumns.PROFESSION_NAME);
        return new Profession(id, name);
    }
}
