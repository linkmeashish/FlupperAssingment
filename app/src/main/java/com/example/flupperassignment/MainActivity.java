package com.example.flupperassignment;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flupperassignment.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final int ADD_PRODUCT_REQUEST_CODE = 1, UPDATE_PRODUCT_REQUEST_CODE = 2;
    SwipeController swipeController = null;
    private ProductViewModel mProductViewModel;
    private ProductListAdapter adapter;
    private ArrayList<Product> mProductList = new ArrayList<>();

    private ActivityMainBinding mActivityMainBinding;
    private MainActivityClickHandlers mMainActivityClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mMainActivityClickHandlers = new MainActivityClickHandlers();
        mActivityMainBinding.setClickHandlers(mMainActivityClickHandlers);

        addAndSetProductListAdapter();

        // Swipe Control for Delete and Update
        swipeController = new SwipeController(MainActivity.this, new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                //Delete the product
                mProductViewModel.delete(mProductList.get(position));
            }

            @Override
            public void onLeftClicked(int position) {
                //Update the product
                if (mProductList.get(position) != null) {
                    Intent intent = new Intent(MainActivity.this, UpdateProductActivity.class);
                    intent.putExtra(ConstantVariables.PRODUCT, mProductList.get(position));
                    startActivityForResult(intent, UPDATE_PRODUCT_REQUEST_CODE);
                }
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(mActivityMainBinding.recyclerview);

        mActivityMainBinding.recyclerview.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        //Set ViewModel
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductViewModel.getAllProducts().observe(this, this::consumeProductResponse);

    }

    private void addAndSetProductListAdapter() {
        adapter = new ProductListAdapter(this, mProductList);
        mActivityMainBinding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        mActivityMainBinding.recyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mActivityMainBinding.setAdapter(adapter);
    }

    private void openAddProductActivity() {
        Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
        startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE);
    }

    private void consumeProductResponse(List<Product> products) {
        ArrayList<Product> mUpdatedProductList = new ArrayList<>();
        mUpdatedProductList.addAll(products);
        adapter.setProduct(mUpdatedProductList);

        if (!mProductList.isEmpty()) {
            mProductList.clear();
        }
        mProductList.addAll(mUpdatedProductList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == ADD_PRODUCT_REQUEST_CODE || requestCode == UPDATE_PRODUCT_REQUEST_CODE) && resultCode == RESULT_OK && data != null
                && data.getParcelableExtra(ConstantVariables.PRODUCT) != null) {
            Product product = data.getParcelableExtra(ConstantVariables.PRODUCT);
            if (requestCode == ADD_PRODUCT_REQUEST_CODE)
                mProductViewModel.insert(product);
            else if (requestCode == UPDATE_PRODUCT_REQUEST_CODE) {
                mProductViewModel.update(product);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Blank Data", Toast.LENGTH_LONG).show();
        }
    }

    public class MainActivityClickHandlers {
        public void onFabButtonClicked(View view) {
            openAddProductActivity();
        }
    }
}
