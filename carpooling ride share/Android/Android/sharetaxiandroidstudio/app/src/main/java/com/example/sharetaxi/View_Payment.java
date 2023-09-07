package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.R.integer;
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

@TargetApi(Build.VERSION_CODES.GINGERBREAD) public class View_Payment extends Activity implements OnItemClickListener,JsonResponse {
    ListView lv;
    SharedPreferences sh;
    TextView t;
    public static String[] username,drivername,amount,date,value,e_res;
//    public static String bid,dlati1,dlongi1,val,amount1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payment);

        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t=(TextView)findViewById(R.id.textView2);
        lv=(ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);


        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)View_Payment.this;
        String q="/viewpayment?riderid=" + Login.logid+"&aprid="+Add_rating.aprid;
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
                drivername=new String[ja.length()];
                username= new String[ja.length()];
                date= new String[ja.length()];
                amount= new String[ja.length()];
                value= new String[ja.length()];
                e_res= new String[ja.length()];
                for(int i=0;i<ja.length();i++)
                {
                    drivername[i]=ja.getJSONObject(i).getString("driver_fname")+" "+ja.getJSONObject(i).getString("driver_lname");
                    username[i]=ja.getJSONObject(i).getString("first_name")+" "+ja.getJSONObject(i).getString("last_name");
                    date[i]=ja.getJSONObject(i).getString("date");
                    amount[i]=ja.getJSONObject(i).getString("amt");

                    amount[i]=ja.getJSONObject(i).getString("amt");


                        value[i] = amount[i];
                       e_res[i]="USER NAME  :"+username[i]+"\n DRIVERNAME :"+drivername[i]+"\n AMOUNT:"+amount[i]+"\n DATE:"+date[i];

//                    e_res[i]="USER NAME  :"+username[i]+"\n DRIVERNAME :"+drivername[i]+"\n AMOUNT:"+amount[i];


                    lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.cust_list,e_res));


                }
            }
            else if (status.equalsIgnoreCase("ofpaid"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                drivername=new String[ja.length()];
                username= new String[ja.length()];
                date= new String[ja.length()];
                amount= new String[ja.length()];
                for(int i=0;i<ja.length();i++)
                {
                    drivername[i]=ja.getJSONObject(i).getString("driver_fname")+" "+ja.getJSONObject(i).getString("driver_lname");
                    username[i]=ja.getJSONObject(i).getString("first_name")+" "+ja.getJSONObject(i).getString("last_name");
                    date[i]=ja.getJSONObject(i).getString("date");
                    amount[i]=ja.getJSONObject(i).getString("amount");

                    amount[i]=ja.getJSONObject(i).getString("amount");


                    value[i] = amount[i];
                    e_res[i]="\nOFFLINE PAYMENT\n"+"USER NAME  :"+username[i]+"\n DRIVERNAME :"+drivername[i]+"\n AMOUNT:"+amount[i]+"\n DATE:"+date[i];

//                    e_res[i]="USER NAME  :"+username[i]+"\n DRIVERNAME :"+drivername[i]+"\n AMOUNT:"+amount[i];


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
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Add_rating.class);
        startActivity(b);
    }

}
