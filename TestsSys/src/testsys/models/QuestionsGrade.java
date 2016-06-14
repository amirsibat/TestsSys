package testsys.models;

import org.json.JSONObject;

import testsys.constants.AppConstants;

public class QuestionsGrade {
    public String mQuestionId;
    public Integer mGrade;

    public QuestionsGrade(String questionId, Integer grade) {
        this.mQuestionId = questionId;
        this.mGrade = grade;
    }
    
    public JSONObject toJSON() throws Exception{
    	JSONObject json = new JSONObject();
    	json.put(AppConstants.GRADE_KEY, mGrade);
    	json.put(AppConstants.QUESTION_ID_KEY, mQuestionId);
    	return json;
    }
}
