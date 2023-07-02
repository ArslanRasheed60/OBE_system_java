package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CLO  extends  LearningOutcomes{
    private List<Courses> cloCoursesList;

    private List<PLO> cloPLOList;
    private List<Topics> cloTopicsList;

    public CLO(int Id, String Description){
        super(Id, Description);
        cloCoursesList = new ArrayList<>();
        cloTopicsList = new ArrayList<>();
        cloPLOList = new ArrayList<>();
    }
    public CLO(int Id, String Description, List<Courses> cloCoursesList,List<PLO> cloPLOList,List<Topics> cloTopicsList  ){
        super(Id, Description);
        this.setCloCoursesList(cloCoursesList);
        this.setCloPLOList(cloPLOList);
        this.setCloTopicsList(cloTopicsList);
    }

    public void addCloCoursesList(Courses course){
        cloCoursesList.add(course);
    }

    private void setCloCoursesList(List<Courses> cloCoursesList) {
        this.cloCoursesList = cloCoursesList;
    }

    public List<Courses> getCloCoursesList() {
        return cloCoursesList;
    }


    public void addPLO(PLO plo){
        this.cloPLOList.add(plo);
    }

    private void setCloPLOList(List<PLO> cloPLOList) {
        this.cloPLOList = cloPLOList;
    }

    public List<PLO> getCloPLOList() {
        return cloPLOList;
    }

    public void addCloTopicList(Topics topic){
        cloTopicsList.add(topic);
    }
    private void setCloTopicsList(List<Topics> cloTopicsList) {
        this.cloTopicsList = cloTopicsList;
    }

    public List<Topics> getCloTopicsList() {
        return cloTopicsList;
    }

    public void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.closPath);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.closPath, append);
            myWriter.write(Integer.toString(this.getId()) + "\n");
            myWriter.write(this.getDescription() + "\n");

            String newArray = "";
            for (Courses course :
                    cloCoursesList) {
                newArray = newArray.concat(Integer.toString(course.getCourseID()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            newArray = "";
            for (PLO plo :
                    cloPLOList) {
                newArray = newArray.concat(Integer.toString(plo.getId()) + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            newArray = "";
            for (Topics topic :
                    cloTopicsList) {
                newArray = newArray.concat(Integer.toString(topic.getTopicID()) + ",");
            }

            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }



}
