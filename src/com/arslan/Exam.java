package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Exam extends Evaluations{

    Exam(int id, String name ,Teachers teacher){
        this.id = id;
        this.EvaluationName = name;
        this.teacher = teacher;
        questionsList = new ArrayList<>();
    }
    @Override
    public int getId(){
        return id;
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

    @Override
    public Teachers getTeacher() {
        return this.teacher;
    }

    @Override
    public void printEvaluationDetails(){
        System.out.println(this.id + " : -> " + this.EvaluationName);
    }

    @Override
    public void setEvaluationName(String name){
        this.EvaluationName = name;
    }

    @Override
    public void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.evaluationsPath);
            if (!keyfile.exists()) {
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.evaluationsPath, append);
            myWriter.write(Integer.toString(this.getId()) + "\n");
            myWriter.write(this.EvaluationName + "\n");
            myWriter.write(Integer.toString(this.teacher.getTeacherID()) + "\n");
            myWriter.write("exam" + "\n");

            String newArray = "";
            for (Questions question :
                    questionsList) {
                newArray = newArray.concat(Integer.toString(question.getId()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
            e.printStackTrace();
        }
    }
}
