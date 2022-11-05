package com.arslan;

public class Users {
//    private int id;
    private String fullName;
    private String phoneNumber;

    private String address;


    public Users (){

    }
    public Users(String fullName, String phoneNumber, String address){
        this.setFullName(fullName);
        this.setPhoneNumber(phoneNumber);
        this.setAddress(address);
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
}

