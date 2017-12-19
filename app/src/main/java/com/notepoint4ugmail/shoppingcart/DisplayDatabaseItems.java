package com.notepoint4ugmail.shoppingcart;

import android.app.Dialog;
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

    ArrayList<String> item_name, item_id;
    ArrayList<Integer> item_price, item_quantity;
    Context context;
    LayoutInflater layoutInflater;
    ProductDatabase productDatabase;

   // int quantity = 1;


    public DisplayDatabaseItems(Context applicationContext, ArrayList<String> item_id, ArrayList<String> item_name,
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

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_in_cart_cell, null);

        TextView view_name_text = view.findViewById(R.id.view_name_text);
       // final TextView view_quantity_text = view.findViewById(R.id.text_quantity);
        TextView view_price_text = view.findViewById(R.id.view_price_text);

        view_name_text.setText(item_name.get(i));
//        view_quantity_text.setText(item_quantity.get(i));
//        view_price_text.setText(item_price.get(i));

        Button remove;

        final String product_id = item_id.get(i);

        final TextView click_quantity = view.findViewById(R.id.click_quantity);
        click_quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Quantity:");
                dialog.setContentView(R.layout.dialog);
                dialog.show();


                dialog.findViewById(R.id.btn_done).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText edt_quantity = dialog.findViewById(R.id.edit_quantity);
                               String str_quantity = edt_quantity.getText().toString();


                        boolean updated = productDatabase.db_update(product_id, str_quantity);

                        if (updated) {
                            Toast.makeText(context, "Database Updated",
                                    Toast.LENGTH_SHORT).show();
                            click_quantity.setText(str_quantity);
                        } else {
                            Toast.makeText(context, "Unable to update database.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                });

            }
        });


       /* final EditText quantity = view.findViewById(R.id.edit_quantity);
        view.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_quantity = quantity.getText().toString();
                boolean updated = productDatabase.db_update(product_id, str_quantity);

                if (updated) {
                    Toast.makeText(context, "Database Updated",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Unable to update database.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

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
