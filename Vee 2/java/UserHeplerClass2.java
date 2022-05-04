package com.example.vee2;

public class UserHeplerClass2 {

    String userID, request;

    public UserHeplerClass2() {
    }

    public UserHeplerClass2(String userID, String request) {
        this.userID = userID;
        this.request = request;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}