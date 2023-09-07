package com.example.sharetaxi_driver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewrentcars extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    Button b1;
    String[] vehiclename, image, details, regno, vstatus, vid,rent, value;
    public static String vehicle_id ,rentt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrentcars);
        b1=(Button)findViewById(R.id.myb);
        l1 = (ListView) findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewrentcars.this;
        String q = "/viewcars";

        q = q.replace(" ", "%20");
        JR.execute(q);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                startActivity(new Intent(getApplicationContext(),Viewbookings.class));
            }
        });
    }



    @Override
    public void response(JSONObject jo) {
        try {


            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                vehiclename = new String[ja1.length()];
                image = new String[ja1.length()];
                details = new String[ja1.length()];
                regno = new String[ja1.length()];
                vstatus = new String[ja1.length()];
                rent = new String[ja1.length()];
                vid = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    vehiclename[i] = ja1.getJSONObject(i).getString("vehicle");
                    details[i] = ja1.getJSONObject(i).getString("details");
                    vstatus[i] = ja1.getJSONObject(i).getString("status");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    regno[i] = ja1.getJSONObject(i).getString("reg_no");
                    rent[i] = ja1.getJSONObject(i).getString("rate");
                    vid[i] = ja1.getJSONObject(i).getString("vehicle_id");
//                    value[i] = "college: " + collegename[i] + "\nimage: " + image[i] + "\ndescription" + description[i] + "\nemialaddress:" + email[i] + "\nphonenumber:" + phone[i] + "\n";

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);
                Custimage a = new Custimage(this, vehiclename, details, regno, vstatus,rent, image);
                l1.setAdapter(a);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();


        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        vehicle_id=vid[i];
        rentt=rent[i];
        final CharSequence[] items = {"Book Car","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewrentcars.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {




                 if (items[item].equals("Book Car")) {


                    startActivity(new Intent(getApplicationContext(),Bookrentcar.class));

                }
//                else if (items[item].equals("Call")) {
//
//
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:"+phones));
//                    startActivity(callIntent);
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
        Intent b=new Intent(getApplicationContext(),HOME.class);
        startActivity(b);
    }
}