package com.example.vee2;

public class UserHeplerClass {
    String UserId, UName, UPassword, EMail, Referal;

    public UserHeplerClass() {
    }

    public UserHeplerClass(String userId, String UName, String UPassword, String EMail, String referal) {
        UserId = userId;
        this.UName = UName;
        this.UPassword = UPassword;
        this.EMail = EMail;
        Referal = referal;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUName() {
        return UName;
    }

    public void setUName(String UName) {
        this.UName = UName;
    }

    public String getUPassword() {
        return UPassword;
    }

    public void setUPassword(String UPassword) {
        this.UPassword = UPassword;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getReferal() {
        return Referal;
    }

    public void setReferal(String referal) {
        Referal = referal;
    }
}