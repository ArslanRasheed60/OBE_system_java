package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Teachers extends Users {
    private int teacherID;
    private List<courseTeacher> coursesList;
    private List<Evaluations> evaluationsList;


    public Teachers(int teacherId, String fullName, String phoneNumber, String address, String username, String password) {
        super(fullName, phoneNumber, address, username, password);
        this.setTeacherID(teacherId);
        this.coursesList = new ArrayList<>();
        this.evaluationsList = new ArrayList<>();
    }

    public void printTeacherDetails(){
        System.out.println(this.teacherID + ": - > " + this.fullName);
    }

    private void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void addCourse(Courses course){
        coursesList.add(course);
    }


    public void setCoursesList(List<courseTeacher> coursesList) {
        this.coursesList = coursesList;
    }

    public List<courseTeacher> getCoursesList() {
        return coursesList;
    }

    public void addEvaluation(Evaluations evaluations) {
        this.evaluationsList.add(evaluations);
    }

    public void setEvaluationsList(List<Evaluations> evaluationsList) {
        this.evaluationsList = evaluationsList;
    }

    public List<Evaluations> getEvaluationsList() {
        return evaluationsList;
    }

    public void printEvaluations(){
        for (Evaluations evaluation :
                this.evaluationsList) {
            evaluation.printEvaluationDetails();
        }
    }

    public Evaluations getEvaluationById(int id){
        for (Evaluations evaluation :
                this.evaluationsList) {
            if(evaluation.getId() == id){
                return evaluation;
            }
        }
        return null;
    }


    public void printCourses() {
        for (courseTeacher course :
                this.coursesList) {
            course.printCourseDetails();
        }
    }

    public courseTeacher getCourseById(int id) {
        for (courseTeacher course :
                this.coursesList) {
            if (course.getCourseID() == id) {
                return course;
            }
        }
        return null;
    }

    public boolean testCLO(CLO clo){
        int count = 0;
        for (Evaluations evaluations:
             evaluationsList) {
            for (Questions questions: evaluations.questionsList) {
                if(questions.getClo() == clo){
                    count++;
                    if(count == 2){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void saveInFile ( boolean append){
        try {
            File keyfile = new File(Globals.teachersPath);
            if (!keyfile.exists()) {
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.teachersPath, append);
            myWriter.write(Integer.toString(this.getTeacherID()) + "\n");
            myWriter.write(this.fullName + "\n");
            myWriter.write(this.phoneNumber + "\n");
            myWriter.write(this.address + "\n");
            myWriter.write(this.username + "\n");
            myWriter.write(this.password + "\n");

            String newArray = "";
            for (courseTeacher course :
                    coursesList) {
                newArray = newArray.concat(Integer.toString(course.getCourseID()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            newArray = "";
            for (Evaluations evaluation :
                    evaluationsList) {
                newArray = newArray.concat(Integer.toString(evaluation.getId()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
            e.printStackTrace();
        }
    }
}
