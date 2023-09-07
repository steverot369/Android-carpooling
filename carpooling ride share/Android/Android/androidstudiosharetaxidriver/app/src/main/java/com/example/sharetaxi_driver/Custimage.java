package com.example.sharetaxi_driver;

import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Custimage extends ArrayAdapter<String>  {

	 private Activity context;       //for to get current activity context
	    SharedPreferences sh;
	private String[] image;
	private String[] details;
	private String[] vehiclename;
	private String[] regno;
	private String[] vstatus;
	private String[] rent;

	 public Custimage(Activity context, String[] vehiclename,String[] details,String[] regno,String[] vstatus,String[] rent,String[] image) {
	        //constructor of this class to get the values from main_activity_class

	        super(context, R.layout.cust_images, image);
	        this.context = context;
	        this.image = image;
		    this.details = details;
		 	this.regno = regno;
		 	this.rent = rent;
		 	this.vehiclename = vehiclename;
		 	this.vstatus = vstatus;
	    }

	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	                 //override getView() method

	        LayoutInflater inflater = context.getLayoutInflater();
	        View listViewItem = inflater.inflate(R.layout.cust_images, null, true);
			//cust_list_view is xml file of layout created in step no.2

	        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView1);
	        TextView t1=(TextView)listViewItem.findViewById(R.id.textView3);

//			TextView t2=(TextView)listViewItem.findViewById(R.id.textView5);
			t1.setText("Vehiclename:"+ "  "+vehiclename[position]+"\nReg no:"+ "  "+regno[position]+"\nDetails:"+ "  "+details[position]+"\nRent per day:"+ "  "+rent[position]+"\nAvailability:"+ "  "+vstatus[position]);
//			t2.setText(caption[position]);
	        sh=PreferenceManager.getDefaultSharedPreferences(getContext());
	        
	       String pth = "http://"+sh.getString("ipval", "")+"/"+image[position];
	       pth = pth.replace("~", "");
//	       Toast.makeText(context, pth, Toast.LENGTH_LONG).show();
	        
	        Log.d("-------------", pth);
	        Picasso.with(context)
	                .load(pth)
	                .placeholder(R.drawable.taxi_drive)
	                .error(R.drawable.taxi_drive).into(im);
	        
	        return  listViewItem;
	    }

		private TextView setText(String string) {
			// TODO Auto-generated method stub
			return null;
		}
}