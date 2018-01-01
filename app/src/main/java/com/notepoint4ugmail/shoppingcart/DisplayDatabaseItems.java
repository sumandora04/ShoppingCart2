package com.notepoint4ugmail.shoppingcart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DisplayDatabaseItems extends BaseAdapter {

    private ArrayList<String> item_name, item_id;
    private ArrayList<Integer> item_price, item_quantity;
    private Context context;
    private LayoutInflater layoutInflater;
    private ProductDatabase productDatabase;

    private int priceOfItem;


    DisplayDatabaseItems(Context applicationContext, ArrayList<String> item_id, ArrayList<String> item_name,
                         ArrayList<Integer> item_quantity, ArrayList<Integer> item_price) {
        this.context = applicationContext;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.item_price = item_price;
        productDatabase = new ProductDatabase(context);
        layoutInflater = LayoutInflater.from(applicationContext);
    }

    @Override
    public int getCount() {
        return item_id.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_in_cart_cell, null);

        TextView view_name_text = view.findViewById(R.id.view_name_text);
        TextView view_price_text = view.findViewById(R.id.view_price_text);
        final EditText click_quantity = view.findViewById(R.id.click_quantity);

        view_name_text.setText(item_name.get(i));
        click_quantity.setText(item_quantity.get(i).toString());
        view_price_text.setText(item_price.get(i).toString());


        Button remove, updateQuantity;

        final String product_id = item_id.get(i);
        updateQuantity= view.findViewById(R.id.btn_update_quantity);
        updateQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_quantity = click_quantity.getText().toString();
                if (str_quantity.isEmpty()){
                    click_quantity.setError("Add quantity");
                }

                boolean updated = productDatabase.db_update(product_id, str_quantity);

                if (updated) {
                    Toast.makeText(context, "Database Updated",
                            Toast.LENGTH_SHORT).show();
                    click_quantity.setText(str_quantity);
                } else {
                    Toast.makeText(context, "Unable to update database.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        remove = view.findViewById(R.id.btn_remove_item);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();

                boolean deleted = productDatabase.db_removeItem(product_id);

                if (deleted) {
                    Toast.makeText(context, "Data deleted form database",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context,
                            "Unable to delete data from database", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

}
