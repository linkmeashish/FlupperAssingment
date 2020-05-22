package com.example.flupperassignment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class AddProductActivity extends AppCompatActivity {
    RelativeLayout mLayoutImage;
    ImageView mProductImage, mAddPhoto;
    TextInputEditText mProductName, mProductDescription, mProductRegularPrice, mProductSalesPrice;
    Button save;
    String filePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        mLayoutImage = findViewById(R.id.layout_image);
        mProductImage = findViewById(R.id.product_image);
        mAddPhoto = findViewById(R.id.add_photo);
        mProductName = findViewById(R.id.name);
        mProductDescription = findViewById(R.id.description);
        mProductRegularPrice = findViewById(R.id.regularPrice);
        mProductSalesPrice = findViewById(R.id.salesPrice);
        save = findViewById(R.id.save);

        mAddPhoto.setVisibility(View.VISIBLE);
        mProductImage.setVisibility(View.GONE);


        mAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (TextUtils.isEmpty(mProductName.getText()) || TextUtils.isEmpty(mProductDescription.getText())
                        || TextUtils.isEmpty(mProductRegularPrice.getText()) || TextUtils.isEmpty(mProductSalesPrice.getText())
                        || filePath == null) {
                    setResult(RESULT_CANCELED, intent);
                } else {
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        try {
                            Bitmap bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(AddProductActivity.this.getContentResolver(), data.getData()), 250, 250, false);
                            if (bitmap != null) {
                                Glide.with(AddProductActivity.this)
                                        .load(bitmap)
                                        .centerCrop()
                                        .into(mProductImage);
                                mAddPhoto.setVisibility(View.GONE);
                                mProductImage.setVisibility(View.VISIBLE);
                                filePath = data.getData().toString();

                            } else {
                                filePath = null;
                                mAddPhoto.setVisibility(View.VISIBLE);
                                mProductImage.setVisibility(View.GONE);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            filePath = null;
                            mAddPhoto.setVisibility(View.VISIBLE);
                            mProductImage.setVisibility(View.GONE);
                        }
                    } else {
                        filePath = null;
                        mAddPhoto.setVisibility(View.VISIBLE);
                        mProductImage.setVisibility(View.GONE);
                    }
                }
        }
    }
}

