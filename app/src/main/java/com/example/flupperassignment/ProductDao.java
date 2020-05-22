package com.example.flupperassignment;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * from product_table")
    LiveData<List<Product>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Product mProduct);

    @Query("DELETE FROM product_table")
    void deleteAll();

    @Delete
    void delete(Product product);

    @Update
    void update(Product product);

    @Query("SELECT COUNT(name) FROM product_table")
    int getRowCount();
}
