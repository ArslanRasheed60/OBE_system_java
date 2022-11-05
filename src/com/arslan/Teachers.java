package com.arslan;

import java.util.List;

public class Teachers extends Users{
    private int teacherID;
    private List<Courses> coursesList;
    private List<CLO> cloList;
    private List<Evaluations> evaluationsList;


    public Teachers(int teacherId,String fullName, String phoneNumber, String address){
        super(fullName, phoneNumber, address);
        this.setTeacherID(teacherId);
    }

    private void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public int getTeacherID() {
        return teacherID;
    }
}
