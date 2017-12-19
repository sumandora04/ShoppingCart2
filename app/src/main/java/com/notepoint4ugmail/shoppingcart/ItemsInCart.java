package com.notepoint4ugmail.shoppingcart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ItemsInCart extends AppCompatActivity {


    ListView listView_items_in_cart;
    ArrayList<String> ary_id, ary_name;
    ArrayList<Integer> ary_quantity, ary_price;
    ProductDatabase productDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_in_cart);
        listView_items_in_cart=findViewById(R.id.listView_items_in_cart);

        productDatabase= new ProductDatabase(ItemsInCart.this);

        getDataFromDataBase();

        findViewById(R.id.button_check_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ItemsInCart.this, CheckOut.class));
            }
        });
    }


    public void getDataFromDataBase() {
        ary_name = new ArrayList<>();
        ary_quantity = new ArrayList<>();
        ary_price = new ArrayList<>();
        ary_id = new ArrayList<>();

        Cursor cursor = productDatabase.db_showCart();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ary_name.add(cursor.getString(cursor.getColumnIndex("name")));
            ary_quantity.add(cursor.getInt(cursor.getColumnIndex("quantity")));
            ary_price.add(cursor.getInt(cursor.getColumnIndex("price")));
            ary_id.add(cursor.getString(cursor.getColumnIndex("id")));

            cursor.moveToNext();
        }

        listView_items_in_cart.setAdapter(new DisplayDatabaseItems
                (ItemsInCart.this,ary_id, ary_name, ary_quantity, ary_price));
    }
}
