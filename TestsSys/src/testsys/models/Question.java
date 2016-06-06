package testsys.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import testsys.utils.Database;
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
	 * @param text
	 *            Question text + description
	 * @param correctAnswer
	 *            The index of the correct answer
	 * @param author
	 *            Added by teacher
	 * @param profession
	 *            Associated profession
	 * @param options
	 *            Array contains list of answers text maintain order
	 * @param courses
	 *            Array contains list of courses maintain order
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
	 * @param text
	 *            Question text + description
	 * @param correctAnswer
	 *            The index of the correct answer
	 * @param authorId
	 *            Added by teacherId
	 * @param professionId
	 *            Associated profession
	 * @param optionsAsString
	 *            Array Text separated by commas contains list of answers text
	 *            maintain order
	 * @param coursesAsString
	 *            Array Text separated by commas contains list of courses
	 * @throws Exception
	 *             failed to retrieve course by courseId, or invalid optionsList
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

	public boolean insert() {

		try {
			String coursesAsString = "";
			for (int i = 0; i < mCourses.size(); i++) {
				coursesAsString += mCourses.get(i).mId+ (i < mCourses.size() - 1 ? "," : "");
			}

			Database.getInstance().executeUpdate(SqlStatements.QUESTION_INSER_NEW_QUESTION, mId, mText, mCorrectAnswer,
					mAuthor, mProfession, mOptions1, mOptions2, mOptions3, mOptions4, coursesAsString);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public boolean update() {

		return false;
	}

	public boolean delete() {

		return false;
	}

	public List<Question> getAllQuestionByTeacherId(String teacherId){
		return new ArrayList<>();
	}
	
	public List<Question> getAllQuestionByProfessionId(String professionId){
		return new ArrayList<>();
	}
	
	public List<Question> getAllQuestionByCourseId(String courseId){
		return new ArrayList<>();
	}
	
	/**
	 * Fetch question from the database where questionId parameter
	 *
	 * @param questionId
	 * @return Question instance if found, else null
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public static Question getQuestionByQuestionId(String questionId) throws Exception {
		return null;
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
