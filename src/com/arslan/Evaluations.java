package com.arslan;

import java.util.List;

public abstract class Evaluations {
    protected int id;
    protected String EvaluationName;
    protected Teachers teacher;
    protected List<Questions> questionsList;

    public abstract int getId();

    public abstract void addQuestions(Questions questions);
    public abstract void setQuestionsList();
    public abstract List<Questions> getQuestionsList();

    public abstract Questions getQuestionsById(int id);

    public abstract Teachers getTeacher();

    public abstract void printEvaluationDetails();

    public abstract void setEvaluationName(String name);

    public abstract void saveInFile(boolean append);

}
