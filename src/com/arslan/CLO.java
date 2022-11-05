package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CLO  extends  LearningOutcomes{
    private List<Courses> cloCoursesList;
    private List<Topics> cloTopicsList;

    public CLO(int Id, String Description){
        super(Id, Description);
        cloCoursesList = new ArrayList<>();
        cloTopicsList = new ArrayList<>();
    }
    public CLO(int Id, String Description, List<Courses> cloCoursesList,List<Topics> cloTopicsList  ){
        super(Id, Description);
        this.setCloCoursesList(cloCoursesList);
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
            String path = "E:\\Development\\SDA_Assigment\\src\\com\\files\\CLOs.txt";
            File keyfile = new File(path);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(path, append);
            myWriter.write(Integer.toString(this.getId()) + "\n");
            myWriter.write(this.getDescription() + "\n");

            String newArray = "";
            for (Courses course :
                    cloCoursesList) {
                newArray = newArray.concat(Integer.toString(course.getCourseID()) + ",");
            }
            myWriter.write((newArray==""?"0":newArray) + "\n");

            newArray = "";
            for (Topics topic :
                    cloTopicsList) {
                newArray = newArray.concat(Integer.toString(topic.getTopicID()) + ",");
            }

            myWriter.write((newArray==""?"0":newArray) + "\n");
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }



}
