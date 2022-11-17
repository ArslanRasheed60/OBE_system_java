package com.arslan;

public class Questions {
    private int id;
    private String statement;
    private CLO clo;

    public Questions(int id, String statement, CLO clo) {
        this.id = id;
        this.statement = statement;
        this.clo = clo;
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
}
