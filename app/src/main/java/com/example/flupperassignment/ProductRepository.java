package com.example.flupperassignment;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class ProductRepository {
    private ProductDao mProductDao;
    private LiveData<List<Product>> mAllProducts;

    ProductRepository(Application application) {
        ProductRoomDatabase mProductRoomDb = ProductRoomDatabase.getDatabase(application);
        mProductDao = mProductRoomDb.productDao();
        mAllProducts = mProductDao.getAllProducts();
    }

    LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    void insert(Product product) {
        ProductRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.insert(product);
        });
    }

    void update(Product product) {
        ProductRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.update(product);
        });
    }

    void delete(Product product) {
        ProductRoomDatabase.databaseWriteExecutor.execute(() -> {
            mProductDao.delete(product);
        });
    }
}
