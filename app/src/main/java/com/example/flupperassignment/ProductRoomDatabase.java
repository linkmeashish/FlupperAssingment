package com.example.flupperassignment;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Product.class}, version = 1, exportSchema = false)
abstract class ProductRoomDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile ProductRoomDatabase INSTANCE;
    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {
                ProductDao dao = INSTANCE.productDao();
                if (dao.getRowCount() <= 0) {
                    //Mocking 1st Product
                    Product product = new Product("Mac Book"
                            , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                            , "450"
                            , "400"
                            , Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.mackbook).toString());

                    ArrayList<Color> mList = new ArrayList<>();
                    Color color = new Color(2141568906);
                    mList.add(color);
                    color = new Color(2142800384);
                    mList.add(color);
                    product.setColorList(mList);

                    ArrayList<City> mCityList = new ArrayList<>();
                    City city = new City("Delhi");
                    mCityList.add(city);
                    city = new City("Chennai");
                    mCityList.add(city);
                    city = new City("Uttrakhand");
                    mCityList.add(city);
                    product.setCityList(mCityList);

                    dao.insert(product);

                    //Mocking 2nd Product
                    product = new Product("I-Phone"
                            , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                            , "250"
                            , "200"
                            , Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.phone).toString());

                    mList.clear();
                    color = new Color(2141568906);
                    mList.add(color);

                    color = new Color(2142800384);
                    mList.add(color);

                    product.setColorList(mList);


                    mCityList.clear();
                    city = new City("Delhi");
                    mCityList.add(city);
                    city = new City("Chennai");
                    mCityList.add(city);
                    city = new City("Uttrakhand");
                    mCityList.add(city);
                    product.setCityList(mCityList);

                    dao.insert(product);

                    //Mocking 3rd Product
                    product = new Product("I-Watch"
                            , "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
                            , "200"
                            , "150"
                            , Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + R.drawable.watch).toString());

                    mList.clear();
                    color = new Color(2141568906);
                    mList.add(color);

                    color = new Color(2142800384);
                    mList.add(color);

                    product.setColorList(mList);

                    mCityList.clear();
                    city = new City("Delhi");
                    mCityList.add(city);
                    city = new City("Chennai");
                    mCityList.add(city);
                    city = new City("Uttrakhand");
                    mCityList.add(city);
                    product.setCityList(mCityList);

                    dao.insert(product);
                }
            });
        }
    };

    static ProductRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductRoomDatabase.class, "product_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    abstract ProductDao productDao();
}
