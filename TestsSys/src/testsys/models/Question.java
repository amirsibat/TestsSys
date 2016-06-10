package testsys.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import testsys.utils.Database;
import testsys.utils.L;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

/**
 * Class Name: Question Table Name: QuestionTable Table Columns: TEXT ID, TEXT
 * QuestionText,INTEGER CorrectAnswer, TEXT TeacherID, TEXT ProfessionID, TEXT
 * QuestionOptions, TEXT CoursesList
 */
public class Question {

    public String mId;
    public String mText;
    public Integer mCorrectAnswer;
    public Teacher mAuthor;
    public Profession mProfession;
    public String mOptions1;
    public String mOptions2;
    public String mOptions3;
    public String mOptions4;
    public List<Course> mCourses;

    /**
     * Default Constructor
     */
    public Question() {
    }

    /**
     * Question Constructor
     *
     * @param id
     * @param text          Question text + description
     * @param correctAnswer The index of the correct answer
     * @param author        Added by teacher
     * @param profession    Associated profession
     * @param options       Array contains list of answers text maintain order
     * @param courses       Array contains list of mCourses maintain order
     */
    public Question(String id, String text, Integer correctAnswer, Teacher author, Profession profession,
                    String options1, String options2, String options3, String options4, List<Course> courses) {
        mId = id;
        mText = text;
        mCorrectAnswer = correctAnswer;
        mAuthor = author;
        mProfession = profession;
        mOptions1 = options1;
        mOptions2 = options2;
        mOptions3 = options3;
        mOptions4 = options4;
        mCourses = courses;
    }

    /**
     * Question Constructor
     *
     * @param id
     * @param text            Question text + description
     * @param correctAnswer   The index of the correct answer
     * @param authorId        Added by teacherId
     * @param professionId    Associated profession
     * @param optionsAsString Array Text separated by commas contains list of answers text
     *                        maintain order
     * @param coursesAsString Array Text separated by commas contains list of mCourses
     * @throws Exception failed to retrieve course by courseId, or invalid optionsList
     */
    public Question(String id, String text, Integer correctAnswer, String authorId, String professionId,
                    String options1, String options2, String options3, String options4, String coursesAsString)
            throws Exception {
        mId = id;
        mText = text;
        mCorrectAnswer = correctAnswer;
        mAuthor = Teacher.getTeacherByTeacherId(authorId);
        mProfession = Profession.getProfessionByProfessionId(professionId);
        mOptions1 = options1;
        mOptions2 = options2;
        mOptions3 = options3;
        mOptions4 = options4;
        mCourses = new ArrayList<>();

        if (!coursesAsString.isEmpty()) {
            String[] tempCoursesArray = coursesAsString.split(",");
            for (int i = 0; i < tempCoursesArray.length; i++) {
                mCourses.add(Course.getCourseByCourseId(tempCoursesArray[i]));
            }
        }
    }

    public static boolean createQuestion(String text, Integer correctAnswer, String authorId, String professionId,
                                         String options1, String options2, String options3, String options4, String coursesAsString) {

        String questionId = generateQuestionId(professionId);
        try {
            Question newQuestion = new Question(questionId, text, correctAnswer, authorId, professionId, options1,
                    options2, options3, options4, coursesAsString);
            return newQuestion.insert();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public static boolean createQuestion(String text, Integer correctAnswer, Teacher author,
                                         String options1, String options2, String options3, String options4, List<Course> courses) {
        return createQuestion(text, correctAnswer, author, courses.get(0).mProfession, options1, options2, options3, options4, courses);
    }

    public static boolean createQuestion(String text, Integer correctAnswer, Teacher author, Profession profession,
                                         String options1, String options2, String options3, String options4, List<Course> courses) {

        String questionId = generateQuestionId(profession.mId);
        try {
            Question newQuestion = new Question(questionId, text, correctAnswer, author, profession, options1,
                    options2, options3, options4, courses);
            return newQuestion.insert();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean insert() {

        try {
            String coursesAsString = "";
            for (int i = 0; i < mCourses.size(); i++) {
                coursesAsString += mCourses.get(i).mId + (i < mCourses.size() - 1 ? "," : "");
            }

            Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, mId, mText, mCorrectAnswer,
                    mAuthor, mProfession, mOptions1, mOptions2, mOptions3, mOptions4, coursesAsString);
            return true;
        } catch (Exception e) {
            L.err(e);
        }
        return false;
    }

    public boolean update() {
        try {
            Database.getInstance().executeUpdate(SqlStatements.QUESTION_UPDATE,
                    mText,
                    mCorrectAnswer,
                    mOptions1,
                    mOptions2,
                    mOptions3,
                    mOptions4,
                    mId);
            return true;
        } catch (Exception e) {
            L.err(e);
        }
        return false;
    }

    public static List<Question> getAllQuestionByTeacherId(String teacherId) {
        List<Question> questionList = new ArrayList<>();
        try {
            List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.QUESTION_GET_QUESTIONS_BY_TEACHER_ID, SqlColumns.QUESTION_ALL_COLUMNS, teacherId);
            for (int i = 0; i < objectsList.size(); i++) {
                questionList.add(hashMapToObject(objectsList.get(i)));
            }
        } catch (Exception e) {
            L.err(e);
        }
        return questionList;
    }

    public static List<Question> getAllQuestionByProfessionId(String professionId) {
        List<Question> questionList = new ArrayList<>();
        try {
            List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.QUESTION_GET_QUESTIONS_BY_PROFESSION, SqlColumns.QUESTION_ALL_COLUMNS, professionId);
            for (int i = 0; i < objectsList.size(); i++) {
                questionList.add(hashMapToObject(objectsList.get(i)));
            }
        } catch (Exception e) {
            L.err(e);
        }
        return questionList;
    }

    public static List<Question> getAllQuestionByCourseId(String courseId) {
        List<Question> questionList = new ArrayList<>();
        try {
            List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.QUESTION_GET_QUESTIONS_BY_COURSE_ID, SqlColumns.QUESTION_ALL_COLUMNS, courseId);
            for (int i = 0; i < objectsList.size(); i++) {
                questionList.add(hashMapToObject(objectsList.get(i)));
            }
        } catch (Exception e) {
            L.err(e);
        }
        return questionList;
    }

    /**
     * Fetch question from the database where questionId parameter
     *
     * @param questionId
     * @return Question instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public static Question getQuestionByQuestionId(String questionId) throws Exception {
        return hashMapToObject(Database.getInstance().executeSingleQuery(SqlStatements.QUESTION_GET_QUESTION_BY_QUESTION_ID, SqlColumns.QUESTION_ALL_COLUMNS, questionId));
    }


    public static Question hashMapToObject(HashMap<String, Object> objectHashMap) throws Exception {
        String id = (String) objectHashMap.get(SqlColumns.QUESTION_ID);
        String text = (String) objectHashMap.get(SqlColumns.QUESTION_QUESTION_TEXT);
        Integer correctAnswer = (Integer) objectHashMap.get(SqlColumns.QUESTION_CORRECT_ANSWER);
        String authorId = (String) objectHashMap.get(SqlColumns.QUESTION_TEACHER_ID);
        String professionId = (String) objectHashMap.get(SqlColumns.QUESTION_PROFESSION_ID);
        String options1 = (String) objectHashMap.get(SqlColumns.QUESTION_QUESTION_OPTIONS1);
        String options2 = (String) objectHashMap.get(SqlColumns.QUESTION_QUESTION_OPTIONS2);
        String options3 = (String) objectHashMap.get(SqlColumns.QUESTION_QUESTION_OPTIONS3);
        String options4 = (String) objectHashMap.get(SqlColumns.QUESTION_QUESTION_OPTIONS4);
        String coursesAsString = (String) objectHashMap.get(SqlColumns.QUESTION_COURSES_LIST);

        return new Question(id,
                text,
                correctAnswer,
                authorId,
                professionId,
                options1,
                options2,
                options3,
                options4,
                coursesAsString);
    }

    public static String generateQuestionId(String professionId) {
        String[] columnsNames = new String[1];
        columnsNames[0] = "ID";
        String[] columnsTypes = new String[1];
        columnsNames[0] = "TEXT";

        try {
            int maxQuestionId = 0;
            JSONArray jsonArray = Database.getInstance().executeListQueryAsJSON("SELECT ID FROM QUESTION_TABLE",
                    columnsNames, columnsTypes);
            for (int i = 0; i < jsonArray.length(); i++) {
                String QuestionId = jsonArray.getJSONObject(i).getString("ID").substring(2, 5);
                if (Integer.parseInt(QuestionId) > maxQuestionId) {
                    maxQuestionId = Integer.parseInt(QuestionId);
                }
            }
            String newQuestionId = "" + (maxQuestionId + 1);
            for (int i = newQuestionId.length(); i < 3; i++) {
                newQuestionId = "0" + newQuestionId;
            }
            return professionId + newQuestionId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
