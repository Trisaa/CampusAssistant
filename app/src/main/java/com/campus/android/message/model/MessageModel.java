package com.campus.android.message.model;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lebron on 17-5-27.
 */

public class MessageModel extends BmobObject implements Parcelable {
    private String title;
    private String content;
    private String coverImage;
    private int type;
    private String sourceUrl;
    private boolean isTop;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.coverImage);
        dest.writeInt(this.type);
        dest.writeString(this.sourceUrl);
        dest.writeByte(this.isTop ? (byte) 1 : (byte) 0);
        dest.writeString(getCreatedAt());
    }

    public MessageModel() {
    }

    protected MessageModel(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.coverImage = in.readString();
        this.type = in.readInt();
        this.sourceUrl = in.readString();
        this.isTop = in.readByte() != 0;
        setCreatedAt(in.readString());
    }

    public static final Parcelable.Creator<MessageModel> CREATOR = new Parcelable.Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel source) {
            return new MessageModel(source);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };
}
