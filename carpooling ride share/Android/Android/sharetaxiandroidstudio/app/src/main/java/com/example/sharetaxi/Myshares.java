package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.R.integer;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class Myshares extends Activity implements OnItemClickListener,JsonResponse {
    ListView lv;
    SharedPreferences sh;
    TextView t;
    public static String[] username,gender,phone,email,value,e_res;
    //    public static String bid,dlati1,dlongi1,val,amount1;
    String phones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myshares);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        t=(TextView)findViewById(R.id.textView2);
        lv=(ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);


        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Myshares.this;
        String q="/myshares?riderid=" + Login.logid+"&req_id="+Sharetoothers.reqid;
        jr.execute(q);

    }

    public void response(JSONObject jo)
    {
        // TODO Auto-generated method stub
        try{
            String status=jo.getString("status");

            if(status.equalsIgnoreCase("success"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                gender=new String[ja.length()];
                username= new String[ja.length()];
                phone= new String[ja.length()];
                email= new String[ja.length()];
                value= new String[ja.length()];
                e_res= new String[ja.length()];
                for(int i=0;i<ja.length();i++)
                {
                    gender[i]=ja.getJSONObject(i).getString("gender");
                    username[i]=ja.getJSONObject(i).getString("first_name")+" "+ja.getJSONObject(i).getString("last_name");
                    phone[i]=ja.getJSONObject(i).getString("phone");
                    email[i]=ja.getJSONObject(i).getString("email");

//                    amount[i]=ja.getJSONObject(i).getString("amount");


//                    value[i] = amount[i];
                    e_res[i]="USER NAME  :"+username[i]+"\n GENDER :"+gender[i]+"\n PHONE:"+phone[i]+"\n EMAIL:"+email[i];



                    lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,e_res));


                }
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.request__statu, menu);
        return true;
    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

            phones=phone[arg2];
        final CharSequence[] items = {"Call User","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Myshares.this);
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
				 if (items[item].equals("Call User")) {


					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+phones));
					startActivity(callIntent);
//                     Toast.makeText(getApplicationContext(), "exp : "+phone, Toast.LENGTH_LONG).show();
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
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Share_home.class);
        startActivity(b);
    }

}
