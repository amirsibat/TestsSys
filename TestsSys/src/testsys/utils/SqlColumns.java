package testsys.utils;

public class SqlColumns {

    /**
     * CourseTable column keys
     * TEXT ID, TEXT Name,TEXT TeacherID, TEXT ProfessionID
     */
    public static final String COURSE_ID = "ID";
    public static final String COURSE_NAME = "Name";
    public static final String COURSE_TEACHER_ID = "TeacherID";
    public static final String COURSE_PROFESSION_ID = "ProfessionID";
    public static final String[] COURSE_ALL_COLUMNS = {
            COURSE_ID,
            COURSE_NAME,
            COURSE_TEACHER_ID,
            COURSE_PROFESSION_ID,
    };





    /**
     * QuestionTable column keys
     * TEXT ID, TEXT QuestionText,INTEGER CorrectAnswer, TEXT TeacherID, TEXT CourseID, TEXT QuestionOptions
     */
    public static final String QUESTION_ID = "ID";
    public static final String QUESTION_QUESTION_TEXT = "QuestionText";
    public static final String QUESTION_CORRECT_ANSWER = "CorrectAnswer";
    public static final String QUESTION_TEACHER_ID = "TeacherID";
    public static final String QUESTION_COURSE_ID = "CourseID";
    public static final String QUESTION_QUESTION_OPTIONS = "QuestionOptions";


    /**
     * Profession column keys
     * TEXT ID, TEXT Name
     */
    public static final String PROFESSION_ID = "ID";
    public static final String PROFESSION_NAME = "Name";
    public static final String[] PROFESSION_ALL_COLUMNS = {
            PROFESSION_ID,
            PROFESSION_NAME
    };
}
