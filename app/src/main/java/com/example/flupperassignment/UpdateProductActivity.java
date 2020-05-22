package com.example.flupperassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class UpdateProductActivity extends AppCompatActivity {

    private ImageView mProductImage, mUpdatePhoto;
    private TextInputEditText mProductName, mProductDescription, mProductRegularPrice, mProductSalesPrice;
    private Button mUpdate;
    String filePath = null;
    Product product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        product = getIntent().getParcelableExtra(ConstantVariables.PRODUCT);
        filePath = product.getProductPhoto();
        init();

        Glide.with(UpdateProductActivity.this)
                .load(Uri.parse(product.getProductPhoto()))
                .centerCrop()
                .into(mProductImage);
        mProductName.setText(product.getName());
        mProductDescription.setText(product.getDescription());
        mProductRegularPrice.setText(product.getRegularPrice());
        mProductSalesPrice.setText(product.getSalePrice());

        mUpdatePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(mProductName.getText()) || TextUtils.isEmpty(mProductDescription.getText())
                        || TextUtils.isEmpty(mProductRegularPrice.getText()) || TextUtils.isEmpty(mProductSalesPrice.getText())
                        || filePath == null) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    intent.putExtra(ConstantVariables.EXTRA_PRODUCT_ID, product.getId());
                    intent.putExtra(ConstantVariables.EXTRA_PRODUCT_NAME, mProductName.getText().toString());
                    intent.putExtra(ConstantVariables.EXTRA_PRODUCT_DESCRIPTION, mProductDescription.getText().toString());
                    intent.putExtra(ConstantVariables.EXTRA_PRODUCT_REGULAR_PRICE, mProductRegularPrice.getText().toString());
                    intent.putExtra(ConstantVariables.EXTRA_PRODUCT_SALES_PRICE, mProductSalesPrice.getText().toString());
                    intent.putExtra(ConstantVariables.EXTRA_PRODUCT_IMAGE, filePath);
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), ConstantVariables.ACTIVITY_SELECT_IMAGE);
    }

    private void init() {
        mProductImage = findViewById(R.id.product_image);
        mUpdatePhoto = findViewById(R.id.update_photo);
        mProductName = findViewById(R.id.name);
        mProductDescription = findViewById(R.id.description);
        mProductRegularPrice = findViewById(R.id.regularPrice);
        mProductSalesPrice = findViewById(R.id.salesPrice);
        mUpdate = findViewById(R.id.save);

        mProductImage.setVisibility(View.VISIBLE);
        mUpdatePhoto.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        try {
                            Bitmap bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(UpdateProductActivity.this.getContentResolver(), data.getData()), 250, 250, false);
                            if (bitmap != null) {
                                Glide.with(UpdateProductActivity.this)
                                        .load(bitmap)
                                        .centerCrop()
                                        .into(mProductImage);
                                filePath = data.getData().toString();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }
}
