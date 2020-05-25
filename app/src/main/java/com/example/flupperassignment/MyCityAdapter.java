package com.example.flupperassignment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flupperassignment.databinding.ItemCityBinding;

import java.util.ArrayList;

public class MyCityAdapter extends RecyclerView.Adapter<MyCityAdapter.CityViewHolder> {
    private ArrayList<City> mListOfCity;

    public MyCityAdapter(ArrayList<City> mListOfCity) {
        this.mListOfCity = mListOfCity;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCityBinding mItemCityBinding = ItemCityBinding.inflate(layoutInflater, parent, false);
        return new CityViewHolder(mItemCityBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = mListOfCity.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        return mListOfCity != null ? mListOfCity.size() : 0;
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        private ItemCityBinding mItemCityBinding;

        public CityViewHolder(ItemCityBinding mItemCityBinding) {
            super(mItemCityBinding.getRoot());
            this.mItemCityBinding = mItemCityBinding;
        }

        public void bind(City city) {
            mItemCityBinding.setCity(city);
            mItemCityBinding.executePendingBindings();
        }
    }
}
