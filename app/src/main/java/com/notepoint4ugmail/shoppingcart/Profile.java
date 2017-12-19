package com.notepoint4ugmail.shoppingcart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class Profile extends AppCompatActivity {

    String str_first_name, str_last_name, str_mobile, str_email;

    EditText edt_first, edt_last, edt_mobile, edt_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        str_first_name = sharedPreferences.getString("first_name", "");
        str_last_name = sharedPreferences.getString("last_name", "");
        str_mobile = sharedPreferences.getString("mobile", "");
        str_email = sharedPreferences.getString("email", "");


        edt_first = findViewById(R.id.edit_first_name);
        edt_first.setText(str_first_name);

        edt_last = findViewById(R.id.edit_last_name);
        edt_last.setText(str_last_name);

        edt_mobile = findViewById(R.id.edit_mobile);
        edt_mobile.setText(str_mobile);

        edt_email = findViewById(R.id.edit_email);
        edt_email.setText(str_email);

    }
}
