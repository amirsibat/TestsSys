package testsys.utils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import testsys.constants.AppConstants;
import testsys.models.Exam;
import testsys.models.Record;
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

     /* dropTables();*/

            createTable(SqlStatements.PROFESSION_CREATE_TABLE, SqlStatements.PROFESSION_TABLE);
            createTable(SqlStatements.COURSE_CREATE_TABLE, SqlStatements.COURSE_TABLE);
            createTable(SqlStatements.USER_CREATE_TABLE, SqlStatements.USER_TABLE);
            createTable(SqlStatements.EXAM_CREATE_TABLE, SqlStatements.QUESTION_TABLE);
            createTable(SqlStatements.QUESTION_CREATE_TABLE, SqlStatements.EXAM_TABLE);
            createTable(SqlStatements.RECORD_CREATE_TABLE, SqlStatements.RECORD_TABLE);
            createTable(SqlStatements.REQUEST_CREATE_TABLE, SqlStatements.REQUEST_TABLE);


            createDummyData();
        } catch (Exception e) {
            System.err.println("Error during database initialization");
            e.printStackTrace();
        }
        
        startBackgroundService();
    }
    
    
    private void startBackgroundService(){
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					Record.checkInProgressExams();
					try{
						Thread.sleep(4000);
					}catch(Exception e){
						L.err(e);
					}
				}
			}
		}).start();
    }

    //utility that checks whether the customer tables already exists
    private boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if (e.getSQLState() != null) {
            if (e.getSQLState().equals("X0Y32")) {
                exists = true;
            } else {
                exists = false;
            }
        } else {
            exists = false;
            e.printStackTrace();
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

    public void dropTables() {

        dropTable("DROP TABLE " + SqlStatements.PROFESSION_TABLE, "PROFESSION_TABLE");
        dropTable("DROP TABLE " + SqlStatements.COURSE_TABLE, "COURSE_TABLE");
        dropTable("DROP TABLE " + SqlStatements.USER_TABLE, "USER_TABLE");
        dropTable("DROP TABLE " + SqlStatements.QUESTION_TABLE, "QUESTION_TABLE");
        dropTable("DROP TABLE " + SqlStatements.EXAM_TABLE, "EXAM_TABLE");
        dropTable("DROP TABLE " + SqlStatements.RECORD_TABLE, "RECORD_TABLE");
        dropTable("DROP TABLE " + SqlStatements.REQUEST_TABLE, "RECORD_TABLE");
//
    }

    public void dropTable(String tableStatement, String name) {
        try {
            Database.getInstance().executeUpdate(tableStatement);
            L.log(name + " dropped successfuly!");
        } catch (Exception e) {
            L.log(name + " failed to drop!");
            L.err(e);
        }
    }

    public void createDummyData() {
        createProfessions();
        L.log("createProfessions DONE");
        createCourses();
        L.log("createCourses DONE");
        createManager();
        L.log("creeateManager DONE");
        createTeachers();
        L.log("createTeachers DONE");
        createStudents();
        L.log("createStudents DONE");
        createQuestions();
        L.log("createQuestions DONE");
        createExams();
        L.log("createExams DONE");
        createRecord();
        L.log("createRecords DONE");


    }


    public void createProfessions() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.PROFESSION_INSERT_NEW_PROFESSION, "01", "Math");
            Database.getInstance().executeUpdate(SqlStatements.PROFESSION_INSERT_NEW_PROFESSION, "02", "Physics");
            Database.getInstance().executeUpdate(SqlStatements.PROFESSION_INSERT_NEW_PROFESSION, "03", "Chemistry");
            Database.getInstance().executeUpdate(SqlStatements.PROFESSION_INSERT_NEW_PROFESSION, "04", "English");
            Database.getInstance().executeUpdate(SqlStatements.PROFESSION_INSERT_NEW_PROFESSION, "05", "Computer Science");
        } catch (Exception e) {
//            L.err(e);
        }
    }

    public void createCourses() {
        try {
            // Math subjects
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "01", "Algebra", "01");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "02", "Calculus", "01");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "03", "Combinatorics", "01");

            // Physics subjects
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "04", "Mechanics", "02");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "05", "Mathematical", "02");

            // Chemistry subjects
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "06", "Elementary atomic", "03");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "07", "Conservation of mass", "03");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "08", "Conservation of energy", "03");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "09", "Stoichiometry", "03");

            // English subjects
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "10", "Pre-basic", "04");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "11", "basic", "04");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "12", "Lower Advanced", "04");

            // Computer Science subjects
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "13", "Web development", "05");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "14", "Java", "05");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "15", "Android", "05");
            Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE, "16", "IOS", "05");

        } catch (Exception e) {
//            L.err(e);
        }
    }

    public void createManager() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0001", "tomj", "pass", "Tom", "Jerry", "I'm Manager", "", User.Type.MANAGER.ordinal(), null);
        } catch (Exception e) {
//            L.err(e);
        }
    }

    public void createTeachers() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0002", "daver", "pass", "Dave", "Reed", "Math and Computer Science Teacher", "01,03,13,14", User.Type.TEACHER.ordinal(), null);
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0003", "deborahs", "pass", "Deborah", "Seehorn", "English and Computer Science Teacher", "10,11,12,15", User.Type.TEACHER.ordinal(), null);
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0004", "laurab", "pass", "Laura", "Blankenship", "Math and Physics Teacher", "01,02,03,04,05", User.Type.TEACHER.ordinal(), null);
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0005", "fredm", "pass", "Fred", "Martin", "Chemistry Teacher", "06,07,08,09", User.Type.TEACHER.ordinal(), null);
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0006", "davida", "pass", "David", "Antoon", "Math and Computer Science Teacher ", "01,02,03,15,16", User.Type.TEACHER.ordinal(), null);
        } catch (Exception e) {
//            L.err(e);
        }
    }

    public void createStudents() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0007", "amirs", "pass", "Amir", "Sibat", "First degree students", "14,10,02,12,15,11", User.Type.STUDENT.ordinal(), "000000001");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0008", "samm", "pass", "Sam", "Maldiv", "Second degree students", "12,01,15,11,03,09,13", User.Type.STUDENT.ordinal(), "000000002");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0009", "carolb", "pass", "Carol", "Bush", "First degree students", "13,11,08,03,07,02,16", User.Type.STUDENT.ordinal(), "000000003");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0010", "mikaylad", "pass", "Mikayla", "Dahlen", "First degree students", "08,03,06,07,16,12,05", User.Type.STUDENT.ordinal(), "000000004");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0011", "zovod", "pass", "Zovo", "Dal", "First degree students", "11,14,08,13,01,12", User.Type.STUDENT.ordinal(), "000000005");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0012", "justind", "pass", "Justin", "Dandurand", "First degree students", "11,01,16,15,13,05", User.Type.STUDENT.ordinal(), "000000006");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0013", "kaitlynd", "pass", "Kaitlyn", "Dansereau", "First degree students", "06,08,13,07,05,02", User.Type.STUDENT.ordinal(), "000000007");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0014", "angelad", "pass", "Angela", "Daponde", "First degree students", "05,04,08,06,10,15,12", User.Type.STUDENT.ordinal(), "000000008");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0015", "ericd", "pass", "Eric", "Daponde", "First degree students", "06,01,09,12,04,10,15", User.Type.STUDENT.ordinal(), "000000009");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0016", "stevend", "pass", "Steven", "Dasilva", "First degree students", "02,08,06,05,10,11", User.Type.STUDENT.ordinal(), "000000010");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0017", "juhid", "pass", "Juhi", "Dasrath", "First degree students", "15,01,04,13,05,12", User.Type.STUDENT.ordinal(), "000000011");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0018", "timothyd", "pass", "Timothy", "Daudelin", "First degree students", "06,16,01,05,13,11", User.Type.STUDENT.ordinal(), "000000012");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0019", "katelynn", "pass", "Katelyn", "Nadeau", "First degree students", "08,01,14,05,06,02,15", User.Type.STUDENT.ordinal(), "000000013");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0020", "marien", "pass", "Marie", "Nadeau", "First degree students", "01,06,05,11,03,15", User.Type.STUDENT.ordinal(), "000000014");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0021", "aazan", "pass", "Aaza", "Najeebi", "First degree students", "01,03,13,16,12,11,02", User.Type.STUDENT.ordinal(), "000000015");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0022", "omarn", "pass", "Omar", "Natour", "First degree students", "01,04,09,07,13,06,14", User.Type.STUDENT.ordinal(), "000000016");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0023", "michelleahn", "pass", "Michelleah", "Navarro", "First degree students", "01,02,07,08,15,12", User.Type.STUDENT.ordinal(), "000000017");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0024", "carlosn", "pass", "Carlos", "Negron", "First degree students", "01,15,16,09,13,07", User.Type.STUDENT.ordinal(), "000000018");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0025", "racheln", "pass", "Rachel", "Neveu", "First degree students", "01,07,06,14,10,03", User.Type.STUDENT.ordinal(), "000000019");
            Database.getInstance().executeUpdate(SqlStatements.USER_INSERT_NEW_USER, "0026", "kimoyn", "pass", "Kimoy", "Newman", "First degree students", "01,10,03,06,16", User.Type.STUDENT.ordinal(), "000000020");
        } catch (Exception e) {
//            L.err(e);
        }
    }

    private void createQuestions() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01001", "Which of the following statements is ALWAYS true?", 3, "0002", "01", "A function is not a relation", "Every function is a relation", "Every relation is a function", "A relation is not a function", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01002", "Which of these inequalities has NO solutions?", 3, "0002", "01", "2x + 4 > 1", "|x| > −5", "|x| < −10", "−x + 3 ≥ 5", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01003", "A system of two linear equations with two variables is independent if it has?", 1, "0002", "01", "Two solutions", "One solution", "No solutions", "Many solutions", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01004", "The lines y = (a + 1)x + 3 and y = −3x + 2 are parallel if a = ?", 3, "0004", "01", "−4", "4", "−3", "3", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01005", "Which of these points is on the graph of the equation 2x + 3y = 7?", 3, "0004", "01", "(2, 3)", "(0, 3)", "(−1, 3)", "(3, 0)", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01006", "If f(x) = 3x^3 − 7x + 5, then f(−1) =?", 4, "0006", "01", "1", "−1", "8", "9", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01007", "Which of these values of x satisfies the inequality −2x + 4 ≤ −6?", 1, "0006", "01", "5", "0", "−2", "−6", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01008", "The lines x = 3 and y = −2 are?", 2, "0006", "01", "Parallel", "Perpendicular", "Neither parallel nor perpendicular", "none of these", "01,02");
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, "01009", "The equation |3x + 1| = k has no solutions if k =", 4, "0006", "01", "0", "3", "1", "−1", "01,02");
        } catch (Exception e) {
//            L.err(e);
        }
    }

    private void createExams() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.EXAM_INSERT_NEW,
                    "010100", "0006", "Hello ,, student notes", "Hello all my note", new Date(),
                    20, "[{\"question\":\"01001\",\"grade\":25},{\"question\":\"01002\",\"grade\":25},{\"question\":\"01004\",\"grade\":25},{\"question\":\"01007\",\"grade\":25}]",
                    "01", "01", Exam.ExamStatus.USED.ordinal(), Exam.ExamType.ONLINE.ordinal(), "4DTG");

            Database.getInstance().executeUpdate(SqlStatements.EXAM_INSERT_NEW,
                    "010101", "0006", "Hello ,, student notes 222", "Hello all my note2222", new Date(),
                    40, "[{\"question\":\"01001\",\"grade\":20},{\"question\":\"01002\",\"grade\":20},{\"question\":\"01003\",\"grade\":10},{\"question\":\"01004\",\"grade\":25},{\"question\":\"01007\",\"grade\":25}]",
                    "01", "01", Exam.ExamStatus.NEW.ordinal(), Exam.ExamType.MANUAL.ordinal(), "4DTT");
        } catch (Exception e) {
//            L.err(e);
        }
    }

    private void createRecord() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.RECORD_INSERT_NEW_RECORD, "1", "0007", "01", "010101", "{\"teacherId\":\"0006\",\"status\":1,\"answers\":[3,2,1,4,3],\"totalGrade\":30,\"startDate\":1466211816920, \"endDate\":1466213016920, \"duration\":40}");
            Database.getInstance().executeUpdate(SqlStatements.RECORD_INSERT_NEW_RECORD, "2", "0007", "01", "010100", "{\"teacherId\":\"0006\",\"status\":0,\"answers\":[3,2,1,1],\"totalGrade\":null,\"startDate\":1466298216920,\"endDate\":null, \"duration\":20}");
            Database.getInstance().executeUpdate(SqlStatements.RECORD_INSERT_NEW_RECORD, "3", "0008", "02", "010101", "{\"teacherId\":\"0006\",\"status\":1,\"answers\":[3,2,1,4,3],\"totalGrade\":30,\"startDate\":1466211816920, \"endDate\":1466213016920, \"duration\":40}");
            

        } catch (Exception e) {
//            L.err(e);
        }

    }

}



































