package testsys.models;

public class QuestionsGrade {
    public Question question;
    public Integer grade;

    public QuestionsGrade(Question question, Integer grade) {
        this.question = question;
        this.grade = grade;
    }
}
