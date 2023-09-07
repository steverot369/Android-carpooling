package com.example.sharetaxi_driver;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class Bookrentcar extends Activity implements JsonResponse {

    private int mYear, mMonth, mDay, mHour, mMinute;
    String date;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookrentcar);

        e1 = findViewById(R.id.editTextTextPersonName);
        e1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Bookrentcar.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                e1.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        Button b1 = findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = e1.getText().toString();

                JsonReq jr = new JsonReq();
                jr.json_response = (JsonResponse) Bookrentcar.this;
                String q = "/bookcar?logid=" + Login.logid + "&vid=" + Viewrentcars.vehicle_id + "&date=" + date;
                jr.execute(q);

            }
        });
    }




    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "Car Bokking SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Viewrentcars.class));

            }
            else if (status.equalsIgnoreCase("failed")) {
                Toast.makeText(getApplicationContext(), "The booking for this car is complete!!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Viewrentcars.class));

            }
            else {
                startActivity(new Intent(getApplicationContext(), Viewrentcars.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b = new Intent(getApplicationContext(), HOME.class);
        startActivity(b);
    }
}