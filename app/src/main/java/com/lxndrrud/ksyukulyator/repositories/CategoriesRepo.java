package com.lxndrrud.ksyukulyator.repositories;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.lxndrrud.ksyukulyator.domain.Category;
import com.lxndrrud.ksyukulyator.providers.DbProvider;

import java.util.ArrayList;
import java.util.List;

public class CategoriesRepo  {
    private DbProvider dbProvider;
    public CategoriesRepo(Context context) {
        dbProvider = new DbProvider(context);
    }


    public List<Category> getAll(boolean needEmpty) {
        SQLiteDatabase db = dbProvider.getReadableDatabase();

        String statement = "SELECT c.category_id, c.title " +
                "FROM categories c " +
                "ORDER BY c.title ASC ";
        Cursor cursorItems = db.rawQuery(statement, null);

        ArrayList<Category> itemList = new ArrayList<>();
        if (needEmpty) {
            Category emptyCategory = new Category(
                    0, "Без категории"
            );
            itemList.add(emptyCategory);
        }

        if (cursorItems.moveToFirst()) {
            do {
                itemList.add(new Category(
                        cursorItems.getLong(0),
                        cursorItems.getString(1)
                ));
            }
            while(cursorItems.moveToNext());
        }
        cursorItems.close();
        db.close();
        return itemList;
    }

    public void createCategory(String title) {
        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "INSERT INTO categories (title) VALUES (?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, title);
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }

    public void deleteCategory(long categoryId) {
        SQLiteDatabase writeDb = dbProvider.getWritableDatabase();
        writeDb.execSQL("UPDATE products SET category_id = NULL WHERE category_id = " + categoryId);
        writeDb.close();

        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "DELETE FROM categories WHERE category_id = ?";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindLong(1, categoryId);
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }

    public Category getCategory(long categoryId) {
        SQLiteDatabase db = dbProvider.getReadableDatabase();

        String statement ="SELECT c.category_id, c.title " +
                "FROM categories c " +
                "WHERE c.category_id = " + categoryId + " " +
                "ORDER BY c.title ASC ";
        Cursor cursorItems = db.rawQuery(statement, null);
        Category resultCategory = null;
        if (cursorItems.moveToFirst()) {
            resultCategory = new Category(
                    cursorItems.getLong(0),
                    cursorItems.getString(1)
            );
        }
        cursorItems.close();
        db.close();
        return resultCategory;
    }

    public void updateCategory(Category category) {
        SQLiteDatabase db = dbProvider.getWritableDatabase();

        String sql = "UPDATE categories SET title = ? WHERE category_id = ? ;";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, category.getTitle());
        stmt.bindLong(2, category.getId());
        stmt.execute();
        stmt.clearBindings();
        db.close();
    }
}
