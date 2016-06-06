package testsys.utils;

import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import testsys.constants.AppConstants;
import testsys.models.User;

public class ServletsListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		//shut down database
		try {
			DriverManager.getConnection(AppConstants.PROTOCOL + AppConstants.DB_NAME + ";shutdown=true");
		} catch (SQLException e) {
			ServletContext cntx = event.getServletContext();
			cntx.log("Error shutting down database", e);
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		try {
//			dropTables();

			createTable(SqlStatements.PROFESSION_CREATE_TABLE, SqlStatements.PROFESSION_TABLE);
			createTable(SqlStatements.COURSE_CREATE_TABLE, SqlStatements.COURSE_TABLE);
			createTable(SqlStatements.USER_CREATE_TABLE, SqlStatements.USER_TABLE);
			createTable(SqlStatements.EXAM_CREATE_TABLE, SqlStatements.QUESTION_TABLE);
			createTable(SqlStatements.QUESTION_CREATE_TABLE, SqlStatements.EXAM_TABLE);
			createTable(SqlStatements.RECORD_CREATE_TABLE, SqlStatements.RECORD_TABLE);


			createDummyData();
		} catch (Exception e) {
			System.err.println("Error during database initialization");
			e.printStackTrace();
		}
	}

	//utility that checks whether the customer tables already exists
	private boolean tableAlreadyExists(SQLException e) {
		boolean exists;
		if (e.getSQLState().equals("X0Y32")) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	public void createTable(String tableStatement, String name) throws Exception {
		try {
			Database.getInstance().executeUpdate(tableStatement);
			L.log(name + " created successfuly!");
		} catch (SQLException e) {
			if (!tableAlreadyExists(e)) {
				throw e;
				//re-throw the exception so it will be caught in the
				//external try..catch and recorded as error in the log
			}
			L.log(name + " alreay exists!");
		}
	}

	public void dropTables(){
		dropTable("DROP TABLE " + SqlStatements.PROFESSION_TABLE, "PROFESSION_TABLE");
		dropTable("DROP TABLE " + SqlStatements.COURSE_TABLE, "COURSE_TABLE");
		dropTable("DROP TABLE " + SqlStatements.USER_TABLE, "USER_TABLE");
		dropTable("DROP TABLE " + SqlStatements.QUESTION_TABLE, "QUESTION_TABLE");
		dropTable("DROP TABLE " + SqlStatements.EXAM_TABLE, "EXAM_TABLE");
		dropTable("DROP TABLE " + SqlStatements.RECORD_TABLE, "RECORD_TABLE");

	}

	public void dropTable(String tableStatement, String name){
		try {
			Database.getInstance().executeUpdate(tableStatement);
			L.log(name + " dropped successfuly!");
		} catch (Exception e) {
			L.log(name + " failed to drop!");
			L.err(e);
		}	
	}

	public void createDummyData(){
		try {
			Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0001", "amirs", "pass", "amir", "sibat", "Web developer", "", User.Type.STUDENT.ordinal());
			L.log("User 0001 STUDENT created");
		} catch (SQLException e1) {
			if(e1.getSQLState().equals("23505"))
				L.log("User 0001 STUDENT already exists");
			else
				L.err(e1);
		} catch(Exception e2){
			L.err(e2);
		}
		try {
			Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0002", "davida", "pass", "david", "anton", "Math Teacher ", "", User.Type.TEACHER.ordinal());
			L.log("User 0002 TEACHER created");
		} catch (SQLException e1) {
			if(e1.getSQLState().equals("23505"))
				L.log("User 0002 TEACHER already exists");
			else
				L.err(e1);
		} catch(Exception e2){
			L.err(e2);
		}
	}

}
