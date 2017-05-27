package com.campus.android.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lebron on 17-5-27.
 */

public class UserModel extends BmobObject implements Parcelable {
    private String studentId;
    private String name;
    private String gender;
    private String phone;
    private String campus;
    private String password;

    public void clear() {
        studentId = "";
        name = "";
        gender = "";
        phone = "";
        campus = "";
        password = "";
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.studentId);
        dest.writeString(this.name);
        dest.writeString(this.gender);
        dest.writeString(this.phone);
        dest.writeString(this.campus);
        dest.writeString(this.password);
        dest.writeString(getObjectId());
    }

    public UserModel() {
    }

    protected UserModel(Parcel in) {
        this.studentId = in.readString();
        this.name = in.readString();
        this.gender = in.readString();
        this.phone = in.readString();
        this.campus = in.readString();
        this.password = in.readString();
        setObjectId(in.readString());
    }

    public static final Parcelable.Creator<UserModel> CREATOR = new Parcelable.Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
