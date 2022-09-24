package com.example.bookmanageapp.featureclass;

import java.util.Date;

public class ReadingHistory {
    private String userID;
    private int bookID;
    private String bookTitle;
    private String readDate;

    public ReadingHistory() {
    }

    public ReadingHistory(String userID, int bookID, String bookTitle, String readDate) {
        this.userID = userID;
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.readDate = readDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }
}
