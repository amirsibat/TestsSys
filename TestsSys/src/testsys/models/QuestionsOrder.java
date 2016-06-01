package testsys.models;

public class QuestionsOrder {
    public Question question;
    public Integer order;

    public QuestionsOrder(Question question, Integer order) {
        this.question = question;
        this.order = order;
    }
}
