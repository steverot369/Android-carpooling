package com.example.sharetaxi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sharetofriend extends Activity {
EditText e1;
Button b1;
String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharetofriend);

        e1=(EditText) findViewById(R.id.number);
        b1=(Button) findViewById(R.id.sendloc);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number=e1.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(number, null, "My Location U Can Track ME By Clicking The Link Below https://maps.google.com/?q="+LocationService.lati+","+LocationService.logi, null, null);
                Toast.makeText(getApplicationContext(), "Location Shared Successfully....", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        });

    }
}