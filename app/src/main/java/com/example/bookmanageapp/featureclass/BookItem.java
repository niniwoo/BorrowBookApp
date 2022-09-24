package com.example.bookmanageapp.featureclass;

import android.os.Parcel;
import android.os.Parcelable;

public class BookItem implements Parcelable {
    private int bookID;
    private String title;
    private String author;
    private String publisher;
    private String publishYear;
    private String ownerID = null;
    private String renterID = null;
    private String status = null;
    private float rentFee = 0;
    private boolean isRead = false;

    public BookItem () {

    }

    public BookItem(int bookID, String title, String author, String publisher, String publishYear, String ownerID, String renterID, String status, float rentFee, boolean isRead) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.ownerID = ownerID;
        this.renterID = renterID;
        this.status = status;
        this.rentFee = rentFee;
        this.isRead = isRead;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getRenterID() {
        return renterID;
    }

    public void setRenterID(String renterID) {
        this.renterID = renterID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getRentFee() {
        return rentFee;
    }

    public void setRentFee(float rentFee) {
        this.rentFee = rentFee;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    protected BookItem(Parcel in) {
        bookID = in.readInt();
        title = in.readString();
        author = in.readString();
        publisher = in.readString();
        publishYear = in.readString();
        ownerID = in.readString();
        renterID = in.readString();
        status = in.readString();
        rentFee = in.readFloat();
        isRead = in.readByte() != 0;
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(bookID);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(publisher);
        parcel.writeString(publishYear);
        parcel.writeString(ownerID);
        parcel.writeString(renterID);
        parcel.writeString(status);
        parcel.writeFloat(rentFee);
        parcel.writeByte((byte) (isRead ? 1 : 0));
    }
}
