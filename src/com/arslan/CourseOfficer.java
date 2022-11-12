package com.arslan;

import java.util.List;

public interface CourseOfficer {

    public int getCourseID();
    public String getCourseName();
    public int getCourseCreditHours();
    public void addProgram(Programs newProgram);
    public List<Programs> getCourseProgramList();
    public void addCLO(CLO clo);
    public List<CLO> getCourseCloList();
    public void saveInFile(boolean append);

}
