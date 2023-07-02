package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Programs {
    private  int programID;
    private String programDegreeType;
    private String programName;

    private List <CourseOfficer> programCoursesList;
    private List <PLO> programPloList;


    public Programs(int programID, String  programDegreeType, String programName){
        this.setProgramID(programID);
        this.setProgramDegreeType(programDegreeType);
        this.setProgramName(programName);
        this.programCoursesList = new ArrayList<>();
        this.programPloList = new ArrayList<>();
    }
public Programs(int programID, String  programDegreeType, String programName, List<CourseOfficer> programCoursesList, List <PLO> programPloList ){
        this.setProgramID(programID);
        this.setProgramDegreeType(programDegreeType);
        this.setProgramName(programName);
        this.setProgramCoursesList(programCoursesList);
        this.setProgramPloList(programPloList);
    }


    private void setProgramID(int programID) {
        this.programID = programID;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getProgramName() {
        return programName;
    }
    public void setProgramDegreeType(String programDegreeType) {
        this.programDegreeType = programDegreeType;
    }

    public String getProgramDegreeType() {
        return programDegreeType;
    }

    public void addCourse(CourseOfficer newCourse){
        this.programCoursesList.add(newCourse);
    }
    private void setProgramCoursesList(List<CourseOfficer> programCoursesList) {
        this.programCoursesList = programCoursesList;
    }

    public List<CourseOfficer> getProgramCoursesList() {
        return programCoursesList;
    }

    public void printAllCourses(){
        for (CourseOfficer course :
                this.getProgramCoursesList()) {
            System.out.println(course.getCourseID() + " : -> " + course.getCourseName() + " : -> " + course.getCourseCreditHours() + " ");
        }
    }

    public CourseOfficer getCourseById(int id){
        for (CourseOfficer course :
                this.programCoursesList) {
            if(course.getCourseID() == id){
                return course;
            }
        }
        return null;
    }

    public void addPlo(PLO newPlo){
        this.programPloList.add(newPlo);
    }
    private void setProgramPloList(List<PLO> programPloList) {
        this.programPloList = programPloList;
    }

    public List<PLO> getProgramPloList() {
        return programPloList;
    }

    public void printAllPLOs(){
        for (PLO plo :
                this.getProgramPloList()) {
            System.out.println(plo.getId() + ": -> " + plo.getDescription() + " ");
        }
    }

    public PLO getPLObyId(int id){
        for (PLO plo:
                this.programPloList) {
            if(plo.getId() == id){
                return plo;
            }
        }
        return null;
    }

    protected void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.programsPath);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.programsPath, append);
            myWriter.write(Integer.toString(this.programID) + "\n");
            myWriter.write(this.programName + "\n");
            myWriter.write(this.programDegreeType + "\n");

            String newArray = "";
            for (CourseOfficer course :
                    programCoursesList) {
                newArray = newArray.concat(Integer.toString(course.getCourseID()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            newArray = "";
            for (PLO plo :
                    programPloList) {
                newArray = newArray.concat(Integer.toString(plo.getId()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            myWriter.close();
        }catch (Exception e){
            System.out.println("Error Occurred + " + e.getMessage());
        }
    }



}
