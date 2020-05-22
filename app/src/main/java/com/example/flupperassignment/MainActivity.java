package com.example.flupperassignment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    public final int ADD_PRODUCT_REQUEST_CODE = 1;
    public final int UPDATE_PRODUCT_REQUEST_CODE = 2;
    SwipeController swipeController = null;
    private ProductViewModel mProductViewModel;
    private ProductListAdapter adapter;
    private ArrayList<Product> mProductList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set Adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new ProductListAdapter(this, mProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

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
                if(mProductList.get(position)!=null){
                    Intent intent = new Intent(MainActivity.this, UpdateProductActivity.class);
                    intent.putExtra(ConstantVariables.PRODUCT,mProductList.get(position));
                    startActivityForResult(intent, UPDATE_PRODUCT_REQUEST_CODE);
                }
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        //Set ViewModel
        mProductViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        mProductViewModel.getAllProducts().observe(this, this::consumeProductResponse);


        //Button to Add new Product
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivityForResult(intent, ADD_PRODUCT_REQUEST_CODE);
            }
        });

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
                && data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_NAME) != null
                && data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_DESCRIPTION) != null
                && data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_REGULAR_PRICE) != null
                && data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_SALES_PRICE) != null
                && data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_IMAGE) != null) {
            Product product = new Product(data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_NAME),
                    data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_DESCRIPTION),
                    data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_REGULAR_PRICE),
                    data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_SALES_PRICE),
                    data.getStringExtra(ConstantVariables.EXTRA_PRODUCT_IMAGE));
            if(requestCode==ADD_PRODUCT_REQUEST_CODE)
            mProductViewModel.insert(product);
            else if(requestCode==UPDATE_PRODUCT_REQUEST_CODE){
                product.setId(data.getIntExtra(ConstantVariables.EXTRA_PRODUCT_ID,-1));
                mProductViewModel.update(product);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Blank Data", Toast.LENGTH_LONG).show();
        }
    }
}
