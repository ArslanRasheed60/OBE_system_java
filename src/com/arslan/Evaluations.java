package com.arslan;

import java.util.List;

public abstract class Evaluations {
    protected int id;
    protected List<Teachers> teachersList;
    protected List<Questions> questionsList;

    public abstract int getId();

    public abstract  void addTeacher(Teachers teachers);
    public abstract  void setTeachersList();
    public abstract List<Teachers> getTeachersList();

    public abstract Teachers getTeacherById(int id);

    public abstract void addQuestions(Questions questions);
    public abstract void setQuestionsList();
    public abstract List<Questions> getQuestionsList();

    public abstract Questions getQuestionsById(int id);

}
