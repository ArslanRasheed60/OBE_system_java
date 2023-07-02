package com.arslan;

import java.util.List;

public abstract class Evaluations {
    protected int id;
    protected String EvaluationName;
    protected Teachers teacher;
    protected List<Questions> questionsList;

        public int getId(){
        return id;
    }

    public void addQuestions(Questions questions) {
        this.questionsList.add(questions);
    }

    public Teachers getTeacher() {
        return this.teacher;
    }
    public void printEvaluationDetails(){
        System.out.println(this.id + " : -> " + this.EvaluationName);
    }

    public void setEvaluationName(String name){
        this.EvaluationName = name;
    }

    public Questions getQuestionsById(int id) {
        for (Questions question :
                this.questionsList) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }

    public List<Questions> getQuestionsList() {
        return this.questionsList;
    }

    public abstract void saveInFile(boolean append);

}
