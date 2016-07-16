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

    public static List<Record> getPublishedExams(String courseId, String studentId) throws Exception {

        List<Record> recordList = getRecordsByCourseId(courseId);
        List<Exam> exams = new ArrayList<>();
        List<Record> returnedExams = new ArrayList<>();


        for (int i = 0; i < recordList.size(); i++) {
            JSONObject extraData = recordList.get(i).mExtraData;
            String student = recordList.get(i).mStudent.mId;

            if (extraData.getInt("status") == RecordExamStatus.PUBLISHED.ordinal() && student.equals(studentId)) {
                returnedExams.add(recordList.get(i));
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


    public static JSONObject getStatisticsAdmin() throws Exception {
        JSONObject jsonObject = new JSONObject();
        List<Record> records = getAllRecords();
        jsonObject.put("teacherExamAverage", getTeachersExamAverage(records));
        jsonObject.put("courseExamAverage", getCoursesExamsAverage(records));
        jsonObject.put("studentExamAverage", getStudentsExamAverage(records));
        return jsonObject;
    }
    
    public static JSONObject getStatisticsTeacher(String teacherId) throws Exception {
    	
        List<Record> records = getAllRecords();
     
        JSONObject json = new JSONObject();
        json.put("teacherExamAverage", getTeacherPublishedExamsAverage(records,teacherId));
       
        return json;
    }

    public static JSONArray getTeacherPublishedExamsAverage(List<Record> records, String teacherId) throws Exception {
        JSONArray jsonArray = new JSONArray();
        
        for (int j = 0; j < records.size(); j++) {
            JSONObject examDetails = new JSONObject();
            examDetails.put("exam", records.get(j).mExam.toJSON());
            examDetails.put("statistics", getTeacherExamsAverage(records, Teacher.getTeacherByTeacherId(teacherId) ));
            jsonArray.put(examDetails);
        }
        return jsonArray;
    }
    

    public static JSONArray getTeachersExamAverage(List<Record> records) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<Teacher> teacherList = Teacher.getAllTeachers();
        for (int j = 0; j < teacherList.size(); j++) {
            JSONObject teacherDetails = new JSONObject();
            teacherDetails.put("teacher", teacherList.get(j).toJSON());
            teacherDetails.put("statistics", getTeacherExamsAverage(records, teacherList.get(j)));
            jsonArray.put(teacherDetails);
        }
        return jsonArray;
    }

    public static JSONObject getTeacherExamsAverage(List<Record> records, Teacher teacher) throws Exception {
        List<String> uniqueExams = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
       
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).mExtraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal()) {
                if (records.get(i).mExam.mAuthor.mId.equals(teacher.mId)) {
                    if (!uniqueExams.contains(records.get(i).mExam.mId)) {
                        uniqueExams.add(records.get(i).mExam.mId);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("exam", records.get(i).mExam.toJSON());
                        jsonObject.put("average", 0);
                        jsonObject.put("totalExams", 0);
                        jsonArray.put(jsonObject);
                    }
                }
            }
        }
        int[] gradesArray = new int[jsonArray.length()];
        
        for (int i = 0; i < jsonArray.length(); i++) {
            for (int j = 0; j < records.size(); j++) {
                if (records.get(j).mExtraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal()) {
                    if (records.get(j).mExam.mAuthor.mId.equals(teacher.mId)) {

                        if (records.get(j).mExam.mId.equals(jsonArray.getJSONObject(i).getJSONObject("exam").getString("id"))) {
                            jsonArray.getJSONObject(i).put("average", jsonArray.getJSONObject(i).getInt("average") + records.get(j).mExtraData.getInt("teacherGrade"));
                            jsonArray.getJSONObject(i).put("totalExams", jsonArray.getJSONObject(i).getInt("totalExams") + 1);
                            gradesArray[i] = records.get(j).mExtraData.getInt("teacherGrade");
                        }
                    }
                }
            }
            jsonArray.getJSONObject(i).put("average", jsonArray.getJSONObject(i).getInt("average") / jsonArray.getJSONObject(i).getInt("totalExams"));
        }
        int teacherExamAverage = 0;
        JSONObject teacherExamStatistics = new JSONObject();
        teacherExamStatistics.put("average", 0);
        teacherExamStatistics.put("middle", 0);
        teacherExamStatistics.put("probability", getTeacherProbability(gradesArray));
       /* teacherExamStatistics.put("probability", 0);*/

        for (int i = 0; i < jsonArray.length(); i++) {
            teacherExamAverage += jsonArray.getJSONObject(i).getInt("average");
        }
        if (jsonArray.length() != 0) {
            teacherExamStatistics.put("average", teacherExamAverage / jsonArray.length());
            if (jsonArray.length() % 2 == 1) {
                teacherExamStatistics.put("middle", jsonArray.getJSONObject((jsonArray.length() / 2) + 1).getInt("average"));
            } else {
                if (jsonArray.length() == 2) {
                    teacherExamStatistics.put("middle", ((jsonArray.getJSONObject(0).getInt("average") + jsonArray.getJSONObject(1).getInt("average")) / 2));
                } else {
                    teacherExamStatistics.put("middle", ((jsonArray.getJSONObject(jsonArray.length() / 2).getInt("average") + jsonArray.getJSONObject(jsonArray.length() / 2 - 1).getInt("average")) / 2));
                }
            }
        }
        return teacherExamStatistics;
    }
    public static JSONArray getTeacherProbability(int[] grades)  throws Exception {
    	float[] counter = new float[10];
    	JSONArray jsonArray = new JSONArray();
    	JSONObject jsonObject = new JSONObject();
    	
    	for(int i=0; i< 10; i++){
    		counter[i] = 0;
    	}
    	
    	for(int i=0; i< grades.length; i++){
    		int grade = grades[i];
    		
    		if(grade >= 0 && grade<=10) counter[0]++;
    		else{
    			if(grade >= 11 && grade<=20) counter[1]++;
    			else{
    				if(grade >= 21 && grade<=30) counter[2]++;
    				else{
    					if(grade >= 31 && grade<=40) counter[3]++;
    					else {
    						if(grade >= 41 && grade<=50) counter[4]++;
    						else{
    							if(grade >= 51 && grade<=60) counter[5]++;
    							else{
    								if(grade >= 61 && grade<=70) counter[6]++;
    								else{
    									if(grade >= 71 && grade<=80) counter[7]++;
    									else{
    										if(grade >= 81 && grade<=90) counter[8]++;
    										else{
    											counter[9]++;
    										}
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	if(grades.length != 0){
    		for(int i=0; i< 10; i++){
        		counter[i] = counter[i] / grades.length;
        	}
    		
    		
    	}
    	
    	
    	jsonObject.put("prob0", counter[0]);
    	jsonObject.put("prob1", counter[1]);
    	jsonObject.put("prob2", counter[2]);
    	jsonObject.put("prob3", counter[3]);
    	jsonObject.put("prob4", counter[4]);
    	jsonObject.put("prob5", counter[5]);
    	jsonObject.put("prob6", counter[6]);
    	jsonObject.put("prob7", counter[7]);
    	jsonObject.put("prob8", counter[8]);
    	jsonObject.put("prob9", counter[9]);
    	jsonArray.put(jsonObject);
    		
    	return jsonArray;
    }

    public static JSONArray getCoursesExamsAverage(List<Record> records)  throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<Course> courseList = Course.getAllCourses();
        for (int j = 0; j < courseList.size(); j++) {
            JSONObject courseDetails = new JSONObject();
            courseDetails.put("course", courseList.get(j).toJSON());
            courseDetails.put("statistics", getCourseExamAverage(records, courseList.get(j)));
            jsonArray.put(courseDetails);
        }
        return jsonArray;
    }

    public static JSONObject getCourseExamAverage(List<Record> records, Course course)  throws Exception {
        List<String> uniqueExams = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).mExtraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal()) {
                if (records.get(i).mExam.mCourse.mId.equals(course.mId)) {
                    if (!uniqueExams.contains(records.get(i).mExam.mId)) {
                        uniqueExams.add(records.get(i).mExam.mId);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("exam", records.get(i).mExam.toJSON());
                        jsonObject.put("average", 0);
                        jsonObject.put("totalExams", 0);
                        jsonArray.put(jsonObject);
                    }
                }
            }
        }
        int[] gradesArray = new int[jsonArray.length()];
        
        for (int i = 0; i < jsonArray.length(); i++) {
            for (int j = 0; j < records.size(); j++) {
                if (records.get(j).mExtraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal()) {
                    if (records.get(j).mExam.mCourse.mId.equals(course.mId)) {
                        if (records.get(j).mExam.mId.equals(jsonArray.getJSONObject(i).getJSONObject("exam").getString("id"))) {
                            jsonArray.getJSONObject(i).put("average", jsonArray.getJSONObject(i).getInt("average") + records.get(j).mExtraData.getInt("teacherGrade"));
                            jsonArray.getJSONObject(i).put("totalExams", jsonArray.getJSONObject(i).getInt("totalExams") + 1);
                            gradesArray[i] = records.get(j).mExtraData.getInt("teacherGrade");
                        }
                    }
                }
            }
            jsonArray.getJSONObject(i).put("average", jsonArray.getJSONObject(i).getInt("average") / jsonArray.getJSONObject(i).getInt("totalExams"));
        }
        int courseExamAverage = 0;
        JSONObject courseExamStatistics = new JSONObject();
        courseExamStatistics.put("average", 0);
        courseExamStatistics.put("middle", 0);
        courseExamStatistics.put("probability", getTeacherProbability(gradesArray));

        for (int i = 0; i < jsonArray.length(); i++) {
            courseExamAverage += jsonArray.getJSONObject(i).getInt("average");
        }
        if (jsonArray.length() != 0) {
            courseExamStatistics.put("average", courseExamAverage / jsonArray.length());
            if (jsonArray.length() % 2 == 1) {
                courseExamStatistics.put("middle", jsonArray.getJSONObject((jsonArray.length() / 2) + 1).getInt("average"));
            } else {
                if (jsonArray.length() == 2) {
                    courseExamStatistics.put("middle", ((jsonArray.getJSONObject(0).getInt("average") + jsonArray.getJSONObject(1).getInt("average")) / 2));
                } else {
                    courseExamStatistics.put("middle", ((jsonArray.getJSONObject(jsonArray.length() / 2).getInt("average") + jsonArray.getJSONObject(jsonArray.length() / 2 - 1).getInt("average")) / 2));
                }
            }
        }
        return courseExamStatistics;
    }

    public static JSONArray getStudentsExamAverage(List<Record> records) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<Student> studentList = Student.getAllStudents();
        for (int j = 0; j < studentList.size(); j++) {
            JSONObject studentDetails = new JSONObject();
            studentDetails.put("student", studentList.get(j).toJSON());
            studentDetails.put("statistics", getStudentExamAverage(records, studentList.get(j)));
            jsonArray.put(studentDetails);
        }
        return jsonArray;
    }
    public static JSONObject getStudentExamAverage(List<Record> records, Student student) throws Exception {
        List<String> uniqueExams = new ArrayList<>();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).mExtraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal()) {
                if (records.get(i).mStudent.mId.equals(student.mId)) {
                    if (!uniqueExams.contains(records.get(i).mExam.mId)) {
                        uniqueExams.add(records.get(i).mExam.mId);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("exam", records.get(i).mExam.toJSON());
                        jsonObject.put("average", 0);
                        jsonObject.put("totalExams", 0);
                        jsonArray.put(jsonObject);
                    }
                }
            }
        }
        int[] gradesArray = new int[jsonArray.length()];
        
        for (int i = 0; i < jsonArray.length(); i++) {
            for (int j = 0; j < records.size(); j++) {
                if (records.get(j).mExtraData.getInt("status") == RecordExamStatus.SUBMITTED.ordinal()) {
                    if (records.get(j).mStudent.mId.equals(student.mId)) {
                    	
                        if (records.get(j).mExam.mId.equals(jsonArray.getJSONObject(i).getJSONObject("exam").getString("id"))) {
                            jsonArray.getJSONObject(i).put("average", jsonArray.getJSONObject(i).getInt("average") + records.get(j).mExtraData.getInt("teacherGrade"));
                            jsonArray.getJSONObject(i).put("totalExams", jsonArray.getJSONObject(i).getInt("totalExams") + 1);
                        }
                    }
                }
            }
            jsonArray.getJSONObject(i).put("average", jsonArray.getJSONObject(i).getInt("average") / jsonArray.getJSONObject(i).getInt("totalExams"));
        }
        int studentExamAverage = 0;
        JSONObject studentExamStatistics = new JSONObject();
        studentExamStatistics.put("average", 0);
        studentExamStatistics.put("middle", 0);
        studentExamStatistics.put("probability", getTeacherProbability(gradesArray));

        for (int i = 0; i < jsonArray.length(); i++) {
            studentExamAverage += jsonArray.getJSONObject(i).getInt("average");
        }
        if (jsonArray.length() != 0) {
            studentExamStatistics.put("average", studentExamAverage / jsonArray.length());
            if (jsonArray.length() % 2 == 1) {
            	if (jsonArray.length() == 1) {
            		studentExamStatistics.put("middle", jsonArray.getJSONObject(0).getInt("average"));
            	}else{
            		studentExamStatistics.put("middle", jsonArray.getJSONObject((jsonArray.length() / 2) + 1).getInt("average"));	
            	}
            } else {
                if (jsonArray.length() == 2) {
                    studentExamStatistics.put("middle", ((jsonArray.getJSONObject(0).getInt("average") + jsonArray.getJSONObject(1).getInt("average")) / 2));
                } else {
                    studentExamStatistics.put("middle", ((jsonArray.getJSONObject(jsonArray.length() / 2).getInt("average") + jsonArray.getJSONObject(jsonArray.length() / 2 - 1).getInt("average")) / 2));
                }
            }
        }
        return studentExamStatistics;
    }
}
