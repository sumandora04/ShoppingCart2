package com.notepoint4ugmail.shoppingcart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
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

public class Register extends AppCompatActivity {

    EditText firstName, lastName, mobile, email, password;
    Button btn_register;

    String str_first_name, str_last_name, str_mobile, str_email, str_password;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);

        requestQueue = Volley.newRequestQueue(Register.this);

        firstName = findViewById(R.id.edit_first_name);
        lastName = findViewById(R.id.edit_last_name);
        mobile = findViewById(R.id.edit_mobile);
        email = findViewById(R.id.edit_email);
        password = findViewById(R.id.edit_password);

        editor = sharedPreferences.edit();

        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                str_first_name = firstName.getText().toString();
                str_last_name = lastName.getText().toString();
                str_mobile = mobile.getText().toString();
                str_email = email.getText().toString();
                str_password = password.getText().toString();

                if (str_first_name.equals("")) {

                    Toast.makeText(Register.this, "Enter first name", Toast.LENGTH_SHORT).show();
                } else if (str_last_name.equals("")) {
                    Toast.makeText(Register.this, "Enter last name", Toast.LENGTH_SHORT).show();

                } else if (!Patterns.PHONE.matcher(str_mobile).matches()) {
                    Toast.makeText(Register.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();

                } else if (!Patterns.EMAIL_ADDRESS.matcher(str_email).matches()) {
                    Toast.makeText(Register.this, "Enter valid email id", Toast.LENGTH_SHORT).show();

                } else if (str_password.equals("")) {
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();

                } else {

                    register(str_first_name, str_last_name, str_mobile, str_email, str_password);
                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);

                }
            }
        });
    }

    private void register(final String str_first_name, final String str_last_name, final String str_mobile,
                          final String str_email, final String str_password) {

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("first_name", str_first_name);
            jsonObject.put("last_name", str_last_name);
            jsonObject.put("mobile", str_mobile);
            jsonObject.put("email", str_email);
            jsonObject.put("password", str_password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("jsonObject", "" + jsonObject);


        JsonObjectRequest registrationRequest = new JsonObjectRequest(Request.Method.POST,
                WebsiteLink.url + "register", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response", "" + response);
/*
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

*//*

                        editor.putString("first_name", first_name);
                        editor.putString("last_name", last_name);
                        editor.putString("mobile", mobile);
                        editor.putString("email", email);

                        editor.commit();
*//*
                    } else {
                        String error = object.optString("error");
                        Toast.makeText(Register.this, "" + error, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response", "" + error);
            }
        });

        requestQueue.add(registrationRequest);
    }
}
