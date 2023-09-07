package com.example.sharetaxi_driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

public class Rentpayment extends Activity implements JsonResponse, View.OnClickListener {
    EditText e, ecard, ecvv, edate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button b;
    String amt, card, cvv, dt;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentpayment);

        e = (EditText) findViewById(R.id.editText1);
        ecard = (EditText) findViewById(R.id.editText2);
        ecvv = (EditText) findViewById(R.id.editText3);
        edate = (EditText) findViewById(R.id.editText4);
        Double amount = (Double.parseDouble(Viewbookings.renttt));
        e.setText(amount + "");
        e.setEnabled(false);
        b = (Button) findViewById(R.id.button1);

        edate.setOnClickListener(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amt = e.getText().toString();
                card = ecard.getText().toString();
                cvv = ecvv.getText().toString();
                dt = edate.getText().toString();

                if (card.equalsIgnoreCase("")) {

                    ecard.setError("enter card number");
                    ecard.setFocusable(true);

                } else if (card.length() != 16) {

                    ecard.setError("enter valid card number(16 digit)");
                    ecard.setFocusable(true);

                } else if (cvv.equalsIgnoreCase("")) {

                    ecvv.setError("enter your cvv");
                    ecvv.setFocusable(true);

                } else if (cvv.length() != 3) {

                    ecvv.setError("enter valid cvv (3 digit)");
                    ecvv.setFocusable(true);

                } else if (dt.equalsIgnoreCase("")) {

                    edate.setError("enter valid date");
                    edate.setFocusable(true);

                } else {
//

                    JsonReq jr = new JsonReq();
                    jr.json_response = (JsonResponse)Rentpayment.this;
                    String q = "/rentpayment?amt=" + amt + "&bookid=" + Viewbookings.vreqid + "&total=" + Viewbookings.renttt + "&vid=" + Viewbookings.vehicle_id+"&loginid="+Login.logid;
                    jr.execute(q);
                    Toast.makeText(getApplicationContext(), "PAYMENT SUCCESSFULL", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),HOME.class));

                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");

            if(status.equalsIgnoreCase("success"))
            {
//                Toast.makeText(getApplicationContext(), "payment success ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),HOME.class));

            }
            else
            {
                e.setError("Payment Failed  ");
                e.setFocusable(true);
            }
        }

        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edate.setText((monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}