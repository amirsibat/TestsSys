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
     */
    public static final String QUESTION_ID = "ID";
    public static final String QUESTION_QUESTION_TEXT = "QuestionText";
    public static final String QUESTION_CORRECT_ANSWER = "CorrectAnswer";
    public static final String QUESTION_TEACHER_ID = "TeacherID";
    public static final String QUESTION_PROFESSION_ID = "CourseID";
    public static final String QUESTION_QUESTION_OPTIONS1 = "QuestionOptions1";
    public static final String QUESTION_QUESTION_OPTIONS2 = "QuestionOptions2";
    public static final String QUESTION_QUESTION_OPTIONS3 = "QuestionOptions3";
    public static final String QUESTION_QUESTION_OPTIONS4 = "QuestionOptions4";
    public static final String QUESTION_COURSES_LIST = "CoursesList";

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
    public static final String[] PROFESSION_ALL_COLUMNS_TYPES = {
            "TEXT",
            "TEXT"
    };
    
    
    /**
     * Exam column kets
     */
    public static final String EXAM_ID = "ID";
    public static final String EXAM_TEACHER_ID = "TeacherID";
    public static final String EXAM_DESCRIPTION = "Description";
    public static final String EXAM_DESCRIPTION_TEACHER = "DescriptionTeacher";
    public static final String EXAM_DATE_ADDED = "DateAdded";
    public static final String EXAM_DURATION = "Duration";
    public static final String EXAM_QUESTIONS_LIST = "QuestionsList";
    public static final String EXAM_COURSE_ID = "CourseID";
    public static final String EXAM_PROFESSION_ID = "ProfessionID";
    public static final String EXAM_STATUS = "Status";    
    
}
