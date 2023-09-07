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

public class Viewbookings extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    Button b1;
    String[] vehiclename, image, details, regno, vstatus,vreq_id, vid,rent, value;
    public static String vehicle_id,v_status,renttt,vreqid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookings);

        l1 = (ListView) findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewbookings.this;
        String q = "/Viewmycars?id="+Login.logid;

        q = q.replace(" ", "%20");
        JR.execute(q);


//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                JsonReq JR = new JsonReq();
//                JR.json_response = (JsonResponse) Viewbookings.this;
//                String q = "/Viewmycars?id="+Login.logid;
//
//                q = q.replace(" ", "%20");
//                JR.execute(q);
//            }
//        });
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
                vreq_id = new String[ja1.length()];
                rent = new String[ja1.length()];
                vid = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    vehiclename[i] = ja1.getJSONObject(i).getString("vehicle");
                    details[i] = ja1.getJSONObject(i).getString("details");
                    vstatus[i] = ja1.getJSONObject(i).getString("status");
                    image[i] = ja1.getJSONObject(i).getString("image");
                    regno[i] = ja1.getJSONObject(i).getString("reg_no");
                    vreq_id[i] = ja1.getJSONObject(i).getString("v_request_id");
                    rent[i] = ja1.getJSONObject(i).getString("rate");
                    vid[i] = ja1.getJSONObject(i).getString("vehicle_id");
//                    value[i] = "college: " + collegename[i] + "\nimage: " + image[i] + "\ndescription" + description[i] + "\nemialaddress:" + email[i] + "\nphonenumber:" + phone[i] + "\n";

                }
//                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                l1.setAdapter(ar);
                Custimage a = new Custimage(this, vehiclename, details, regno, vstatus,rent, image);
                l1.setAdapter(a);

            } else if (status.equalsIgnoreCase("return")) {
                Toast.makeText(getApplicationContext(), "vehicle Returned successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),HOME.class));
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
        vreqid=vreq_id[i];
        v_status=vstatus[i];
        renttt=rent[i];
//        Toast.makeText(getApplicationContext(),v_status, Toast.LENGTH_LONG).show();

        if (v_status.equalsIgnoreCase("accept")) {

            final CharSequence[] items = {"Make Payment", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(Viewbookings.this);
            // builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {


                    if (items[item].equals("Make Payment")) {


                        startActivity(new Intent(getApplicationContext(), Rentpayment.class));

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

            else if (v_status.equalsIgnoreCase("rented")) {

                final CharSequence[] items = {"Return vehicle", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Viewbookings.this);
                // builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {


                        if (items[item].equals("Return vehicle")) {

                            JsonReq JR = new JsonReq();
                            JR.json_response = (JsonResponse) Viewbookings.this;
                            String q = "/returnvehicle?id="+Login.logid+"&vid="+vehicle_id;
                            q = q.replace(" ", "%20");
                            JR.execute(q);

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

                });builder.show();

        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),HOME.class);
        startActivity(b);
    }
}