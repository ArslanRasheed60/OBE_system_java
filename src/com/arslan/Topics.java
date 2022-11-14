package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Topics {
    private int topicID;
    private String topicDescription;

    private List<CLO> topicCLOList;

    public Topics(int topicID, String topicDescription){
        this.setTopicID(topicID);
        this.setTopicDescription(topicDescription);
        if(topicCLOList == null){
            topicCLOList = new ArrayList<>();
        }
    }
    public Topics(int topicID, String topicDescription, List<CLO> topicCLOList){
        this.setTopicID(topicID);
        this.setTopicDescription(topicDescription);
        this.setTopicCLOList(topicCLOList);
    }


    private void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public int getTopicID() {
        return topicID;
    }

    private void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void addTopicClo(CLO clo){
        this.topicCLOList.add(clo);
    }


    public void setTopicCLOList(List<CLO> topicCLOList) {
        this.topicCLOList = topicCLOList;
    }

    public List<CLO> getTopicCLOList() {
        return topicCLOList;
    }

    protected void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.topicPath);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.topicPath, append);
            myWriter.write(Integer.toString(this.topicID) + "\n");
            myWriter.write(this.topicDescription + "\n");

            String newArray = "";   //convert array to string
            for (CLO clo :
                    topicCLOList) {
                newArray = newArray.concat(Integer.toString(clo.getId()) + ",");
            }

            myWriter.write((newArray==""?"0":newArray) + "\n");
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }


}
