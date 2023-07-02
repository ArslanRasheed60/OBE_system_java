package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Courses implements CourseOfficer, courseTeacher{

    private int courseID;
    private  String courseName;
    private  int courseCreditHours;

    private List<Programs> courseProgramList;
    private List<Teachers> courseTeacherList;
    private List<CLO> courseCloList;

    public Courses (int courseID, String courseName, int courseCreditHours){
        this.setCourseID(courseID);
        this.setCourseName(courseName);
        this.setCourseCreditHours(courseCreditHours);
        courseProgramList = new ArrayList<>();
        courseTeacherList = new ArrayList<>();
        courseCloList = new ArrayList<>();
    }
    public Courses (int courseID, String courseName, int courseCreditHours, List<Programs> courseProgramList, List<Teachers> courseTeacherList, List<CLO> courseCloList){
        this.setCourseID(courseID);
        this.setCourseName(courseName);
        this.setCourseCreditHours(courseCreditHours);
        this.setCourseProgramList(courseProgramList);
        this.setCourseTeacherList(courseTeacherList);
        this.setCourseCloList(courseCloList);
    }

    private void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseCreditHours(int courseCreditHours) {
        this.courseCreditHours = courseCreditHours;
    }

    public int getCourseCreditHours() {
        return courseCreditHours;
    }

    public void addProgram(Programs newProgram){
        this.courseProgramList.add(newProgram);
    }

    private void setCourseProgramList(List<Programs> courseProgramList) {
        this.courseProgramList = courseProgramList;
    }

    public List<Programs> getCourseProgramList() {
        return courseProgramList;
    }

    public  void addTeacher(Teachers teacher){
        this.courseTeacherList.add(teacher);
    }
    private void setCourseTeacherList(List<Teachers> courseTeacherList) {
        this.courseTeacherList = courseTeacherList;
    }

    public List<Teachers> getCourseTeacherList() {
        return courseTeacherList;
    }

    public void addCLO(CLO clo){
        courseCloList.add(clo);
    }
    private void setCourseCloList(List<CLO> courseCloList) {
        this.courseCloList = courseCloList;
    }

    public List<CLO> getCourseCloList() {
        return courseCloList;
    }

    public void printCourseDetails(){
        System.out.println(this.courseID + ": -> " + this.courseName + " ");
    }

    public void printAllCLOs(){
        for (CLO clo :
                this.getCourseCloList()) {
            System.out.println(clo.getId() + " : -> " + clo.getDescription() + " ");
        }
    }

    public CLO getCLOById(int id){
        for (CLO clo :
                this.getCourseCloList()) {
            if(clo.getId() == id){
                return clo;
            }
        }
        return null;
    }

    public void saveInFile (boolean append){

        try {
            File keyfile = new File(Globals.coursesPath);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.coursesPath, append);
            myWriter.write(Integer.toString(this.courseID) + "\n");
            myWriter.write(this.courseName + "\n");
            myWriter.write(Integer.toString(this.courseCreditHours) + "\n");

            String newArray = "";
            for (Programs program :
                    courseProgramList) {
                newArray = newArray.concat(Integer.toString(program.getProgramID()) + ",");
            }
            myWriter.write((newArray==""?"0":newArray) + "\n");

            newArray = "";
            for (Teachers teacher :
                    courseTeacherList) {
                newArray = newArray.concat(Integer.toString(teacher.getTeacherID()) + ",");
            }
            myWriter.write((newArray==""?"0":newArray) + "\n");

            newArray = "";
            for (CLO clo :
                    courseCloList) {
                newArray = newArray.concat(Integer.toString(clo.getId()) + ",");
            }
            myWriter.write((newArray==""?"0":newArray) + "\n");

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }

}
