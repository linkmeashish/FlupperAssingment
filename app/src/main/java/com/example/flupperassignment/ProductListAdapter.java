package com.example.flupperassignment;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {

    private final LayoutInflater mInflater;
    private ArrayList<Product> mProductList; // Cached copy of words
    private Context mContext;

    ProductListAdapter(Context context, ArrayList<Product> mProductList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        this.mProductList = mProductList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (mProductList != null) {
            Product product = mProductList.get(position);
            holder.mName.setText(product.getName());
            holder.mDescription.setText(product.getDescription());
            holder.mRegularPrice.setText("₹ " + product.getRegularPrice());
            holder.mRegularPrice.setPaintFlags(holder.mRegularPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mSalePrice.setText("₹ " + product.getSalePrice());

            Glide.with(mContext)
                    .load(Uri.parse(product.getProductPhoto()))
                    .centerCrop()
                    .override(200, 200)
                    .into(holder.mProductImage);

        }
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
        private TextView mName, mDescription, mRegularPrice, mSalePrice;
        private ImageView mProductImage;

        private ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.product_title);
            mDescription = itemView.findViewById(R.id.product_description);
            mRegularPrice = itemView.findViewById(R.id.product_regularPrice);
            mSalePrice = itemView.findViewById(R.id.product_salePrice);
            mProductImage = itemView.findViewById(R.id.product_photo);
        }
    }
}
