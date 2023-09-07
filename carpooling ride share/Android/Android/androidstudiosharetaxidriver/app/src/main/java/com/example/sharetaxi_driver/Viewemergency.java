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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewemergency extends Activity implements JsonResponse, AdapterView.OnItemClickListener {

    ListView l1;
    Button b1;
    String[] username,  phone, email, gender,lati, longi, value,em_id;
    public static String longitude,latitude,emid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewemergency);

        l1 = (ListView) findViewById(R.id.lv1);
        l1.setOnItemClickListener(this);
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewemergency.this;
        String q = "/Viewemergency?id="+Login.logid+"&lati="+LocationService.lati+"&longi="+LocationService.logi;

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
                username = new String[ja1.length()];
                phone = new String[ja1.length()];
                email = new String[ja1.length()];
                gender = new String[ja1.length()];
                lati = new String[ja1.length()];
                longi = new String[ja1.length()];
                em_id = new String[ja1.length()];

                value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    username[i] = ja1.getJSONObject(i).getString("first_name")+ja1.getJSONObject(i).getString("last_name");
                    phone[i] = ja1.getJSONObject(i).getString("phone");
                    email[i] = ja1.getJSONObject(i).getString("email");
                    gender[i] = ja1.getJSONObject(i).getString("gender");
                    lati[i] = ja1.getJSONObject(i).getString("latitude");
                    longi[i] = ja1.getJSONObject(i).getString("longitude");
                    em_id[i] = ja1.getJSONObject(i).getString("emergency_id");
                    value[i] = "Username: " + username[i] + "\nGender: " + gender[i] + "\nPhone" + phone[i] + "\nEmail:" + email[i] + "\nLatitude:" + lati[i] + "\nLongitude:"+longi[i]+"\n";

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                l1.setAdapter(ar);
//                Custimage a = new Custimage(this, vehiclename, details, regno, vstatus,rent, image);
//                l1.setAdapter(a);

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

       latitude=lati[i];
        longitude=longi[i];
        emid=em_id[i];
        final CharSequence[] items = {"Map","Respond","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewemergency.this);
        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Map")) {


                    String url = "http://www.google.com/maps?q=" + Viewemergency.latitude + "," + Viewemergency.longitude;
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);

                }
                else if (items[item].equals("Respond")){
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewemergency.this;
                    String q = "/respond?id="+emid;

                    q = q.replace(" ", "%20");
                    JR.execute(q);
                    Toast.makeText(getApplicationContext(),"Success....", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Viewemergency.class));
                }

                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }

            }

        });
        builder.show();


//        else if (v_status.equalsIgnoreCase("rented")) {
//
//            final CharSequence[] items = {"Return vehicle", "Cancel"};
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(Viewemergency.this);
//            // builder.setTitle("Add Photo!");
//            builder.setItems(items, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int item) {
//
//
//                    if (items[item].equals("Return vehicle")) {
//
//                        JsonReq JR = new JsonReq();
//                        JR.json_response = (JsonResponse) Viewemergency.this;
//                        String q = "/returnvehicle?id="+Login.logid+"&vid="+vehicle_id;
//                        q = q.replace(" ", "%20");
//                        JR.execute(q);
//
//                    }
//                else if (items[item].equals("Call")) {
//
//
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:"+phones));
//                    startActivity(callIntent);
//                }

//                    else if (items[item].equals("Cancel")) {
//                        dialog.dismiss();
//                    }
//
//                }
//
//            });builder.show();

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),HOME.class);
        startActivity(b);
    }
}