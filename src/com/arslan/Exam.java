package com.arslan;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
public class Exam extends Evaluations{

    Exam(int id, String name ,Teachers teacher){
        this.id = id;
        this.EvaluationName = name;
        this.teacher = teacher;
        questionsList = new ArrayList<>();
    }

    @Override
    public void saveInFile(boolean append){
        try {
            File keyfile = new File(Globals.evaluationsPath);
            if (!keyfile.exists()) {
                keyfile.createNewFile();
            }
            FileWriter myWriter = new FileWriter(Globals.evaluationsPath, append);
            myWriter.write(this.getId() + "\n");
            myWriter.write(this.EvaluationName + "\n");
            myWriter.write(this.teacher.getTeacherID() + "\n");
            myWriter.write("exam" + "\n");

            String newArray = "";
            for (Questions question :
                    questionsList) {
                newArray = newArray.concat(question.getId() + ",");
            }
            myWriter.write((newArray.equals("") ?"0":newArray) + "\n");

            myWriter.close();

        } catch (Exception e) {
            System.out.println("An Error Occurred + " + e.getMessage());
            e.printStackTrace();
        }
    }
}
