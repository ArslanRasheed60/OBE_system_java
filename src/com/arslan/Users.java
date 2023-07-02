package com.arslan;

public class Users {
//    private int id;
    protected String fullName;
    protected String phoneNumber;
    protected String address;

    protected String username;

    protected String password;

    public Users (){

    }
    public Users(String fullName, String phoneNumber, String address, String username, String password){
        this.setFullName(fullName);
        this.setPhoneNumber(phoneNumber);
        this.setAddress(address);
        this.setUsername(username);
        this.setPassword(password);
    }


    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private String getFullName() {
        return fullName;
    }

    private void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.length() != 11){
            throw new IllegalArgumentException("phoneNumber must be of 11 digits");
        }
        this.phoneNumber = phoneNumber;
    }

    private String getPhoneNumber() {
        return phoneNumber;
    }

    private String getAddress() {
        return address;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

