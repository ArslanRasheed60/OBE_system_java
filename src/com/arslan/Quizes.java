package com.arslan;

import java.util.ArrayList;
import java.util.List;

public class Quizes extends Evaluations {
    Quizes(int id){
        this.id = id;
        teachersList = new ArrayList<>();
        questionsList = new ArrayList<>();
    }
    @Override
    public int getId(){
        return id;
    }



    @Override
    public void addTeacher(Teachers teachers) {
        this.teachersList.add(teachers);
    }

    @Override
    public void setTeachersList() {

    }

    @Override
    public List<Teachers> getTeachersList() {
        return teachersList;
    }

    @Override
    public Teachers getTeacherById(int id) {
        for (Teachers teacher :
                this.teachersList) {
            if (teacher.getTeacherID() == id) {
                return teacher;
            }
        }
        return null;
    }

    @Override
    public void addQuestions(Questions questions) {
            this.questionsList.add(questions);
    }

    @Override
    public void setQuestionsList() {

    }

    @Override
    public List<Questions> getQuestionsList() {
        return this.questionsList;
    }

    @Override
    public Questions getQuestionsById(int id) {
        for (Questions question :
                this.questionsList) {
            if (question.getId() == id) {
                return question;
            }
        }
        return null;
    }
}
