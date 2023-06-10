package com.lxndrrud.ksyukulyator.providers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.lxndrrud.ksyukulyator.utils.toastMaker.IToastMaker;
import com.lxndrrud.ksyukulyator.utils.toastMaker.LongToastMaker;

public class DbProvider extends SQLiteOpenHelper {
    public DbProvider(Context context) {
        super(context, "ksyukulyator", null, 1);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String foreignKeysEnabledStatement = "PRAGMA foreign_keys = ON";
        sqLiteDatabase.execSQL(foreignKeysEnabledStatement);

        String categoriesStatement = "CREATE TABLE IF NOT EXISTS categories  ( " +
                "category_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR(100) NOT NULL " +
                ");";
        sqLiteDatabase.execSQL(categoriesStatement);
        String productsStatement = "CREATE TABLE IF NOT EXISTS products  ( " +
                "product_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title VARCHAR(100) NOT NULL, " +
                "cost FLOAT NOT NULL," +
                "category_id INTEGER," +
                "FOREIGN KEY(category_id) REFERENCES categories(category_id) ON UPDATE CASCADE ON DELETE SET NULL" +
                ");";
        sqLiteDatabase.execSQL(productsStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String productsStatement = "DROP TABLE IF EXISTS products";
        sqLiteDatabase.execSQL(productsStatement);
        String categoriesStatement = "DROP TABLE IF EXISTS categories";
        sqLiteDatabase.execSQL(categoriesStatement);
    }
}
