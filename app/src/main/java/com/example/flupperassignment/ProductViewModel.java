package com.example.flupperassignment;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private ProductRepository mProductRepository;
    private LiveData<List<Product>> mAllProducts;

    public ProductViewModel(Application application) {
        super(application);
        mProductRepository = new ProductRepository(application);
        mAllProducts = mProductRepository.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    void insert(Product product) {
        mProductRepository.insert(product);
    }

    void update(Product product) {
        mProductRepository.update(product);
    }

    void delete(Product product) {
        mProductRepository.delete(product);
    }
}
