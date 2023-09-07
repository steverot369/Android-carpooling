package com.example.sharetaxi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Add_rating extends Activity implements OnItemClickListener,JsonResponse {

	ListView lv;
	SharedPreferences sh;
	public static String[] reqid,from,to,driver,vehtype,e_res,apr_id;
    public static String rid,aprid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_rating);
		
        lv=(ListView)findViewById(R.id.listView1);
        lv.setOnItemClickListener(this);
        
    	JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Add_rating.this;
		String q="/finished_ride?riderid=" + Login.logid;
		jr.execute(q);
		
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_rating, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) 
	{
		// TODO Auto-generated method stub
		rid=reqid[arg2];
		aprid=apr_id[arg2];

		final CharSequence[] items = {"View Payment","Add Rating","Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(Add_rating.this);
		// builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {




				 if (items[item].equals("View Payment")) {


					startActivity(new Intent(getApplicationContext(),View_Payment.class));

				}
//				else if (items[item].equals("Call")) {
//
//
//					Intent callIntent = new Intent(Intent.ACTION_CALL);
//					callIntent.setData(Uri.parse("tel:"+phones));
//					startActivity(callIntent);
//				}

				else if (items[item].equals("Add Rating")) {


					startActivity(new Intent(getApplicationContext(),RATE.class));

				}

				else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}

			}

		});
		builder.show();

	}
	@Override
	public void response(JSONObject jo)
	{
		// TODO Auto-generated method stub
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				reqid=new String[ja.length()];
				driver= new String[ja.length()];
				vehtype= new String[ja.length()];
				from= new String[ja.length()];
				to= new String[ja.length()];
				e_res= new String[ja.length()];
				apr_id= new String[ja.length()];
				for(int i=0;i<ja.length();i++)
				{
					reqid[i]=ja.getJSONObject(i).getString("req_id");
					driver[i]=ja.getJSONObject(i).getString("driver_fname")+" "+ja.getJSONObject(i).getString("driver_lname");
					vehtype[i]=ja.getJSONObject(i).getString("vehicle_type");
					from[i]=ja.getJSONObject(i).getString("booking_from");
					apr_id[i]=ja.getJSONObject(i).getString("apr_id");
					to[i]=ja.getJSONObject(i).getString("booking_to");
					
					 e_res[i]="DRIVER NAME  :"+driver[i]+
	                    	    "\nVEHICLE      :"+vehtype[i]+
	                    	    "\nFROM         :"+from[i]+
	                    	    "\nTO           :"+to[i];       
				}
				lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,e_res));
			}
			else
			{
				Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_LONG).show();
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "exp : "+e, Toast.LENGTH_LONG).show();
		}
	}

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Home.class);
		startActivity(b);
	}
}
