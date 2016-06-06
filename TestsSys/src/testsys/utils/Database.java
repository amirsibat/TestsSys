package testsys.utils;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.json.JSONArray;
import org.json.JSONObject;

import testsys.constants.AppConstants;

public class Database {


    private static Database INSTANCE;
    private BasicDataSource mBasicDataSource;
    /**
     * @throws NamingException 
     * @throws Exception failed to initial data source context
     */
    public Database() throws NamingException {
        Context context = new InitialContext();
        mBasicDataSource = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);

    }

    public static Database getInstance() throws NamingException {
        if (INSTANCE == null)
            INSTANCE = new Database();
        return INSTANCE;
    }

    public List<HashMap<String, Object>> executeListQuery(String statementString, String[] columnNames, Object... params) throws Exception {
        List<HashMap<String, Object>> objectListToReturn = new ArrayList<>();
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            HashMap<String, Object> tempObject = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                tempObject.put(columnNames[i], resultSet.getObject(columnNames[i]));
            }
            objectListToReturn.add(tempObject);
        }
        statement.close();
        connection.close();
        return objectListToReturn;
    }
    
    public JSONArray executeListQueryAsJSON(String statementString, String[] columnNames, String[] columnTypes, Object... params) throws Exception {
    	JSONArray objectListToReturn = new JSONArray();
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            JSONObject tempObject = new JSONObject();
            for (int i = 0; i < columnNames.length; i++) {
				switch (columnTypes[i]) {
				case "TEXT":
					tempObject.put(columnNames[i], (String) resultSet.getObject(columnNames[i]));
					break;
				case "INTEGER":
					tempObject.put(columnNames[i], (Integer) resultSet.getObject(columnNames[i]));
					break;
				case "DATE":
					tempObject.put(columnNames[i], (Date) resultSet.getObject(columnNames[i]));
					break;
				default:
					break;
				}
                
            }
            objectListToReturn.put(tempObject);
        }
        statement.close();
        connection.close();
        return objectListToReturn;
    }

    public HashMap<String, Object> executeSingleQuery(String statementString, String[] columnNames, Object... params) throws Exception {
        HashMap<String, Object> objectToReturn = null;
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            objectToReturn = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                objectToReturn.put(columnNames[i], resultSet.getObject(columnNames[i]));
            }
        }
        statement.close();
        connection.close();
        return objectToReturn;
    }
    
    public JSONObject executeSingleQueryAsJSON(String statementString, String[] columnNames,String[] columnTypes, Object... params) throws Exception {
        JSONObject objectToReturn = null;
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            objectToReturn = new JSONObject();
            for (int i = 0; i < columnNames.length; i++) {
            	switch (columnTypes[i]) {
				case "TEXT":
					objectToReturn.put(columnNames[i], (String) resultSet.getObject(columnNames[i]));
					break;
				case "INTEGER":
					objectToReturn.put(columnNames[i], (Integer) resultSet.getObject(columnNames[i]));
					break;
				case "DATE":
					objectToReturn.put(columnNames[i], (Date) resultSet.getObject(columnNames[i]));
					break;
				default:
					break;
				}
                
            }
        }
        statement.close();
        connection.close();
        return objectToReturn;
    }
    

    public Boolean executeUpdate(String statementString, Object... params) throws Exception {
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i+1, params[i]);
        }
        Boolean result = statement.execute();
        statement.close();
        connection.close();
        return result;
    }

    /**
     * Create connection to the application data source to execute SQL statements
     *
     * @return Connection to the data source
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        return mBasicDataSource.getConnection();
    }

    public void InitDatabases() throws Exception {
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SqlStatements.COURSE_CREATE_TABLE);
        statement.execute();
    }
}
