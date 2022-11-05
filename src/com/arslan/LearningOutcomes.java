package com.arslan;

public class LearningOutcomes {
    private int Id;
    private String Description;
    public LearningOutcomes(int Id, String Description){
        this.setId(Id);
        this.setDescription(Description);
    }
    private void setId(int id) {
        Id = id;
    }
    public int getId() {
        return Id;
    }
    private void setDescription(String description) {
        Description = description;
    }
    public String getDescription() {
        return Description;
    }
}
