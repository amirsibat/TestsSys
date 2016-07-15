package testsys.models;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import testsys.utils.Database;
import testsys.utils.L;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;


/**
 * Class Name:      Record
 * Table Name:      RecordTable
 * Table Columns:   TEXT ID, TEXT StudentID,TEXT CourseID, TEXT ExamID, TEXT ExtraData
 */
public class Record {

    public enum RecordExamStatus {
        IN_PROGRESS,
        PENDING_CHECK,
        SUBMITTED,
        PUBLISHED
    }

    public String mId;
    public Student mStudent;
    public Course mCourse;
    public Exam mExam;
    public JSONObject mExtraData;

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
        mStudent = Student.getStudentByStudentId(studentId);
        mCourse = Course.getCourseByCourseId(courseId);
        mExam = Exam.getExamByExamId(examId);
        mExtraData = new JSONObject(extraData);
    }

    /**
     * Insert record instance as new record in the database
     *
     * @throws Exception failed to execute SQL query
     */
    public void insert() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.RECORD_INSERT_NEW_RECORD, mId,
                mStudent.mId,
                mCourse.mId,
                mExam.mId,
                mExtraData.toString());
    }

    public void update() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.RECORD_UPDATE_RECORD, mExtraData.toString(), mId);
    }

    /**
     * Fetch record from Database where recordId parameter
     *
     * @param recordId
     * @return Record instance if found, else null
     * @throws Exception failed to execute SQL query
     */
    public static Record getRecordByRecordId(String recordId) throws Exception {
        return hashMapToObject(Database.getInstance().executeSingleQuery(SqlStatements.RECORD_GET_RECORD_BY_RECORD_ID, SqlColumns.RECORD_ALL_COLUMNS, recordId));
    }

    public static List<Record> getAllRecords() throws Exception {
        List<Record> recordList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.RECORD_GET_ALL_RECORDS, SqlColumns.RECORD_ALL_COLUMNS);
        for (int i = 0; i < objectsList.size(); i++) {
            recordList.add(hashMapToObject(objectsList.get(i)));
        }
        return recordList;
    }

    /**
     * Fetch all record from the database where ExamColumn equals to examId parameter
     *
     * @param examId
     * @return List of Records if Found, else empty list
     * @throws Exception failed to execute SQL query
     */
    public static List<Record> getRecordsByExamId(String examId) throws Exception {
        List<Record> recordList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.RECORD_GET_RECORDS_BY_EXAM_ID, SqlColumns.RECORD_ALL_COLUMNS, examId);
        for (int i = 0; i < objectsList.size(); i++) {
            recordList.add(hashMapToObject(objectsList.get(i)));
        }
        return recordList;
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
        List<Record> recordList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.RECORD_GET_RECORDS_BY_COURSE_ID, SqlColumns.RECORD_ALL_COLUMNS, courseId);
        for (int i = 0; i < objectsList.size(); i++) {
            recordList.add(hashMapToObject(objectsList.get(i)));
        }
        return recordList;
    }


    public static Record hashMapToObject(HashMap<String, Object> objectHashMap) throws Exception {
        String id = (String) objectHashMap.get(SqlColumns.RECORD_ID);
        String student = (String) objectHashMap.get(SqlColumns.RECORD_STUDENT_ID);
        String course = (String) objectHashMap.get(SqlColumns.RECORD_COURSE_ID);
        String exam = (String) objectHashMap.get(SqlColumns.RECORD_EXAM_ID);
        String extraData = (String) objectHashMap.get(SqlColumns.RECORD_EXTRA_DATA);

        return new Record(id, student, course, exam, extraData);
    }


    public static List<Exam> getCurrentExamsByTeacher(String teacherId) throws Exception {
        List<Record> recordList = getAllRecords();
        List<Exam> exams = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            JSONObject jsonObject = recordList.get(i).mExtraData;
            if (jsonObject.getString("teacherId").equals(teacherId)) {
                if (jsonObject.getInt("status") == RecordExamStatus.IN_PROGRESS.ordinal()) {
                    boolean alreadyExists = false;
                    for (int j = 0; j < exams.size(); j++) {
                        if (exams.get(j).mId == recordList.get(i).mExam.mId) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) {
                        exams.add(recordList.get(i).mExam);
                    }
                }
            }
        }
        return exams;
    }

    public static List<Record> getCurrentRecordsByTeacher(String teacherId) throws Exception {
        List<Record> recordList = getAllRecords();
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            JSONObject jsonObject = recordList.get(i).mExtraData;
            if (jsonObject.getString("teacherId").equals(teacherId)) {
                if (jsonObject.getInt("status") == RecordExamStatus.IN_PROGRESS.ordinal()) {
                    records.add(recordList.get(i));
                }
            }
        }
        return records;
    }


    public static List<Record> getCurrentExamByStudent(String studentId) throws Exception {
        List<Record> recordList = getRecordsByStudentId(studentId);
        List<Record> returnRecords = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            JSONObject jsonObject = recordList.get(i).mExtraData;
            if (jsonObject.getInt("status") == RecordExamStatus.IN_PROGRESS.ordinal()) {
                returnRecords.add(recordList.get(i));
            }
        }
        return returnRecords;
    }

    public static List<Record> getPendingExamsByStudent(String studentId) throws Exception {
        List<Record> recordList = getRecordsByStudentId(studentId);
        List<Record> returnRecords = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            JSONObject jsonObject = recordList.get(i).mExtraData;
            if (jsonObject.getInt("status") == RecordExamStatus.PUBLISHED.ordinal()) {
                returnRecords.add(recordList.get(i));
            }
        }
        return returnRecords;
    }


    public static List<Record> getExamsToCheckByTeacher(String teacherId) throws Exception {
        List<Record> recordList = getAllRecords();
        List<Record> returnRecords = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            JSONObject jsonObject = recordList.get(i).mExtraData;
            if (jsonObject.getString("teacherId").equals(teacherId)) {
                if (jsonObject.getInt("status") == RecordExamStatus.PENDING_CHECK.ordinal()) {
                    returnRecords.add(recordList.get(i));
                }
            }
        }
        return returnRecords;
    }

    public static List<Exam> getPublishedExams(String courseId, String studentId) throws Exception {

        List<Record> recordList = getRecordsByCourseId(courseId);
        List<Exam> exams = new ArrayList<>();
        List<Exam> returnedExams = new ArrayList<>();


        for (int i = 0; i < recordList.size(); i++) {
            JSONObject extraData = recordList.get(i).mExtraData;
            String student = recordList.get(i).mStudent.mId;

            if (extraData.getInt("status") == RecordExamStatus.PUBLISHED.ordinal() && student.equals(studentId)) {
                returnedExams.add(recordList.get(i).mExam);
            }
        }
        return returnedExams;

    }

    public static List<Record> getSubmittedExams(String courseId, String studentId) throws Exception {

        List<Record> recordList = getRecordsByCourseId(courseId);
        List<Exam> exams = new ArrayList<>();
        List<Record> returnedRecords = new ArrayList<>();

        for (int i = 0; i < recordList.size(); i++) {
            JSONObject extraData = recordList.get(i).mExtraData;
            String student = recordList.get(i).mStudent.mId;

            if (extraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal() && student.equals(studentId)) {
                returnedRecords.add(recordList.get(i));
            }
        }
        return returnedRecords;

    }

    public JSONObject toJSON() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", mId);
        jsonObject.put("student", mStudent.toJSON());
        jsonObject.put("course", mCourse.toJSON());
        jsonObject.put("exam", mExam.toJSON());
        jsonObject.put("extraData", mExtraData);
        jsonObject.put("teacher", Teacher.getTeacherByTeacherId(mExtraData.getString("teacherId")).toJSON());
        return jsonObject;
    }

    public static void checkInProgressExams() {
        try {
            List<Record> recordList = getAllRecords();
            for (int i = 0; i < recordList.size(); i++) {
                JSONObject jsonObject = recordList.get(i).mExtraData;
                if (jsonObject.getInt("status") == RecordExamStatus.IN_PROGRESS.ordinal()) {
                    long fromDate = jsonObject.getLong("startDate");
                    long toDate = fromDate + (jsonObject.getInt("duration") * 60 * 1000);
                    if (!jsonObject.isNull("addExtraMinutes")) {
                        toDate += (jsonObject.getInt("addExtraMinutes") * 60 * 1000);
                    }
                    if (toDate < new Date().getTime()) {
                        L.log("Force closing progress Exam (" + recordList.get(i).mId + ")");
                        recordList.get(i).closeInPorgressExam();
                    }
                }
            }
        } catch (Exception e) {
            L.err(e);
        }
    }

    private void closeInPorgressExam() throws Exception {
        mExtraData.put("status", RecordExamStatus.PENDING_CHECK.ordinal());
        mExtraData.put("endDate", new Date().getTime());
        mExtraData.put("answers", new JSONArray());
        mExtraData.remove("totalGrade");
        update();
        L.log("Progress Exam (" + mId + ") closed");
    }

    public static String generateRecordId() {
        String[] columnsNames = new String[1];
        columnsNames[0] = "ID";
        String[] columnsTypes = new String[1];
        columnsTypes[0] = "TEXT";

        try {
            int maxRecordId = 0;
            JSONArray jsonArray = Database.getInstance().executeListQueryAsJSON("SELECT ID FROM " + SqlStatements.RECORD_TABLE,
                    columnsNames, columnsTypes);
            for (int i = 0; i < jsonArray.length(); i++) {
                String RecordId = jsonArray.getJSONObject(i).getString("ID");
                if (Integer.parseInt(RecordId) > maxRecordId) {
                    maxRecordId = Integer.parseInt(RecordId);
                }
            }
            return "" + (maxRecordId + 1);
        } catch (Exception e) {
            L.err(e);
        }
        return null;
    }

  

}
