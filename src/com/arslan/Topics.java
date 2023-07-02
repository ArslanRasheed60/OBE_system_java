package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Topics {
    private final int topicID;
    private final String topicDescription;
    private final List<CLO> topicCLOList;
    public Topics(int topicID, String topicDescription){
        this.topicID = topicID;
        this.topicDescription = topicDescription;
        topicCLOList = new ArrayList<>();
    }
    public int getTopicID() {
        return topicID;
    }
    public String getTopicDescription() {
        return topicDescription;
    }
    public void addTopicClo(CLO clo){
        this.topicCLOList.add(clo);
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
            myWriter.write(this.topicID + "\n");
            myWriter.write(this.topicDescription + "\n");

            String newArray = "";   //convert array to string
            for (CLO clo :
                    topicCLOList) {
                newArray = newArray.concat(clo.getId() + ",");
            }

            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }


}
