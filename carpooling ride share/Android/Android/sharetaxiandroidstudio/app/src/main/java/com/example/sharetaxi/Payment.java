package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.R.integer;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Payment extends Activity implements JsonResponse, View.OnClickListener {
    EditText e,ecard,ecvv,edate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button b;
    String amt,card,cvv,dt;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        e=(EditText)findViewById(R.id.editText1);
        ecard=(EditText)findViewById(R.id.editText2);
        ecvv=(EditText)findViewById(R.id.editText3);
        edate=(EditText)findViewById(R.id.editText4);
        Double amount=(Double.parseDouble(REQUEST_STATUS.val));
        e.setText(amount+"");
        e.setEnabled(false);
        b=(Button)findViewById(R.id.button1);
        edate.setOnClickListener(this);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                amt=e.getText().toString();
                card=ecard.getText().toString();
                cvv=ecvv.getText().toString();
                dt=edate.getText().toString();

                if(card.equalsIgnoreCase(""))
                {

                    ecard.setError("enter card number");
                    ecard.setFocusable(true);

                }
                else if(card.length()!=16)
                {

                    ecard.setError("enter valid card number(16 digit)");
                    ecard.setFocusable(true);

                }
                else if(cvv.equalsIgnoreCase(""))
                {

                    ecvv.setError("enter your cvv");
                    ecvv.setFocusable(true);

                }
                else if(cvv.length()!=3)
                {

                    ecvv.setError("enter valid cvv (3 digit)");
                    ecvv.setFocusable(true);

                }
                else if(dt.equalsIgnoreCase(""))
                {

                    edate.setError("enter valid date");
                    edate.setFocusable(true);

                }

                else
                {
//

                    final CharSequence[] items = {"Want To Use Wallet","Yes","No","Cancel"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(Payment.this);
                    // builder.setTitle("Add Photo!");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int item) {




//                if (items[item].equals("View Payment")) {
//
//
//                    startActivity(new Intent(getApplicationContext(),View_Payment.class));
//
//                }
                            if (items[item].equals("Yes")) {

                                JsonReq jr= new JsonReq();
                                jr.json_response=(JsonResponse)Payment.this;
                                String q="/fullpayw?amt=" + amt+"&bookid="+REQUEST_STATUS.bid+"&total="+REQUEST_STATUS.val+"&loginid="+Login.logid+"&aprid="+REQUEST_STATUS.apr_id;
                                jr.execute(q);
//
                            }
                            else if (items[item].equals("No")) {

                                JsonReq jr= new JsonReq();
                              jr.json_response=(JsonResponse)Payment.this;
                              String q="/fullpay?amt=" + amt+"&bookid="+REQUEST_STATUS.bid+"&total="+REQUEST_STATUS.val+"&aprid="+REQUEST_STATUS.apr_id+"&loginid="+Login.logid;

                              jr.execute(q);

                            }

//                 if (items[item].equals("Call")) {
//
//
//                    startActivity(new Intent(getApplicationContext(),RATE.class));
//
//                }

                            else if (items[item].equals("Cancel")) {
                                dialog.dismiss();
                            }

                        }

                    });
                    builder.show();


                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advancepay, menu);
        return true;
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub
        try{
            String status=jo.getString("status");


            if(status.equalsIgnoreCase("success"))
            {
                Toast.makeText(getApplicationContext(), "payment success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
            else if(status.equalsIgnoreCase("failed")){

                Toast.makeText(getApplicationContext(), "Wallet Have Not Minimum Balence..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Payment.class));
            }
            else if(status.equalsIgnoreCase("paid")){
                Toast.makeText(getApplicationContext(), "Already Piad..", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Home.class));
            }
            else
            {
                e.setError("Payment Failes  ");
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

