package com.example.bookmanageapp.featureclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

public class UserAccount implements Parcelable {
    private String uId = null;
    private String password = null;
    private String name = null;
    private int age = 0;
    private String address = null;
    private String genre = null;
    private boolean isAdmin = false;

    private SharedPreferences mSPref;

    public UserAccount() {
    }

    public UserAccount(String id, String pw) {
        this.uId = id;
        this.password = pw;
    }

    public UserAccount(String uId, String password, String name, int age, String address, String genre, boolean isAdmin) {
        this.uId = uId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.address = address;
        this.genre = genre;
        this.isAdmin = isAdmin;
    }

    protected UserAccount(Parcel in) {
        uId = in.readString();
        password = in.readString();
        name = in.readString();
        age = in.readInt();
        address = in.readString();
        genre = in.readString();
        isAdmin = in.readByte() != 0;
    }

    public static final Creator<UserAccount> CREATOR = new Creator<UserAccount>() {
        @Override
        public UserAccount createFromParcel(Parcel in) {
            return new UserAccount(in);
        }

        @Override
        public UserAccount[] newArray(int size) {
            return new UserAccount[size];
        }
    };

    // user class method
    public boolean isLogin(Context context) {
        mSPref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        uId = mSPref.getString(ConstantValue.SHARED_PREFERENCE_ID_VALUE, null);
        password = mSPref.getString(ConstantValue.SHARED_PREFERENCE_PASSWORD_VALUE, null);

        return uId != null && password != null;
    }

    public boolean tryLogin(Context context) {
        if (uId == null || password == null) {
            UseLog.w("ID or Password is not available.");
            return false;
        }
        mSPref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSPref.edit();
        editor.putString(ConstantValue.SHARED_PREFERENCE_ID_VALUE, uId);
        editor.putString(ConstantValue.SHARED_PREFERENCE_PASSWORD_VALUE, password);
        if (editor.commit()) {
            return true;
        } else {
            UseLog.w("Something was wrong during SharedPreferences logic");
            return false;
        }
    }

    public void tryLogout(Context context) {
        uId = null;
        password = null;

        SharedPreferences pref = context.getSharedPreferences(ConstantValue.SHARED_PREFERENCE_FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uId);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(address);
        parcel.writeString(genre);
    }

    public String getId() {
        return uId;
    }

    public void setId(String uId) {
        this.uId = uId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}