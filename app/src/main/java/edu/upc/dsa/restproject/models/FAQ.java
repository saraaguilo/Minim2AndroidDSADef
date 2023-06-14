package edu.upc.dsa.restproject.models;

public class FAQ {

    String question;
    String answer;

    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public FAQ(){

    }

    public FAQ(String question, String answer){
        setQuestion(question);
        setAnswer(answer);
    }
}
