package com.arslan;

import java.io.File;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//        Paths
            String academicOfficersPath  = "E:\\Development\\SDA_Assigment\\src\\com\\arslan\\AcademicOfficers.txt";
            String coursesPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Courses.txt";
            String programsPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Programs.txt";
            String closPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\CLOs.txt";
//        Paths

        //Administrator starts here
        AcademicOfficer academicOfficer = null;

        try {
            //Read administrator credentials
            File keyfile = new File(academicOfficersPath);
            Scanner myReader = new Scanner(keyfile);
            String fullName = "", phoneNumber = "", address = "";
            while(myReader.hasNextLine()) {

                fullName = myReader.nextLine();
                phoneNumber = myReader.nextLine();
                address = myReader.nextLine();
                System.out.println(fullName + phoneNumber + address);
            }
            myReader.close();
            academicOfficer = new AcademicOfficer(fullName, phoneNumber, address);

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
        //Basic Administrator StartUp to read files (End)
        if(academicOfficer != null) {
            //load files
            loadFileData(academicOfficer);

            //Start Interface
            System.out.print("**************************** OBE *****************************\n");
            System.out.println("1: Login as Administrator");
            System.out.println("2: Login as Teacher");
            System.out.println("3: Login as Guest");
            System.out.print("Enter Choice: ");
            int userInput = getChoiceInput(1, 3);          //get input from user

            if (userInput == 1) {
                try {
                    //************************ Login or administrator will be implemented here*********************************
                    if (AcademicOfficer.isAcademicOfficer) {

                        System.out.print("Login Successfully as Administrator\n");
                        System.out.println("1: Add New Program");
                        System.out.println("2: Delete Program");
                        System.out.println("3: Update Program");
                        System.out.println("4: Add New Course");
                        System.out.println("5: Delete Course");
                        System.out.println("6: Update Course");
                        System.out.println("7: Get List of all Courses");
                        System.out.println("8: Get List of all Programs");
                        System.out.println("9: Add PLO");
                        System.out.println("10: Add CLO");
                        System.out.println("11: Add Topic");
                        int userInput2 = getChoiceInput(1, 11);          //get 2nd input from user

                        if (userInput2 == 1) {
                            //Add Program
                            System.out.println("\tEnter Program Name");
                            String programName = getStringInput();
                            System.out.println("\tEnter Program Degree Type");
                            String programDegreeType = getStringInput();
                            int programID = academicOfficer.getProgramsList().size();
                            if (programID > 0) {
                                programID = academicOfficer.getProgramsList().get(programID - 1).getProgramID();
                            }
                            programID++;
                            Programs newProgram = new Programs(programID, programDegreeType, programName);
                            academicOfficer.saveProgram(newProgram);
                        }
                        else if (userInput2 == 2 || userInput2 == 3) {
//                        delete/update program
                            String userData2 = userInput2 == 2 ? "delete" : "update";
                            System.out.println("\tEnter Program Id for " + userData2);
                            int programID = getIntegerInput();

                            // get program from program list with the help of id
                            List<Programs> programsList = academicOfficer.getProgramsList();
                            Programs manageProgram = null;
                            for (Programs program :
                                    programsList) {
                                if (programID == program.getProgramID()) {
                                    manageProgram = program;
                                    break;
                                }
                            }
                            //if id found then delete/update it
                            if (manageProgram != null) {
                                if (userInput2 == 2) {
                                    academicOfficer.removeProgram(manageProgram);
                                } else {
                                    System.out.println("\tEnter --New-- Program Name");
                                    String programName = getStringInput();
                                    System.out.println("\tEnter --New-- Program Degree Type");
                                    String programDegreeType = getStringInput();
                                    manageProgram.setProgramName(programName);
                                    manageProgram.setProgramDegreeType(programDegreeType);
                                }
                                academicOfficer.updateProgramFile();
                                System.out.println("Program " + userData2 + " Successfully");
                            } else {
                                System.out.println("Invalid Program ID");
                            }
                        }
                        else if (userInput2 == 4) {

                            System.out.println("1: Add New Course to Program Course");
                            System.out.println("2: Add Existing Course to other Program");
                            System.out.print("Enter Choice");
                            int userInput3 = getChoiceInput(1,2);

                            //add new course to the program
                            int total = academicOfficer.getProgramsList().size();
                            if(total > 0){
                                //Entering in program
                                System.out.println("\nEnter Program Id from given list: ");
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    System.out.println(program.getProgramID() + " : -> " + program.getProgramDegreeType() + " -> " + program.getProgramName());
                                }
                                int pId = getIntegerInput();
                                Programs nProgram = null;
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    if (program.getProgramID() == pId) {
                                        nProgram = program;
                                        break;
                                    }
                                }
                                if(userInput3 == 1 && nProgram != null){
                                    //add new course
                                    System.out.println("\n");
                                    System.out.println("\tEnter course Name");
                                    String courseName = getStringInput();
                                    System.out.println("\tEnter creditHours");
                                    int courseCreditHours = getIntegerInput();

                                    int courseId = academicOfficer.currentNumberOfCourses();
//                                    if (courseId > 0) {
//                                        courseId = nProgram.getProgramCoursesList().get(courseId - 1).getCourseID();
//                                    }
                                    courseId++;
                                    Courses newCourse = new Courses(courseId,courseName, courseCreditHours);
                                    //update list
                                    nProgram.addCourse(newCourse);
                                    newCourse.addProgram(nProgram);
                                    //update file
                                    newCourse.saveInFile(true);
                                    academicOfficer.updateProgramFile();
                                    System.out.println("Course Saved Successfully");
                                }else if (userInput3 == 2 && nProgram!=null){

                                    total = nProgram.getProgramCoursesList().size();

                                    if(total > 0){
                                        System.out.println("\n\tEnter Course Id from given list:  ");
                                        for (Courses findCourse :
                                                nProgram.getProgramCoursesList()) {
                                            System.out.println(findCourse.getCourseID() + ": -> " + findCourse.getCourseName() + " ");
                                        }
                                        int cId = getIntegerInput();
                                        Courses nCourse = null;
                                        for (Courses findCourse :
                                                nProgram.getProgramCoursesList()) {
                                            if(findCourse.getCourseID() == cId){
                                                nCourse = findCourse;
                                            }
                                        }
                                        if(nCourse != null){
                                            //update lists
                                            nCourse.addProgram(nProgram);
                                            nProgram.addCourse(nCourse);
                                            //update files
                                            academicOfficer.updateProgramFile();
                                            academicOfficer.updateCourseFile();
                                        }else{
                                            System.out.println("Invalid Course ID");
                                        }
                                    }else{
                                        System.out.println("Course does not exist");
                                    }
                                }else{
                                    System.out.println("Invalid input");
                                }
                            }else{
                                System.out.println("Program does not exist");
                            }

                        }
                        else if (userInput2 == 5) {
                            //delete course
                        } else if (userInput2 == 6) {
                            //update course

                        } else if (userInput2 == 7) {
                            //list of all courses

                        } else if (userInput2 == 8) {
                            //list of all programs
                        }
                        else if(userInput2 == 9){
                            //add plo
                            System.out.println("Program ID");
                            int pId = getIntegerInput();
                            System.out.println("Enter PLO Description");
                            String description = getStringInput();

                            PLO newPlo = null;

                            for (Programs program :
                                    academicOfficer.getProgramsList()) {
                                if (program.getProgramID() == pId) {
                                    int ploId = program.getProgramPloList().size();
                                    if (ploId > 0) {
                                        ploId = program.getProgramPloList().get(ploId - 1).getId();
                                    }
                                    ploId++;
                                    newPlo = new PLO(ploId,program, description);
                                    newPlo.saveInFile(true);
                                    program.addPlo(newPlo);
                                    break;
                                }
                            }

                            if (newPlo != null) {
                                academicOfficer.updateProgramFile();
                                System.out.println("PLO Saved Successfully");
                            } else {
                                System.out.println("Invalid PLO Program Id");
                            }

                        }
                        else if(userInput2 == 10){
                            //add new clo to course
                            System.out.println("1: Add New ClO to  Course");
                            System.out.println("2: Add Existing CLO to another course");
                            System.out.print("Enter Choice");
                            int userInput3 = getChoiceInput(1,2);

                            int total = academicOfficer.getProgramsList().size();
                            //enter in program
                            if(total > 0) {
                                System.out.println("\nEnter Program Id from given list: ");
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    System.out.println(program.getProgramID() + " : -> " + program.getProgramDegreeType() + " -> " + program.getProgramName());
                                }
                                int pId = getChoiceInput(1, total);
                                Programs nProgram = null;
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    if (program.getProgramID() == pId) {
                                        nProgram = program;
                                        break;
                                    }
                                }
                                //enter in course
                                if(nProgram != null)
                                {
                                    System.out.println("\n");
                                    total = nProgram.getProgramCoursesList().size();
                                    if(total > 0){
                                        System.out.print("Enter Course Id from given list: ");
                                        for (Courses course :
                                                nProgram.getProgramCoursesList()) {
                                            System.out.println(course.getCourseID() + ": -> " + course.getCourseName() + " ");
                                        }
                                        int cId = getChoiceInput(1, total);
                                        Courses nCourse= null;
                                        for (Courses course :
                                                nProgram.getProgramCoursesList()) {
                                            if(course.getCourseID() == cId){
                                                nCourse = course;
                                                break;
                                            }
                                        }
                                        if(userInput3 == 1 && nCourse != null){
                                        //add clo
                                        System.out.println("\nEnter clo description");
                                        String cloDescription = getStringInput();

                                        int cloId = nCourse.getCourseCloList().size();
                                        if(cloId > 0){
                                            cloId = nCourse.getCourseCloList().get(cloId - 1).getId();
                                        }
                                        cloId++;
                                        CLO newClo = new CLO(cloId, cloDescription);
                                        //update links
                                        newClo.addCloCoursesList(nCourse);
                                        nCourse.addCLO(newClo);
                                        //update file
                                        newClo.saveInFile(true);
                                        academicOfficer.updateCourseFile();

                                        }else if(userInput3 == 2 && nCourse != null){
                                            total = nCourse.getCourseCloList().size();
                                            if(total > 0){
                                            System.out.print("\nEnter CLO ID from the list:  ");
                                                for (CLO clo :
                                                        nCourse.getCourseCloList()) {
                                                    System.out.print(clo.getId() + " , ");
                                                }
                                                int cin = getChoiceInput(1,total);
                                                for (CLO clo :
                                                        nCourse.getCourseCloList()) {
                                                    if(cin == clo.getId()){
                                                        //update links
                                                        clo.addCloCoursesList(nCourse);
                                                        nCourse.addCLO(clo);
                                                        //update files
                                                        academicOfficer.updateCourseFile();
                                                        academicOfficer.updateCloFile();
                                                    }
                                                }

                                            }else{
                                                System.out.println("ClO does not exist");
                                            }
                                        }

                                        System.out.println("CLO Saved Successfully");

                                    }else{
                                        System.out.println("Course does not exist");
                                    }
                                }
                            }else{
                                System.out.println("Program does not exist");
                            }

                        }else if(userInput2 == 11){
                            //add topic
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An Error occurred :  " + e.getMessage());
                }
            } else if (userInput == 2) {
                //teacher
            } else if (userInput == 3) {
                //guest
            }
        }

    }

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
        String userInput = myInput.nextLine();
        return userInput;
    }
    public static int getIntegerInput(){
        Scanner myInput = new Scanner(System.in);
        int userInput = myInput.nextInt();
        return userInput;
    }

    public static void loadFileData(AcademicOfficer officer){

//        Paths
        String coursesPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Courses.txt";
        String programsPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Programs.txt";
        String closPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\CLOs.txt";
        String ploPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\PLOs.txt";
        String topicPath = "E:\\Development\\SDA_Assigment\\src\\com\\files\\Topics.txt";
//        Paths

        try {
            List<Programs> programsList = new ArrayList<>();
            List<String> programPloList = new ArrayList<>();
            List<String> programCoursesList = new ArrayList<>();

            List<Courses> coursesList = new ArrayList<>();
            List<String> courseProgramsList = new ArrayList<>();
            List<String> courseTeachersList = new ArrayList<>();
            List<String> courseCloList = new ArrayList<>();

            List<PLO> ploList = new ArrayList<>();

            List<CLO> cloList = new ArrayList<>();
            List<String> cloCourseList = new ArrayList<>();
            List<String> cloTopicList = new ArrayList<>();

            List<Topics> topicsList = new ArrayList<>();
            List<String> topicCloList = new ArrayList<>();

            //teacher
            List<Teachers> teachersList = new ArrayList<>();

            //Read Program data
            File programKeyFile = new File(programsPath);
            if(programKeyFile.exists()){
                Scanner myProgramReader = new Scanner(programKeyFile);
                while(myProgramReader.hasNextLine()){
                    int pId = Integer.parseInt(myProgramReader.nextLine());
                    String pName = myProgramReader.nextLine();
                    String pDegreeType = myProgramReader.nextLine();
                    String pCourseList = myProgramReader.nextLine();
                    String pPloList = myProgramReader.nextLine();

                    Programs newProgram = new Programs(pId, pDegreeType, pName);
                    //add in lists;
                    programsList.add(newProgram);

                    programCoursesList.add(pCourseList);
                    programPloList.add(pPloList);
                }
            }

            //Read course data
            File courseKeyFile = new File(coursesPath);
            if(courseKeyFile.exists()){
                Scanner myCourseReader = new Scanner(courseKeyFile);
                while(myCourseReader.hasNextLine()){
                    int cId = Integer.parseInt(myCourseReader.nextLine());
                    String cName = myCourseReader.nextLine();
                    int cCreditHours = Integer.parseInt(myCourseReader.nextLine());
                    String cProgramList = myCourseReader.nextLine();
                    String cTeacherList = myCourseReader.nextLine();
                    String cCloList = myCourseReader.nextLine();

                    Courses newCourse = new Courses(cId, cName, cCreditHours);
                    // add in list
                    coursesList.add(newCourse);
                    courseProgramsList.add(cProgramList);
                    courseTeachersList.add(cTeacherList);
                    courseCloList.add(cCloList);


                }
            }

            //Read PLO data
            File ploKeyFile = new File(ploPath);
            if(ploKeyFile.exists()){
                Scanner myPloReader = new Scanner(ploKeyFile);
                while(myPloReader.hasNextLine()){
                    int ploId = Integer.parseInt(myPloReader.nextLine());
                    String ploDescription = myPloReader.nextLine();
                    int ploProgramId = Integer.parseInt(myPloReader.nextLine());

                    Programs getProgram = null;

                    for (Programs program :
                            programsList) {
                        if (program.getProgramID() == ploProgramId) {
                            getProgram = program;
                            break;
                        }
                    }

                    PLO newPlo = new PLO(ploId, getProgram, ploDescription);
                    //add in list
                    ploList.add(newPlo);
                }
            }

            //Read CLO data
            File cloKeyFile = new File(closPath);
            if(cloKeyFile.exists()){
                Scanner myCloReader = new Scanner(cloKeyFile);
                while(myCloReader.hasNextLine()){
                    int cloId = Integer.parseInt(myCloReader.nextLine());
                    String cloDescription = myCloReader.nextLine();
                    String cloCList = myCloReader.nextLine();
                    String cloTList = myCloReader.nextLine();

                    CLO newClo = new CLO(cloId, cloDescription);

                    //add in list
                    cloList.add(newClo);
                    cloCourseList.add(cloCList);
                    cloTopicList.add(cloTList);
                }
            }

            //Read Topic data
            File topicKeyFile = new File(topicPath);
            if(topicKeyFile.exists()){
                Scanner myTopicReader = new Scanner(topicKeyFile);
                while(myTopicReader.hasNextLine()){
                    int tId = Integer.parseInt(myTopicReader.nextLine());
                    String tDescription = myTopicReader.nextLine();
                    String tCloList = myTopicReader.nextLine();

                    Topics newTopics = new Topics(tId, tDescription);

                    //add in list
                    topicsList.add(newTopics);
                    topicCloList.add(tCloList);
                }
            }


            //update all lists

            for (Programs program :
                    programsList) {
                int id = program.getProgramID();
                String pPlo = programPloList.get(id-1);
                String pCourse = programCoursesList.get(id-1);

                if(!Objects.equals(pPlo, "0")) {
                    String[] splitArray1 = pPlo.split(",");
                    List<Integer> arrayPloList = new ArrayList<>();
                    for (String s : splitArray1) {
                        arrayPloList.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            arrayPloList) {
                        PLO nPlo = null;
                        for (PLO plo :
                                ploList) {
                            if (plo.getId() == i) {
                                nPlo = plo;
                                break;
                            }
                        }
                        program.addPlo(nPlo);
                    }

                }

                if(!Objects.equals(pCourse, "0")) {
                    String[] splitArray1 = pCourse.split(",");
                    List<Integer> arrayCourseList = new ArrayList<>();
                    for (String s : splitArray1) {
                        arrayCourseList.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            arrayCourseList) {
                        Courses nCourse = null;
                        for (Courses course :
                                coursesList) {
                            if (course.getCourseID() == i) {
                                nCourse = course;
                                break;
                            }
                        }
                        program.addCourse(nCourse);
                    }
                }

                officer.addProgram(program);
            }

            for (Courses course :
                    coursesList) {
                int id = course.getCourseID();
                String cProgram = courseProgramsList.get(id-1);
                String cTeacher = courseTeachersList.get(id-1);
                String cClo = courseCloList.get(id-1);

                if(!Objects.equals(cProgram, "0")) {
                    String[] splitArray1 = cProgram.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        Programs nProgram = null;
                        for (Programs program :
                                programsList) {
                            if (program.getProgramID() == i) {
                                nProgram = program;
                                break;
                            }
                        }
                        course.addProgram(nProgram);
                    }
                }

                if(!Objects.equals(cTeacher, "0")) {
                    String[] splitArray1 = cTeacher.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        Teachers nTeacher = null;
                        for (Teachers teacher :
                                teachersList) {
                            if (teacher.getTeacherID() == i) {
                                nTeacher = teacher;
                                break;
                            }
                        }
                        course.addTeacher(nTeacher);
                    }
                }

                if(!Objects.equals(cClo, "0")) {
                    String[] splitArray1 = cClo.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        CLO nClo = null;
                        for (CLO clo :
                                cloList) {
                            if (clo.getId() == i) {
                                nClo = clo;
                                break;
                            }
                        }
                        course.addCLO(nClo);
                    }
                }
            }

            for (CLO clo :
                    cloList) {
                int id = clo.getId();
                String cCourse = cloCourseList.get(id-1);
                String cTopic = cloTopicList.get(id-1);

                if(!Objects.equals(cCourse, "0")) {
                    String[] splitArray1 = cCourse.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        Courses nCourse = null;
                        for (Courses course :
                                coursesList) {
                            if (course.getCourseID() == i) {
                                nCourse = course;
                                break;
                            }
                        }
                        clo.addCloCoursesList(nCourse);
                    }
                }

                if(!Objects.equals(cTopic, "0")) {
                    String[] splitArray1 = cTopic.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        Topics nTopic = null;
                        for (Topics topic :
                                topicsList) {
                            if (topic.getTopicID() == i) {
                                nTopic = topic;
                                break;
                            }
                        }
                        clo.addCloTopicList(nTopic);
                    }
                }

            }

            for (Topics topic :
                    topicsList) {
                int id = topic.getTopicID();
                String tClo = topicCloList.get(id-1);

                if(!Objects.equals(tClo, "0")) {
                    String[] splitArray1 = tClo.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        CLO nClo = null;
                        for (CLO clo :
                                cloList) {
                            if (clo.getId() == i) {
                                nClo = clo;
                                break;
                            }
                        }
                        topic.addTopicClo(nClo);
                    }
                }

            }

        }
        catch (Exception e){
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }


}