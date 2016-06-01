package testsys.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Class Name:      Exam
 * Table Name:      ExamTable
 * Table Columns:   TEXT ID, TEXT Author,TEXT Description, DATETIME ExamDate, INTEGER Duration, TEXT QuestionsList
 */
public class Exam {
    private String mId;
    private Teacher mAuthor;
    private String mDescription;
    private Date mDate;
    private Integer mDuration;
    private List<QuestionsOrder> mQuestionsList;


    /**
     * Default Constructor
     */
    public Exam(){}

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
    public Exam(String id, Teacher author, String description, Date date, Integer duration, List<QuestionsOrder> questionsList) {
        mId = id;
        mAuthor = author;
        mDescription = description;
        mDate = date;
        mDuration = duration;
        mQuestionsList = questionsList;
    }

    /**
     * Exam Constructor
     *
     * @param id
     * @param author        Added by teacher
     * @param description   Exam Description
     * @param date          Exam publish date
     * @param duration      Length in minutes
     * @param questionsList JSON string contains List of Pairs includes Question and Grade as String
     * @throws Exception failed to parse questionsList string to List<Pair<Question, Integer>>
     */
    public Exam(String id, Teacher author, String description, Date date, Integer duration, String questionsList) throws Exception{
        mId = id;
        mAuthor = author;
        mDescription = description;
        mDate = date;
        mDuration = duration;
        mQuestionsList = new ArrayList<>();
        JSONArray questionsJSON = new JSONArray(questionsList);
        for(int i=0; i<questionsJSON.length(); i++){
            JSONObject tempQuestion = questionsJSON.getJSONObject(i);
            Question question = Question.getQuestionByQuestionId(tempQuestion.getString(Constants.QUESTION_ID));
            assert question != null;
            Integer questionOrder = tempQuestion.getInt(Constants.QUESTION_ORDER);
            mQuestionsList.add(new QuestionsOrder(question, questionOrder));
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Teacher getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Teacher author) {
        mAuthor = author;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Integer getDuration() {
        return mDuration;
    }

    public void setDuration(Integer duration) {
        mDuration = duration;
    }

    public List<QuestionsOrder> getQuestionsList() {
        return mQuestionsList;
    }

    public void setQuestionsList(List<QuestionsOrder> questionsList) {
        mQuestionsList = questionsList;
    }

    /**
     * Insert exam instance as new exam in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void insert() throws Exception {

    }

    /**
     * Update last changes of this exam instance in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void update() throws Exception {

    }

    /**
     * Delete this exam instance from the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void delete() throws Exception {

    }

    /**
     * Fetch exam from the database where ExamColumn equals to examId parameter
     *
     * @param examId
     * @return Exam instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public Course getExamByExamId(String examId) throws Exception {
        return null;
    }

    /**
     * Fetch all exams from Database where CourseColumn equals to courseId parameter
     *
     * @param courseId
     * @return List of Exams if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Exam> getExamsByCourseId(String courseId) throws Exception {
        return null;
    }
}
