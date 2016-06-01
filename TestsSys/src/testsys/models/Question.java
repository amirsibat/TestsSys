package testsys.models;



import java.util.ArrayList;
import java.util.List;

/**
 * Class Name:      Question
 * Table Name:      QuestionTable
 * Table Columns:   TEXT ID, TEXT QuestionText,INTEGER CorrectAnswer, TEXT TeacherID, TEXT CourseID, TEXT QuestionOptions
 */
public class Question {
    private String mId;
    private String mText;
    private Integer mCorrectAnswer;
    private Teacher mAuthor;
    private Course mCourse;
    private List<String> mOptions;

    /**
     * Default Constructor
     */
    public Question(){
    }

    /**
     * Question Constructor
     * @param id
     * @param text              Question text + description
     * @param correctAnswer     The index of the correct answer
     * @param author            Added by teacher
     * @param course            Associated course
     * @param options           Array contains list of answers text maintain order
     */
    public Question(String id, String text, Integer correctAnswer, Teacher author, Course course, List<String> options) {
        mId = id;
        mText = text;
        mCorrectAnswer = correctAnswer;
        mAuthor = author;
        mCourse = course;
        mOptions = options;
    }

    /**
     * Question Constructor
     * @param id
     * @param text              Question text + description
     * @param correctAnswer     The index of the correct answer
     * @param authorId          Added by teacherId
     * @param courseId            Associated course
     * @param optionsAsString           Array Text separated by commas contains list of answers text maintain order
     * @throws Exception failed to retrieve course by courseId, or invalid optionsList
     */
    public Question(String id, String text, Integer correctAnswer, String authorId, String courseId, String optionsAsString) throws Exception{
        mId = id;
        mText = text;
        mCorrectAnswer = correctAnswer;
        mAuthor = Teacher.getTeacherByTeacherId(authorId);
        mCourse = Course.getCourseByCourseId(courseId);
        mOptions = new ArrayList<>();
        if(!optionsAsString.isEmpty()){
            String[] tempOptionsArray =optionsAsString.split(",");
            for(int i=0; i<tempOptionsArray.length; i++){
                mOptions.add(tempOptionsArray[i]);
            }
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Integer getCorrectAnswer() {
        return mCorrectAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        mCorrectAnswer = correctAnswer;
    }

    public Teacher getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Teacher author) {
        mAuthor = author;
    }

    public Course getCourse() {
        return mCourse;
    }

    public void setCourse(Course course) {
        mCourse = course;
    }

    public List<String> getOptions() {
        return mOptions;
    }

    public void setOptions(List<String> options) {
        mOptions = options;
    }


    /**
     * Fetch question from the database where questionId parameter
     *
     * @param questionId
     * @return Question instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public static Question getQuestionByQuestionId(String questionId) throws Exception{
        return null;
    }
}
