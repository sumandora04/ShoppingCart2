package com.notepoint4ugmail.shoppingcart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class ProductDatabase extends SQLiteOpenHelper {
    public ProductDatabase(Context context) {
        super(context, "Cart_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table shopping_cart" + "(id text,name text," +
                "price text,quantity text,imageUrl text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean db_addToCart(String id,String name, String price, String quantity,String imageUrl) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id",id);
        cv.put("name", name);
        cv.put("price", price);
        cv.put("quantity", quantity);
        cv.put("imageUrl",imageUrl);
        sdb.insert("shopping_cart", null, cv);
        return true;
    }

    public Cursor db_showCart() {
        SQLiteDatabase sdb = this.getReadableDatabase();
        Cursor cur = sdb.rawQuery("select * from shopping_cart", null);
        return cur;
    }

    public Boolean db_update(String id, String quantity) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("quantity", quantity);
        String[] ary = {id};
        sdb.update("shopping_cart", cv, "id=?", ary);
        return true;
    }

    public Boolean db_removeItem(String id) {
        SQLiteDatabase sdb = this.getWritableDatabase();
        String[] ary = {id};
        sdb.delete("shopping_cart", "id=?", ary);
        return true;
    }
}
