package testsys.utils;

import testsys.models.User;

public class SqlStatements {

    /**
     * Table names
     */
    public static final String PROFESSION_TABLE = "ProfessionTable";
    public static final String COURSE_TABLE = "CourseTable";
    public static final String USER_TABLE = "UserTable";
    public static final String QUESTION_TABLE = "QuestionTable";
    public static final String EXAM_TABLE = "ExamTable";
    public static final String RECORD_TABLE = "RecordTable";
    public static final String REQUEST_TABLE = "RequestTable";


    /**
     * Create Tables
     */
    public static final String PROFESSION_CREATE_TABLE = "CREATE TABLE " + PROFESSION_TABLE + " (ID VARCHAR(2), Name VARCHAR(30), PRIMARY KEY (ID))";
    public static final String COURSE_CREATE_TABLE = "CREATE TABLE " + COURSE_TABLE + " (ID VARCHAR(2), Name VARCHAR(30), ProfessionID VARCHAR(2), PRIMARY KEY (ID))";
    public static final String USER_CREATE_TABLE = "CREATE TABLE " + USER_TABLE + " (ID VARCHAR(4), Username VARCHAR(30), Password VARCHAR(30), FirstName VARCHAR(30), LastName VARCHAR(30), Description VARCHAR(100), Courses VARCHAR(100), UserType INT, UserStId VARCHAR(100), PRIMARY KEY (ID))";
    public static final String EXAM_CREATE_TABLE = "CREATE TABLE " + EXAM_TABLE + " (ID VARCHAR(6), TeacherID VARCHAR(4), Description VARCHAR(200), DescriptionTeacher VARCHAR(200), DateAdded DATE, Duration INT, QuestionsList VARCHAR(2000), CourseID VARCHAR(2), ProfessionID VARCHAR(2), Status INT, ExamType INT, ExamCode VARCHAR(4), PRIMARY KEY (ID))";
    public static final String QUESTION_CREATE_TABLE = "CREATE TABLE " + QUESTION_TABLE + "  (ID VARCHAR(6), QuestionText VARCHAR(200), CorrectAnswer INT,  TeacherID VARCHAR(4), ProfessionID VARCHAR(2), QuestionOptions1 VARCHAR(200), QuestionOptions2  VARCHAR(200), QuestionOptions3 VARCHAR(200), QuestionOptions4 VARCHAR(200), CoursesList VARCHAR(100), PRIMARY KEY (ID))";
    public static final String RECORD_CREATE_TABLE = "CREATE TABLE " + RECORD_TABLE + "  (ID VARCHAR(10), StudentID VARCHAR(4), CourseID VARCHAR(2), ExamID VARCHAR(6), ExtraData VARCHAR(2000), PRIMARY KEY (ID))";
    public static final String REQUEST_CREATE_TABLE = "CREATE TABLE " + REQUEST_TABLE + "  (ID VARCHAR(10), Pending Integer, ExamID VARCHAR(6), RequestText VARCHAR(1000), TeacherID VARCHAR(4), DurationToAdd Integer, PRIMARY KEY (ID))";


    /**
     * ProfessionTable Statements
     */
    public static final String PROFESSION_GET_PROFESSION_BY_PROFESSION_ID = "SELECT * FROM " + PROFESSION_TABLE
            + " WHERE " + SqlColumns.PROFESSION_ID + " = ?";
    public static final String PROFESSION_INSERT_NEW_PROFESSION = "INSERT INTO " + PROFESSION_TABLE + " ("
            + SqlColumns.PROFESSION_ID + ", "
            + SqlColumns.PROFESSION_NAME + ") VALUES(?,?)";


    /**
     * CourseTable Statements
     */
    public static final String COURSE_GET_ALL_COURSES = "SELECT * FROM " + COURSE_TABLE;
    public static final String COURSE_GET_COURSE_BY_ID = "SELECT * FROM " + COURSE_TABLE + " WHERE "
            + SqlColumns.COURSE_ID + " = ?";
    public static final String COURSE_GET_PROFESSION_COURSES = "SELECT * FROM " + COURSE_TABLE + " WHERE "
            + SqlColumns.COURSE_PROFESSION_ID + " = ?";
    public static final String COURSE_INSERT_NEW_COURSE = "INSERT INTO " + COURSE_TABLE + " ( "
            + SqlColumns.COURSE_ID + ", "
            + SqlColumns.COURSE_NAME + ", "
            + SqlColumns.COURSE_PROFESSION_ID + ") VALUES(?,?,?)";
    public static final String COURSE_UPDATE_EXISTING_COURSE = "UPDATE " + COURSE_TABLE + " SET "
            + SqlColumns.COURSE_NAME + "=? , "
            + SqlColumns.COURSE_PROFESSION_ID + "=? WHERE "
            + SqlColumns.COURSE_ID
            + "=?";
    public static final String COURSE_DELETE_COURSE = "DELETE FROM " + COURSE_TABLE + " WHERE " + SqlColumns.COURSE_ID
            + "=?";

    /**
     * UserTable
     */
    public static final String USER_LOGIN = "SELECT * FROM " + USER_TABLE + " WHERE " + SqlColumns.USER_USERNAME
            + "=? AND " + SqlColumns.USER_PASSWORD + "=?";
    public static final String USER_GET_USER_BY_USER_ID = "SELECT * FROM " + USER_TABLE + " WHERE " + SqlColumns.USER_ID
            + "=?";
    public static final String USER_GET_ALL_TEACHERS = "SELECT * FROM " + USER_TABLE + " WHERE " + SqlColumns.USER_TYPE
            + "=" + User.Type.TEACHER.ordinal();
    public static final String USER_GET_ALL_STUDENTS = "SELECT * FROM " + USER_TABLE + " WHERE " + SqlColumns.USER_TYPE
            + "=" + User.Type.STUDENT.ordinal();
    public static final String USER_INSERT_NEW_USER = "INSERT INTO " + USER_TABLE + " ("
            + SqlColumns.USER_ID + ", "
            + SqlColumns.USER_USERNAME + ", "
            + SqlColumns.USER_PASSWORD + ", "
            + SqlColumns.USER_FIRST_NAME + ", "
            + SqlColumns.USER_LAST_NAME + ", "
            + SqlColumns.USER_DESCRIPTION + ", "
            + SqlColumns.USER_COURSES + ", "
            + SqlColumns.USER_TYPE + ", "
            + SqlColumns.USER_ST_ID
            + ") VALUES(?,?,?,?,?,?,?,?,?)";
    public static final String USER_DELETE = "DELETE FROM " + USER_TABLE + " WHERE " + SqlColumns.USER_ID + "=?";

    public static final String STUDENT_GET_STUDENTS_BY_COURSE_ID = "SELECT * FROM " + USER_TABLE + " WHERE "
            + SqlColumns.USER_TYPE + "=" + User.Type.STUDENT.ordinal() + " AND " + SqlColumns.USER_COURSES + " LIKE ?";


    /**
     * ExamTable Statements
     */

    public static final String EXAM_GET_EXAM_BY_EXAM_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_ID + " = ?";
    public static final String EXAM_GET_ALL = "SELECT * FROM " + EXAM_TABLE;
    public static final String EXAM_GET_EXAM_IDS = "SELECT ID FROM " + EXAM_TABLE;
    public static final String EXAM_INSERT_NEW = "INSERT INTO " + EXAM_TABLE + " ("
            + SqlColumns.EXAM_ID + ", "
            + SqlColumns.EXAM_TEACHER_ID + ", "
            + SqlColumns.EXAM_DESCRIPTION + ", "
            + SqlColumns.EXAM_DESCRIPTION_TEACHER + ", "
            + SqlColumns.EXAM_DATE_ADDED + ", "
            + SqlColumns.EXAM_DURATION + ", "
            + SqlColumns.EXAM_QUESTIONS_LIST + ", "
            + SqlColumns.EXAM_COURSE_ID + ", "
            + SqlColumns.EXAM_PROFESSION_ID + ", "
            + SqlColumns.EXAM_STATUS + ", "
            + SqlColumns.EXAM_TYPE + ", "
            + SqlColumns.EXAM_CODE + ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ";

    public static final String EXAM_UPDATE_STATEMENT = "UPDATE " + EXAM_TABLE + " SET "
            + SqlColumns.EXAM_DESCRIPTION + "=? , "
            + SqlColumns.EXAM_DESCRIPTION_TEACHER + "=? , "
            + SqlColumns.EXAM_DURATION + "=? , "
            + SqlColumns.EXAM_QUESTIONS_LIST + "=? , "
            + SqlColumns.EXAM_STATUS + "=? "
            + " WHERE " + SqlColumns.EXAM_ID + "=?";

    public static final String EXAM_DELETE = "DELETE FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_ID + "=?";
    public static final String EXAM_GET_EXAMS_BY_COURSE_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_COURSE_ID + " = ?";
    public static final String EXAM_GET_EXAMS_BY_TEACHER_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_TEACHER_ID + " = ?";
    public static final String EXAM_GET_EXAMS_BY_PROFESSION_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_PROFESSION_ID + " = ?";


    /**
     * QuestionTable Statements
     */
    public static final String QUESTION_GET_QUESTION_BY_QUESTION_ID = "SELECT * FROM " + QUESTION_TABLE + " WHERE "
            + SqlColumns.QUESTION_ID + " = ?";
    public static final String QUESTION_GET_ALL_QUESTIONS = "SELECT * FROM " + QUESTION_TABLE;
    public static final String QUESTION_GET_QUESTIONS_BY_TEACHER_ID = "SELECT * FROM " + QUESTION_TABLE + " WHERE "
            + SqlColumns.QUESTION_TEACHER_ID + " = ?";
    public static final String QUESTION_GET_QUESTIONS_BY_PROFESSION = "SELECT * FROM " + QUESTION_TABLE + " WHERE "
            + SqlColumns.QUESTION_PROFESSION_ID + " = ?";
    public static final String QUESTION_GET_QUESTIONS_BY_COURSE_ID = "SELECT * FROM " + QUESTION_TABLE + " WHERE "
            + SqlColumns.QUESTION_COURSES_LIST + " LIKE '%?%'";
    public static final String QUESTION_UPDATE = "UPDATE " + COURSE_TABLE + " SET "
            + SqlColumns.QUESTION_QUESTION_TEXT + "=? ,"
            + SqlColumns.QUESTION_CORRECT_ANSWER + "=? ,"
            + SqlColumns.QUESTION_QUESTION_OPTIONS1 + "=? ,"
            + SqlColumns.QUESTION_QUESTION_OPTIONS2 + "=? ,"
            + SqlColumns.QUESTION_QUESTION_OPTIONS3 + "=? ,"
            + SqlColumns.QUESTION_QUESTION_OPTIONS4 + "=? ,"
            + " WHERE " + SqlColumns.QUESTION_ID + "=?";

    public static final String QUESTION_INSER_NEW_QUESTION = "INSERT INTO " + QUESTION_TABLE + " ("
            + SqlColumns.QUESTION_ID + ", " + SqlColumns.QUESTION_QUESTION_TEXT + ", "
            + SqlColumns.QUESTION_CORRECT_ANSWER + ", "
            + SqlColumns.QUESTION_TEACHER_ID + ", "
            + SqlColumns.QUESTION_PROFESSION_ID + ", "
            + SqlColumns.QUESTION_QUESTION_OPTIONS1 + ", "
            + SqlColumns.QUESTION_QUESTION_OPTIONS2 + ", "
            + SqlColumns.QUESTION_QUESTION_OPTIONS3 + ", "
            + SqlColumns.QUESTION_QUESTION_OPTIONS4 + ", "
            + SqlColumns.QUESTION_COURSES_LIST
            + ") VALUES(?,?,?,?,?,?,?,?,?,?)";


    /**
     * RecordTable Statements
     */
    public static final String RECORD_GET_ALL_RECORDS = "SELECT * FROM " + RECORD_TABLE;
    public static final String RECORD_GET_RECORD_BY_RECORD_ID = "SELECT * FROM " + RECORD_TABLE + " WHERE " + SqlColumns.RECORD_ID + "=?";
    public static final String RECORD_GET_RECORDS_BY_STUDENT_ID = "SELECT * FROM " + RECORD_TABLE + " WHERE " + SqlColumns.RECORD_STUDENT_ID + "=?";
    public static final String RECORD_GET_RECORDS_BY_EXAM_ID = "SELECT * FROM " + RECORD_TABLE + " WHERE " + SqlColumns.RECORD_EXAM_ID + "=?";
    public static final String RECORD_GET_RECORDS_BY_COURSE_ID = "SELECT * FROM " + RECORD_TABLE + " WHERE " + SqlColumns.RECORD_COURSE_ID + "=?";

    public static final String RECORD_INSERT_NEW_RECORD = "INSERT INTO " + RECORD_TABLE + " ("
            + SqlColumns.RECORD_ID + ", "
            + SqlColumns.RECORD_STUDENT_ID + ", "
            + SqlColumns.RECORD_COURSE_ID + ", "
            + SqlColumns.RECORD_EXAM_ID + ", "
            + SqlColumns.RECORD_EXTRA_DATA
            + ") VALUES(?,?,?,?,?)";

    public static final String RECORD_UPDATE_RECORD = "UPDATE " + RECORD_TABLE + " SET "
            + SqlColumns.RECORD_EXTRA_DATA + "=? WHERE " + SqlColumns.RECORD_ID + "=?";

    /**
     * RequestTable Statements
     */
    public static final String REQUEST_GET_REQUEST_BY_REQUEST_ID = "SELECT * FROM " + REQUEST_TABLE + " WHERE " + SqlColumns.REQUEST_ID + "=?";
    public static final String REQUEST_GET_REQUESTS_BY_TEACHER = "SELECT * FROM " + REQUEST_TABLE + " WHERE " + SqlColumns.REQUEST_TEACHER_ID + "=?";
    public static final String REQUEST_GET_PINDEING_REQUESTS = "SELECT * FROM " + REQUEST_TABLE + " WHERE " + SqlColumns.REQUEST_PENDING + "=?";
    public static final String REQUEST_INSERT_NEW_REQUEST = "INSERT INTO " + REQUEST_TABLE + " ("
            + SqlColumns.REQUEST_ID + ", "
            + SqlColumns.REQUEST_PENDING + ", "
            + SqlColumns.REQUEST_EXAM_ID + ", "
            + SqlColumns.REQUEST_REQUEST_TEXT + ", "
            + SqlColumns.REQUEST_TEACHER_ID + ", "
            + SqlColumns.REQUEST_DURATION_TO_ADD
            + ") VALUES(?,?,?,?,?,?)";

    public static final String REQUEST_UPDATE_REQUEST = "UPDATE " + REQUEST_TABLE + " SET "
            + SqlColumns.REQUEST_PENDING + "=? "
            + " WHERE " + SqlColumns.REQUEST_ID + "=?";
}





















