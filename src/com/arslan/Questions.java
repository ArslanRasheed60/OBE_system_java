package com.arslan;

import java.io.File;
import java.io.FileWriter;

public class Questions {
    private int id;
    private String statement;
    private CLO clo;

    private Evaluations evaluation;

    public Questions(int id, String statement, CLO clo) {
        this.id = id;
        this.statement = statement;
        this.clo = clo;
        this.evaluation = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public CLO getClo() {
        return clo;
    }

    public void setClo(CLO clo) {
        this.clo = clo;
    }

    public Evaluations getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluations evaluation) {
        this.evaluation = evaluation;
    }

    public void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.questionPath);
            if (!keyfile.exists()) {
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.questionPath, append);
            myWriter.write(this.getId() + "\n");
            myWriter.write(this.getStatement() + "\n");
            myWriter.write(this.getClo().getId() + "\n");

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
            e.printStackTrace();
        }
    }

}
