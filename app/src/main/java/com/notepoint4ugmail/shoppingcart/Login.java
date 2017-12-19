package com.notepoint4ugmail.shoppingcart;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    EditText globalUserName, globalPassword;
    Button login_btn, signup;

    RequestQueue requestQueue;

    String shrd_userName, shrd_id;

    /*Create a shared preference here: */
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(Login.this);

        /*Initialising shared preference */
        sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);

        //saving data to preference using editor.
        editor = sharedPreferences.edit();

        globalUserName = findViewById(R.id.edit_user_name);
        globalPassword = findViewById(R.id.edit_password);

        login_btn = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_sign_up);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_user_name, str_password;
                str_user_name = globalUserName.getText().toString();
                str_password = globalPassword.getText().toString();

                if (str_user_name.equals("")) {
                    Toast.makeText(Login.this, "Enter username", Toast.LENGTH_SHORT).show();
                } else if (str_password.equals("")) {
                    Toast.makeText(Login.this, "Enter password", Toast.LENGTH_SHORT).show();

                } else {
                    login(str_user_name, str_password);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signUpIntent = new Intent(Login.this, Register.class);
                startActivity(signUpIntent);
            }
        });
    }

    private void login(final String str_user_name, String str_password) {


        final ProgressDialog dialog = new ProgressDialog(Login.this);
        dialog.setMessage("Loading...");
        dialog.show();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("email", str_user_name);
            jsonObject.put("password", str_password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("jsonObject", "" + jsonObject);

        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST,
                WebsiteLink.url + "login", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("Response", "" + response);
                dialog.dismiss();

                try {
                    JSONObject object = new JSONObject("" + response);
                    String status = object.optString("status");
                    if (status.equals("success")) {
                        JSONObject subObj = object.getJSONObject("data");
                        String id = subObj.optString("_id");
                        String email = subObj.optString("email");
                        String first_name = subObj.optString("first_name");
                        String last_name = subObj.optString("last_name");
                        String mobile = subObj.optString("mobile");

//                        shrd_id = id;
//                        shrd_userName = email;

                        editor.putString("id", id);
                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("mobile", mobile);
                        editor.putString("email", email);

                        editor.apply();

                        globalUserName.setText("");
                        globalPassword.setText("");
                        globalUserName.requestFocus();

                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);

                    } else {
                        String error = object.optString("error");
                        Toast.makeText(Login.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Login.this, "No network", Toast.LENGTH_SHORT).show();
                Log.e("Error Response", "" + error);
                dialog.dismiss();
            }
        });

        requestQueue.add(loginRequest);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
