package com.example.sharetaxi_driver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity implements JsonResponse, AdapterView.OnItemSelectedListener {
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11,e12;

	private int mYear, mMonth, mDay, mHour, mMinute;
	Button b,b1;
	Spinner sp;
	String data;
    String q;
	public static String e;
	String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
	String passw = "userEnteredPassword";
	String [] exp={"less than 1 year","1 year","2 year","3 year","4 year","5 year","more than 5 year"};
	RadioButton r1,r2,r3;
	String fname,lname,hname,gender,city,pincode,phone,email,dob,license,doj,uname,pass;

	SharedPreferences sh;
	String[] ctype,cdes,value,typeid;
	String id,crname,crquantity,crdesc,cramount;
	String[] img;
	final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
	public static String encodedImage = "", path = "";
	private Uri mImageCaptureUri;
	byte[] byteArray = null;
	ImageButton i1;
	ImageView im1;

	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		e1=(EditText)findViewById(R.id.editText1);
		e2=(EditText)findViewById(R.id.editText2);
		e3=(EditText)findViewById(R.id.editText3);
		e4=(EditText)findViewById(R.id.editText4);
		e5=(EditText)findViewById(R.id.editText5);
		e6=(EditText)findViewById(R.id.editText6);
		e7=(EditText)findViewById(R.id.editText7);
		e7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);


				DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {

								e7.setText( year+ "," + (monthOfYear + 1) + "," + dayOfMonth);

							}
						}, mYear, mMonth, mDay);
				datePickerDialog.show();
			}
		});
		e8=(EditText)findViewById(R.id.editText8);
		e9=(EditText)findViewById(R.id.editText9);
		e10=(EditText)findViewById(R.id.editText10);
		e10.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);


				DatePickerDialog datePickerDialog = new DatePickerDialog(Register.this,
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {

								e10.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

							}
						}, mYear, mMonth, mDay);
				datePickerDialog.show();

			}
		});
		e11=(EditText)findViewById(R.id.ed_uname);
		e12=(EditText)findViewById(R.id.ed_pass);
		sp=(Spinner)findViewById(R.id.spinner1);
		sp.setOnItemSelectedListener(this);
		sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,exp));
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		r3=(RadioButton)findViewById(R.id.radio2);
		b=(Button)findViewById(R.id.button1);
//		b1=(Button)findViewById(R.id.bt_browse);
//		im1=(ImageView)findViewById(R.id.imageView1);

		 i1 = findViewById(R.id.imageView1);

		i1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectImageOption();
			}
		});


		b.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fname=e1.getText().toString();
				lname=e2.getText().toString();
				city=e4.getText().toString();
				hname=e3.getText().toString();
				pincode=e5.getText().toString();
				email=e6.getText().toString();
				dob=e7.getText().toString();
				phone=e8.getText().toString();
				license=e9.getText().toString();
				doj=e10.getText().toString();
				uname=e11.getText().toString();
				pass=e12.getText().toString();
				
				if(r1.isChecked())
				{
					gender="male";
				}
				else if(r2.isChecked())
				{
					gender="female";
				}
				else
				{
					gender="others";
				}
				if(fname.equalsIgnoreCase(""))
				{
					
					e1.setError("please fill this field");
					e1.setFocusable(true);
				}
				else if(lname.equalsIgnoreCase(""))
				{
					
					e2.setError("please fill this field");
					e2.setFocusable(true);
				}
				
				else if(hname.equalsIgnoreCase(""))
				{
					
					e3.setError("please fill this field");
					e3.setFocusable(true);
				}
				else if(city.equalsIgnoreCase(""))
				{
					
					e4.setError("please fill this field");
					e4.setFocusable(true);
				}
				else if(pincode.equalsIgnoreCase(""))
				{
					
					e5.setError("please fill this field");
					e5.setFocusable(true);
				}
				else if(pincode.length()!=6)
				{
					
					e5.setError("please enter a valid pincode");
					e5.setFocusable(true);
				}
				else if(email.equalsIgnoreCase(""))
				{
					
					e6.setError("please fill this field");
					e6.setFocusable(true);
				}else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
				{
					
					e6.setError("enter valid email address");
					e6.setFocusable(true);
				}
				
				else if(dob.equalsIgnoreCase(""))
				{
					
					e7.setError("please fill this field");
					e7.setFocusable(true);
				}
				else if(phone.equalsIgnoreCase(""))
				{
					
					e8.setError("please fill this field");
					e8.setFocusable(true);
				}
				else if(!Patterns.PHONE.matcher(phone).matches())
				{
					
					e8.setError("enter valid phone number");
					e8.setFocusable(true);
				}
				else if(phone.length()!=10)
				{
					
					e8.setError("enter valid phone number");
					e8.setFocusable(true);
				}
				else if(license.equalsIgnoreCase(""))
				{
					
					e9.setError("please fill this field");
					e9.setFocusable(true);
				}
				else if(doj.equalsIgnoreCase(""))
				{
					
					e10.setError("please fill this field");
					e10.setFocusable(true);
				}
				else if(uname.equalsIgnoreCase("")|| !uname.matches("[a-zA-Z ]+"))
				{
					
					e11.setError("match the Follwing Format");
					e11.setFocusable(true);
				}
				else if (!pass.matches(pattern)) {
					e12.setError("\"please Meet the Required Pattern \\n Please Use One Caps\\n Must Use A Number\\n Must use A Special Charector\\nMust Use 8 Charectors\")");
					e12.setFocusable(true);
				}
				else if(pass.equalsIgnoreCase(""))
				{
					
					e12.setError("please fill this field");
					e12.setFocusable(true);
				}
				else
				{

					sendAttach();
					
					
				}
			}
		});
		
	}
	private void sendAttach() {

		try {
			SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");


//                String q = "http://" + IpSetting.ip + "/smart city/apis.php";
			String q = "http://" +IPSETTING.ipval+"/api/registration";

//			Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();


			Map<String, byte[]> aa = new HashMap<>();

			aa.put("image", byteArray);
			aa.put("fname",fname.getBytes());
			aa.put("lname",lname.getBytes());
			aa.put("gender",gender.getBytes());
			aa.put("hname",hname.getBytes());
			aa.put("city",city.getBytes());
			aa.put("pincode",pincode.getBytes());
			aa.put("email",email.getBytes());
			aa.put("exp",e.getBytes());
			aa.put("dob",dob.getBytes());
			aa.put("phone",phone.getBytes());
			aa.put("license",license.getBytes());
			aa.put("doj",doj.getBytes());
			aa.put("uname",uname.getBytes());
			aa.put("pass",pass.getBytes());
//            aa.put("house",house.getBytes());

			FileUploadAsync fua = new FileUploadAsync(q);
			fua.json_response = (JsonResponse) Register.this;
			fua.execute(aa);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
		}
	}

	private void selectImageOption() {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

		final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

		AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
		builder.setTitle("Take Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {

				if (items[item].equals("Capture Photo")) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					//intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

					startActivityForResult(intent, CAMERA_PIC_REQUEST);

				} else if (items[item].equals("Choose from Gallery")) {
					Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(i, GALLERY_CODE);

				} else if (items[item].equals("Cancel")) {
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		e=exp[i];
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}


	@Override
	public void response(JSONObject jo) {
		try {

			String method = jo.getString("method");
			Log.d("pearl", method);

			if (method.equalsIgnoreCase("registration")) {


				String status = jo.getString("status");
				if (status.equalsIgnoreCase("success")) {
					Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
					Intent next = new Intent(getApplicationContext(), Login.class);
					startActivity(next);
				}else if (status.equalsIgnoreCase("duplicate")) {
					Toast.makeText(getApplicationContext(), "Email/Phone/Username is Exists...", Toast.LENGTH_LONG).show();
					Intent next = new Intent(getApplicationContext(), Register.class);
				}else if (status.equalsIgnoreCase("unverified")) {
					Toast.makeText(getApplicationContext(), "OOps Sorry Age Must be 18 or above", Toast.LENGTH_LONG).show();
					Intent next = new Intent(getApplicationContext(), Register.class);
				}
			}
				}
		catch(
				Exception e)

		{
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

			mImageCaptureUri = data.getData();
			System.out.println("Gallery Image URI : " + mImageCaptureUri);
			//   CropingIMG();

			Uri uri = data.getData();
			Log.d("File Uri", "File Uri: " + uri.toString());
			// Get the path
			//String path = null;
			try {
//                path = FileUtils.getPath(this, uri);
				path=FileUtils.getPath(this,uri);
				Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

				File fl = new File(path);
				int ln = (int) fl.length();

				InputStream inputStream = new FileInputStream(fl);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] b = new byte[ln];
				int bytesRead = 0;

				while ((bytesRead = inputStream.read(b)) != -1) {
					bos.write(b, 0, bytesRead);
				}
				inputStream.close();
				byteArray = bos.toByteArray();

				Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
				i1.setImageBitmap(bit);

				String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
				encodedImage = str;
//                sendAttach1();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
			}
		} else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

			try {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				i1.setImageBitmap(thumbnail);
				byteArray = baos.toByteArray();

				String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
				encodedImage = str;
//                sendAttach1();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),Login.class);
		startActivity(b);
	}


}
