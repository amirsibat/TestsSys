package testsys.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import testsys.utils.Database;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

/**
 * Profession class required for each Course class
 */
public class Profession {
	private String mId;
	private String mName;

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

	public String getId() {
		return mId;
	}

	public void setId(String id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	/**
	 * Fetch profession from Database where professionId parameter
	 *
	 * @param professionId
	 * @return Profession instance if found, else null
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public static Profession getProfessionByProfessionId(String professionId) throws Exception {
		HashMap<String, Object> results = Database.getInstance().executeSingleQuery(
				SqlStatements.PROFESSION_GET_PROFESSION_BY_PROFESSION_ID, SqlColumns.PROFESSION_ALL_COLUMNS,
				professionId);
		Profession profession = null;
		if (results != null) {
			String id = (String) results.get(SqlColumns.PROFESSION_ID);
			String name = (String) results.get(SqlColumns.PROFESSION_NAME);
			profession = new Profession(id, name);
		}
		return profession;
	}

	/**
	 * Fetch all Courses from the database where professionColumn equals to this
	 * profession Id
	 * 
	 * @return profession associated courses list, else empty list
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public List<Course> getCoursesList() throws Exception {
		List<Course> courseList = new ArrayList<>();

		List<HashMap<String, Object>> results = Database.getInstance().executeListQuery(
				SqlStatements.COURSE_GET_PROFESSION_COURSES, SqlColumns.PROFESSION_ALL_COLUMNS, this.mId);

		Database database = Database.getInstance();
		Connection connection = database.getConnection();
		PreparedStatement statement = connection.prepareStatement(SqlStatements.COURSE_GET_PROFESSION_COURSES);
		statement.setString(0, this.mId);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			String id = resultSet.getString(SqlColumns.COURSE_ID);
			String name = resultSet.getString(SqlColumns.COURSE_NAME);
			String teacherId = resultSet.getString(SqlColumns.COURSE_TEACHER_ID);
			String professionId = resultSet.getString(SqlColumns.COURSE_PROFESSION_ID);
			courseList.add(new Course(id, name, teacherId, professionId));
		}
		statement.close();
		connection.close();
		return courseList;
	}
}
