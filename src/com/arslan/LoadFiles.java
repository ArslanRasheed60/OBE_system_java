package com.arslan;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.arslan.Globals.*;

public class LoadFiles {
    public static AcademicOfficer ReadAcademicOfficerCredentials(){
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
            }
            myReader.close();
            return new AcademicOfficer(fullName, phoneNumber, address, username, password);

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    public static void loadFileData(AcademicOfficer officer){
        try {
            //Programs
            List<Programs>  fileProgramsList = new ArrayList<>();
            List<String> programPloList = new ArrayList<>();
            List<String> programCoursesList = new ArrayList<>();
            //Courses
            List<Courses> fileCoursesList = new ArrayList<>();
            List<String> courseProgramsList = new ArrayList<>();
            List<String> courseTeachersList = new ArrayList<>();
            List<String> courseCloList = new ArrayList<>();
            //PLO
            List<PLO> filePloList = new ArrayList<>();
            List<String> ploCloList = new ArrayList<>();
            //CLO
            List<CLO> fileCloList = new ArrayList<>();
            List<String> cloCourseList = new ArrayList<>();
            List<String> cloPLOList = new ArrayList<>();
            List<String> cloTopicList = new ArrayList<>();
            //topics
            List<Topics> fileTopicsList = new ArrayList<>();
            List<String> topicCloList = new ArrayList<>();
            //teacher
            List<Teachers> fileTeachersList = new ArrayList<>();
            List<String> teacherCourseList = new ArrayList<>();
            List<String> teacherEvaluationList = new ArrayList<>();
            //Evaluation
            List<Evaluations> fileEvaluationsList = new ArrayList<>();
            List<String> evaluationQuestionList = new ArrayList<>();
            //Questions
            List<Questions> fileQuestionsList = new ArrayList<>();

            //Load all files
            loadProgram(fileProgramsList, programPloList, programCoursesList);
            loadCourses(fileCoursesList,courseProgramsList, courseTeachersList, courseCloList);
            loadPLOs(filePloList,fileProgramsList,ploCloList);
            loadCLOs(fileCloList,cloCourseList, cloPLOList, cloTopicList);
            loadTopics(fileTopicsList,topicCloList);
            loadTeachers(fileTeachersList,teacherCourseList, teacherEvaluationList);
            loadEvaluations(fileEvaluationsList,fileTeachersList,evaluationQuestionList);
            loadQuestions();

            //update all lists
            updateProgramsList(fileProgramsList,filePloList, fileCoursesList ,officer,programPloList,programCoursesList);
            updateCoursesList(fileCoursesList,fileProgramsList, fileTeachersList, fileCloList,courseProgramsList, courseTeachersList, courseCloList);
            updatePLOsList(filePloList,fileCloList,ploCloList);
            updateCLOsList(fileCloList,fileCoursesList,filePloList,fileTopicsList,cloCourseList, cloPLOList, cloTopicList);
            updateTopicsList(fileTopicsList,fileCloList,topicCloList);
            updateTeachersList(fileTeachersList,fileCoursesList,fileEvaluationsList,teacherCourseList, teacherEvaluationList);
            updateEvaluationsList(fileEvaluationsList,fileQuestionsList,evaluationQuestionList);

            programsList = fileProgramsList;
            coursesList = fileCoursesList;
            ploList = filePloList;
            cloList = fileCloList;
            topicsList = fileTopicsList;
            teachersList = fileTeachersList;
            evaluationsList = fileEvaluationsList;
            questionsList = fileQuestionsList;
        }
        catch (Exception e){
            System.out.println("Error Occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void loadProgram(List<Programs>  fileProgramsList,List<String> programPloList, List<String> programCoursesList){
        try{
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
                    fileProgramsList.add(newProgram);
                    programCoursesList.add(pCourseList);
                    programPloList.add(pPloList);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadCourses(List<Courses> fileCoursesList,List<String> courseProgramsList,List<String> courseTeachersList,List<String> courseCloList ){
        try{
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
                    fileCoursesList.add(newCourse);
                    courseProgramsList.add(cProgramList);
                    courseTeachersList.add(cTeacherList);
                    courseCloList.add(cCloList);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadPLOs(List<PLO> filePloList,List<Programs>  fileProgramsList,List<String> ploCloList){
        try{
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
                            fileProgramsList) {
                        if (program.getProgramID() == ploProgramId) {
                            getProgram = program;
                            break;
                        }
                    }

                    PLO newPlo = new PLO(ploId, getProgram, ploDescription);
                    //add in list
                    filePloList.add(newPlo);
                    ploCloList.add(pCLOList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadCLOs(List<CLO> fileCloList,List<String> cloCourseList,List<String> cloPLOList,List<String> cloTopicList ){
        try {
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
                    fileCloList.add(newClo);
                    cloCourseList.add(cloCList);
                    cloPLOList.add(cloPList);
                    cloTopicList.add(cloTList);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadTopics(List<Topics> fileTopicsList,List<String> topicCloList){
        try{
            File topicKeyFile = new File(topicPath);
            if(topicKeyFile.exists()){
                Scanner myTopicReader = new Scanner(topicKeyFile);
                while(myTopicReader.hasNextLine()){
                    int tId = Integer.parseInt(myTopicReader.nextLine());
                    String tDescription = myTopicReader.nextLine();
                    String tCloList = myTopicReader.nextLine();

                    Topics newTopics = new Topics(tId, tDescription);

                    //add in list
                    fileTopicsList.add(newTopics);
                    topicCloList.add(tCloList);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadTeachers(List<Teachers> fileTeachersList,List<String> teacherCourseList,List<String> teacherEvaluationList  ){
        try{
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
                    fileTeachersList.add(newTeacher);
                    teacherCourseList.add(tCourses);
                    teacherEvaluationList.add(tEvaluations);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadEvaluations(List<Evaluations> fileEvaluationsList,List<Teachers> fileTeachersList,List<String> evaluationQuestionList){
        try {
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
                            fileTeachersList) {
                        if(teacher.getTeacherID() == eTeacherId){
                            nTeacher = teacher;
                            break;
                        }
                    }

                    Evaluations newEvaluation;
                    if(eType.equals("quiz")){
                        newEvaluation  = new Quizes(eId,eName,nTeacher);
                    }else if(eType.equals("assignment")){
                        newEvaluation  = new Assignment(eId,eName,nTeacher);
                    }else{
                        newEvaluation  = new Exam(eId,eName,nTeacher);
                    }

                    //add in list
                    fileEvaluationsList.add(newEvaluation);
                    evaluationQuestionList.add(eQuestions);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void loadQuestions(){
        try{
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void updateProgramsList(List<Programs>  fileProgramsList,List<PLO> filePloList, List<Courses> fileCoursesList,AcademicOfficer officer , List<String> programPloList, List<String> programCoursesList){
        int index = 0;

        if(fileProgramsList != null) {
            for (Programs program :
                    fileProgramsList) {
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
                                filePloList) {
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
                                fileCoursesList) {
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
    }
    private static void updateCoursesList(List<Courses> fileCoursesList,List<Programs>  fileProgramsList,List<Teachers> fileTeachersList, List<CLO> fileCloList ,List<String> courseProgramsList,List<String> courseTeachersList,List<String> courseCloList){
        int index = 0;
        if(fileCoursesList != null) {
            for (Courses course :
                    fileCoursesList) {
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
                                fileProgramsList) {
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
                                fileTeachersList) {
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
                                fileCloList) {
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
    }
    private static void updatePLOsList(List<PLO> filePloList,List<CLO> fileCloList,List<String> ploCloList){
        int index = 0;
        if(filePloList != null) {
            for (PLO plo :
                    filePloList) {
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
                                fileCloList) {
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
    }
    private static void updateCLOsList(List<CLO> fileCloList,List<Courses> fileCoursesList,List<PLO> filePloList,List<Topics> fileTopicsList,List<String> cloCourseList,List<String> cloPLOList,List<String> cloTopicList){
        int index = 0;
        if(fileCloList != null) {
            for (CLO clo :
                    fileCloList) {
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
                                fileCoursesList) {
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
                                filePloList) {
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
                                fileTopicsList) {
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
    }
    private static void updateTopicsList(List<Topics> fileTopicsList,List<CLO> fileCloList,List<String> topicCloList){
        int index = 0;
        if(fileTopicsList != null) {
            for (Topics topic :
                    fileTopicsList) {
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
                                fileCloList) {
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
    }
    private static void updateTeachersList(List<Teachers> fileTeachersList,List<Courses> fileCoursesList,List<Evaluations> fileEvaluationsList,List<String> teacherCourseList,List<String> teacherEvaluationList){
        int index = 0;
        if(fileTeachersList != null) {
            for (Teachers teacher :
                    fileTeachersList) {

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
                                fileCoursesList) {
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
                                fileEvaluationsList) {
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
    }
    private static void updateEvaluationsList(List<Evaluations> fileEvaluationsList,List<Questions> fileQuestionsList,List<String> evaluationQuestionList){
        int index = 0;
        if(fileEvaluationsList != null) {
            for (Evaluations evaluation :
                    fileEvaluationsList) {
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
                                fileQuestionsList) {
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
    }

}