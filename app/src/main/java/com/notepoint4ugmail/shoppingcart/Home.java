package com.notepoint4ugmail.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ArrayList<String> productName, productPrice, productId, productImageUrl;
    ListView list_view_home;
    //ImageView image;

    RequestQueue requestQueue;

    EditText search_text;
    Button btn_search;
    SharedPreferences preferences;

    SharedPreferences.Editor editor;
    String str_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        requestQueue = Volley.newRequestQueue(Home.this);

         /* For Shared Preference*/
        preferences = getSharedPreferences("User", Context.MODE_PRIVATE);

        editor = preferences.edit();

        str_id = preferences.getString("id", "");

        productName = new ArrayList<>();
        //productName.add("Shoe");
        productPrice = new ArrayList<>();
        //productPrice.add("90");
        productId = new ArrayList<>();
        productImageUrl = new ArrayList<>();

        list_view_home = findViewById(R.id.list_view_home);
        list_view_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Home.this, "Item clicked", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.button_goto_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, ItemsInCart.class));
            }
        });

        //Search VIew
        search("-1");
        search_text = findViewById(R.id.edit_search);
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_search = search_text.getText().toString();
                if (str_search.equals("")) {
                    Toast.makeText(Home.this, "Enter search item", Toast.LENGTH_SHORT).show();
                } else {
                    search(str_search);
                }
            }
        });
    }

    /*Overloading methods for menu items*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            Intent intent;
            Context context;
            Class cls;
            if (id == R.id.setting) {
                cls = Profile.class;
                context = Home.this;
            } else {

                editor.clear();
                editor.apply();
                cls = Login.class;
                context = Home.this;
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
            }
            intent = new Intent(context, cls);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "" + e);
        }
        return true;
    }

    /*Creating the search method*/
    private void search(String str_search) {

        StringRequest searchRequest = new StringRequest(Request.Method.GET,
                WebsiteLink.url + "search/product/" + str_search,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("Response", "" + response);

                        try {
                            JSONObject root = new JSONObject("" + response);
                            String status = root.optString("status");

                            if (status.equals("success")) {
                                JSONArray records = root.getJSONArray("records");

                                for (int i = 0; i <= records.length(); i++) {
                                    JSONObject currentRecord = records.getJSONObject(i);

                                    String product_id = currentRecord.getString("_id");
                                    String product_Name = currentRecord.getString("product_name");
                                   // String product_Price = "120";
                                    String product_Price = currentRecord.getString("product_id");
                                    String imageUrl = currentRecord.getString("product_image");

                                    // items.add(new Items(productName,price));
                                    productName.add(product_Name);
                                    productPrice.add(product_Price);
                                    //need this productId in the DisplayDatabase.java 
                                    productId.add(product_id);
                                    productImageUrl.add(imageUrl);

                                    // list_view_home = findViewById(R.id.list_view_home);
                                    list_view_home.setAdapter(new HomeAdapter());

                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("Error", "" + error);
                    }
                });

        requestQueue.add(searchRequest);

    }

    /*Creating the Custom adapter*/
    public class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return productName.size();
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
            LayoutInflater layoutInflater = LayoutInflater.from(Home.this);
            view = layoutInflater.inflate(R.layout.home_list_cell, null);

            final TextView itemName = view.findViewById(R.id.view_name_text);
            itemName.setText(productName.get(i));
            final TextView itemPrice = view.findViewById(R.id.view_price_text);
            itemPrice.setText(productPrice.get(i));

            ImageView item_image= view.findViewById(R.id.product_image_url);
            /* Showing images to the screen.*/
            Picasso.with(Home.this)
                    .load(productImageUrl.get(i))
                    .into(item_image);

            view.findViewById(R.id.btn_add_to_cart).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductDatabase cart = new ProductDatabase(Home.this);
                    final String product_id = productId.get(i);
                    final String imageUrl = productImageUrl.get(i);
                    String item_name = productName.get(i);
                    String item_price = productPrice.get(i);
                   // String item_id = product_id;
                    boolean b = cart.db_addToCart(product_id, item_name, item_price, "1", imageUrl);
                    if (b) {
                        Toast.makeText(Home.this, "Added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Home.this, "Failed to add", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return view;
        }
    }
}