package com.example.flupperassignment;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;

    }

    protected City(Parcel in) {
        cityName = in.readString();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cityName);
    }
}
