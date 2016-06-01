package testsys.utils;

public class SqlStatements {


    /**
     * CourseTable Statements
     */
    public static final String COURSE_TABLE = "CourseTable";
    public static final String COURSE_CREATE_TABLE = "CREATE TABLE IF NOT EXIST '" + COURSE_TABLE + "' (TEXT ID, TEXT Name,TEXT TeacherID, TEXT ProfessionID)";
    public static final String COURSE_GET_COURSE_BY_ID = "SELECT * FROM " + COURSE_TABLE + " WHERE " + SqlColumns.COURSE_ID + " = ?";
    public static final String COURSE_GET_PROFESSION_COURSES = "SELECT * FROM " + COURSE_TABLE + " WHERE " + SqlColumns.PROFESSION_ID + " = ?";
    public static final String COURSE_INSERT_NEW_COURSE = "INSERT INTO " + COURSE_TABLE + " ( " +
            SqlColumns.COURSE_ID + ", " +
            SqlColumns.COURSE_NAME + ", " +
            SqlColumns.COURSE_TEACHER_ID + ", " +
            SqlColumns.COURSE_PROFESSION_ID + ") VALUES(?,?,?,?)";
    public static final String COURSE_UPDATE_EXISTING_COURSE = "UPDATE " + COURSE_TABLE + " SET " +
            SqlColumns.COURSE_NAME + "=? , " +
            SqlColumns.COURSE_TEACHER_ID + "=? , " +
            SqlColumns.COURSE_PROFESSION_ID + "=? WHERE " + SqlColumns.COURSE_ID + "=?";
    public static final String COURSE_DELETE_COURSE = "DELETE FROM " + COURSE_TABLE + " WHERE " + SqlColumns.COURSE_ID + "=?";


    /**
     * QuestionTable Statements
     */
    public static final String QUESTION_TABLE = "QuestionTable";
    public static final String QUESTION_GET_QUESTION_BY_QUESTION_ID = "SELECT * FROM " + QUESTION_TABLE + " WHERE " + SqlColumns.QUESTION_ID + " = ?";


    /**
     * ProfessionTable Statements
     */
    public static final String PROFESSION_TABLE = "ProfessionTable";
    public static final String PROFESSION_GET_PROFESSION_BY_PROFESSION_ID = "SELECT * FROM " + PROFESSION_TABLE + " WHERE " + SqlColumns.PROFESSION_ID + " = ?";

}
