package testsys.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;

import testsys.constants.AppConstants;

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
            createTable(SqlStatements.PROFESSION_CREATE_TABLE, SqlStatements.PROFESSION_TABLE);
            createTable(SqlStatements.COURSE_CREATE_TABLE, SqlStatements.COURSE_TABLE);
            createTable(SqlStatements.USER_CREATE_TABLE, SqlStatements.USER_TABLE);
            createTable(SqlStatements.QUESTION_CREATE_TABLE, SqlStatements.QUESTION_TABLE);

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

}
