package com.example.flupperassignment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.flupperassignment.databinding.ActivityAddProductBinding;
import com.example.flupperassignment.databinding.CitychooserBinding;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.io.IOException;
import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity {
    String filePath = null;
    MyCityAdapter mMyCityAdapter;
    MyColorAdapter mMyColorAdapter;
    private ArrayList<Color> mListOfColor;
    private ArrayList<City> mListOfCity;
    private ActivityAddProductBinding mActivityAddProductBinding;
    private AddProductActivityClickHandlers mAddProductActivityClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddProductBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_product);

        mAddProductActivityClickHandlers = new AddProductActivityClickHandlers();
        mActivityAddProductBinding.setClickHandlers(mAddProductActivityClickHandlers);

        mListOfColor = new ArrayList<>();
        mListOfCity = new ArrayList<>();

        addAndSetColorAndCityAdapter();
    }

    private void addAndSetColorAndCityAdapter() {
        mMyColorAdapter = new MyColorAdapter(mListOfColor, ConstantVariables.ADD_PRODUCT_PAGE);
        mActivityAddProductBinding.setMyAdapter(mMyColorAdapter);

        mMyCityAdapter = new MyCityAdapter(mListOfCity);
        mActivityAddProductBinding.setMyCityAdapter(mMyCityAdapter);
    }

    private void sendDataToMainActivity() {
        Intent intent = new Intent();
        Product product = new Product(mActivityAddProductBinding.name.getText().toString(),
                mActivityAddProductBinding.description.getText().toString(),
                mActivityAddProductBinding.regularPrice.getText().toString(),
                mActivityAddProductBinding.salesPrice.getText().toString(), filePath);
        product.setColorList(mListOfColor);
        product.setCityList(mListOfCity);
        intent.putExtra(ConstantVariables.PRODUCT, product);
        mActivityAddProductBinding.setProduct(product);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), ConstantVariables.ACTIVITY_SELECT_IMAGE);
    }

    private void openDialogToEnterCity() {

        Dialog dialog = new Dialog(AddProductActivity.this);

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

    private void addColorToProduct() {
        openDialogToChooseColor();
    }

    private void openDialogToChooseColor() {
        ColorPickerDialogBuilder
                .with(AddProductActivity.this)
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
                            Bitmap bitmap = Bitmap.createScaledBitmap(MediaStore.Images.Media.getBitmap(AddProductActivity.this.getContentResolver(), data.getData()), 250, 250, false);
                            if (bitmap != null) {
                                Glide.with(AddProductActivity.this)
                                        .load(bitmap)
                                        .centerCrop()
                                        .into(mActivityAddProductBinding.productImage);
                                mActivityAddProductBinding.addPhoto.setVisibility(View.GONE);
                                mActivityAddProductBinding.productImage.setVisibility(View.VISIBLE);
                                filePath = data.getData().toString();

                            } else {
                                setVisibilityOfImages();
                            }

                        } catch (IOException e) {
                            setVisibilityOfImages();
                        }
                    } else {
                        setVisibilityOfImages();
                    }
                }
        }
    }

    private void setVisibilityOfImages() {
        filePath = null;
        mActivityAddProductBinding.addPhoto.setVisibility(View.VISIBLE);
        mActivityAddProductBinding.productImage.setVisibility(View.GONE);
    }

    public class AddProductActivityClickHandlers {

        public void OnSaveButtonClicked(View view) {
            sendDataToMainActivity();
        }

        public void OnAddPhotoClicked(View view) {
            openGallery();
        }

        public void OnAddColorButtonClicked(View view) {
            addColorToProduct();
        }

        public void OnAddStoreButtonClicked(View view) {
            openDialogToEnterCity();
        }
    }


}

