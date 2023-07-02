package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CLO  extends  LearningOutcomes{
    private final List<Courses> cloCoursesList;
    private final List<PLO> cloPLOList;
    private final List<Topics> cloTopicsList;
    public CLO(int Id, String Description){
        super(Id, Description);
        cloCoursesList = new ArrayList<>();
        cloTopicsList = new ArrayList<>();
        cloPLOList = new ArrayList<>();
    }
    public void addCloCoursesList(Courses course){
        cloCoursesList.add(course);
    }
    public List<Courses> getCloCoursesList() {
        return cloCoursesList;
    }
    public void addPLO(PLO plo){
        this.cloPLOList.add(plo);
    }
    public List<PLO> getCloPLOList() {
        return cloPLOList;
    }
    public void addCloTopicList(Topics topic){
        cloTopicsList.add(topic);
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
            myWriter.write(this.getId() + "\n");
            myWriter.write(this.getDescription() + "\n");

            String newArray = "";
            for (Courses course :
                    cloCoursesList) {
                newArray = newArray.concat(course.getCourseID() + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            newArray = "";
            for (PLO plo :
                    cloPLOList) {
                newArray = newArray.concat(plo.getId() + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            newArray = "";
            for (Topics topic :
                    cloTopicsList) {
                newArray = newArray.concat(topic.getTopicID() + ",");
            }

            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }



}
