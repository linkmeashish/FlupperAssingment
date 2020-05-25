package com.example.flupperassignment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flupperassignment.databinding.ItemColorBinding;

import java.util.ArrayList;

public class MyColorAdapter extends RecyclerView.Adapter<MyColorAdapter.ColorViewHolder> {
    private ArrayList<Color> mListOfColor;
    private String page;

    public MyColorAdapter(ArrayList<Color> mListOfColor, String page) {
        this.mListOfColor = mListOfColor;
        this.page = page;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemColorBinding mItemColorBinding = ItemColorBinding.inflate(layoutInflater, parent, false);
        return new ColorViewHolder(mItemColorBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        Color color = mListOfColor.get(position);
        holder.bind(color);
    }

    @Override
    public int getItemCount() {
        return mListOfColor != null ? mListOfColor.size() : 0;
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {
        private ItemColorBinding mItemColorBinding;

        public ColorViewHolder(ItemColorBinding mItemColorBinding) {
            super(mItemColorBinding.getRoot());
            this.mItemColorBinding = mItemColorBinding;
        }

        public void bind(Color color) {
            mItemColorBinding.setColor(color);
            mItemColorBinding.executePendingBindings();
            if (page.equals(ConstantVariables.PRODUCT_PAGE)) {
                setWidthandHeight(20);
            } else {
                setWidthandHeight(60);
            }
        }

        private void setWidthandHeight(int dimens) {
            mItemColorBinding.circleColor.getLayoutParams().height = dimens;
            mItemColorBinding.circleColor.getLayoutParams().width = dimens;
        }
    }
}
