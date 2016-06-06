package testsys.utils;

public class SqlStatements {

    /**
     * UserTable
     */
    public static final String USER_TABLE = "UserTable";
    public static final String USER_LOGIN = "SELECT * FROM " + USER_TABLE + " WHERE " + SqlColumns.USER_USERNAME
            + "=? AND " + SqlColumns.USER_PASSWORD + "=?";

    /**
     * CourseTable Statements
     */
    public static final String COURSE_TABLE = "CourseTable";
    public static final String COURSE_CREATE_TABLE = "CREATE TABLE IF NOT EXIST '" + COURSE_TABLE
            + "' (TEXT ID, TEXT Name,TEXT TeacherID, TEXT ProfessionID)";
    public static final String COURSE_GET_COURSE_BY_ID = "SELECT * FROM " + COURSE_TABLE + " WHERE "
            + SqlColumns.COURSE_ID + " = ?";
    public static final String COURSE_GET_PROFESSION_COURSES = "SELECT * FROM " + COURSE_TABLE + " WHERE "
            + SqlColumns.PROFESSION_ID + " = ?";
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
     * QuestionTable Statements
     */
    public static final String QUESTION_TABLE = "QuestionTable";
    public static final String QUESTION_GET_QUESTION_BY_QUESTION_ID = "SELECT * FROM " + QUESTION_TABLE + " WHERE "
            + SqlColumns.QUESTION_ID + " = ?";
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
     * ProfessionTable Statements
     */
    public static final String PROFESSION_TABLE = "ProfessionTable";
    public static final String PROFESSION_GET_PROFESSION_BY_PROFESSION_ID = "SELECT * FROM " + PROFESSION_TABLE
            + " WHERE " + SqlColumns.PROFESSION_ID + " = ?";

    /**
     * ExamTable Statements
     */
    public static final String EXAM_TABLE = "ExamTable";
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
            + SqlColumns.EXAM_STATUS + ") VALUES(?,?,?,?,?,?,?,?,?,?) ";

    public static final String EXAM_UPDATE_STATEMENT = "UPDATE " + EXAM_TABLE + " SET "
            + SqlColumns.EXAM_DESCRIPTION + "=? , " 
            + SqlColumns.EXAM_DESCRIPTION_TEACHER + "=? , " 
            + SqlColumns.EXAM_DURATION + "=? , " 
            + SqlColumns.EXAM_QUESTIONS_LIST + "=? , " 
            + SqlColumns.EXAM_STATUS + "=? "
            + " WHERE " + SqlColumns.EXAM_ID + "=?";

    public static final String EXAM_DELETE_STATEMENT = "DELETE FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_ID + "=?";
    public static final String EXAM_GET_EXAMS_BY_COURSE_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_COURSE_ID + " = ?";
    public static final String EXAM_GET_EXAMS_BY_TEACHER_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_TEACHER_ID + " = ?";
    public static final String EXAM_GET_EXAMS_BY_PROFESSION_ID = "SELECT * FROM " + EXAM_TABLE + " WHERE " + SqlColumns.EXAM_PROFESSION_ID + " = ?";
}
