package com.arslan;

import java.util.List;

public interface CourseOfficer {

    public int getCourseID();
    public void setCourseName(String courseName);
    public void setCourseCreditHours(int courseCreditHours);
    public String getCourseName();
    public int getCourseCreditHours();
    public void addProgram(Programs newProgram);
    public List<Programs> getCourseProgramList();
    public void addCLO(CLO clo);
    public List<CLO> getCourseCloList();
    public void printAllCLOs();
    public CLO getCLOById(int id);
    public void saveInFile(boolean append);

}
