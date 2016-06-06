package testsys.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import testsys.constants.AppConstants;
import testsys.utils.Database;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

/**
 * Class Name: Exam Table Name: ExamTable Table Columns: TEXT ID, TEXT
 * Author,TEXT Description, DATETIME ExamDate, INTEGER Duration, TEXT
 * QuestionsList
 */
public class Exam {

    public enum ExamStatus {NEW, USED}

    public String mId;
    public Teacher mAuthor;
    public String mDescription;
    public String mDescriptionTeacher;
    public Date mDate;
    public Integer mDuration;
    public List<QuestionsGrade> mQuestionsList;
    public Course mCourse;
    public Profession mProfession;
    public ExamStatus mStatus;

    /**
     * Default Constructor
     */
    public Exam() {
    }

    /**
     * Exam Constructor
     *
     * @param id
     * @param author        Added by teacher
     * @param description   Exam Description
     * @param date          Exam publish date
     * @param duration      Length in minutes
     * @param questionsList List of Pairs includes Question and Grade
     */
    public Exam(String id, Teacher author, String description, String descriptionTeacher, Date date, Integer duration,
                List<QuestionsGrade> questionsList, Course course, Profession profession, ExamStatus status) {
        mId = id;
        mAuthor = author;
        mDescription = description;
        mDescriptionTeacher = descriptionTeacher;
        mDate = date;
        mDuration = duration;
        mQuestionsList = questionsList;
        mCourse = course;
        mProfession = profession;
        mStatus = status;
    }

    /**
     * Exam Constructor
     *
     * @param id
     * @param author        Added by teacher
     * @param description   Exam Description
     * @param date          Exam publish date
     * @param duration      Length in minutes
     * @param questionsList JSON string contains List of Pairs includes Question and Grade
     *                      as String
     */
    public Exam(String id, String teacherId, String description, String descriptionTeacher, Date date, Integer duration,
                String questionsList, String courseId, String professionId, int statusInt) throws Exception {
        mId = id;
        mAuthor = Teacher.getTeacherByTeacherId(teacherId);
        mDescription = description;
        mDescriptionTeacher = descriptionTeacher;
        mDate = date;
        mDuration = duration;
        mQuestionsList = new ArrayList<>();
        JSONArray questionsJSON = new JSONArray(questionsList);
        for (int i = 0; i < questionsJSON.length(); i++) {
            JSONObject tempQuestion = questionsJSON.getJSONObject(i);
            Question question = Question.getQuestionByQuestionId(tempQuestion.getString(AppConstants.QUESTION_ID_KEY));
            assert question != null;
            Integer questionOrder = tempQuestion.getInt(AppConstants.GRADE_KEY);
            mQuestionsList.add(new QuestionsGrade(question, questionOrder));
        }
        mCourse = Course.getCourseByCourseId(courseId);
        mProfession = Profession.getProfessionByProfessionId(professionId);
        mStatus = ExamStatus.values()[statusInt];
    }


    public static boolean createExam(String teacherId, String description, String descriptionTeacher, Date date, Integer duration,
                                     String questionsList, String courseId, String professionId, int statusInt) {
        try {
            Exam exam = new Exam(Exam.generateExamId(professionId, courseId), teacherId, description, descriptionTeacher,
                    date, duration, questionsList, courseId, professionId, statusInt);
            exam.insert();
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Insert exam instance as new exam in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void insert() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.EXAM_INSERT_NEW, mId, mAuthor.mId, mDescription, mDescriptionTeacher,
                mDate, mDuration, new JSONArray(mQuestionsList).toString(), mCourse.mId, mProfession.mId, mStatus.ordinal());
    }

    /**
     * Update last changes of this exam instance in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void update() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.EXAM_UPDATE_STATEMENT, mDescription, mDescriptionTeacher,
                mDuration, new JSONArray(mQuestionsList).toString(), mStatus.ordinal(), mId);
    }

    /**
     * Delete this exam instance from the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void delete() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.EXAM_DELETE, mId);
    }


    /**
     * Fetch all exams from Database
     * parameter
     *
     * @param courseId
     * @return List of Exams if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Exam> getAllExams() throws Exception {
        List<Exam> examsList = new ArrayList<>();
        List<HashMap<String, Object>> examsHash = Database.getInstance().executeListQuery(SqlStatements.EXAM_GET_ALL, SqlColumns.EXAM_ALL_COLUMNS);
        for (int i = 0; i < examsHash.size(); i++) {
            examsList.add(HashMapToObject(examsHash.get(i)));
        }
        return examsList;
    }

    /**
     * Fetch exam from the database where ExamColumn equals to examId parameter
     *
     * @param examId
     * @return Exam instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public Exam getExamByExamId(String examId) throws Exception {
        return HashMapToObject(Database.getInstance().executeSingleQuery(SqlStatements.EXAM_GET_EXAM_BY_EXAM_ID, SqlColumns.EXAM_ALL_COLUMNS, examId));
    }

    /**
     * Fetch all exams from Database where CourseColumn equals to courseId
     * parameter
     *
     * @param courseId
     * @return List of Exams if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Exam> getExamsByCourseId(String courseId) throws Exception {
        List<Exam> examsList = new ArrayList<>();
        List<HashMap<String, Object>> examsHash = Database.getInstance().executeListQuery(SqlStatements.EXAM_GET_EXAMS_BY_COURSE_ID, SqlColumns.EXAM_ALL_COLUMNS, courseId);
        for (int i = 0; i < examsHash.size(); i++) {
            examsList.add(HashMapToObject(examsHash.get(i)));
        }
        return examsList;
    }

    /**
     * Fetch all exams from Database where TeacherColumn equals to teacherId
     * parameter
     *
     * @param courseId
     * @return List of Exams if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Exam> getExamsByTeacherId(String teacherId) throws Exception {
        List<Exam> examsList = new ArrayList<>();
        List<HashMap<String, Object>> examsHash = Database.getInstance().executeListQuery(SqlStatements.EXAM_GET_EXAMS_BY_TEACHER_ID, SqlColumns.EXAM_ALL_COLUMNS, teacherId);
        for (int i = 0; i < examsHash.size(); i++) {
            examsList.add(HashMapToObject(examsHash.get(i)));
        }
        return examsList;
    }

    /**
     * Fetch all exams from Database where ProfessionColumn equals to professionId
     * parameter
     *
     * @param courseId
     * @return List of Exams if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Exam> getExamsByProfessionId(String professionId) throws Exception {
        List<Exam> examsList = new ArrayList<>();
        List<HashMap<String, Object>> examsHash = Database.getInstance().executeListQuery(SqlStatements.EXAM_GET_EXAMS_BY_PROFESSION_ID, SqlColumns.EXAM_ALL_COLUMNS, professionId);
        for (int i = 0; i < examsHash.size(); i++) {
            examsList.add(HashMapToObject(examsHash.get(i)));
        }
        return examsList;
    }


    public static String generateExamId(String professionId, String courseId) {
        String[] columnsNames = new String[1];
        columnsNames[0] = "ID";
        String[] columnsTypes = new String[1];
        columnsNames[0] = "TEXT";

        try {
            int maxExamId = 0;
            JSONArray jsonArray = Database.getInstance().executeListQueryAsJSON(SqlStatements.EXAM_GET_EXAM_IDS,
                    columnsNames, columnsTypes);
            for (int i = 0; i < jsonArray.length(); i++) {
                String examId = jsonArray.getJSONObject(i).getString("ID").substring(4, 6);
                if (Integer.parseInt(examId) > maxExamId) {
                    maxExamId = Integer.parseInt(examId);
                }
            }
            String newExamId = "" + (maxExamId + 1);
            for (int i = newExamId.length(); i < 2; i++) {
                newExamId = "0" + newExamId;
            }
            return professionId + courseId + newExamId;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    private static Exam HashMapToObject(HashMap<String, Object> examHashMap) throws Exception {
        String id = (String) examHashMap.get(SqlColumns.EXAM_ID);
        String teacherId = (String) examHashMap.get(SqlColumns.EXAM_TEACHER_ID);
        String description = (String) examHashMap.get(SqlColumns.EXAM_DESCRIPTION);
        String descriptionTeacher = (String) examHashMap.get(SqlColumns.EXAM_DESCRIPTION_TEACHER);
        Date date = (Date) examHashMap.get(SqlColumns.EXAM_DATE_ADDED);
        Integer duration = (Integer) examHashMap.get(SqlColumns.EXAM_DURATION);
        String questionsList = (String) examHashMap.get(SqlColumns.EXAM_QUESTIONS_LIST);
        String courseId = (String) examHashMap.get(SqlColumns.EXAM_COURSE_ID);
        String professionId = (String) examHashMap.get(SqlColumns.EXAM_PROFESSION_ID);
        int statusInt = (int) examHashMap.get(SqlColumns.EXAM_STATUS);
        return new Exam(id, teacherId, description, descriptionTeacher, date, duration, questionsList, courseId, professionId, statusInt);
    }

}
