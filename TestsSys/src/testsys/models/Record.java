package testsys.models;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import testsys.utils.Database;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

import javax.xml.crypto.Data;

/**
 * Class Name:      Record
 * Table Name:      RecordTable
 * Table Columns:   TEXT ID, TEXT StudentID,TEXT CourseID, TEXT ExamID, TEXT ExtraData
 */
public class Record {
    private String mId;
    private Student mStudent;
    private Course mCourse;
    private Exam mExam;
    private JSONObject mExtraData;

    /**
     * Default Constructor
     */
    public Record() {
    }

    /**
     * Record Constructor
     *
     * @param id        record id
     * @param student   associated student object
     * @param course    associated course object
     * @param exam      associated exam object
     * @param extraData json object contains exam and questions grade
     */
    public Record(String id, Student student, Course course, Exam exam, JSONObject extraData) {
        mId = id;
        mStudent = student;
        mCourse = course;
        mExam = exam;
        mExtraData = extraData;
    }

    public Record(String id, Student student, Course course, Exam exam, String extraData) throws Exception {
        mId = id;
        mStudent = student;
        mCourse = course;
        mExam = exam;
        mExtraData = new JSONObject(extraData);
    }

    public Record(String id, String studentId, String courseId, String examId, String extraData) throws Exception {
        mId = id;
        mStudent = (Student) User.getUserByUserId(studentId);
        mCourse = Course.getCourseByCourseId(courseId);
        mExam = Exam.getExamByExamId(examId);
        mExtraData = new JSONObject(extraData);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Student getStudent() {
        return mStudent;
    }

    public void setStudent(Student student) {
        mStudent = student;
    }

    public Course getCourse() {
        return mCourse;
    }

    public void setCourse(Course course) {
        mCourse = course;
    }

    public Exam getExam() {
        return mExam;
    }

    public void setExam(Exam exam) {
        mExam = exam;
    }

    public JSONObject getExtraData() {
        return mExtraData;
    }

    public void setExtraData(JSONObject extraData) {
        mExtraData = extraData;
    }

    /**
     * Insert record instance as new record in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void insert() throws Exception {

    }

    /**
     * Fetch record from Database where recordId parameter
     *
     * @param recordId
     * @return Record instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public static Record getRecordByRecordId(String recordId) throws Exception {
        return null;
    }

    /**
     * Fetch all record from the database where ExamColumn equals to examId parameter
     *
     * @param examId
     * @return List of Records if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Record> getRecordsByExamId(String examId) throws Exception {
        return new ArrayList<>();
    }

    /**
     * Fetch all record from the database where StudentColumn equals to studentId parameter
     *
     * @param studentId
     * @return List of Records if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Record> getRecordsByStudentId(String studentId) throws Exception {
        List<Record> recordList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.RECORD_GET_RECORDS_BY_STUDENT_ID, SqlColumns.RECORD_ALL_COLUMNS, studentId);
        for (int i = 0; i < objectsList.size(); i++) {
            recordList.add(hashMapToObject(objectsList.get(i)));
        }
        return recordList;
    }

    /**
     * Fetch all record from the database where CourseColumn equals to courseId parameter
     *
     * @param courseId
     * @return List of Records if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Record> getRecordsByCourseId(String courseId) throws Exception {
        return new ArrayList<>();
    }


    public static Record hashMapToObject(HashMap<String, Object> objectHashMap) throws Exception {
        String id = (String) objectHashMap.get(SqlColumns.RECORD_ID);
        String student = (String) objectHashMap.get(SqlColumns.RECORD_STUDENT_ID);
        String course = (String) objectHashMap.get(SqlColumns.RECORD_COURSE_ID);
        String exam = (String) objectHashMap.get(SqlColumns.RECORD_EXAM_ID);
        String extraData = (String) objectHashMap.get(SqlColumns.RECORD_EXTRA_DATA);

        return new Record(id, student, course, exam, extraData);
    }

}
