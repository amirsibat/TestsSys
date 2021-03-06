package testsys.models;

import org.json.JSONArray;
import org.json.JSONObject;
import testsys.utils.Database;
import testsys.utils.L;
import testsys.utils.SqlColumns;
import testsys.utils.SqlStatements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Request {
    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }

    public String mId;
    public Teacher mTeacher;
    public Exam mExam;
    public RequestStatus mPending;
    public String mRequestText;
    public Integer mDurationToAdd;


    public Request(String id, Teacher teacher, Exam exam, RequestStatus pending, String requestText, Integer durationToAdd) {
        this.mId = id;
        this.mTeacher = teacher;
        this.mExam = exam;
        this.mPending = pending;
        this.mRequestText = requestText;
        this.mDurationToAdd = durationToAdd;
    }

    public Request(String id, String teacherId, String examId, Integer pending, String requestText, Integer durationToAdd) throws Exception {
        this.mId = id;
        this.mTeacher = Teacher.getTeacherByTeacherId(teacherId);
        this.mExam = Exam.getExamByExamId(examId);
        this.mPending = RequestStatus.values()[pending];
        this.mRequestText = requestText;
        this.mDurationToAdd = durationToAdd;
    }


    public void insert() throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.REQUEST_INSERT_NEW_REQUEST,
                mId,
                mPending.ordinal(),
                mExam.mId,
                mRequestText,
                mTeacher.mId,
                mDurationToAdd);
    }

    public static Request getRequestById(String id) throws Exception{
        return hashMapToObject(Database.getInstance().executeSingleQuery(SqlStatements.REQUEST_GET_REQUEST_BY_REQUEST_ID, SqlColumns.REQUEST_ALL_COLUMNS, id));
    }

    public void answerRequest(RequestStatus requestStatus) throws Exception {
        Database.getInstance().executeUpdate(SqlStatements.REQUEST_UPDATE_REQUEST, requestStatus.ordinal(),
                mId);
    }

    public static List<Request> getPendingRequest() throws Exception {
        List<Request> requestList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.REQUEST_GET_PINDEING_REQUESTS, SqlColumns.REQUEST_ALL_COLUMNS, RequestStatus.PENDING.ordinal());
        for (int i = 0; i < objectsList.size(); i++) {
            requestList.add(hashMapToObject(objectsList.get(i)));
        }
        return requestList;
    }

    public static List<Request> getRequestByTeacherId(String teacherId) throws Exception {
        List<Request> requestList = new ArrayList<>();
        List<HashMap<String, Object>> objectsList = Database.getInstance().executeListQuery(SqlStatements.REQUEST_GET_REQUESTS_BY_TEACHER, SqlColumns.REQUEST_ALL_COLUMNS, teacherId);
        for (int i = 0; i < objectsList.size(); i++) {
            requestList.add(hashMapToObject(objectsList.get(i)));
        }
        return requestList;
    }


    public JSONObject toJSON() throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", mId);
        json.put("teacher", mTeacher.toJSON());
        json.put("exam", mExam.toJSON());
        json.put("status", mPending.ordinal());
        json.put("requestText", mRequestText);
        json.put("duration", mDurationToAdd);
        return json;
    }

    public static Request hashMapToObject(HashMap<String, Object> objectHashMap) throws Exception {
        String id = (String) objectHashMap.get(SqlColumns.REQUEST_ID);
        String teacher = (String) objectHashMap.get(SqlColumns.REQUEST_TEACHER_ID);
        String exam = (String) objectHashMap.get(SqlColumns.REQUEST_EXAM_ID);
        Integer pending = (Integer) objectHashMap.get(SqlColumns.REQUEST_PENDING);
        String requestText = (String) objectHashMap.get(SqlColumns.REQUEST_REQUEST_TEXT);
        Integer durationToAdd = (Integer) objectHashMap.get(SqlColumns.REQUEST_DURATION_TO_ADD);
        return new Request(id, teacher, exam, pending, requestText, durationToAdd);
    }


    public static String generateRequestId() {
        String[] columnsNames = new String[1];
        columnsNames[0] = "ID";
        String[] columnsTypes = new String[1];
        columnsTypes[0] = "TEXT";

        try {
            int maxQuestionId = 0;
            JSONArray jsonArray = Database.getInstance().executeListQueryAsJSON("SELECT ID FROM " + SqlStatements.REQUEST_TABLE,
                    columnsNames, columnsTypes);
            for (int i = 0; i < jsonArray.length(); i++) {
                String QuestionId = jsonArray.getJSONObject(i).getString("ID");
                if (Integer.parseInt(QuestionId) > maxQuestionId) {
                    maxQuestionId = Integer.parseInt(QuestionId);
                }
            }
            return "" + (maxQuestionId + 1);
        } catch (Exception e) {
            L.err(e);
        }
        return null;
    }

}
