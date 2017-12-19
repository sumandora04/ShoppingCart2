package com.notepoint4ugmail.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences preferences;
    String str_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences("User", Context.MODE_PRIVATE);

        str_id = preferences.getString("id", "");

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);

                    if (str_id.equals("")) {
                        Intent intent1 = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent1);
                    } else {
                        Intent intent = new Intent(SplashScreen.this, Home.class);
                        startActivity(intent);
                    }

                } catch (InterruptedException e) {
                    Toast.makeText(SplashScreen.this, "" + e, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };
        timer.start();
    }
}
