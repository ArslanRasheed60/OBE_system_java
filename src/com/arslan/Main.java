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
                System.out.println("3: Sign Up as Teacher");
                System.out.println("4: Exit");
                System.out.print("Enter Choice: ");
                int userInput = getChoiceInput(1, 4);                                               //get input from user

                if (userInput == 1) {
                    try {
                        //************************ Login or administrator is implemented here*********************************

                        System.out.println("Enter username ");
                        String username = getStringInput();
                        System.out.println("Enter password");
                        String password = getStringInput();

                        boolean verifyAdmin = verifyAdministrator(username, password);

                        if (AcademicOfficer.isAcademicOfficer && verifyAdmin) {
                            int userInput2 = 0;

                                System.out.print("Login Successfully as Administrator\n");
                            do {
                                System.out.println("****************************");
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
                                System.out.println("13: Allocate Course to Teacher");
                                System.out.println("14: Exit");
                                System.out.print("Enter Choice: ");
                                userInput2 = getChoiceInput(1, 14);                                 //get 2nd input from user

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

                                    if (!(isDuplicateProgram(newProgram))) {
                                        academicOfficer.addProgram(newProgram);                             //update lists
                                        Globals.programsList.add(newProgram);
                                        System.out.println("Program added Successfully");
                                    } else {
                                        System.out.println("Program already exists");
                                    }
                                } else if (userInput2 == 2) {                                             //   update program
                                    if (academicOfficer.getProgramsList().size() > 0) {
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
                                    } else {
                                        System.out.println("No Any Program exists");
                                    }
                                }
                                else if (userInput2 == 3) {
                                    System.out.println("1: Add New Course to Program Course");
                                    System.out.println("2: Add Another Course to current Program");
                                    System.out.print("Enter Choice");
                                    int userInput3 = getChoiceInput(1, 2);

                                    //add new course to the program
                                    int total = academicOfficer.getProgramsList().size();
                                    if (total > 0) {                                                      //Entering in program
                                        System.out.println("\nEnter Program Id from given list: ");
                                        academicOfficer.printAllPrograms();                             //print program lists
                                        int pId = getIntegerInput();
                                        Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                        if (userInput3 == 1 && nProgram != null) {                        //add new course
                                            System.out.println("\n\tEnter course Name");
                                            String courseName = getStringInput();
                                            System.out.println("\tEnter creditHours");
                                            int courseCreditHours = getIntegerInput();
                                            //get course id
                                            int courseId = coursesList.size();
                                            if (courseId > 0) {
                                                coursesList.sort(Comparator.comparingLong(Courses::getCourseID));
                                                courseId = coursesList.get(coursesList.size() - 1).getCourseID();
                                            }
                                            courseId++;
                                            Courses newCourse = new Courses(courseId, courseName, courseCreditHours);

                                            if (!(isDuplicateCourse(newCourse, coursesList))) {
                                                nProgram.addCourse(newCourse);                              //update lists
                                                newCourse.addProgram(nProgram);
                                                Globals.coursesList.add(newCourse);
                                                System.out.println("Course Saved Successfully");
                                            } else {
                                                System.out.println("Course Already exists");
                                            }
                                        } else if (userInput3 == 2 && nProgram != null) {
                                            int courseLength = Globals.coursesList.size();
                                            if (courseLength > 0) {
                                                System.out.println("\n\tEnter Course Id from given list:  ");
                                                for (CourseOfficer findCourse :                         //print all courses from OBE system
                                                        Globals.coursesList) {
                                                    System.out.println(findCourse.getCourseID() + ": -> " + findCourse.getCourseName() + " ");
                                                }
                                                int cId = getIntegerInput();
                                                CourseOfficer nCourse = null;
                                                for (CourseOfficer findCourse :
                                                        Globals.coursesList) {
                                                    if (findCourse.getCourseID() == cId) {
                                                        nCourse = findCourse;
                                                    }
                                                }
                                                if (nCourse != null) {                                    //update lists
                                                    if (!(nProgram.getProgramCoursesList().contains(nCourse))) {
                                                        nCourse.addProgram(nProgram);
                                                        nProgram.addCourse(nCourse);
                                                        System.out.println("Course Linked Successfully");
                                                    } else {
                                                        System.out.println("Course Already exists in program");
                                                    }

                                                } else {
                                                    System.out.println("!! Invalid Course ID !!");
                                                }
                                            } else {
                                                System.out.println("!! Course does not exist !!");
                                            }
                                        } else {
                                            System.out.println("!! Invalid input !!");
                                        }
                                    } else {
                                        System.out.println("!! Program does not exist !!");
                                    }
                                }
                                else if (userInput2 == 4 || userInput2 == 5) {                         //  delete/update course
                                    int programLength = academicOfficer.getProgramsList().size();
                                    if (programLength > 0) {
                                        System.out.println("Enter Program ID from given List");
                                        academicOfficer.printAllPrograms();                             //print all programs
                                        int pId = getIntegerInput();
                                        Programs modifyProgram = academicOfficer.getProgramById(pId);   //get program by id from officer

                                        if (modifyProgram != null) {
                                            System.out.println("Enter Course ID");
                                            modifyProgram.printAllCourses();                            //print all courses from current program
                                            int cId = getIntegerInput();
                                            CourseOfficer modifyCourse = modifyProgram.getCourseById(cId); //get course by id from program

                                            if (modifyCourse != null && userInput2 == 5) {                //update course
                                                System.out.print("\nWant to change Course Name? y/n: ");
                                                char check = getCheckInput();
                                                if (check == 'y') {
                                                    System.out.println("Enter new Course Name");
                                                    String newName = getStringInput();
                                                    modifyCourse.setCourseName(newName);
                                                }
                                                System.out.print("\nWant to change Course Credit Hours? y/n: ");
                                                check = getCheckInput();
                                                if (check == 'y') {
                                                    System.out.println("Enter new Course Credit Hours");
                                                    int newCredit = getIntegerInput();
                                                    modifyCourse.setCourseCreditHours(newCredit);
                                                }
                                                System.out.print("\nWant to delete Course from entered Program? y/n: ");
                                                check = getCheckInput();
                                                if (check == 'y') {                                             //update links
                                                    modifyProgram.getProgramCoursesList().remove(modifyCourse);
                                                    modifyCourse.getCourseProgramList().remove(modifyProgram);
                                                    UpdateLinksOfDeletedCourse((Courses) modifyCourse);
                                                    System.out.println("Updated Successfully");
                                                }

                                            } else if (userInput2 == 4 && modifyCourse != null) {          //delete course

                                                Globals.coursesList.remove((Courses) modifyCourse);      //delete from global
                                                for (Programs program :                                 //delete from programs
                                                        modifyCourse.getCourseProgramList()) {
                                                    program.getProgramCoursesList().remove(modifyCourse);
                                                }
                                                UpdateLinksOfDeletedCourse((Courses) modifyCourse);  //update CLO's accordingly

                                                for (Teachers teacher:
                                                     ((Courses) modifyCourse).getCourseTeacherList()) {
                                                    teacher.getCoursesList().remove(modifyCourse);
                                                }

                                                System.out.println("Course Deleted Successfully");

                                            } else {
                                                System.out.println("!! Course not found !!");
                                            }
                                        } else {
                                            System.out.println("!! Program not found !!");
                                        }
                                    }
                                }
                                else if (userInput2 == 6) {                                            //list of all courses
                                    int coursesLength = Globals.coursesList.size();
                                    if (coursesLength > 0) {
                                        Globals.coursesList.sort(Comparator.comparingLong(Courses::getCourseID));
                                        for (Courses course :
                                                Globals.coursesList) {
                                            System.out.println(course.getCourseID() + " : -> " + course.getCourseName() + " : -> " + course.getCourseCreditHours() + " ");
                                        }
                                    } else {
                                        System.out.println("No courses Exist");
                                    }

                                }
                                else if (userInput2 == 7) {                                          //list of all programs
                                    int programLength = Globals.programsList.size();
                                    if (programLength > 0) {
                                        Globals.programsList.sort(Comparator.comparingLong(Programs::getProgramID));
                                        for (Programs programs :
                                                Globals.programsList) {
                                            System.out.println(programs.getProgramID() + " : -> " + programs.getProgramName() + " ");
                                        }
                                    } else {
                                        System.out.println("No Program Exist");
                                    }
                                }
                                else if (userInput2 == 8) {                                               //add plo
                                    int programLength = academicOfficer.getProgramsList().size();
                                    if (programLength > 0) {
                                        System.out.println("Enter Program ID from given list");
                                        academicOfficer.printAllPrograms();                             //print programs from officer
                                        int pId = getIntegerInput();
                                        Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                        if (nProgram != null) {
                                            System.out.println("Enter PLO Description");
                                            String description = getStringInput();

                                            int ploId = ploList.size();
                                            if (ploId > 0) {
                                                ploList.sort(Comparator.comparingLong(PLO::getId));
                                                ploId = ploList.get(ploList.size() - 1).getId();
                                            }
                                            ploId++;
                                            PLO newPlo = new PLO(ploId, nProgram, description);

                                            if (!(isDuplicatePLO(newPlo))) {
                                                nProgram.addPlo(newPlo);                                     //update lists
                                                Globals.ploList.add(newPlo);
                                                System.out.println("PLO Saved Successfully");
                                            } else {
                                                System.out.println("Plo already exists");
                                            }
                                        } else {
                                            System.out.println("!! Invalid Program Id !!");
                                        }
                                    } else {
                                        System.out.println("!! Program does not exist !!");
                                    }
                                }
                                else if (userInput2 == 9) {                                               //add new clo to course
                                    System.out.println("1: Add New ClO to  Course");
                                    System.out.println("2: Add Another CLO to current course");
                                    System.out.print("Enter Choice");
                                    int userInput3 = getChoiceInput(1, 2);

                                    int total = academicOfficer.getProgramsList().size();
                                    if (total > 0) {                                                     //enter in program
                                        System.out.println("\nEnter Program Id from given list: ");
                                        academicOfficer.printAllPrograms();                             //print programs from officer
                                        int pId = getIntegerInput();
                                        Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                        if (nProgram != null)                                            //enter in course and plo
                                        {
                                            int ploTotal = 0, courseTotal = 0;                          //how many course and plo exists
                                            System.out.println("\n");
                                            courseTotal = nProgram.getProgramCoursesList().size();
                                            ploTotal = nProgram.getProgramPloList().size();

                                            if (courseTotal > 0 && ploTotal > 0) {

                                                //display courses and get id from user
                                                System.out.print("Enter Course Id from given list: ");
                                                nProgram.printAllCourses();                             //print all courses from current program
                                                int courseID = getIntegerInput();
                                                CourseOfficer nCourse = nProgram.getCourseById(courseID);//get course by id

                                                //display plo's and get id from user
                                                System.out.print("Enter PLO Id from given list: ");
                                                nProgram.printAllPLOs();                                //print all PLO's from current program
                                                int ploID = getIntegerInput();
                                                PLO nPlo = nProgram.getPLObyId(ploID);                  //get plo by id

                                                if (userInput3 == 1 && nCourse != null && nPlo != null) {     //add clo
                                                    System.out.println("\nEnter clo description");
                                                    String cloDescription = getStringInput();

                                                    int cloId = Globals.cloList.size();                     //get clo list size
                                                    if (cloId > 0) {
                                                        cloList.sort(Comparator.comparingLong(CLO::getId));
                                                        cloId = cloList.get(cloList.size() - 1).getId();
                                                    }
                                                    cloId++;
                                                    CLO newClo = new CLO(cloId, cloDescription);

                                                    if (!(isDuplicateCLO(newClo, cloList))) {
                                                        newClo.addCloCoursesList((Courses) nCourse);            //update links
                                                        newClo.addPLO(nPlo);
                                                        nCourse.addCLO(newClo);
                                                        nPlo.addCLO(newClo);
                                                        Globals.cloList.add(newClo);
                                                        System.out.println("CLO Saved Successfully");
                                                    } else {
                                                        System.out.println("CLO already exists");
                                                    }
                                                } else if (userInput3 == 2 && nCourse != null && nPlo != null)
                                                {   //link clo
                                                    int cloLength = Globals.cloList.size();
                                                    if (cloLength > 0) {
                                                        System.out.print("\nEnter CLO ID from the list:  ");
                                                        for (CLO clo :
                                                                Globals.cloList) {
                                                            System.out.print(clo.getId() + " , ");
                                                        }
                                                        int cin = getChoiceInput(1, cloLength);
                                                        for (CLO clo :
                                                                Globals.cloList) {
                                                            if (cin == clo.getId()) {                             //update links

                                                                if (!(isDuplicateCLO(clo, nCourse.getCourseCloList())) && !(isDuplicateCLO(clo, nPlo.getPloCLOList()))) {
                                                                    clo.addCloCoursesList((Courses) nCourse);
                                                                    clo.addPLO(nPlo);
                                                                    nCourse.addCLO(clo);
                                                                    nPlo.addCLO(clo);
                                                                } else {
                                                                    System.out.println("CLO already exists");
                                                                }
                                                                break;
                                                            }
                                                        }
                                                    } else {
                                                        System.out.println("!! ClO does not exist !!");
                                                    }
                                                }else{
                                                    System.out.println("!! Invalid Course/PLO ID !!");
                                                }
                                            } else {
                                                System.out.println("!! Course/PLO does not exist !!");
                                            }
                                        }
                                    } else {
                                        System.out.println("!! Program does not exist !!");
                                    }
                                }
                                else if (userInput2 == 10 || userInput2 == 11) {                          //update/ delete clo

                                    int programsLength = academicOfficer.getProgramsList().size();
                                    //enter in program
                                    if (programsLength > 0) {
                                        System.out.println("\nEnter Program Id from given list: ");
                                        academicOfficer.printAllPrograms();                             //printing all programs from officer
                                        int pId = getIntegerInput();
                                        Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id

                                        //enter in course and plo
                                        if (nProgram != null) {
                                            int ploTotal = 0, courseTotal = 0;                          //how many course and plo exists
                                            courseTotal = nProgram.getProgramCoursesList().size();
                                            ploTotal = nProgram.getProgramPloList().size();

                                            if (courseTotal > 0 && ploTotal > 0) {
                                                System.out.print("Enter Course Id from given list: ");
                                                nProgram.printAllCourses();                             //display courses
                                                int courseID = getIntegerInput();
                                                System.out.print("Enter PLO Id from given list: ");
                                                nProgram.printAllPLOs();                                //display plo's
                                                int ploID = getIntegerInput();
                                                Courses nCourse = (Courses) nProgram.getCourseById(courseID);    //get course by user id
                                                PLO nPlo = nProgram.getPLObyId(ploID);                      //get plo by user id

                                                if (nCourse != null && nPlo != null) {
                                                    if (nCourse.getCourseCloList().size() > 0) {
                                                        System.out.print("\n Enter CLO id that is common in both below lists: ");
                                                        System.out.println("--------------List of CLOs in Course-------------");
                                                        nCourse.printAllCLOs();
                                                        System.out.println("--------------List of CLOs in PLOs  -------------");
                                                        nPlo.printAllCLOs();
                                                        int cloID = getIntegerInput();
                                                        CLO updateCLO = nPlo.getCLOById(cloID);
                                                        //get clo by id from plo list and verify in course list
                                                        if (updateCLO != null && nCourse.getCourseCloList().contains(updateCLO)) {

                                                            if (userInput2 == 10) {                               //update clo
                                                                System.out.print("\nWant to change CLO Description? y/n: ");
                                                                char check = getCheckInput();
                                                                if (check == 'y') {
                                                                    System.out.println("Enter new Description ");
                                                                    String newValue = getStringInput();
                                                                    updateCLO.setDescription(newValue);
                                                                }
                                                                System.out.print("\nWant to Remove from current Course? y/n: ");
                                                                check = getCheckInput();
                                                                if (check == 'y') {
                                                                    nCourse.getCourseCloList().remove(updateCLO);
                                                                    updateCLO.getCloCoursesList().remove(nCourse);
                                                                    if (updateCLO.getCloCoursesList().size() == 0) {      //delete clo if its course does not exist
                                                                        UpdatePLOLinksOfDeletedCLO(updateCLO);
                                                                    }
                                                                }
                                                                System.out.print("\nWant to Remove from current PLO? y/n: ");
                                                                check = getCheckInput();
                                                                if (check == 'y') {
                                                                    nPlo.getPloCLOList().remove(updateCLO);
                                                                    updateCLO.getCloPLOList().remove(nPlo);
                                                                    if (updateCLO.getCloPLOList().size() == 0) {               //delete clo if its plo does not exists
                                                                        UpdateCourseLinksOfDeletedCLO(updateCLO);
                                                                    }
                                                                }

                                                            } else {                         //delete clo
                                                                Globals.cloList.remove(updateCLO);
                                                                UpdatePLOLinksOfDeletedCLO(updateCLO);
                                                                UpdateCourseLinksOfDeletedCLO(updateCLO);
                                                            }
                                                        } else {
                                                            System.out.println("!! Invalid Input Id !!");
                                                        }
                                                    } else {
                                                        System.out.println("Clo does not exist");
                                                    }
                                                } else {
                                                    System.out.println("!! Invalid Course/PLO ID !!");
                                                }

                                                System.out.println("CLO Updated Successfully");

                                            } else {
                                                System.out.println("!! Course/PLO does not exist !!");
                                            }
                                        }
                                    } else {
                                        System.out.println("!! Program does not exist !!");
                                    }

                                }
                                else if (userInput2 == 12) {                                              // add topic

                                    System.out.println("1: Add new Topic to CLO");
                                    System.out.println("2: Add Another Topic to current CLO");
                                    System.out.print("Enter Choice");
                                    int userInput3 = getChoiceInput(1, 2);

                                    int total = academicOfficer.getProgramsList().size();
                                    if (total > 0) {                                                     //enter in program
                                        System.out.println("\nEnter Program Id from given list: ");
                                        academicOfficer.printAllPrograms();                             //print programs from officer
                                        int pId = getIntegerInput();
                                        Programs nProgram = academicOfficer.getProgramById(pId);        //get program by id from officer

                                        if (nProgram != null)                                            //enter in course and plo
                                        {
                                            int ploTotal = 0, courseTotal = 0;                          //how many course and plo exists
                                            System.out.println("\n");
                                            courseTotal = nProgram.getProgramCoursesList().size();
                                            ploTotal = nProgram.getProgramPloList().size();

                                            if (courseTotal > 0 && ploTotal > 0) {                //display courses and get id from user
                                                System.out.print("Enter Course Id from given list: ");
                                                nProgram.printAllCourses();                             //print all courses from current program
                                                int courseID = getIntegerInput();
                                                CourseOfficer nCourse = nProgram.getCourseById(courseID);//get course by id

                                                if (nCourse != null) {                            //display CLOs and get id from user
                                                    System.out.print("Enter CLO Id from given list: ");
                                                    nCourse.printAllCLOs();                             //print all CLOs from current course
                                                    int cloID = getIntegerInput();
                                                    CLO nCLO = nCourse.getCLOById(cloID);                //get course by id

                                                    if (nCLO != null) {
                                                        if (userInput3 == 1) {                            //Add new topic
                                                            System.out.println("Enter Topic Description: ");
                                                            String desc = getStringInput();

                                                            int tID = Globals.topicsList.size();
                                                            if (tID > 0) {
                                                                topicsList.sort(Comparator.comparingLong(Topics::getTopicID));
                                                                tID = topicsList.get(topicsList.size() - 1).getTopicID();
                                                            }
                                                            tID++;
                                                            Topics newTopic = new Topics(tID, desc);

                                                            if (!(isDuplicateTopic(newTopic, topicsList))) {
                                                                newTopic.addTopicClo(nCLO);                 //update links
                                                                nCLO.addCloTopicList(newTopic);
                                                                Globals.topicsList.add(newTopic);
                                                            } else {
                                                                System.out.println("Topic already exists");
                                                            }
                                                        } else {
                                                            if (Globals.topicsList.size() > 0) {
                                                                System.out.println("Enter Topic Id from given lists: ");
                                                                for (Topics topic :
                                                                        Globals.topicsList) {
                                                                    System.out.println(topic.getTopicID() + " : -> " + topic.getTopicDescription() + " ");
                                                                }
                                                                int tId = getIntegerInput();

                                                                Topics nTopic = null;
                                                                for (Topics topic :
                                                                        Globals.topicsList) {
                                                                    if (topic.getTopicID() == tId) {
                                                                        nTopic = topic;
                                                                        break;
                                                                    }
                                                                }
                                                                if (nTopic != null) {                    //update links

                                                                    if (!(isDuplicateTopic(nTopic, nCLO.getCloTopicsList()))) {
                                                                        nTopic.getTopicCLOList().add(nCLO);
                                                                        nCLO.getCloTopicsList().add(nTopic);
                                                                    } else {
                                                                        System.out.println("Topic already exist");
                                                                    }
                                                                } else {
                                                                    System.out.println("Invalid topic id");
                                                                }
                                                            } else {
                                                                System.out.println("!! topic does not exist !!");
                                                            }
                                                        }
                                                        System.out.println("Updated Successfully");
                                                    } else {
                                                        System.out.println("!! CLO does not exist !!");
                                                    }
                                                }
                                            } else {
                                                System.out.println("!! Course/PLO does not exist !!");
                                            }
                                        }
                                    } else {
                                        System.out.println("!! Program does not exist !!");
                                    }
                                }
                                else if (userInput2 == 13) {
                                    if (coursesList.size() > 0 && teachersList.size() > 0) {
                                        System.out.println("Select Course from given list");
                                        for (Courses course :
                                                coursesList) {
                                            course.printCourseDetails();
                                        }
                                        int courseInput = getIntegerInput();
                                        Courses nCourse = getCourseById(courseInput);

                                        System.out.println("Select Teacher from given list");
                                        for (Teachers teachers :
                                                teachersList) {
                                            teachers.printTeacherDetails();
                                        }
                                        int teacherInput = getIntegerInput();
                                        Teachers nTeacher = getTeacherById(teacherInput);

                                        if (nCourse != null && nTeacher != null) {
                                            nCourse.addTeacher(nTeacher);
                                            nTeacher.addCourse(nCourse);
                                            System.out.println("Course assigned to teacher successfully");
                                        } else {
                                            System.out.println("Invalid course/teacher id");
                                        }
                                    } else {
                                        System.out.println("Course or Teacher does not exist.");
                                    }
                                }
                            }while(userInput2 != 14);
                        }
                        else{
                            System.out.println("Invalid Username/Password");
                        }
                    } catch (Exception e) {
                        System.out.println("An Error occurred :  " + e.getMessage());
                        e.printStackTrace();
                    }
                }
                else if (userInput == 2) {
                    //teacher
                    System.out.println("Enter Username: ");
                    String username = getStringInput();
                    System.out.println("Enter Password: ");
                    String password = getStringInput();

                    Teachers teacher = Globals.verifyTeacher(username, password);

                    if (teacher != null) {
//                    System.out.println("-------------------- ( Welcome " + );

                        int userInput2 = 0;
                        do {
                            System.out.println("*******************************");
                            System.out.println("1: Add Evaluation");
                            System.out.println("2: Update Evaluation");
                            System.out.println("3: Delete Evaluation");
                            System.out.println("4: Add Question");
                            System.out.println("5: Update Question");
                            System.out.println("6: Delete Question");
                            System.out.println("7: Test CLO");
                            System.out.println("8: Exit");
                            System.out.print("Enter choice:  ");
                            userInput2 = getChoiceInput(1, 8);

                            if (userInput2 == 1 || userInput2 == 4 || userInput2 == 7) {
                                if(teacher.getCoursesList().size() > 0) {
                                    System.out.println("Select Course from which you want to get clo ");
                                    teacher.printCourses();                                             //print courses of teachers
                                    int CourseID = getIntegerInput();                                   //get course id
                                    Courses course = (Courses) teacher.getCourseById(CourseID);

                                    if (course != null) {
                                        System.out.println("Select CLO from given list");
                                        course.printAllCLOs();
                                        int cloID = getIntegerInput();
                                        CLO clo = course.getCLOById(cloID);
                                        if (clo != null) {

                                            // add new question
                                            if (userInput2 == 1) {                                        //add evaluation
                                                Questions newQuestions = null;

                                                System.out.println("Want to add new Question? y/n: ");
                                                char check = getCheckInput();
                                                if (check == 'y') {
                                                    System.out.println("Enter Statement for Question: ");
                                                    String statement = getStringInput();
                                                    //calling question constructor
                                                    int qID = questionsList.size();
                                                    if(qID > 0) {
                                                        Globals.questionsList.sort(Comparator.comparingLong(Questions::getId));
                                                        qID = Globals.questionsList.get(Globals.questionsList.size() - 1).getId();
                                                    }
                                                    qID++;
                                                    newQuestions = new Questions(qID, statement, clo);
                                                    questionsList.add(newQuestions);                                //add in global array
                                                }
                                                //add question from existing ones
                                                if (check == 'n') {
                                                    System.out.println("Want to add Questions from existing ones? y/n: ");
                                                    check = getCheckInput();
                                                    if (check == 'y') {
                                                        if(questionsList.size() > 0) {
                                                            System.out.println("Select Questions from given list: ");
                                                            for (Questions question :                                       //print questions from global list
                                                                    questionsList) {
                                                                System.out.println(question.getId() + " : -> " + question.getStatement());
                                                            }
                                                            int cin = getIntegerInput();
                                                            for (Questions question :                                       //get question from global list
                                                                    questionsList) {
                                                                if (cin == question.getId()) {
                                                                    newQuestions = question;
                                                                    break;
                                                                }
                                                            }
                                                        }else{
                                                            System.out.println("No any question Exists");
                                                        }
                                                    }
                                                }

                                                if (newQuestions != null) {
                                                    System.out.println("1: Add in Quiz");
                                                    System.out.println("2: Add in Assignment");
                                                    System.out.println("3: Add in Exams");
                                                    int userInput3 = getChoiceInput(1, 3);

                                                    int evaluationId = evaluationsList.size();
                                                    if (evaluationId > 0) {
                                                        Globals.evaluationsList.sort(Comparator.comparingLong(Evaluations::getId));
                                                        evaluationId = evaluationsList.get((evaluationsList.size() - 1)).getId();
                                                    }
                                                    evaluationId++;

                                                    System.out.println("Enter Evaluation Name: ");
                                                    String eName = getStringInput();

                                                    Evaluations evaluations = null;

                                                    if (userInput3 == 1) {
                                                        //teacher is making question here
                                                        evaluations = new Quizes(evaluationId, eName, teacher);
                                                    } else if (userInput3 == 2) {
                                                        evaluations = new Assignment(evaluationId, eName, teacher);
                                                    } else {
                                                        evaluations = new Exam(evaluationId, eName, teacher);
                                                    }
                                                    newQuestions.setEvaluation(evaluations);                //update links
                                                    evaluations.addQuestions(newQuestions);
                                                    teacher.addEvaluation(evaluations);                     //add evaluations here
                                                    evaluationsList.add(evaluations);
                                                    System.out.println("Questions Added Successfully");
                                                } else {
                                                    System.out.println("Question is not selected");
                                                }
                                            } else if (userInput2 == 4) {
                                                Questions newQuestions;
                                                System.out.println("Enter Statement for Question: ");
                                                String statement = getStringInput();
                                                //calling question constructor
                                                Globals.questionsList.sort(Comparator.comparingLong(Questions::getId));
                                                int qID = Globals.questionsList.get(Globals.questionsList.size() - 1).getId();
                                                qID++;
                                                newQuestions = new Questions(qID, statement, clo);
                                                questionsList.add(newQuestions);                                //add in global array
                                            } else {
                                                boolean testCLO = teacher.testCLO(clo);
                                                if (testCLO) {
                                                    System.out.println("CLO has been tested");
                                                } else {
                                                    System.out.println("CLO Test is failed");
                                                }
                                            }
                                        } else {
                                            System.out.println("No any clo Exist. Contact Administrator");
                                        }
                                    } else {
                                        System.out.println("Invalid ID");
                                    }
                                }else{
                                    System.out.println("You are not teaching any course. Contact Administrator");
                                }
                            }
                            else if (userInput2 == 2) {
                                if (teacher.getEvaluationsList().size() > 0) {                                //check evaluation size

                                    System.out.println("Select Which evaluation you want to update");
                                    teacher.printEvaluations();                                             //print teacher evaluations
                                    int Input = getIntegerInput();
                                    Evaluations modifyEvaluation = teacher.getEvaluationById(Input);        //get evaluation by user id

                                    if (modifyEvaluation != null) {                                          //update it
                                        System.out.println("Want to change Evaluation Name? y/n");
                                        char check = getCheckInput();
                                        if (check == 'y') {
                                            System.out.println("Enter New Evaluation Name: ");
                                            String newName = getStringInput();
                                            modifyEvaluation.setEvaluationName(newName);
                                            System.out.println("Evaluation Updated Successfully");
                                        }
                                    } else {
                                        System.out.println("Invalid Input");
                                    }
                                } else {
                                    System.out.println("No Any Evaluation Exist");
                                }
                            }
                            else if (userInput2 == 3) {

                                if (teacher.getEvaluationsList().size() > 0) {
                                    System.out.println("Enter which evaluation you want to delete");
                                    for (Evaluations evaluation :
                                            teacher.getEvaluationsList()) {
                                        if (evaluation instanceof Quizes) {
                                            System.out.println(evaluation.getId() + " : - > Quiz's");
                                        }else if (evaluation instanceof Assignment){
                                            System.out.println(evaluation.getId() + " : - > Assignments");
                                        }else{
                                            System.out.println(evaluation.getId() + " : - > Exams");
                                        }
                                    }
                                    int eInput = getIntegerInput();
                                    Evaluations deleteEvaluation = null;
                                    for (Evaluations evaluation :
                                            teacher.getEvaluationsList()) {
                                        if (evaluation.getId() == eInput) {
                                            deleteEvaluation = evaluation;
                                            break;
                                        }
                                    }
                                    if (deleteEvaluation != null) {
                                        teacher.getEvaluationsList().remove(deleteEvaluation);
                                        evaluationsList.remove(deleteEvaluation);
                                        System.out.println("Evaluation deleted Successfully");
                                    } else {
                                        System.out.println("Invalid Input");
                                    }
                                } else {
                                    System.out.println("You Don't have any Evaluations yet");
                                }

                            }
                            else if (userInput2 == 5 || userInput2 == 6) {
                                if (questionsList.size() > 0) {
                                    System.out.println("Select Question from the given list: ");
                                    for (Questions question :
                                            questionsList) {
                                        System.out.println(question.getId() + " : -> " + question.getStatement());
                                    }
                                    int qInput = getIntegerInput();
                                    Questions modifyQuestion = null;
                                    for (Questions question :
                                            questionsList) {
                                        if (question.getId() == qInput) {
                                            modifyQuestion = question;
                                            break;
                                        }
                                    }
                                    if (modifyQuestion != null) {

                                        //verify it belongs to the teacher or not
                                        boolean verify = verifyQuestionAndTeacher(modifyQuestion, teacher);

                                        if (verify) {
                                            if (userInput2 == 5) {
                                                System.out.println("Want to update Statement of question? y/n");            //update statement
                                                char check = getCheckInput();
                                                if (check == 'y') {
                                                    System.out.println("Enter new Statement");
                                                    String newState = getStringInput();
                                                    modifyQuestion.setStatement(newState);
                                                    System.out.println("Updated Successfully");
                                                }
                                                System.out.println("Want to update clo of question? y/n");                  //update clo
                                                check = getCheckInput();
                                                if (check == 'y') {
                                                    System.out.println("Select New CLO from given list");
                                                    for (CLO nClo :
                                                            cloList) {
                                                        System.out.println(nClo.getId() + " : -> " + nClo.getDescription() + " ");
                                                    }
                                                    int cInput = getIntegerInput();
                                                    for (CLO nClo :
                                                            cloList) {
                                                        if (nClo.getId() == cInput) {
                                                            modifyQuestion.setClo(nClo);
                                                        }
                                                    }
                                                    System.out.println("Updated Successfully");
                                                }
                                            } else {
                                                questionsList.remove(modifyQuestion);                               //remove globally
                                                for (Evaluations evaluation :                                       //remove from evaluations
                                                        teacher.getEvaluationsList()) {
                                                    evaluation.questionsList.remove(modifyQuestion);
                                                }
                                                System.out.println("Question Deleted Successfully");
                                            }
                                        } else {
                                            System.out.println("You don't have access to this question");
                                        }
                                    }
                                } else {
                                    System.out.println("No Any Questions Exists");
                                }
                            }
                        }while(userInput2 != 8);
                    } else {
                        System.out.println("!! Invalid Username/Password !!");
                    }
                    System.out.println();
                } else if (userInput == 3) {
                    System.out.println("Enter Full Name");
                    String fullName = getStringInput();
                    System.out.println("Enter Phone Number");
                    String phoneNumber = getStringInput();
                    System.out.println("Enter Address");
                    String address = getStringInput();
                    System.out.println("Enter Username");
                    String username = getStringInput();
                    System.out.println("Enter password");
                    String password = getStringInput();

                    int tId = teachersList.size();
                    if (tId > 0) {
                        teachersList.sort(Comparator.comparingLong(Teachers::getTeacherID));
                        tId = teachersList.get(teachersList.size() - 1).getTeacherID();
                    }
                    tId++;
                    Teachers newTeacher = new Teachers(tId, fullName, phoneNumber, address, username, password);
                    teachersList.add(newTeacher);
                    System.out.println("Teacher Added Successfully. Now you can login");
                }

        }

        Globals.saveProgram();
        Globals.saveCourse();
        Globals.savePLO();
        Globals.saveCLO();
        Globals.saveTopic();
        Globals.saveTeacher();
        Globals.saveEvaluation();
        Globals.saveQuestion();
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
            //Programs
//            List<Programs> fileProgramsList = new ArrayList<>();
            programsList = new ArrayList<>();
            List<String> programPloList = new ArrayList<>();
            List<String> programCoursesList = new ArrayList<>();

            //Courses
//            List<Courses> fileCoursesList = new ArrayList<>();
            coursesList = new ArrayList<>();
            List<String> courseProgramsList = new ArrayList<>();
            List<String> courseTeachersList = new ArrayList<>();
            List<String> courseCloList = new ArrayList<>();

            //PLO
//            List<PLO> filePloList = new ArrayList<>();
            ploList = new ArrayList<>();
            List<String> ploCloList = new ArrayList<>();

            //CLO
//            List<CLO> fileCloList = new ArrayList<>();
            cloList = new ArrayList<>();
            List<String> cloCourseList = new ArrayList<>();
            List<String> cloPLOList = new ArrayList<>();
            List<String> cloTopicList = new ArrayList<>();

            //topics
//            List<Topics> fileTopicsList = new ArrayList<>();
            topicsList = new ArrayList<>();
            List<String> topicCloList = new ArrayList<>();

            //teacher
//            List<Teachers> fileTeachersList = new ArrayList<>();
            teachersList = new ArrayList<>();
            List<String> teacherCourseList = new ArrayList<>();
            List<String> teacherEvaluationList = new ArrayList<>();

            //Evaluation
//            List<Evaluations> fileEvaluationsList = new ArrayList<>();
            evaluationsList = new ArrayList<>();
            List<String> evaluationQuestionList = new ArrayList<>();

            //Questions
//            List<Questions> fileQuestionsList = new ArrayList<>();
            questionsList = new ArrayList<>();

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
            File cloKeyFile = new File(closPath);
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

            //Read Teachers data
            File teacherKeyFile = new File(teachersPath);
            if(teacherKeyFile.exists()){
                Scanner myTeacherReader = new Scanner(teacherKeyFile);
                while(myTeacherReader.hasNextLine()){
                    int tId = Integer.parseInt(myTeacherReader.nextLine());
                    String fullName = myTeacherReader.nextLine();
                    String phoneNumber = myTeacherReader.nextLine();
                    String address = myTeacherReader.nextLine();
                    String username = myTeacherReader.nextLine();
                    String password = myTeacherReader.nextLine();

                    String tCourses = myTeacherReader.nextLine();
                    String tEvaluations = myTeacherReader.nextLine();

                    Teachers newTeacher = new Teachers(tId,fullName, phoneNumber, address, username, password);

                    //add in list
                    teachersList.add(newTeacher);
                    teacherCourseList.add(tCourses);
                    teacherEvaluationList.add(tEvaluations);
                }
            }

            //Read Evaluations data
            File EvaluationKeyFile = new File(evaluationsPath);
            if(EvaluationKeyFile.exists()){
                Scanner myEvaluationReader = new Scanner(EvaluationKeyFile);
                while(myEvaluationReader.hasNextLine()){
                    int eId = Integer.parseInt(myEvaluationReader.nextLine());
                    String eName = myEvaluationReader.nextLine();
                    int eTeacherId = Integer.parseInt(myEvaluationReader.nextLine()) ;
                    String eType = myEvaluationReader.nextLine();

                    String eQuestions = myEvaluationReader.nextLine();

                    Teachers nTeacher = null;
                    for (Teachers teacher :
                            teachersList) {
                        if(teacher.getTeacherID() == eTeacherId){
                            nTeacher = teacher;
                            break;
                        }
                    }

                    Evaluations newEvaluation = null;
                    if(eType.equals("quiz")){
                        newEvaluation  = new Quizes(eId,eName,nTeacher);
                    }else if(eType.equals("assignment")){
                        newEvaluation  = new Assignment(eId,eName,nTeacher);
                    }else{
                        newEvaluation  = new Exam(eId,eName,nTeacher);
                    }

                    //add in list
                    evaluationsList.add(newEvaluation);
                    evaluationQuestionList.add(eQuestions);
                }
            }

            //Read Questions data
            File QuestionsKeyFile = new File(questionPath);
            if(QuestionsKeyFile.exists()){
                Scanner myQuestionReader = new Scanner(QuestionsKeyFile);
                while(myQuestionReader.hasNextLine()){
                    int qId = Integer.parseInt(myQuestionReader.nextLine());
                    String qStatement = myQuestionReader.nextLine();
                    int qCloId = Integer.parseInt(myQuestionReader.nextLine()) ;


                    CLO nCLo = null;
                    for (CLO clo :
                            cloList) {
                        if (clo.getId() == qCloId) {
                            nCLo = clo;
                        }
                    }

                    Questions newQuestion = new Questions(qId, qStatement,nCLo);

                    //add in list
                    questionsList.add(newQuestion);
                }
            }


            //update all lists
            int index = 0;

            if(programsList != null) {
                for (Programs program :
                        programsList) {
                    int id = program.getProgramID();        //get program id from array
                    String pPlo = programPloList.get(index);     //get plo list
                    String pCourse = programCoursesList.get(index);  //get course list

                    //setup of plo's
                    if (!Objects.equals(pPlo, "0")) {
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
                    if (!Objects.equals(pCourse, "0")) {
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
                    index++;
                }
            }

            index = 0;
            if(coursesList != null) {
                for (Courses course :
                        coursesList) {
                    int id = course.getCourseID();
                    String cProgram = courseProgramsList.get(index);
                    String cTeacher = courseTeachersList.get(index);
                    String cClo = courseCloList.get(index);

                    if (!Objects.equals(cProgram, "0")) {
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

                    if (!Objects.equals(cTeacher, "0")) {
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

                    if (!Objects.equals(cClo, "0")) {
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
                    index++;
                }
            }

            index = 0;
            if(ploList != null) {
                for (PLO plo :
                        ploList) {
                    int id = plo.getId();
                    String pCLO = ploCloList.get(index);
                    //setup of clo
                    if (!Objects.equals(pCLO, "0")) {
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
                    index++;
                }
            }

            index = 0;
            if(cloList != null) {
                for (CLO clo :
                        cloList) {
                    int id = clo.getId();
                    String cCourse = cloCourseList.get(index);
                    String cPLO = cloPLOList.get(index);
                    String cTopic = cloTopicList.get(index);

                    if (!Objects.equals(cCourse, "0")) {
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

                    if (!Objects.equals(cPLO, "0")) {
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

                    if (!Objects.equals(cTopic, "0")) {
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
                    index++;
                }
            }

            index = 0;
            if(topicsList != null) {
                for (Topics topic :
                        topicsList) {
                    int id = topic.getTopicID();
                    String tClo = topicCloList.get(index);

                    if (!Objects.equals(tClo, "0")) {
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
                    index++;
                }
            }

            index = 0;
            if(teachersList != null) {
                for (Teachers teacher :
                        teachersList) {
                    int id = teacher.getTeacherID();

                    String tCourse = teacherCourseList.get(index);
                    String tEvaluation = teacherEvaluationList.get(index);

                    if (!Objects.equals(tCourse, "0")) {
                        String[] splitArray1 = tCourse.split(",");
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
                            teacher.addCourse(nCourse);
                        }
                    }

                    if (!Objects.equals(tEvaluation, "0")) {
                        String[] splitArray1 = tEvaluation.split(",");
                        List<Integer> array = new ArrayList<>();
                        for (String s : splitArray1) {
                            array.add(Integer.parseInt(s));
                        }

                        for (Integer i :
                                array) {
                            Evaluations nEvaluation = null;
                            for (Evaluations evaluation :
                                    evaluationsList) {
                                if (evaluation.getId() == i) {
                                    nEvaluation = evaluation;
                                    break;
                                }
                            }
                            teacher.addEvaluation(nEvaluation);
                        }
                    }
                    index++;
                }
            }

            index = 0;
            if(evaluationsList != null) {
                for (Evaluations evaluation :
                        evaluationsList) {
                    int id = evaluation.getId();
                    String eQues = evaluationQuestionList.get(index);

                    if (!Objects.equals(eQues, "0")) {
                        String[] splitArray1 = eQues.split(",");
                        List<Integer> array = new ArrayList<>();
                        for (String s : splitArray1) {
                            array.add(Integer.parseInt(s));
                        }

                        for (Integer i :
                                array) {
                            Questions nQuestion = null;
                            for (Questions question :
                                    questionsList) {
                                if (question.getId() == i) {
                                    nQuestion = question;
                                    break;
                                }
                            }
                            evaluation.addQuestions(nQuestion);
                        }
                    }
                    index++;
                }
            }

//            Globals.programsList = programsList;
//            Globals.coursesList = coursesList;
//            Globals.ploList = ploList;
//            Globals.cloList = cloList;
//            Globals.topicsList = topicsList;

        }
        catch (Exception e){
            System.out.println("Error Occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }


}