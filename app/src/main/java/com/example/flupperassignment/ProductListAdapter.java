package com.example.flupperassignment;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flupperassignment.databinding.ItemProductlistBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private ArrayList<Product> mProductList; // Cached copy of words
    private Context mContext;

    ProductListAdapter(Context context, ArrayList<Product> mProductList) {
        mContext = context;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemProductlistBinding mItemProductlistBinding = ItemProductlistBinding.inflate(layoutInflater, parent, false);
        return new ProductViewHolder(mItemProductlistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mProductList.get(position);
        holder.bind(product);
    }

    void setProduct(List<Product> products) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MyDiffUtilCallBack(products, mProductList));
        diffResult.dispatchUpdatesTo(this);
        if (!mProductList.isEmpty()) {
            mProductList.clear();
        }
        this.mProductList.addAll(products);
    }

    @Override
    public int getItemCount() {
        return (mProductList != null ? mProductList.size() : 0);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ItemProductlistBinding mItemProductlistBinding;

        public ProductViewHolder(ItemProductlistBinding mItemProductlistBinding) {
            super(mItemProductlistBinding.getRoot());
            this.mItemProductlistBinding = mItemProductlistBinding;
        }

        public void bind(Product product) {
            mItemProductlistBinding.setProduct(product);
            mItemProductlistBinding.executePendingBindings();
            mItemProductlistBinding.productRegularPrice
                    .setPaintFlags(mItemProductlistBinding.productRegularPrice.getPaintFlags()
                            | Paint.STRIKE_THRU_TEXT_FLAG);
            Glide.with(mContext)
                    .load(Uri.parse(product.getProductPhoto()))
                    .centerCrop()
                    .override(200, 200)
                    .into(mItemProductlistBinding.productPhoto);
            MyColorAdapter mMyColorAdapter = new MyColorAdapter(product.mColorList, ConstantVariables.PRODUCT_PAGE);
            mItemProductlistBinding.setMyAdapter(mMyColorAdapter);
            MyCityAdapter mMyCityAdapter = new MyCityAdapter(product.mCityList);
            mItemProductlistBinding.setMyCityAdapter(mMyCityAdapter);
        }
    }
}
