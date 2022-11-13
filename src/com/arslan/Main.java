package com.arslan;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.File;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Administrator starts here
        AcademicOfficer academicOfficer = null;

        try {
            //Read administrator credentials
            File keyfile = new File(Globals.academicOfficersPath);
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
                        System.out.println("2: Delete Program");            //no need to delete program
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
                            //update lists
                            academicOfficer.addProgram(newProgram);
                            Globals.programsList.add(newProgram);
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
                                    if (program.getProgramID() == pId ) {
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

                                    int courseId = Globals.coursesList.size();
                                    courseId++;
                                    Courses newCourse = new Courses(courseId,courseName, courseCreditHours);
                                    //update list
                                    nProgram.addCourse(newCourse);
                                    newCourse.addProgram(nProgram);
                                    Globals.coursesList.add(newCourse);
                                    //update file
//                                    newCourse.saveInFile(true);
//                                    academicOfficer.updateProgramFile();
                                    System.out.println("Course Saved Successfully");
                                }
                                else if (userInput3 == 2 && nProgram!=null){

                                    int courseLength = Globals.coursesList.size();

                                    if(courseLength > 0){
                                        System.out.println("\n\tEnter Course Id from given list:  ");
                                        for (CourseOfficer findCourse :
                                                Globals.coursesList) {
                                            System.out.println(findCourse.getCourseID() + ": -> " + findCourse.getCourseName() + " ");
                                        }
                                        int cId = getIntegerInput();
                                        CourseOfficer nCourse = null;
                                        for (CourseOfficer findCourse :
                                                Globals.coursesList) {
                                            if(findCourse.getCourseID() == cId){
                                                nCourse = findCourse;
                                            }
                                        }
                                        if(nCourse != null){
                                            //update lists
                                            nCourse.addProgram(nProgram);
                                            nProgram.addCourse(nCourse);
                                            System.out.println("Course Added Successfully");
                                            //update files
//                                            academicOfficer.updateProgramFile();
//                                            academicOfficer.updateCourseFile();
                                        }else{
                                            System.out.println("!! Invalid Course ID !!");
                                        }
                                    }else{
                                        System.out.println("!! Course does not exist !!");
                                    }
                                }else{
                                    System.out.println("!! Invalid input !!");
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
                            }

                        }
                         else if (userInput2 == 5 || userInput2 == 6) {
//                        delete/update course
                            String userData2 = userInput2 == 5 ? "delete" : "update";

                            int programLength = academicOfficer.getProgramsList().size();
                            if(programLength > 0){

                                System.out.println("Enter Program ID");
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    System.out.println(program.getProgramID() + " : -> " + program.getProgramDegreeType() + " -> " + program.getProgramName());
                                }
                                int pId = getIntegerInput();

                                Programs modifyProgram = null;
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    if(program.getProgramID() == pId){
                                        modifyProgram = program;
                                        break;
                                    }
                                }

                                if(modifyProgram != null){
                                    System.out.println("Enter Course ID");
                                    for (CourseOfficer courses :
                                            modifyProgram.getProgramCoursesList()) {
                                        System.out.println(courses.getCourseID() + " : -> " + courses.getCourseName() + " ");
                                    }
                                    int cId = getIntegerInput();

                                    CourseOfficer modifyCourse = null;
                                    for (CourseOfficer courses :
                                            modifyProgram.getProgramCoursesList()) {
                                        if(courses.getCourseID() == cId){
                                            modifyCourse = courses;
                                            break;
                                        }
                                    }

                                    if(userInput2 == 6 && modifyCourse != null){
                                        //update course
                                        System.out.print("\nWant to change Course Name? y/n: ");
                                        char check = getCheckInput();
                                        if(check == 'y'){
                                            System.out.println("Enter new Course Name");
                                            String newName = getStringInput();
                                            modifyCourse.setCourseName(newName);
                                        }
                                        System.out.print("\nWant to change Course Credit Hours? y/n: ");
                                        check = getCheckInput();
                                        if(check == 'y'){
                                            System.out.println("Enter new Course Credit Hours");
                                            int newCredit = getIntegerInput();
                                            modifyCourse.setCourseCreditHours(newCredit);
                                        }
                                        System.out.print("\nWant to delete Course from entered Program? y/n: ");
                                        check = getCheckInput();
                                        if(check == 'y'){
                                            modifyProgram.getProgramCoursesList().remove(modifyCourse);
                                            modifyCourse.getCourseProgramList().remove(modifyProgram);
                                        }

                                    }else if(userInput2 == 5 && modifyCourse != null){
                                        //delete course
                                        Globals.coursesList.remove((Courses)modifyCourse);

                                        for (Programs program :
                                                modifyCourse.getCourseProgramList()) {
                                            program.getProgramCoursesList().remove(modifyCourse);
                                        }

                                        for (CLO clo :
                                                modifyCourse.getCourseCloList()) {
                                            clo.getCloCoursesList().remove((Courses)modifyCourse);
                                            if(clo.getCloCoursesList().size() == 0){
                                                Globals.cloList.remove(clo);
                                                for (PLO plo:
                                                     clo.getCloPLOList()) {
                                                    plo.getPloCLOList().remove(clo);
                                                }
                                                for (Topics topics :
                                                        clo.getCloTopicsList()) {
                                                    topics.getTopicCLOList().remove(clo);
                                                    if(topics.getTopicCLOList().size() == 0){
                                                        Globals.topicsList.remove(topics);
                                                    }
                                                }
                                            }
                                        }

///////////////////////////////////////////////////////////////////////////update teacher list while deleting of course
//                                        for (Teachers teacher:
//                                             ((Courses) modifyCourse).getCourseTeacherList()) {
//
//                                        }


                                    }else{
                                        System.out.println("!! Course not found !!");
                                    }

                                }else{
                                    System.out.println("!! Program not found !!");
                                }
                            }
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

                        } else if (userInput2 == 7) {
                            //list of all courses

                        } else if (userInput2 == 8) {
                            //list of all programs
                        }
                        else if(userInput2 == 9){
                            //add plo
                            int programLength = academicOfficer.getProgramsList().size();
                            if(programLength > 0){

                                System.out.println("Program ID");
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    System.out.println(program.getProgramID() + " : -> " + program.getProgramDegreeType() + " -> " + program.getProgramName());
                                }
                                int pId = getIntegerInput();

                                System.out.println("Enter PLO Description");
                                String description = getStringInput();

                                PLO newPlo = null;
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    if (program.getProgramID() == pId) {
                                        int ploId = Globals.ploList.size();
                                        ploId++;
                                        newPlo = new PLO(ploId,program, description);
                                        program.addPlo(newPlo);
                                        Globals.ploList.add(newPlo);
                                        break;
                                    }
                                }

                                if (newPlo != null) {
                                    academicOfficer.updateProgramFile();
                                    System.out.println("PLO Saved Successfully");
                                } else {
                                    System.out.println("Invalid PLO Program Id");
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
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
                                int pId = getIntegerInput();
                                Programs nProgram = null;
                                for (Programs program :
                                        academicOfficer.getProgramsList()) {
                                    if (program.getProgramID() == pId) {
                                        nProgram = program;
                                        break;
                                    }
                                }
                                //enter in course and plo
                                if(nProgram != null)
                                {
                                    int ploTotal = 0, courseTotal = 0;          //how many course and plo exists
                                    System.out.println("\n");
                                    courseTotal = nProgram.getProgramCoursesList().size();
                                    ploTotal = nProgram.getProgramPloList().size();

                                    if(courseTotal > 0 && ploTotal > 0){
                                        //display courses and get id from user
                                        System.out.print("Enter Course Id from given list: ");
                                        for (CourseOfficer course :
                                                nProgram.getProgramCoursesList()) {
                                            System.out.println(course.getCourseID() + ": -> " + course.getCourseName() + " ");
                                        }
                                        int courseID = getIntegerInput();
                                        //display plo's and get id from user
                                        System.out.print("Enter PLO Id from given list: ");
                                        for (PLO plo :
                                                nProgram.getProgramPloList()) {
                                            System.out.println(plo.getId() + ": -> " + plo.getDescription() + " ");
                                        }
                                        int ploID = getIntegerInput();

                                        //get course
                                        CourseOfficer nCourse= null;
                                        for (CourseOfficer course :
                                                nProgram.getProgramCoursesList()) {
                                            if(course.getCourseID() == courseID){
                                                nCourse = course;
                                                break;
                                            }
                                        }
                                        //get plo
                                        PLO nPlo = null;
                                        for (PLO plo:
                                             nProgram.getProgramPloList()) {
                                            if(plo.getId() == ploID){
                                                nPlo = plo;
                                                break;
                                            }
                                        }

                                        if(userInput3 == 1 && nCourse != null && nPlo != null){
                                        //add clo
                                        System.out.println("\nEnter clo description");
                                        String cloDescription = getStringInput();

                                        int cloId = Globals.cloList.size();
                                        cloId++;
                                        CLO newClo = new CLO(cloId, cloDescription);

                                        //update links
                                        newClo.addCloCoursesList((Courses) nCourse);
                                        newClo.addPLO(nPlo);
                                        nCourse.addCLO(newClo);
                                        nPlo.addCLO(newClo);
                                        Globals.cloList.add(newClo);
                                        //update file
//                                        newClo.saveInFile(true);
//                                        academicOfficer.updateCourseFile();

                                        }else if(userInput3 == 2 && nCourse != null && nPlo != null){
                                            int cloLength = Globals.cloList.size();
                                            if(cloLength > 0){
                                            System.out.print("\nEnter CLO ID from the list:  ");
                                                for (CLO clo :
                                                        Globals.cloList) {
                                                    System.out.print(clo.getId() + " , ");
                                                }
                                                int cin = getChoiceInput(1,cloLength);
                                                for (CLO clo :
                                                        Globals.cloList) {
                                                    if(cin == clo.getId()){
                                                        //update links
                                                        clo.addCloCoursesList((Courses) nCourse);
                                                        clo.addPLO(nPlo);
                                                        nCourse.addCLO(clo);
                                                        nPlo.addCLO(clo);
                                                        //update files
//                                                        academicOfficer.updateCourseFile();
//                                                        academicOfficer.updateCloFile();
                                                    }
                                                }

                                            }else{
                                                System.out.println("!! ClO does not exist !!");
                                            }
                                        }

                                        System.out.println("CLO Saved Successfully");

                                    }else{
                                        System.out.println("!! Course/PLO does not exist !!");
                                    }
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
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

        Globals.saveProgram();
        Globals.saveCourse();
        Globals.savePLO();
        Globals.saveCLO();
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

    public static char getCheckInput(){
        Scanner myInput = new Scanner(System.in);
        char check = myInput.nextLine().toLowerCase().charAt(0);
        while(check != 'y' && check != 'n'){
            System.out.println("Wrong input . Enter Again");
            check = myInput.nextLine().toLowerCase().charAt(0);
        }
        return check;
    }

    public static void loadFileData(AcademicOfficer officer){
        try {
            List<Programs> programsList = new ArrayList<>();
            List<String> programPloList = new ArrayList<>();
            List<String> programCoursesList = new ArrayList<>();

            List<Courses> coursesList = new ArrayList<>();
            List<String> courseProgramsList = new ArrayList<>();
            List<String> courseTeachersList = new ArrayList<>();
            List<String> courseCloList = new ArrayList<>();

            List<PLO> ploList = new ArrayList<>();
            List<String> ploCloList = new ArrayList<>();

            List<CLO> cloList = new ArrayList<>();
            List<String> cloCourseList = new ArrayList<>();
            List<String> cloPLOList = new ArrayList<>();
            List<String> cloTopicList = new ArrayList<>();

            List<Topics> topicsList = new ArrayList<>();
            List<String> topicCloList = new ArrayList<>();

            //teacher
            List<Teachers> teachersList = new ArrayList<>();

            //Read Program data
            File programKeyFile = new File(Globals.programsPath);
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
            File courseKeyFile = new File(Globals.coursesPath);
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
            File ploKeyFile = new File(Globals.ploPath);
            if(ploKeyFile.exists()){
                Scanner myPloReader = new Scanner(ploKeyFile);
                while(myPloReader.hasNextLine()){
                    int ploId = Integer.parseInt(myPloReader.nextLine());
                    String ploDescription = myPloReader.nextLine();
                    int ploProgramId = Integer.parseInt(myPloReader.nextLine());
                    String pCLOList = myPloReader.nextLine();

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
                    ploCloList.add(pCLOList);
                }
            }

            //Read CLO data
            File cloKeyFile = new File(Globals.closPath);
            if(cloKeyFile.exists()){
                Scanner myCloReader = new Scanner(cloKeyFile);
                while(myCloReader.hasNextLine()){
                    int cloId = Integer.parseInt(myCloReader.nextLine());
                    String cloDescription = myCloReader.nextLine();
                    String cloCList = myCloReader.nextLine();
                    String cloPList = myCloReader.nextLine();
                    String cloTList = myCloReader.nextLine();

                    CLO newClo = new CLO(cloId, cloDescription);

                    //add in list
                    cloList.add(newClo);
                    cloCourseList.add(cloCList);
                    cloPLOList.add(cloPList);
                    cloTopicList.add(cloTList);
                }
            }

            //Read Topic data
            File topicKeyFile = new File(Globals.topicPath);
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
                int id = program.getProgramID();        //get program id from array
                String pPlo = programPloList.get(id-1);     //get plo list
                String pCourse = programCoursesList.get(id-1);  //get course list

                //setup of plo's
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

                //setup of courses
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

            for (PLO plo :
                    ploList) {
             int id = plo.getId();
             String pCLO = ploCloList.get(id-1);
                //setup of clo
                if(!Objects.equals(pCLO, "0")) {
                    String[] splitArray1 = pCLO.split(",");
                    List<Integer> arrayCourseList = new ArrayList<>();
                    for (String s : splitArray1) {
                        arrayCourseList.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            arrayCourseList) {
                        CLO nClo = null;
                        for (CLO clo :
                                cloList) {
                            if (clo.getId() == i) {
                                nClo = clo;
                                break;
                            }
                        }
                        plo.addCLO(nClo);
                    }
                }
            }

            for (CLO clo :
                    cloList) {
                int id = clo.getId();
                String cCourse = cloCourseList.get(id-1);
                String cPLO = cloPLOList.get(id-1);
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

                if(!Objects.equals(cPLO, "0")) {
                    String[] splitArray1 = cPLO.split(",");
                    List<Integer> array = new ArrayList<>();
                    for (String s : splitArray1) {
                        array.add(Integer.parseInt(s));
                    }

                    for (Integer i :
                            array) {
                        PLO nPlo = null;
                        for (PLO plo :
                                ploList) {
                            if (plo.getId() == i) {
                                nPlo = plo;
                                break;
                            }
                        }
                        clo.addPLO(nPlo);
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

            Globals.programsList = programsList;
            Globals.coursesList = coursesList;
            Globals.ploList = ploList;
            Globals.cloList = cloList;
            Globals.topicsList = topicsList;

        }
        catch (Exception e){
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }


}