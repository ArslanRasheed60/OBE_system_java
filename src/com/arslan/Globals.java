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
    public static String academicOfficersPath  = "E:\\Development\\SDA_pro2\\src\\com\\files\\AcademicOfficers.txt";
    public static String coursesPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\Courses.txt";
    public static String programsPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\Programs.txt";
    public static String closPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\CLOs.txt";
    public static String ploPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\PLOs.txt";
    public static String topicPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\Topics.txt";
    public static String teachersPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\Teachers.txt";
    public static String evaluationsPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\Evaluations.txt";
    public static String questionPath = "E:\\Development\\SDA_pro2\\src\\com\\files\\Questions.txt";

//        Paths end

    //Lists start
    public static List<AcademicOfficer> academicOfficerList = null;
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

    public static boolean verifyAdministrator(String eUsername, String ePassword){
        try {
            File keyfile = new File(Globals.academicOfficersPath);
            Scanner myReader = new Scanner(keyfile);
            String fullName = "", phoneNumber = "", address = "", username = "", password = "";
            while(myReader.hasNextLine()) {
                fullName = myReader.nextLine();
                phoneNumber = myReader.nextLine();
                address = myReader.nextLine();
                username = myReader.nextLine();
                password = myReader.nextLine();

                if(username.equals(eUsername) && password.equals(ePassword)){
                    return true;
                }
            }
            myReader.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
        return false;
    }

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

    public static boolean verifyQuestionAndTeacher(Questions question, Teachers teacher){
        if(question != null){
            if(question.getEvaluation() != null){
                if(question.getEvaluation().getTeacher() != null)
                {
                    return question.getEvaluation().getTeacher().getTeacherID() == teacher.getTeacherID();
                }
            }
        }
        return false;
    }

    // verifications ends


    //filing    starts
    public static void saveCourse(){
        int count = 0;
        boolean flag = false;

        if(coursesList != null && coursesList.size() > 0){
            for (Courses updateCourse :
                    coursesList) {
                flag = count >= 1;
                updateCourse.saveInFile(flag);
                count++;
            }
        }else{
            clearCourseFile();
        }
    }

    public static void clearCourseFile(){
        try{
            File keyfile = new File(Globals.coursesPath);
            FileWriter myWriter = new FileWriter(Globals.coursesPath, false);
            myWriter.write("");
            myWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveProgram(){
        int count = 0;
        boolean flag = false;

        if(programsList != null && programsList.size() > 0){
            for (Programs programs :
                    programsList) {
                flag = count >= 1;
                programs.saveInFile(flag);
                count++;
            }
        }
    }

    public static void saveCLO(){
        int count = 0;
        boolean flag = false;

        if(cloList != null && cloList.size() > 0){
            for (CLO clo :
                    cloList) {
                flag = count >= 1;
                clo.saveInFile(flag);
                count++;
            }
        }else{
            clearCLOFile();
        }
    }

    public static void clearCLOFile(){
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

        if(ploList != null && ploList.size() > 0) {
            for (PLO plo :
                    ploList) {
                flag = count >= 1;
                plo.saveInFile(flag);
                count++;
            }
        }
    }

    public static void saveTopic(){
        int count = 0;
        boolean flag = false;

        if(topicsList != null && topicsList.size() > 0){
            for (Topics topic :
                    topicsList) {
                flag = count >= 1;
                topic.saveInFile(flag);
                count++;
            }
        }else{
            clearTopicFile();
        }
    }

    public static void clearTopicFile(){
        try{
            FileWriter myWriter = new FileWriter(Globals.topicPath, false);
            myWriter.write("");
            myWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveTeacher(){
        int count = 0;
        boolean flag = false;

        if(teachersList != null && teachersList.size() > 0){
            for (Teachers teacher :
                    teachersList) {
                flag = count >= 1;
                teacher.saveInFile(flag);
                count++;
            }
        }
    }

    public static void saveEvaluation(){
        int count = 0;
        boolean flag = false;

        if(evaluationsList != null && evaluationsList.size() > 0){
            for (Evaluations evaluation :
                    evaluationsList) {
                flag = count >= 1;
                evaluation.saveInFile(flag);
                count++;
            }
        }else{
            clearEvaluationFile();
        }
    }

    public static void clearEvaluationFile(){
        try{
            FileWriter myWriter = new FileWriter(Globals.evaluationsPath, false);
            myWriter.write("");
            myWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void saveQuestion(){
        int count = 0;
        boolean flag = false;

        if(questionsList != null && questionsList.size() > 0){
            for (Questions question :
                    questionsList) {
                flag = count >= 1;
                question.saveInFile(flag);
                count++;
            }
        }else{
            clearQuestionFile();
        }
    }

    public static void clearQuestionFile(){
        try{
            FileWriter myWriter = new FileWriter(Globals.questionPath, false);
            myWriter.write("");
            myWriter.close();
        }catch (Exception e){
            e.printStackTrace();
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

    //get object by id starts

    public static Courses getCourseById(int id){
        for (Courses course :
                coursesList) {
            if (course.getCourseID() == id) {
                return course;
            }
        }
        return null;
    }

    public static Teachers getTeacherById(int id){
        for (Teachers teacher :
                teachersList) {
            if (teacher.getTeacherID() == id) {
                return teacher;
            }
        }
        return null;
    }


    //get object by id ends

    //duplicate testing starts

    public static boolean isDuplicateProgram(Programs nPrograms){
        if(programsList != null){
            for (Programs program :
                    programsList) {
                if(program.getProgramName().equals(nPrograms.getProgramName()) && program.getProgramDegreeType().equals(nPrograms.getProgramDegreeType())){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isDuplicateCourse(Courses nCourse, List<Courses> nCourseList){
        if(nCourseList != null){
            for (Courses course :
                    nCourseList) {
                if (course.getCourseName().equals(nCourse.getCourseName())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isDuplicatePLO(PLO nPlo){
        if(ploList != null){
            for (PLO plo :
                    ploList) {
                if (plo.getDescription().equals(nPlo.getDescription())) {
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean isDuplicateCLO(CLO nClo, List<CLO> nCloList){
            if(nCloList != null){
                for (CLO clo :
                        nCloList) {
                    if (clo.getDescription().equals(nClo.getDescription())) {
                        return true;
                    }
                }
            }
            return false;
        }

    public static boolean isDuplicateTopic(Topics nTopic, List<Topics> nTopicList){
       if(nTopicList != null){
           for (Topics topic :
                   nTopicList) {
               if(topic.getTopicDescription().equals(nTopic.getTopicDescription())){
                   return true;
               }
           }
       }
        return false;
    }


    //duplicate testing ends

}
