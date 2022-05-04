package com.example.vee2;

public class UserHeplerClass {
    String UserId, UName, UPassword, EMail ;
    int Invested, Mined, Miner1, Miner2, Miner3, Miner4, Miner5;

    public UserHeplerClass() {
    }

    public UserHeplerClass(String userId, String UName, String UPassword, String EMail, int invested, int mined, int miner1, int miner2, int miner3, int miner4, int miner5)
    {
        UserId = userId;
        this.UName = UName;
        this.UPassword = UPassword;
        this.EMail = EMail;
        Invested = invested;
        Mined = mined;
        Miner1 = miner1;
        Miner2 = miner2;
        Miner3 = miner3;
        Miner4 = miner4;
        Miner5 = miner5;
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

    public int getInvested() {
        return Invested;
    }

    public void setInvested(int invested) {
        Invested = invested;
    }

    public int getMined() {
        return Mined;
    }

    public void setMined(int mined) {
        Mined = mined;
    }

    public int getMiner1() {
        return Miner1;
    }

    public void setMiner1(int miner1) {
        Miner1 = miner1;
    }

    public int getMiner2() {
        return Miner2;
    }

    public void setMiner2(int miner2) {
        Miner2 = miner2;
    }

    public int getMiner3() {
        return Miner3;
    }

    public void setMiner3(int miner3) {
        Miner3 = miner3;
    }

    public int getMiner4() {
        return Miner4;
    }

    public void setMiner4(int miner4) {
        Miner4 = miner4;
    }

    public int getMiner5() {
        return Miner5;
    }

    public void setMiner5(int miner5) {
        Miner5 = miner5;
    }
}
