package com.example.sharetaxi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Managewallet extends Activity implements JsonResponse {
    EditText e1;
    Button  b1,b2;
    String money;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managewallet);

//        e1=(EditText) findViewById(R.id.etwallet);
//        b1=(Button) findViewById(R.id.addmoney);
        b2=(Button) findViewById(R.id.viewmoney);

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Managewallet.this;
        String q="/viewmoney?riderid=" + Login.logid;
        jr.execute(q);

//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                money=e1.getText().toString();
//                JsonReq jr= new JsonReq();
//                jr.json_response=(JsonResponse)Managewallet.this;
//                String q="/addmoney?riderid=" + Login.logid+"&money="+money;
//                jr.execute(q);
//            }
//        });


    }

    @Override
    public void response(JSONObject jo) {
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("addmoney")) {

                String status = jo.getString("status");

                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Money Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Managewallet.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Your Wallet Is Empty", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Managewallet.class));

                }
            } if(method.equalsIgnoreCase("viewmoney")) {

                String money1=jo.getString("money");
                b2.setText("Your Balance="+money1);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
        }
    }
}