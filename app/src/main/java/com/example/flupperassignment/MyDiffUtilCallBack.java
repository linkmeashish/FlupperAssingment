package com.example.flupperassignment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class MyDiffUtilCallBack extends DiffUtil.Callback {
    List<Product> newList;
    List<Product> oldList;

    public MyDiffUtilCallBack(List<Product> newList, List<Product> oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return (newList.get(newItemPosition).getId() == oldList.get(oldItemPosition).getId() &&
                newList.get(newItemPosition).getName().equals(oldList.get(oldItemPosition).getName()) &&
                newList.get(newItemPosition).getDescription().equals(oldList.get(oldItemPosition).getDescription()) &&
                newList.get(newItemPosition).getRegularPrice().equals(oldList.get(oldItemPosition).getRegularPrice()) &&
                newList.get(newItemPosition).getSalePrice().equals(oldList.get(oldItemPosition).getSalePrice()) &&
                newList.get(newItemPosition).getProductPhoto().equals(oldList.get(oldItemPosition).getProductPhoto()) &&
                newList.get(newItemPosition).getColorList().equals(oldList.get(oldItemPosition).getColorList()));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = newList.get(newItemPosition).compareTo(oldList.get(oldItemPosition));
        return result == 0;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Product newProductList = newList.get(newItemPosition);
        Product oldProductList = oldList.get(oldItemPosition);

        Bundle diff = new Bundle();

        if (newProductList.getId() == oldProductList.getId()) {
            diff.putInt("id", newProductList.getId());
        }
        if (!newProductList.getName().equals(oldProductList.getName())) {
            diff.putString("name", newProductList.getName());
        }
        if (!newProductList.getDescription().equals(oldProductList.getDescription())) {
            diff.putString("description", newProductList.getDescription());
        }
        if (!newProductList.getRegularPrice().equals(oldProductList.getRegularPrice())) {
            diff.putString("regular_price", newProductList.getRegularPrice());
        }
        if (!newProductList.getSalePrice().equals(oldProductList.getSalePrice())) {
            diff.putString("sale_price", newProductList.getSalePrice());
        }
        if (!newProductList.getProductPhoto().equals(oldProductList.getProductPhoto())) {
            diff.putString("product_photo", newProductList.getProductPhoto());
        }
        if (!newProductList.getColorList().equals(oldProductList.getColorList())) {
            diff.putParcelableArrayList("colorlist", newProductList.getColorList());
        }
        if (diff.size() == 0) {
            return null;
        }
        return diff;
    }
}
