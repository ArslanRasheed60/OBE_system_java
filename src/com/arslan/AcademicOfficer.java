package com.arslan;

import java.util.ArrayList;
import java.util.List;
public class AcademicOfficer extends Users{
    private List<Programs> programsList;
    public AcademicOfficer (String fullName, String phoneNumber, String address, String username, String password){
        super(fullName,phoneNumber, address, username, password);
        programsList = new ArrayList<>();
    };
    public void addProgram(Programs program) {
        programsList.add(program);
    }
    public List<Programs> getProgramsList() {
        return programsList;
    }

    public void printAllPrograms(){
        for (Programs program :
                this.programsList) {
            System.out.println(program.getProgramID() + " : -> " + program.getProgramDegreeType() + " -> " + program.getProgramName());
        }
    }

    public Programs getProgramById(int id){
        for (Programs program :
                this.programsList) {
            if (program.getProgramID() == id) {
                return program;
            }
        }
        return null;
    }

    public void addCourse(Programs nProgram, Courses newCourse){
        nProgram.addCourse(newCourse);
        newCourse.addProgram(nProgram);
    }

}
