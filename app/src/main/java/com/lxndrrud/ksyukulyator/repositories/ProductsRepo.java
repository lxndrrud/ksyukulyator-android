package com.lxndrrud.ksyukulyator.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import androidx.annotation.NonNull;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.domain.Product;
import com.lxndrrud.ksyukulyator.providers.DbProvider;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepo {
    private DbProvider dbProvider;

    public ProductsRepo(Context context) {
        dbProvider = new DbProvider(context);
    }

    public List<Product> getAll() {
        SQLiteDatabase db = dbProvider.getReadableDatabase();

        String statement ="SELECT p.product_id, p.title, p.cost, " +
                "CASE WHEN p.category_id IS NOT NULL THEN p.category_id ELSE 0 END, " +
                "CASE WHEN p.category_id IS NOT NULL THEN c.title ELSE 'Без категории' END " +
                "FROM products p " +
                "LEFT JOIN categories AS c ON c.category_id = p.category_id " +
                "ORDER BY p.title ASC ";
        Cursor cursorItems = db.rawQuery(statement, null);
        ArrayList<Product> itemList = new ArrayList<>();

        if (cursorItems.moveToFirst()) {
            do {
                itemList.add(new Product(
                        cursorItems.getLong(0),
                        cursorItems.getString(1),
                        cursorItems.getFloat(2),
                        new Category(
                                cursorItems.getLong(3),
                                cursorItems.getString(4)
                        )
                ));
            }
            while(cursorItems.moveToNext());
        }
        cursorItems.close();
        db.close();
        return itemList;
    }

    public List<Product> getSelectedForCalculation() {
        SQLiteDatabase db = dbProvider.getReadableDatabase();

        String statement ="SELECT p.product_id, p.title, p.cost, " +
                "CASE WHEN p.category_id IS NOT NULL THEN p.category_id ELSE 0 END, " +
                "CASE WHEN p.category_id IS NOT NULL THEN c.title ELSE 'Без категории' END " +
                "FROM products p " +
                "LEFT JOIN categories AS c ON c.category_id = p.category_id " +
                "WHERE p.is_selected_to_calculation = 1 " +
                "ORDER BY p.title ASC ";
        Cursor cursorItems = db.rawQuery(statement, null);
        ArrayList<Product> itemList = new ArrayList<>();

        if (cursorItems.moveToFirst()) {
            do {
                itemList.add(new Product(
                        cursorItems.getLong(0),
                        cursorItems.getString(1),
                        cursorItems.getFloat(2),
                        new Category(
                                cursorItems.getLong(3),
                                cursorItems.getString(4)
                        )
                ));
            }
            while(cursorItems.moveToNext());
        }
        cursorItems.close();
        db.close();
        return itemList;
    }

    public void createProduct(String title, float cost, Long categoryId) {
        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "INSERT INTO products (title, cost, category_id) VALUES (?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, title);
        stmt.bindDouble(2, cost);
        if (categoryId != null) {
            stmt.bindLong(3, categoryId);
        } else {
            stmt.bindNull(3);
        }
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }

    public void deleteProduct(long productId) {
        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "DELETE FROM products WHERE product_id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, productId);
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }

    public Product getProduct(long productId) {
        SQLiteDatabase db = dbProvider.getReadableDatabase();

        String statement ="SELECT p.product_id, p.title, p.cost, " +
                "CASE WHEN p.category_id IS NOT NULL THEN p.category_id ELSE 0 END, " +
                "CASE WHEN p.category_id IS NOT NULL THEN c.title ELSE 'Без категории' END " +
                "FROM products p " +
                "LEFT JOIN categories AS c ON c.category_id = p.category_id " +
                "WHERE p.product_id = " + productId + " " +
                "ORDER BY p.title ASC ";
        Cursor cursorItems = db.rawQuery(statement, null);
        Product resultProduct = null;
        if (cursorItems.moveToFirst()) {
            resultProduct = new Product(
                    cursorItems.getLong(0),
                    cursorItems.getString(1),
                    cursorItems.getFloat(2),
                    new Category(
                            cursorItems.getLong(3),
                            cursorItems.getString(4)
                    )
            );
        }
        cursorItems.close();
        db.close();
        return resultProduct;
    }

    public void updateProduct(@NonNull Product product) {
        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "UPDATE products SET title = ?, cost = ?, category_id = ? WHERE product_id = ? ;";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, product.getTitle());
        stmt.bindDouble(2, product.getCost());
        if (product.getCategory() != null) {
            stmt.bindLong(3, product.getCategory().getId());
        } else {
            stmt.bindNull(3);
        }
        stmt.bindLong(4, product.getId());
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }

    public void updateIsSelectedForCalculation(@NonNull Product product, boolean value) {
        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "UPDATE products SET is_selected_to_calculation = ? WHERE product_id = ? ;";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, value ? 1L : 0L);
        stmt.bindLong(2, product.getId());
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }
}
