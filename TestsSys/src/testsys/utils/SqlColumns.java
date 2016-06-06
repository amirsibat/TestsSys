package testsys.utils;

public class SqlColumns {


    /**
     * Profession column keys
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
     * CourseTable column keys
     */
    public static final String COURSE_ID = "ID";
    public static final String COURSE_NAME = "Name";
    public static final String COURSE_PROFESSION_ID = "ProfessionID";
    public static final String[] COURSE_ALL_COLUMNS = {
            COURSE_ID,
            COURSE_NAME,
            COURSE_PROFESSION_ID
    };
    public static final String[] COURSE_ALL_COLUMNS_TYPES = {
            "TEXT",
            "TEXT",
            "TEXT"
    };



	/**
	 * UserTable columns keys
	 */
	public static final String USER_ID = "ID";
	public static final String USER_USERNAME = "Username";
	public static final String USER_PASSWORD = "Password";
	public static final String USER_FIRST_NAME = "FirstName";
	public static final String USER_LAST_NAME = "LastName";
	public static final String USER_DESCRIPTION = "Description";
	public static final String USER_COURSES = "Courses";
	public static final String USER_TYPE = "UserType";
	public static final String[] USER_ALL_COLUMNS = {
			USER_ID,
			USER_USERNAME,
			USER_PASSWORD,
			USER_FIRST_NAME,
			USER_LAST_NAME,
			USER_DESCRIPTION,
			USER_COURSES,
			USER_TYPE
    };
	public static final String[] USER_ALL_COLUMNS_TYPES = {
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "INTEGER"
    };

    
    
    /**
     * Exam column keys
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
    public static final String[] EXAM_ALL_COLUMNS = {
    		EXAM_ID,
    		EXAM_TEACHER_ID,
    		EXAM_DESCRIPTION,
    		EXAM_DESCRIPTION_TEACHER,
    		EXAM_DATE_ADDED,
    		EXAM_DURATION,
    		EXAM_QUESTIONS_LIST,
    		EXAM_COURSE_ID,
    		EXAM_PROFESSION_ID,
    		EXAM_STATUS
    };
    public static final String[] EXAM_ALL_COLUMNS_TYPES = {
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "DATE",
            "INTEGER",
            "TEXT",
            "TEXT",
            "INTEGER"
    };



    /**
     * QuestionTable column keys
     */
    public static final String QUESTION_ID = "ID";
    public static final String QUESTION_QUESTION_TEXT = "QuestionText";
    public static final String QUESTION_CORRECT_ANSWER = "CorrectAnswer";
    public static final String QUESTION_TEACHER_ID = "TeacherID";
    public static final String QUESTION_PROFESSION_ID = "ProfessionID";
    public static final String QUESTION_QUESTION_OPTIONS1 = "QuestionOptions1";
    public static final String QUESTION_QUESTION_OPTIONS2 = "QuestionOptions2";
    public static final String QUESTION_QUESTION_OPTIONS3 = "QuestionOptions3";
    public static final String QUESTION_QUESTION_OPTIONS4 = "QuestionOptions4";
    public static final String QUESTION_COURSES_LIST = "CoursesList";
    public static final String[] QUESTION_ALL_COLUMNS = {
            QUESTION_ID,
            QUESTION_QUESTION_TEXT,
            QUESTION_CORRECT_ANSWER,
            QUESTION_TEACHER_ID,
            QUESTION_PROFESSION_ID,
            QUESTION_QUESTION_OPTIONS1,
            QUESTION_QUESTION_OPTIONS2,
            QUESTION_QUESTION_OPTIONS3,
            QUESTION_QUESTION_OPTIONS4,
            QUESTION_COURSES_LIST
    };
    public static final String[] QUESTION_ALL_COLUMNS_TYPES = {
            "TEXT",
            "TEXT",
            "INETEGER",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
    };


    /**
     * RecordTable column keys
     */

    public static final String RECORD_ID = "ID";
    public static final String RECORD_STUDENT_ID = "StudentID";
    public static final String RECORD_COURSE_ID = "CourseID";
    public static final String RECORD_EXAM_ID = "ExamID";
    public static final String RECORD_EXTRA_DATA = "ExtraData";

    public static final String[] RECORD_ALL_COLUMNS = {
            RECORD_ID,
            RECORD_STUDENT_ID,
            RECORD_COURSE_ID,
            RECORD_EXAM_ID,
            RECORD_EXTRA_DATA
    };

    public static final String[] RECORD_ALL_COLUMNS_TYPES = {
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT",
            "TEXT"
    };

}
