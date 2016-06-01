package testsys.models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

import testsys.utils.Database;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

/**
 * Class Name:      Course
 * Table Name:      CourseTable
 * Table Columns:   TEXT ID, TEXT Name,TEXT TeacherID, TEXT ProfessionID
 */
public class Course {

    private String mId;
    private String mName;
    private Teacher mTeacher;
    private Profession mProfession;

    /**
     * Default Constructor
     */
    public Course(){

    }
    /**
     * Course Constructor
     *
     * @param id      Course id
     * @param name    Course name
     * @param teacherId Course's teacher owner.
     * @throws Exception exception if failed to get profession instance
     */
    public Course(String id, String name, String teacherId, String professionId) throws Exception {
        this.mId = id;
        this.mName = name;
        this.mTeacher = Teacher.getTeacherByTeacherId(teacherId);
        this.mProfession = Profession.getProfessionByProfessionId(professionId);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Teacher getTeacher() {
        return mTeacher;
    }

    public void setTeacher(Teacher teacher) {
        mTeacher = teacher;
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
                mTeacher.getId(),
                mProfession.getId());
    }

    /**
     * Update last changes of this course instance in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void update() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.COURSE_UPDATE_EXISTING_COURSE,
                mName,
                mTeacher.getId(),
                mProfession.getId()
                ,mId);
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


        HashMap<String, Object> results = Database.getInstance().executeSingleQuery(
                SqlStatements.COURSE_GET_COURSE_BY_ID,
                SqlColumns.COURSE_ALL_COLUMNS, courseId);
        Course course = null;
        if(results != null){
            String id = (String) results.get(SqlColumns.COURSE_ID);
            String name = (String) results.get(SqlColumns.COURSE_NAME);
            String teacherId = (String) results.get(SqlColumns.COURSE_TEACHER_ID);
            String professionId = (String) results.get(SqlColumns.COURSE_PROFESSION_ID);
            course = new Course(id, name, teacherId, professionId);
        }
        return course;
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
}
