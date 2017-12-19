package com.notepoint4ugmail.shoppingcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CheckOut extends AppCompatActivity {

    EditText address1,address2,pincode,state;
    TextView txt_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        address1 = findViewById(R.id.edit_address1);
        address2 = findViewById(R.id.edit_address2);
        pincode = findViewById(R.id.edit_pincode);
        state = findViewById(R.id.edit_state);

        txt_address = findViewById(R.id.txt_address);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_address1,str_address2,str_pin,str_state;
                str_address1=address1.getText().toString();
                str_address2=address2.getText().toString();
                str_pin=pincode.getText().toString();
                str_state=state.getText().toString();

                txt_address.setText(str_address1+"\n"+str_address2+"\n"+str_pin+"\n"+str_state);
            }
        });

        findViewById(R.id.btn_place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOut.this,OrderCompletion.class));
            }
        });
    }
}
