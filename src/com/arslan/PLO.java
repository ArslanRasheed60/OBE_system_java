package com.arslan;

import java.io.File;
import java.io.FileWriter;

public class PLO extends LearningOutcomes{
    private  Programs ploProgram;

    public  PLO(int Id, Programs ploProgram, String Description){
        super(Id,Description);
        this.setProgram(ploProgram);
    }

    private void setProgram(Programs ploProgram) {
        this.ploProgram = ploProgram;
    }

    public Programs getProgram() {
        return ploProgram;
    }

    public void saveInFile(boolean append){
        try {
            String path = "E:\\Development\\SDA_Assigment\\src\\com\\files\\PLOs.txt";
            File keyfile = new File(path);
            if(!keyfile.exists()){
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(path, append);
            myWriter.write(Integer.toString(this.getId()) + "\n");
            myWriter.write(this.getDescription() + "\n");
            myWriter.write(this.ploProgram.getProgramID() + "\n");
            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
        }
    }

}
