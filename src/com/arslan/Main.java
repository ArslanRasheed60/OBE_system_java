package com.arslan;

import java.io.File;
import java.util.*;
import java.util.List;

import static com.arslan.Globals.*;

public class Main {
    public static void main(String[] args) {

        //Administrator starts here
        AcademicOfficer academicOfficer = null;

        try {
            //Read administrator credentials
            File keyfile = new File(Globals.academicOfficersPath);
            Scanner myReader = new Scanner(keyfile);
            String fullName = "", phoneNumber = "", address = "", username = "", password = "";
            while(myReader.hasNextLine()) {

                fullName = myReader.nextLine();
                phoneNumber = myReader.nextLine();
                address = myReader.nextLine();
                username = myReader.nextLine();
                password = myReader.nextLine();
                System.out.println(fullName + phoneNumber + address);
            }
            myReader.close();
            academicOfficer = new AcademicOfficer(fullName, phoneNumber, address, username, password);

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
            int userInput = getChoiceInput(1, 3);                                               //get input from user

            if (userInput == 1) {
                try {
                    //************************ Login or administrator is implemented here*********************************
                    if (AcademicOfficer.isAcademicOfficer) {

                        System.out.print("Login Successfully as Administrator\n");
                        System.out.println("1: Add New Program");
                        System.out.println("2: Update Program");
                        System.out.println("3: Add New Course");
                        System.out.println("4: Delete Course");
                        System.out.println("5: Update Course");
                        System.out.println("6: Get List of all Courses");
                        System.out.println("7: Get List of all Programs");
                        System.out.println("8: Add PLO");
                        System.out.println("9: Add CLO");
                        System.out.println("10: Update CLO");
                        System.out.println("11: Delete CLO");
                        System.out.println("12: Add Topic");
                        int userInput2 = getChoiceInput(1, 12);                                 //get 2nd input from user

                        if (userInput2 == 1) {                                                  //Add Program
                            System.out.println("\tEnter Program Name");
                            String programName = getStringInput();
                            System.out.println("\tEnter Program Degree Type");
                            String programDegreeType = getStringInput();

                            int programID = academicOfficer.getProgramsList().size();
                            if (programID > 0) {                                            //sort and get program id
                                academicOfficer.getProgramsList().sort(Comparator.comparingLong(Programs::getProgramID));
                                programID = academicOfficer.getProgramsList().get(programID - 1).getProgramID();
                            }
                            programID++;
                            Programs newProgram = new Programs(programID, programDegreeType, programName);

                            if(!(isDuplicateProgram(newProgram))){
                                academicOfficer.addProgram(newProgram);                             //update lists
                                Globals.programsList.add(newProgram);
                            }else{
                                System.out.println("Program already exists");
                            }
                        }
                        else if (userInput2 == 2) {                                             //   update program
                            if(academicOfficer.getProgramsList().size() > 0) {
                                System.out.println("\tEnter Program Id for ID from given list");
                                academicOfficer.printAllPrograms();
                                int programID = getIntegerInput();
                                Programs manageProgram = academicOfficer.getProgramById(programID);   // get program by id from officer programs

                                if (manageProgram != null) {                                        //if id found then update it
                                    System.out.println("\tEnter --New-- Program Name");
                                    String programName = getStringInput();
                                    System.out.println("\tEnter --New-- Program Degree Type");
                                    String programDegreeType = getStringInput();
                                    manageProgram.setProgramName(programName);                      //update by setters
                                    manageProgram.setProgramDegreeType(programDegreeType);

//                                academicOfficer.updateProgramFile();
                                    System.out.println("Program Updated Successfully");
                                } else {
                                    System.out.println("Invalid Program ID");
                                }
                            }else{
                                System.out.println("No Any Program exists");
                            }
                        }
                        else if (userInput2 == 3) {
                            System.out.println("1: Add New Course to Program Course");
                            System.out.println("2: Add Another Course to current Program");
                            System.out.print("Enter Choice");
                            int userInput3 = getChoiceInput(1,2);

                            //add new course to the program
                            int total = academicOfficer.getProgramsList().size();
                            if(total > 0){                                                      //Entering in program
                                System.out.println("\nEnter Program Id from given list: ");
                                academicOfficer.printAllPrograms();                             //print program lists
                                int pId = getIntegerInput();
                                Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                if(userInput3 == 1 && nProgram != null){                        //add new course
                                    System.out.println("\n\tEnter course Name");
                                    String courseName = getStringInput();
                                    System.out.println("\tEnter creditHours");
                                    int courseCreditHours = getIntegerInput();
                                                                                                //get course id
                                    int courseId = coursesList.size();
                                    if(courseId > 0){
                                        coursesList.sort(Comparator.comparingLong(Courses::getCourseID));
                                        courseId = coursesList.get(coursesList.size()-1).getCourseID();
                                    }
                                    courseId++;
                                    Courses newCourse = new Courses(courseId,courseName, courseCreditHours);

                                    nProgram.addCourse(newCourse);                              //update lists
                                    newCourse.addProgram(nProgram);
                                    Globals.coursesList.add(newCourse);
                                    System.out.println("Course Saved Successfully");
                                }
                                else if (userInput3 == 2 && nProgram!=null){
                                    int courseLength = Globals.coursesList.size();
                                    if(courseLength > 0){
                                        System.out.println("\n\tEnter Course Id from given list:  ");
                                        for (CourseOfficer findCourse :                         //print all courses from OBE system
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
                                        if(nCourse != null){                                    //update lists
                                            if(!(nProgram.getProgramCoursesList().contains(nCourse))){
                                                nCourse.addProgram(nProgram);
                                                nProgram.addCourse(nCourse);
                                                System.out.println("Course Linked Successfully");
                                            }else{
                                                System.out.println("Course Already exists in program");
                                            }

                                        }else{
                                            System.out.println("!! Invalid Course ID !!");
                                        }
                                    }else{
                                        System.out.println("!! Course does not exist !!");
                                    }
                                }
                                else{
                                    System.out.println("!! Invalid input !!");
                                }
                            }
                            else{
                                System.out.println("!! Program does not exist !!");
                            }
                        }
                         else if (userInput2 == 4 || userInput2 == 5) {                         //  delete/update course
                            int programLength = academicOfficer.getProgramsList().size();
                            if(programLength > 0){
                                System.out.println("Enter Program ID from given List");
                                academicOfficer.printAllPrograms();                             //print all programs
                                int pId = getIntegerInput();
                                Programs modifyProgram = academicOfficer.getProgramById(pId);   //get program by id from officer

                                if(modifyProgram != null){
                                    System.out.println("Enter Course ID");
                                    modifyProgram.printAllCourses();                            //print all courses from current program
                                    int cId = getIntegerInput();
                                    CourseOfficer modifyCourse = modifyProgram.getCourseById(cId); //get course by id from program

                                    if(modifyCourse != null && userInput2 == 5){                //update course
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
                                            UpdateLinksOfDeletedCourse((Courses) modifyCourse);
                                        }

                                    }else if(userInput2 == 4 && modifyCourse != null){          //delete course

                                        Globals.coursesList.remove((Courses)modifyCourse);      //delete from global
                                        for (Programs program :                                 //delete from programs
                                                modifyCourse.getCourseProgramList()) {
                                            program.getProgramCoursesList().remove(modifyCourse);
                                        }
                                        UpdateLinksOfDeletedCourse((Courses) modifyCourse);  //update CLO's accordingly
/*
-----------------------------------------update teacher list while deleting of course---------------------------------------------
                                        for (Teachers teacher:
                                             ((Courses) modifyCourse).getCourseTeacherList()) {

                                        }
*/

                                    }else{
                                        System.out.println("!! Course not found !!");
                                    }
                                }else{
                                    System.out.println("!! Program not found !!");
                                }
                            }
                        }
                         else if (userInput2 == 6) {                                            //list of all courses
                            int coursesLength = Globals.coursesList.size();
                            if(coursesLength > 0){
                                Globals.coursesList.sort(Comparator.comparingLong(Courses::getCourseID));
                                for (Courses course :
                                        Globals.coursesList) {
                                    System.out.println(course.getCourseID() + " : -> " + course.getCourseName() + " : -> " + course.getCourseCreditHours() + " ");
                                }
                            }else{
                                System.out.println("No courses Exist");
                            }

                        }
                         else if (userInput2 == 7) {                                          //list of all programs
                            int programLength = Globals.programsList.size();
                            if(programLength > 0){
                                Globals.programsList.sort(Comparator.comparingLong(Programs::getProgramID));
                                for (Programs programs :
                                        Globals.programsList) {
                                    System.out.println(programs.getProgramID() + " : -> " + programs.getProgramName() + " ");
                                }
                            }else{
                                System.out.println("No Program Exist");
                            }
                        }
                        else if(userInput2 == 8){                                               //add plo
                            int programLength = academicOfficer.getProgramsList().size();
                            if(programLength > 0){
                                System.out.println("Enter Program ID from given list");
                                academicOfficer.printAllPrograms();                             //print programs from officer
                                int pId = getIntegerInput();
                                Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                if(nProgram != null){
                                    System.out.println("Enter PLO Description");
                                    String description = getStringInput();

                                    int ploId = ploList.size();
                                    if(ploId > 0){
                                        ploList.sort(Comparator.comparingLong(PLO::getId));
                                        ploId = ploList.get(ploList.size()-1).getId();
                                    }
                                    ploId++;
                                    PLO newPlo = new PLO(ploId,nProgram, description);

                                    nProgram.addPlo(newPlo);                                     //update lists
                                    Globals.ploList.add(newPlo);
                                    System.out.println("PLO Saved Successfully");
                                }else{
                                    System.out.println("!! Invalid Program Id !!");
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
                            }
                        }
                        else if(userInput2 == 9){                                               //add new clo to course
                            System.out.println("1: Add New ClO to  Course");
                            System.out.println("2: Add Another CLO to current course");
                            System.out.print("Enter Choice");
                            int userInput3 = getChoiceInput(1,2);

                            int total = academicOfficer.getProgramsList().size();
                            if(total > 0) {                                                     //enter in program
                                System.out.println("\nEnter Program Id from given list: ");
                                academicOfficer.printAllPrograms();                             //print programs from officer
                                int pId = getIntegerInput();
                                Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                if(nProgram != null)                                            //enter in course and plo
                                {
                                    int ploTotal = 0, courseTotal = 0;                          //how many course and plo exists
                                    System.out.println("\n");
                                    courseTotal = nProgram.getProgramCoursesList().size();
                                    ploTotal = nProgram.getProgramPloList().size();

                                    if(courseTotal > 0 && ploTotal > 0){

                                        //display courses and get id from user
                                        System.out.print("Enter Course Id from given list: ");
                                        nProgram.printAllCourses();                             //print all courses from current program
                                        int courseID = getIntegerInput();
                                        CourseOfficer nCourse= nProgram.getCourseById(courseID);//get course by id

                                        //display plo's and get id from user
                                        System.out.print("Enter PLO Id from given list: ");
                                        nProgram.printAllPLOs();                                //print all PLO's from current program
                                        int ploID = getIntegerInput();
                                        PLO nPlo = nProgram.getPLObyId(ploID);                  //get plo by id

                                        if(userInput3 == 1 && nCourse != null && nPlo != null){     //add clo
                                        System.out.println("\nEnter clo description");
                                        String cloDescription = getStringInput();

                                        int cloId = Globals.cloList.size();                     //get clo list size
                                        if(cloId > 0){
                                            cloList.sort(Comparator.comparingLong(CLO::getId));
                                            cloId = cloList.get(cloList.size()-1).getId();
                                        }
                                        cloId++;
                                        CLO newClo = new CLO(cloId, cloDescription);

                                        newClo.addCloCoursesList((Courses) nCourse);            //update links
                                        newClo.addPLO(nPlo);
                                        nCourse.addCLO(newClo);
                                        nPlo.addCLO(newClo);
                                        Globals.cloList.add(newClo);
                                        System.out.println("CLO Saved Successfully");

                                        }
                                        else if(userInput3 == 2 && nCourse != null && nPlo != null){   //link clo
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
                                                    if(cin == clo.getId()){                             //update links
                                                        clo.addCloCoursesList((Courses) nCourse);
                                                        clo.addPLO(nPlo);
                                                        nCourse.addCLO(clo);
                                                        nPlo.addCLO(clo);
                                                    }
                                                }
                                            }else{
                                                System.out.println("!! ClO does not exist !!");
                                            }
                                        }
                                    }else{
                                        System.out.println("!! Course/PLO does not exist !!");
                                    }
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
                            }
                        }
                        else if(userInput2 == 10 || userInput2 == 11){                          //update/ delete clo

                            int programsLength = academicOfficer.getProgramsList().size();
                            //enter in program
                            if(programsLength > 0) {
                                System.out.println("\nEnter Program Id from given list: ");
                                academicOfficer.printAllPrograms();                             //printing all programs from officer
                                int pId = getIntegerInput();
                                Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id

                                //enter in course and plo
                                if(nProgram != null)
                                {
                                    int ploTotal = 0, courseTotal = 0;                          //how many course and plo exists
                                    courseTotal = nProgram.getProgramCoursesList().size();
                                    ploTotal = nProgram.getProgramPloList().size();

                                    if(courseTotal > 0 && ploTotal > 0){
                                        System.out.print("Enter Course Id from given list: ");
                                        nProgram.printAllCourses();                             //display courses
                                        int courseID = getIntegerInput();
                                        System.out.print("Enter PLO Id from given list: ");
                                        nProgram.printAllPLOs();                                //display plo's
                                        int ploID = getIntegerInput();
                                        Courses nCourse= (Courses) nProgram.getCourseById(courseID);    //get course by user id
                                        PLO nPlo = nProgram.getPLObyId(ploID);                      //get plo by user id

                                        if(nCourse != null && nPlo != null){
                                            if(nCourse.getCourseCloList().size() > 0){
                                                System.out.print("\n Enter CLO id that is common in both below lists: ");
                                                System.out.println("--------------List of CLOs in Course-------------");
                                                nCourse.printAllCLOs();
                                                System.out.println("--------------List of CLOs in PLOs  -------------");
                                                nPlo.printAllCLOs();
                                                int cloID = getIntegerInput();
                                                CLO updateCLO = nPlo.getCLOById(cloID);
                                                //get clo by id from plo list and verify in course list
                                                if(updateCLO != null && nCourse.getCourseCloList().contains(updateCLO)){

                                                    if(userInput2 == 10){                               //update clo
                                                        System.out.print("\nWant to change CLO Description? y/n: ");
                                                        char check = getCheckInput();
                                                        if(check == 'y'){
                                                            System.out.println("Enter new Description ");
                                                            String newValue = getStringInput();
                                                            updateCLO.setDescription(newValue);
                                                        }
                                                        System.out.print("\nWant to Remove from current Course? y/n: ");
                                                        check = getCheckInput();
                                                        if(check == 'y'){
                                                            nCourse.getCourseCloList().remove(updateCLO);
                                                            updateCLO.getCloCoursesList().remove(nCourse);
                                                            if(updateCLO.getCloCoursesList().size() == 0){      //delete clo if its course does not exist
                                                                UpdatePLOLinksOfDeletedCLO(updateCLO);
                                                            }
                                                        }
                                                        System.out.print("\nWant to Remove from current PLO? y/n: ");
                                                        check = getCheckInput();
                                                        if(check == 'y'){
                                                            nPlo.getPloCLOList().remove(updateCLO);
                                                            updateCLO.getCloPLOList().remove(nPlo);
                                                            if(updateCLO.getCloPLOList().size() == 0){               //delete clo if its plo does not exists
                                                                UpdateCourseLinksOfDeletedCLO(updateCLO);
                                                            }
                                                        }

                                                    }else {                         //delete clo
                                                        Globals.cloList.remove(updateCLO);
                                                        if(Globals.cloList.size() == 0){
                                                            Globals.deleteCLOFile();
                                                        }
                                                        UpdatePLOLinksOfDeletedCLO(updateCLO);
                                                        UpdateCourseLinksOfDeletedCLO(updateCLO);
                                                    }
                                                }else{
                                                    System.out.println("!! Invalid Input Id !!");
                                                }
                                            }else{
                                                System.out.println("Clo does not exist");
                                            }
                                        }else{
                                            System.out.println("!! Invalid Course/PLO ID !!");
                                        }

                                        System.out.println("CLO Updated Successfully");

                                    }else{
                                        System.out.println("!! Course/PLO does not exist !!");
                                    }
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
                            }

                        }
                        else if(userInput2 == 12){                                              // add topic

                            System.out.println("1: Add new Topic to CLO");
                            System.out.println("2: Add Another Topic to current CLO");
                            System.out.print("Enter Choice");
                            int userInput3 = getChoiceInput(1,2);

                            int total = academicOfficer.getProgramsList().size();
                            if(total > 0) {                                                     //enter in program
                                System.out.println("\nEnter Program Id from given list: ");
                                academicOfficer.printAllPrograms();                             //print programs from officer
                                int pId = getIntegerInput();
                                Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                if(nProgram != null)                                            //enter in course and plo
                                {
                                    int ploTotal = 0, courseTotal = 0;                          //how many course and plo exists
                                    System.out.println("\n");
                                    courseTotal = nProgram.getProgramCoursesList().size();
                                    ploTotal = nProgram.getProgramPloList().size();

                                    if(courseTotal > 0 && ploTotal > 0){                //display courses and get id from user
                                        System.out.print("Enter Course Id from given list: ");
                                        nProgram.printAllCourses();                             //print all courses from current program
                                        int courseID = getIntegerInput();
                                        CourseOfficer nCourse= nProgram.getCourseById(courseID);//get course by id

                                        if(nCourse != null){                            //display CLOs and get id from user
                                            System.out.print("Enter CLO Id from given list: ");
                                            nCourse.printAllCLOs();                             //print all CLOs from current course
                                            int cloID = getIntegerInput();
                                            CLO nCLO= nCourse.getCLOById(cloID);                //get course by id

                                            if(nCLO != null){
                                                if(userInput3 == 1){                            //Add new topic
                                                    System.out.println("Enter Topic Description: ");
                                                    String desc = getStringInput();

                                                    int tID = Globals.topicsList.size() + 1;
                                                    Topics newTopic = new Topics(tID,desc);

                                                    newTopic.addTopicClo(nCLO);                 //update links
                                                    nCLO.addCloTopicList(newTopic);
                                                    Globals.topicsList.add(newTopic);
                                                }else{
                                                    if(Globals.topicsList.size() > 0){
                                                        System.out.println("Enter Topic Id from given lists: ");
                                                        for (Topics topic :
                                                                Globals.topicsList) {
                                                            System.out.println(topic.getTopicID() + " : -> " + topic.getTopicDescription() + " ");
                                                        }
                                                        int tId = getIntegerInput();

                                                        Topics nTopic = null;
                                                        for (Topics topic :
                                                                Globals.topicsList) {
                                                            if(topic.getTopicID() == tId){
                                                                nTopic = topic;
                                                                break;
                                                            }
                                                        }
                                                        if(nTopic != null){                    //update links
                                                            nTopic.getTopicCLOList().add(nCLO);
                                                            nCLO.getCloTopicsList().add(nTopic);
                                                        }else{
                                                            System.out.println("Invalid topic id");
                                                        }
                                                    }else{
                                                        System.out.println("!! topic does not exist !!");
                                                    }
                                                }
                                                System.out.println("Updated Successfully");
                                            }else{
                                                System.out.println("!! CLO does not exist !!");
                                            }
                                        }
                                    }else{
                                        System.out.println("!! Course/PLO does not exist !!");
                                    }
                                }
                            }else{
                                System.out.println("!! Program does not exist !!");
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("An Error occurred :  " + e.getMessage());
                }
            }
            else if (userInput == 2) {
                //teacher
                System.out.println("Enter Username: ");
                String username = getStringInput();
                System.out.println("Enter Password: ");
                String password = getStringInput();

                Teachers teacher = Globals.verifyTeacher(username, password);

                if(teacher != null){
//                    System.out.println("-------------------- ( Welcome " + );
                    System.out.println("1: Add Evaluation");
                    System.out.println("2: Test CLO");
                    System.out.print("Enter choice:  ");
                    int userInput2 = getChoiceInput(1,2);

                    System.out.println("Select Course from which you want to get clo ");
                    teacher.printCourses();                                             //print teacher courses
                    int CourseID = getIntegerInput();                                   //get course id
                    Courses course = (Courses) teacher.getCourseById(CourseID);

                    if(course != null){
                        System.out.println("Select CLO from given list");
                        course.printAllCLOs();
                        int cloID = getIntegerInput();
                        CLO clo = course.getCLOById(cloID);
                        if(clo != null){

                            if(userInput2 == 1){                                        //add evaluation
                                System.out.println("Enter Statement for Question: ");
                                String statement = getStringInput();
                                //calling question constructor
                                Globals.questionsList.sort(Comparator.comparingLong(Questions::getId));
                                int qID = Globals.questionsList.get(Globals.questionsList.size()-1).getId();
                                qID++;
                                Questions questions = new Questions(qID,statement, clo);

                                System.out.println("1: Add in Quiz");
                                System.out.println("2: Add in Assignment");
                                System.out.println("3: Add in Exams");
                                int userInput3 = getChoiceInput(1,3);

                                if(userInput3 == 1){
                                    //teacher is making question here
                                    Globals.evaluationsList.sort(Comparator.comparingLong(Evaluations::getId));
                                    int evaluationId = evaluationsList.get((evaluationsList.size()-1)).getId();
                                    Evaluations evaluations = new Quizes(evaluationId);
                                    evaluations.addQuestions(questions);
                                    teacher.addEvaluation(evaluations);                     //add evaluations here
                                    evaluationsList.add(evaluations);
                                }else if(userInput3 == 2){

                                }else{

                                }
                            }else{
                                boolean testCLO = teacher.testCLO(clo);
                                if(testCLO == true){
                                    System.out.println("CLO has been tested");
                                }else{
                                    System.out.println("CLO Test is failed");
                                }
                            }
                        }else{
                            System.out.println("No any clo Exist. Contact Administrator");
                        }
                    }else{
                        System.out.println("You are not teaching any course. Contact Administrator");
                    }
                }else{
                    System.out.println("!! Invalid Username/Password !!");
                }
                System.out.println();
            } else if (userInput == 3) {
                //guest
            }
        }

        Globals.saveProgram();
        Globals.saveCourse();
        Globals.savePLO();
        Globals.saveCLO();
        Globals.saveTopic();
    }

    public static void UpdateTopicLinksOfDeletedCLO(CLO clo){
        for (Topics topics :                            //if topic got deleted, update clo accordingly
                clo.getCloTopicsList()) {
            topics.getTopicCLOList().remove(clo);
            if(topics.getTopicCLOList().size() == 0){
                Globals.topicsList.remove(topics);
                topics.getTopicCLOList().remove(clo);
            }
        }
    }

    public static void UpdateCourseLinksOfDeletedCLO(CLO clo){
        Globals.cloList.remove(clo);
        for (Courses course:                                   //if clo got deleted, update course accordingly
                clo.getCloCoursesList()) {
            course.getCourseCloList().remove(clo);
        }
        UpdateTopicLinksOfDeletedCLO(clo);
    }

    public static void UpdatePLOLinksOfDeletedCLO(CLO clo){
        Globals.cloList.remove(clo);
        for (PLO plo:                                   //if clo got deleted, update plo accordingly
                clo.getCloPLOList()) {
            plo.getPloCLOList().remove(clo);
        }
        UpdateTopicLinksOfDeletedCLO(clo);
    }

    public static void UpdateLinksOfDeletedCourse(Courses modifyCourse){
        for (CLO clo :                                          //delete from clo
                modifyCourse.getCourseCloList()) {
            clo.getCloCoursesList().remove((Courses)modifyCourse);
            if(clo.getCloCoursesList().size() == 0){            //update clo accordingly
                UpdatePLOLinksOfDeletedCLO(clo);
            }
        }
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