package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Globals {
    //        Paths start
    public static String academicOfficersPath  = "E:\\Development\\SDA_Assigment\\src\\com\\arslan\\AcademicOfficers.txt";
    public static String coursesPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Courses.txt";
    public static String programsPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Programs.txt";
    public static String closPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\CLOs.txt";
    public static String ploPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\PLOs.txt";
    public static String topicPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Topics.txt";
    public static String teachersPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Teachers.txt";

//        Paths end

    //Lists start
    public static List<Programs> programsList = null;
    public static List<Courses> coursesList = null;
    public static List<PLO> ploList = null;
    public static List<CLO> cloList = null;

    public static List<Topics> topicsList = null;

    public static List<Teachers> teachersList = null;

    public static List<Questions> questionsList = null;

    public static List<Evaluations> evaluationsList = null;

    //Lists end

    // verifications starts

    public static Teachers verifyTeacher(String username, String password){
        if(teachersList != null){
            for (Teachers teacher :
                    teachersList) {
                if (teacher.getUsername().equals(username) && teacher.getPassword().equals(password)){
                    return teacher;
                }
            }
        }
        return null;
    }


    // verifications ends


    //filing    starts
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

    public static void deleteCLOFile(){
        try{
            File keyfile = new File(Globals.closPath);
            FileWriter myWriter = new FileWriter(Globals.closPath, false);
            myWriter.write("");
            myWriter.close();
        }catch (Exception e){
            e.printStackTrace();
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

    public static void saveTopic(){
        int count = 0;
        boolean flag = false;

        for (Topics topic :
                topicsList) {
            flag = count >= 1;
            topic.saveInFile(flag);
            count++;
        }
    }

    //filing    ends

    //user input function starts
    public static int getChoiceInput(int start, int end){
        Scanner myInput = new Scanner(System.in);
        int userInput = myInput.nextByte();
        while(userInput < start || userInput > end){
            System.out.print("Enter Valid Input: ");
            userInput = myInput.nextByte();
            System.out.println("\n");
        }
        return userInput;
    }

    public static String getStringInput(){
        Scanner myInput = new Scanner(System.in);
        return myInput.nextLine();
    }
    public static int getIntegerInput(){
        Scanner myInput = new Scanner(System.in);
        return myInput.nextInt();
    }

    public static char getCheckInput(){
        Scanner myInput = new Scanner(System.in);
        char check = myInput.nextLine().toLowerCase().charAt(0);
        while(check != 'y' && check != 'n'){
            System.out.println("Wrong input . Enter Again");
            check = myInput.nextLine().toLowerCase().charAt(0);
        }
        return check;
    }
    //user input function ends

    //duplicate testing starts

    public static boolean isDuplicateProgram(Programs nPrograms){
        for (Programs program :
                programsList) {
            if(program.getProgramName().equals(nPrograms.getProgramName()) && program.getProgramDegreeType().equals(nPrograms.getProgramDegreeType())){
               return true;
            }
        }
        return false;
    }

    //duplicate testing ends

}
