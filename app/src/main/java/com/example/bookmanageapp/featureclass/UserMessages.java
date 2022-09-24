package com.example.bookmanageapp.featureclass;

public class UserMessages {
    private String userID;
    private String senderID;
    private String sentMsgTxt;

    public UserMessages() {

    }

    public UserMessages(String userID, String senderID, String sentMsgTxt) {
        this.userID = userID;
        this.senderID = senderID;
        this.sentMsgTxt = sentMsgTxt;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSentMsgTxt() {
        return sentMsgTxt;
    }

    public void setSentMsgTxt(String sentMsgTxt) {
        this.sentMsgTxt = sentMsgTxt;
    }
}
