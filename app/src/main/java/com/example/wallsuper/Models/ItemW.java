package com.example.wallsuper.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemW implements Parcelable {
    private int image;
    private String text;

    public ItemW(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return this.image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    protected ItemW(Parcel in) {
        this.image = in.readInt();
        this.text = in.readString();
    }

    public static final Creator<ItemW> CREATOR = new Creator<ItemW>() {
        @Override
        public ItemW createFromParcel(Parcel in) {
            return new ItemW(in);
        }

        @Override
        public ItemW[] newArray(int size) {
            return new ItemW[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.image);
        dest.writeString(this.text);
    }
}
