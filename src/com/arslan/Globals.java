package com.arslan;

import java.util.List;

public class Globals {
    //        Paths
    public static String academicOfficersPath  = "E:\\Development\\SDA_Assigment\\src\\com\\arslan\\AcademicOfficers.txt";

    public static String coursesPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Courses.txt";
    public static String programsPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Programs.txt";
    public static String closPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\CLOs.txt";
    public static String ploPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\PLOs.txt";
    public static String topicPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Topics.txt";
//        Paths

    //Lists
    public static List<Programs> programsList;
    public static List<Courses> coursesList;
    public static List<PLO> ploList;
    public static List<CLO> cloList;

    //

    //filing
    public static void saveCourse(){
        int count = 0;
        boolean flag = false;

        for (Courses updateCourse :
                coursesList) {
            flag = count >= 1;
            updateCourse.saveInFile(flag);
            count++;
        }
    }

    public static void saveProgram(){
        int count = 0;
        boolean flag = false;

        for (Programs programs :
                programsList) {
            flag = count >= 1;
            programs.saveInFile(flag);
            count++;
        }
    }

    public static void saveCLO(){
        int count = 0;
        boolean flag = false;

        for (CLO clo :
                cloList) {
            flag = count >= 1;
            clo.saveInFile(flag);
            count++;
        }
    }

    public static void savePLO()
    {
        int count = 0;
        boolean flag = false;

        for (PLO plo :
                ploList) {
            flag = count >= 1;
            plo.saveInFile(flag);
            count++;
        }
    }

    //filing

}
