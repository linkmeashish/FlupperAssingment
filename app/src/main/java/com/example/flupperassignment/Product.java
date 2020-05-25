package com.example.flupperassignment;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity(tableName = "product_table")
public class Product implements Comparable, Cloneable, Parcelable {

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    @TypeConverters(Converters.class)
    @NonNull
    @ColumnInfo(name = "colorlist")
    public ArrayList<Color> mColorList = new ArrayList<>();
    @TypeConverters(Converters.class)
    @NonNull
    @ColumnInfo(name = "citylist")
    public ArrayList<City> mCityList = new ArrayList<>();
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer mId;
    @NonNull
    @ColumnInfo(name = "name")
    private String mName;
    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription;
    @NonNull
    @ColumnInfo(name = "regular_price")
    private String mRegularPrice;
    @NonNull
    @ColumnInfo(name = "sale_price")
    private String mSalePrice;
    @NonNull
    @ColumnInfo(name = "product_photo")
    private String mProductPhoto;

    public Product(@NonNull String mName, @NonNull String mDescription, @NonNull String mRegularPrice, @NonNull String mSalePrice, @NonNull String mProductPhoto) {
        this.mName = mName;
        this.mDescription = mDescription;
        this.mRegularPrice = mRegularPrice;
        this.mSalePrice = mSalePrice;
        this.mProductPhoto = mProductPhoto;
    }

    protected Product(Parcel in) {
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readInt();
        }
        mName = in.readString();
        mDescription = in.readString();
        mRegularPrice = in.readString();
        mSalePrice = in.readString();
        mProductPhoto = in.readString();
        mColorList = in.createTypedArrayList(Color.CREATOR);
        mCityList = in.createTypedArrayList(City.CREATOR);
    }

    @NonNull
    public Integer getId() {
        return mId;
    }

    public void setId(@NonNull Integer mId) {
        this.mId = mId;
    }

    @NonNull
    public String getName() {
        return this.mName;
    }

    public void setName(@NonNull String mName) {
        this.mName = mName;
    }

    @NonNull
    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(@NonNull String mDescription) {
        this.mDescription = mDescription;
    }

    @NonNull
    public String getRegularPrice() {
        return this.mRegularPrice;
    }

    public void setRegularPrice(@NonNull String mRegularPrice) {
        this.mRegularPrice = mRegularPrice;
    }

    @NonNull
    public String getSalePrice() {
        return this.mSalePrice;
    }

    public void setSalePrice(@NonNull String mSalePrice) {
        this.mSalePrice = mSalePrice;
    }

    @NonNull
    public String getProductPhoto() {
        return mProductPhoto;
    }

    public void setProductPhoto(@NonNull String mProductPhoto) {
        this.mProductPhoto = mProductPhoto;
    }

    @NonNull
    public ArrayList<Color> getColorList() {
        return mColorList;
    }

    public void setColorList(@NonNull ArrayList<Color> mColorList) {
        this.mColorList = mColorList;
    }

    @NonNull
    public ArrayList<City> getCityList() {
        return mCityList;
    }

    public void setCityList(@NonNull ArrayList<City> mCityList) {
        this.mCityList = mCityList;
    }

    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;
        if (product.mId.equals(((Product) o).mId) &&
                product.mName.equals(((Product) o).mName) &&
                product.mDescription.equals(((Product) o).mDescription) &&
                product.mRegularPrice.equals(((Product) o).mRegularPrice) &&
                product.mSalePrice.equals(((Product) o).mSalePrice) &&
                product.mProductPhoto.equals(((Product) o).mProductPhoto) &&
                product.mColorList.equals(((Product) o).mColorList) &&
                product.mCityList.equals(((Product) o).mCityList)) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public Product clone() {
        Product product;
        try {
            product = (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return product;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mId);
        }
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mRegularPrice);
        dest.writeString(mSalePrice);
        dest.writeString(mProductPhoto);
        dest.writeTypedList(mColorList);
        dest.writeTypedList(mCityList);
    }
}
