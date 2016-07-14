package testsys.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import testsys.utils.Database;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

/**
 * Class Name:      Course
 * Table Name:      CourseTable
 * Table Columns:   TEXT ID, TEXT Name,TEXT TeacherID, TEXT ProfessionID
 */
public class Course {

    public String mId;
    public String mName;
    public Profession mProfession;

    /**
     * Default Constructor
     */
    public Course() {

    }

    /**
     * Course Constructor
     *
     * @param id        Course id
     * @param name      Course name
     * @param teacherId Course's teacher owner.
     * @throws Exception exception if failed to get profession instance
     */
    public Course(String id, String name, String professionId) throws Exception {
        this.mId = id;
        this.mName = name;
        this.mProfession = Profession.getProfessionByProfessionId(professionId);
    }


    /**
     * Insert course instance as new course in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void insert() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.COURSE_INSERT_NEW_COURSE,
                mId,
                mName,
                mProfession.mId);
    }

    /**
     * Update last changes of this course instance in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void update() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.COURSE_UPDATE_EXISTING_COURSE,
                mName,
                mProfession.mId
                , mId);
    }

    /**
     * Delete this course instance from the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void delete() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.COURSE_DELETE_COURSE, mId);
    }

    /**
     * Fetch course from the database where courseId parameter
     *
     * @param courseId
     * @return Course instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public static Course getCourseByCourseId(String courseId) throws Exception {

        return hashMapToObject(Database.getInstance().executeSingleQuery(
                SqlStatements.COURSE_GET_COURSE_BY_ID,
                SqlColumns.COURSE_ALL_COLUMNS, courseId));
    }

    /**
     * Fetch all Student from the database where CourseColumn contains this course Id
     *
     * @return course participates students list, else empty list
     * @throws Exception failed to execute SQL query
     */
    public List<Student> getCourseStudents() throws Exception {
        return Student.getStudentsByCourseId(this.mId);
    }

    /**
     * Fetch all Student from the database where CourseColumn equals to this course Id
     *
     * @return course associated exams list, else empty list
     * @throws Exception failed to execute SQL query
     */
    public List<Exam> getExamsList() throws Exception {
        return Exam.getExamsByCourseId(this.mId);
    }

    /**
     * Fetch all Records from the database where CourseColumn equals to this course Id
     *
     * @return course associated records list, else empty list
     * @throws Exception failed to execute SQL query
     */
    public List<Record> getRecordsList() throws Exception {
        return Record.getRecordsByCourseId(this.mId);
    }


    public static List<Course> getCoursesByProfessionId(String professionId) throws Exception {
        List<HashMap<String, Object>> results = Database.getInstance().executeListQuery(SqlStatements.COURSE_GET_PROFESSION_COURSES, SqlColumns.COURSE_ALL_COLUMNS, professionId);
        List<Course> courseList = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            courseList.add(hashMapToObject(results.get(i)));
        }
        return courseList;
    }


    public static Course hashMapToObject(HashMap<String, Object> hashMapProfession) throws Exception {
        String id = (String) hashMapProfession.get(SqlColumns.COURSE_ID);
        String name = (String) hashMapProfession.get(SqlColumns.COURSE_NAME);
        String professionId = (String) hashMapProfession.get(SqlColumns.COURSE_PROFESSION_ID);
        return new Course(id, name, professionId);
    }

    public List<Question> getQuestionsList() throws Exception{
        return Question.getAllQuestionByCourseId(mId);
    }

	public JSONObject toJSON() throws Exception{
        JSONObject json = new JSONObject();
        json.put("id", mId);
        json.put("name", mName);
        json.put("profession", mProfession.mId);
        json.put("profession", mProfession.mName);
		return json;
	}
}
