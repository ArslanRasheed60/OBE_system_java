package com.arslan;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AcademicOfficer extends Users{
    public static boolean isAcademicOfficer;
    private List<Programs> programsList;

    public AcademicOfficer(){
        super(null,null,null);
        isAcademicOfficer=false;
    }

    public AcademicOfficer (String fullName, String phoneNumber, String address){
        super(fullName,phoneNumber, address);
        programsList = new ArrayList<>();

        if(fullName.length() > 2){
            isAcademicOfficer = true;
        }else{
            isAcademicOfficer = false;
        }
    };

    public void addProgram(Programs program) {
        programsList.add(program);
    }

    public void removeProgram(Programs program){
        programsList.remove(program);
    }

    public List<Programs> getProgramsList() {
        return programsList;
    }

    public void updateProgramFile(){
        int count = 0;
        boolean flag = false;

        for (Programs deleteProgram :
                this.programsList) {
            flag = count >= 1;
            deleteProgram.saveInFile(flag);
            count++;
        }
    }

    public <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

//    public void updateCourseFile(){
//        int count = 0;
//        boolean flag = false;
//
//        int programListSize = this.programsList.size();
//        List<Courses> coursesList = null;
//        if(programListSize > 1){
//            for(int i = 0;i<programListSize-1;i++){
//                coursesList = union(this.programsList.get(i).getProgramCoursesList(),this.programsList.get(i+1).getProgramCoursesList());
//            }
//
//        }else if (programListSize == 1){
//            coursesList = this.programsList.get(0).getProgramCoursesList();
//        }
//        if(coursesList != null){
//            for (Courses updateCourse :
//                    coursesList) {
//                flag = count >= 1;
//                updateCourse.saveInFile(flag);
//                count++;
//            }
//        }
//    }

//    public int currentNumberOfCourses(){
//        int programListSize = this.programsList.size();
//        List<Courses> coursesList = null;
//        if(programListSize > 1){
//            for(int i = 0;i<programListSize-1;i++){
//                coursesList = union(this.programsList.get(i).getProgramCoursesList(),this.programsList.get(i+1).getProgramCoursesList());
//            }
//        }else if(programListSize == 1){
//            coursesList = this.programsList.get(0).getProgramCoursesList();
//        }
//        return coursesList == null ? 0 : coursesList.size();
//    }

//    public void updateCloFile(){
//        int count = 0;
//        boolean flag = false;
//
//        int programListSize = this.programsList.size();
//        if(programListSize > 1){
//            List<Courses> coursesList = null;
//            for(int i = 0;i<programListSize-1;i++){
//                coursesList = union(this.programsList.get(i).getProgramCoursesList(),this.programsList.get(i+1).getProgramCoursesList());
//            }
//            int coursesSize = coursesList.size();
//            if(coursesSize > 1){
//                List<CLO> cloList = null;
//                for(int i = 0;i<coursesSize-1;i++){
//                    cloList = union(coursesList.get(i).getCourseCloList(),coursesList.get(i+1).getCourseCloList());
//                }
//                if(cloList != null){
//                    for (CLO clo :
//                            cloList) {
//                        flag = count >= 1;
//                        clo.saveInFile(flag);
//                        count++;
//                    }
//                }
//            }
//
//        }
//    }

}
