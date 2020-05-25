package com.example.flupperassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class Color implements Parcelable {
    public static final Creator<Color> CREATOR = new Creator<Color>() {
        @Override
        public Color createFromParcel(Parcel in) {
            return new Color(in);
        }

        @Override
        public Color[] newArray(int size) {
            return new Color[size];
        }
    };
    private Integer color;

    public Color(Integer color) {
        this.color = color;
    }

    protected Color(Parcel in) {
        if (in.readByte() == 0) {
            color = null;
        } else {
            color = in.readInt();
        }
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (color == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(color);
        }
    }
}
