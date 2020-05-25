package com.example.flupperassignment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.flupperassignment.databinding.ActivityUpdateProductBinding;
import com.example.flupperassignment.databinding.CitychooserBinding;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class UpdateProductActivity extends AppCompatActivity {
    String filePath = null;
    Product product;
    MyColorAdapter mMyColorAdapter;
    MyCityAdapter mMyCityAdapter;
    private ActivityUpdateProductBinding mActivityUpdateProductBinding;
    private ArrayList<Color> mListOfColor;
    private ArrayList<City> mListOfCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityUpdateProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_product);
        product = getIntent().getParcelableExtra(ConstantVariables.PRODUCT);
        mActivityUpdateProductBinding.setProduct(product);
        filePath = product.getProductPhoto();

        UpdateProductActivityClickHandlers mUpdateProductActivityClickHandlers = new UpdateProductActivityClickHandlers();
        mActivityUpdateProductBinding.setClickHandlers(mUpdateProductActivityClickHandlers);

        mListOfColor = new ArrayList<>();
        mListOfColor.addAll(product.mColorList);

        mListOfCity = new ArrayList<>();
        mListOfCity.addAll(product.mCityList);

        addAndSetColorandCityAdapter();

        Glide.with(UpdateProductActivity.this)
                .load(Uri.parse(product.getProductPhoto()))
                .centerCrop()
                .into(mActivityUpdateProductBinding.productImage);
    }

    private void addAndSetColorandCityAdapter() {
        mMyColorAdapter = new MyColorAdapter(mListOfColor, ConstantVariables.ADD_PRODUCT_PAGE);
        mActivityUpdateProductBinding.setMyColorAdapter(mMyColorAdapter);

        mMyCityAdapter = new MyCityAdapter(mListOfCity);
        mActivityUpdateProductBinding.setMyCityAdapter(mMyCityAdapter);
    }

    private void setDataToMainActivity() {
        Intent intent = new Intent();
        Product newProduct = new Product(mActivityUpdateProductBinding.name.getText().toString(),
                mActivityUpdateProductBinding.description.getText().toString(),
                mActivityUpdateProductBinding.regularPrice.getText().toString(),
                mActivityUpdateProductBinding.salesPrice.getText().toString(),
                filePath);
        newProduct.setId(product.getId());
        newProduct.setColorList(mListOfColor);
        newProduct.setCityList(mListOfCity);
        mActivityUpdateProductBinding.setProduct(newProduct);
        intent.putExtra(ConstantVariables.PRODUCT, newProduct);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), ConstantVariables.ACTIVITY_SELECT_IMAGE);
    }

    private void addColorToProduct() {
        openDialogToChooseColor();
    }

    private void openDialogToChooseColor() {
        ColorPickerDialogBuilder
                .with(UpdateProductActivity.this)
                .setTitle("Choose color")
                .initialColor(R.color.colorAccent)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        Color color = new Color(selectedColor);
                        mListOfColor.add(color);
                        mMyColorAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ConstantVariables.ACTIVITY_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    if (data != null && data.getData() != null) {
                        try {
                            Bitmap bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(UpdateProductActivity.this.getContentResolver(), data.getData()), 250, 250, false);
                            if (bitmap != null) {
                                Glide.with(UpdateProductActivity.this)
                                        .load(bitmap)
                                        .centerCrop()
                                        .into(mActivityUpdateProductBinding.productImage);
                                filePath = data.getData().toString();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    private void openDialogToEnterCity() {

        Dialog dialog = new Dialog(UpdateProductActivity.this);

        CitychooserBinding mCitychooserBinding = DataBindingUtil.inflate(
                dialog.getLayoutInflater(), R.layout.citychooser, null, false);

        mCitychooserBinding.okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City city = new City(mCitychooserBinding.city.getText().toString());
                mListOfCity.add(city);
                mMyCityAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        mCitychooserBinding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        dialog.setContentView(mCitychooserBinding.getRoot());
        dialog.show();
        dialog.getWindow().setLayout((6 * width) / 7, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    public class UpdateProductActivityClickHandlers {
        public void onUpdateButtonClicked(View view) {
            setDataToMainActivity();
        }

        public void OnAddStoreButtonClicked(View view) {
            openDialogToEnterCity();
        }

        public void onChangePhotoClicked(View view) {
            openGallery();
        }

        public void OnAddColorButtonClicked(View view) {
            addColorToProduct();
        }
    }
}
