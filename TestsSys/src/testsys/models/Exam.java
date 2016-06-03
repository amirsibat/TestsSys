package testsys.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import testsys.utils.Database;
import testsys.utils.SqlStatements;

/**
 * Class Name: Exam Table Name: ExamTable Table Columns: TEXT ID, TEXT
 * Author,TEXT Description, DATETIME ExamDate, INTEGER Duration, TEXT
 * QuestionsList
 */
public class Exam {
	
	public enum ExamStatus{NEW, USED}
	
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
	 * @param author
	 *            Added by teacher
	 * @param description
	 *            Exam Description
	 * @param date
	 *            Exam publish date
	 * @param duration
	 *            Length in minutes
	 * @param questionsList
	 *            List of Pairs includes Question and Grade
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
	 * @param author
	 *            Added by teacher
	 * @param description
	 *            Exam Description
	 * @param date
	 *            Exam publish date
	 * @param duration
	 *            Length in minutes
	 * @param questionsList
	 *            JSON string contains List of Pairs includes Question and Grade
	 *            as String
	 * @throws Exception
	 *             failed to parse questionsList string to List<Pair<Question,
	 *             Integer>>
	 */
	public Exam(String id, Teacher author, String description, String descriptionTeacher, Date date, Integer duration,
			String questionsList, String courseId, String professionId, int statusInt) throws Exception {
		mId = id;
		mAuthor = author;
		mDescription = description;
		mDescriptionTeacher = descriptionTeacher;
		mDate = date;
		mDuration = duration;
		mQuestionsList = new ArrayList<>();
		JSONArray questionsJSON = new JSONArray(questionsList);
		for (int i = 0; i < questionsJSON.length(); i++) {
			JSONObject tempQuestion = questionsJSON.getJSONObject(i);
			Question question = Question.getQuestionByQuestionId(tempQuestion.getString(Constants.QUESTION_ID));
			assert question != null;
			Integer questionOrder = tempQuestion.getInt(Constants.QUESTION_ORDER);
			mQuestionsList.add(new QuestionsGrade(question, questionOrder));
		}
		mCourse = Course.getCourseByCourseId(courseId);
		mProfession = Profession.getProfessionByProfessionId(professionId);
		mStatus = ExamStatus.values()[statusInt];
	}

	/**
	 * Insert exam instance as new exam in the database
	 *
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public void insert() throws Exception {

	}

	/**
	 * Update last changes of this exam instance in the database
	 *
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public void update() throws Exception {

	}

	/**
	 * Delete this exam instance from the database
	 *
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public void delete() throws Exception {

	}

	
	/**
	 * Fetch all exams from Database
	 * parameter
	 *
	 * @param courseId
	 * @return List of Exams if Found, else empty list
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public static List<Exam> getAllExams() throws Exception {
		return null;
	}
	
	/**
	 * Fetch exam from the database where ExamColumn equals to examId parameter
	 *
	 * @param examId
	 * @return Exam instance if found, else null
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public Course getExamByExamId(String examId) throws Exception {
		return null;
	}

	/**
	 * Fetch all exams from Database where CourseColumn equals to courseId
	 * parameter
	 *
	 * @param courseId
	 * @return List of Exams if Found, else empty list
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public static List<Exam> getExamsByCourseId(String courseId) throws Exception {
		return null;
	}
	
	/**
	 * Fetch all exams from Database where TeacherColumn equals to teacherId
	 * parameter
	 *
	 * @param courseId
	 * @return List of Exams if Found, else empty list
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public static List<Exam> getExamsByTeacherId(String teacherId) throws Exception {
		return null;
	}
	
	/**
	 * Fetch all exams from Database where ProfessionColumn equals to professionId
	 * parameter
	 *
	 * @param courseId
	 * @return List of Exams if Found, else empty list
	 * @throws Exception
	 *             failed to execute SQL query
	 */
	public static List<Exam> getExamsByProfessionId(String professionId) throws Exception {
		return null;
	}
	
	
	public static String generateExamId(String professionId, String courseId){
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

}
