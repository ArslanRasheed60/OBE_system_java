package com.arslan;

public class Users {
    protected String fullName;
    protected String phoneNumber;
    protected String address;
    protected String username;
    protected String password;
    public Users(String fullName, String phoneNumber, String address, String username, String password){
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

