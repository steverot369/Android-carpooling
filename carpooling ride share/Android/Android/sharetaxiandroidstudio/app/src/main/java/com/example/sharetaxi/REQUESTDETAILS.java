package com.example.sharetaxi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class REQUESTDETAILS extends Activity {
Button b1,b2,b3,b4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_requestdetails);
		b1=(Button)findViewById(R.id.button1);
		b2=(Button)findViewById(R.id.button2);
		b3=(Button) findViewById(R.id.shome);
		b4=(Button) findViewById(R.id.call);




		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//			startActivity(new Intent(getApplicationContext(),Advancepay.class));

				final CharSequence[] items = {"Make Full Payment","Cancel"};

				AlertDialog.Builder builder = new AlertDialog.Builder(REQUESTDETAILS.this);
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
//						if (items[item].equals("Advance Payment")) {
//
//							startActivity(new Intent(getApplicationContext(),Advancepay.class));
////
//						}
						 if (items[item].equals("Make Full Payment")) {

							startActivity(new Intent(getApplicationContext(),Payment.class));
//
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
		});
		
        b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String url = "http://www.google.com/maps?q="+REQUEST_STATUS.dlati1+","+REQUEST_STATUS.dlongi1;
                Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(in);
			}
		});
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getApplicationContext(),Share_home.class));
			}
		});
		b4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:"+REQUEST_STATUS.phone));
					startActivity(callIntent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.requestdetail, menu);
		return true;
	}

}
