package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class PLO extends LearningOutcomes{
    private  Programs ploProgram;

    private List<CLO> ploCLOList;

    public  PLO(int Id, Programs ploProgram, String Description){
        super(Id,Description);
        this.setProgram(ploProgram);
        ploCLOList = new ArrayList<>();
    }

    void setProgram(Programs ploProgram) {
        this.ploProgram = ploProgram;
    }

    public Programs getProgram() {
        return ploProgram;
    }

    public void addCLO(CLO clo){
        this.ploCLOList.add(clo);
    }
    private void setPloCLOList(List<CLO> ploCLOList) {
        this.ploCLOList = ploCLOList;
    }

    public List<CLO> getPloCLOList() {
        return ploCLOList;
    }

    public void printAllCLOs(){
        for (CLO clo :
                this.ploCLOList) {
            System.out.println(clo.getId() + " : -> " + clo.getDescription() + " ");
        }
    }

    public CLO getCLOById(int id){
        for (CLO clo :
                this.ploCLOList) {
            if(clo.getId() == id){
                return clo;
            }
        }
        return null;
    }


    public void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.ploPath);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.ploPath, append);
            myWriter.write(Integer.toString(this.getId()) + "\n");
            myWriter.write(this.getDescription() + "\n");
            myWriter.write(this.ploProgram.getProgramID() + "\n");

            String newArray = "";
            for (CLO clo :
                    ploCLOList) {
                newArray = newArray.concat(Integer.toString(clo.getId()) + ",");
            }
            myWriter.write((newArray==""?"0":newArray) + "\n");

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }

}
