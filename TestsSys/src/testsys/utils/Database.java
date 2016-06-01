package testsys.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import testsys.constants.AppConstants;

public class Database {


    private static Database INSTANCE;
    private BasicDataSource mBasicDataSource;
    private Connection mConnection;

    /**
     * @throws Exception failed to initial data source context
     */
    public Database() {
        Context context = new InitialContext();
        mBasicDataSource = (BasicDataSource) context.lookup(AppConstants.DB_DATASOURCE);

    }

    public static Database getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Database();
        return INSTANCE;
    }

    public List<HashMap<String, Object>> executeListQuery(String statementString, String[] columnNames, Object... params) throws Exception {
        List<HashMap<String, Object>> objectListToReturn = new ArrayList<>();
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i, params[i]);
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

    public HashMap<String, Object> executeSingleQuery(String statementString, String[] columnNames, Object... params) throws Exception {
        HashMap<String, Object> objectToReturn = null;
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i, params[i]);
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

    public Boolean executeUpdate(String statementString, Object... params) throws Exception {
        Connection connection = mBasicDataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(statementString);
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i, params[i]);
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
