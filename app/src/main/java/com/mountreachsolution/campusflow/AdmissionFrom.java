package com.mountreachsolution.campusflow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class AdmissionFrom extends AppCompatActivity {

    EditText etname,etblood,etnumber,etaddress,etadhrno,etdateofbirth,etnationality,etpercentage,etfathername,etfathercontactnumber,etfatheroccuption,etaddressfather;
    Spinner cast,branch,admissionyear,lastexam;



    String Formnumber,strcast,strbarnch,stradissionmyer,strlstexm;
    AppCompatButton btnSubmit;
    private  int pick_image_request=789;
    Bitmap bitmap,bitmapp,bitmappp,bitmapppp,bitmappppp,bitmapppppp;
    Uri filepath;

    private static final int PICK_IMAGE_REQUEST_PASS = 1;
    private static final int PICK_IMAGE_REQUEST_COLLEGE = 2;
    private static final int PICK_IMAGE_REQUEST_CAST = 3;
    private static final int PICK_IMAGE_REQUEST_ARC = 4;
    private static final int PICK_IMAGE_REQUEST_MARKSHEET = 5;
    private static final int PICK_IMAGE_REQUEST_ADHR = 6;


    String name, blood, number, address, adhrno, dob, nationality, percentage, fathername, fathercontact, fatheroccupation, addressfather;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admission_from);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        etname=findViewById(R.id.rtregistername);
        etblood=findViewById(R.id.etbloodgroup);
        etnumber=findViewById(R.id.etNumber);
        etaddress=findViewById(R.id.etregisteraddress);
        etadhrno=findViewById(R.id.etregisteradharno);
        etdateofbirth=findViewById(R.id.etdateofbirth);
        etnationality=findViewById(R.id.etNationality);
        etfathername=findViewById(R.id.etfathername);
        etfathercontactnumber=findViewById(R.id.etfathernumber);
        etaddressfather=findViewById(R.id.etfatheraddress);
        etpercentage=findViewById(R.id.etPercentage);

        cast=findViewById(R.id.spinnercast);
        admissionyear=findViewById(R.id.spinneradmissionyear);
        branch=findViewById(R.id.spinnerbarnch);
        lastexam=findViewById(R.id.spinnerlastexam);



        btnSubmit=findViewById(R.id.btnsubmitform);

        String[] castOptions = {"SC", "ST", "OBC", "SBC", "VJ", "NT-B", "NT-C", "NT-D", "OPEN"};
        ArrayAdapter<String> castAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, castOptions);
        castAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cast.setAdapter(castAdapter);

        cast.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strcast = castOptions[i]; // Save selected c
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        String[] branchOptions = {"CO", "CIVIL", "MECHANICAL", "ELECTRICAL", "IT"};
        ArrayAdapter<String> branchAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchOptions);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branch.setAdapter(branchAdapter);

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                strbarnch = branchOptions[position]; // Save selected branch
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        String[] admissionYearOptions = {"1 YEAR", "2 YEAR"};
        ArrayAdapter<String> admissionYearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, admissionYearOptions);
        admissionYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        admissionyear.setAdapter(admissionYearAdapter);

        admissionyear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                stradissionmyer = admissionYearOptions[position]; // Save selected admission year
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });

        String[] lastExamOptions = {"10TH", "12TH", "ITI"};
        ArrayAdapter<String> lastExamAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lastExamOptions);
        lastExamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lastexam.setAdapter(lastExamAdapter);

        lastexam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                strlstexm = lastExamOptions[position]; // Save selected last exam
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
        




        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 name = etname.getText().toString().trim();
                 blood = etblood.getText().toString().trim();
                 number = etnumber.getText().toString().trim();
                 address = etaddress.getText().toString().trim();
                 adhrno = etadhrno.getText().toString().trim();
                 dob = etdateofbirth.getText().toString().trim();
                 nationality = etnationality.getText().toString().trim();
                 fathername = etfathername.getText().toString().trim();
                 fathercontact = etfathercontactnumber.getText().toString().trim();
                 addressfather = etaddressfather.getText().toString().trim();
                 percentage=etpercentage.getText().toString().trim();

                if (name.isEmpty()) {
                    etname.setError("Name is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (blood.isEmpty()) {
                    etblood.setError("Blood group is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (number.isEmpty()) {
                    etnumber.setError("Number is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (address.isEmpty()) {
                    etaddress.setError("Address is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (adhrno.isEmpty()) {
                    etadhrno.setError("Aadhar number is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (dob.isEmpty()) {
                    etdateofbirth.setError("Date of birth is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (nationality.isEmpty()) {
                    etnationality.setError("Nationality is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (fathername.isEmpty()) {
                    etfathername.setError("Father's name is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (fathercontact.isEmpty()) {
                    etfathercontactnumber.setError("Father's contact number is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (fathercontact.length() != 10) {
                    etfathercontactnumber.setError("Father's contact number should be 10 digits");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (addressfather.isEmpty()) {
                    etaddressfather.setError("Father's address is required");
                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (strcast.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (strbarnch.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (stradissionmyer.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else if (strlstexm.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please fill all fields properly", Toast.LENGTH_SHORT).show();
                } else {
                    // If all fields are valid, proceed with further action
                    // You can now process the data, for example:
                    // Save the data or proceed with next steps here.
                    generateFormnumber();
                   passdat();
                   Intent i = new Intent(AdmissionFrom.this,Doc.class);
                   i.putExtra("formnumber",Formnumber);
                   startActivity(i);
                }


            }
        });



    }

    private void passdat() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("formno", Formnumber);
        params.put("studentname", name);
        params.put("dateofbirth", dob);
        params.put("adhrno", adhrno);
        params.put("cast", strcast);
        params.put("nationality", nationality);
        params.put("address", address);
        params.put("contactnumber", number);
        params.put("barnch", strbarnch);
        params.put("admission_year", stradissionmyer);
        params.put("lastexamination", strlstexm);
        params.put("lastexamppersentage",percentage);
        params.put("fathername", fathername);
        params.put("parentcontactno", fathercontact);
        params.put("addressparent", addressfather);
        params.put("bloodgroup", blood);

        client.post(urls.admission,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        Toast.makeText(AdmissionFrom.this, "Data Go", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AdmissionFrom.this, "Data Already Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdmissionFrom.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String generateFormnumber() {
        // Generate a random number between 10000 and 99999 (5 digits)
        int randomNumber = (int) (Math.random() * 90000) + 10000;

        // Convert the number to a string
        Formnumber = String.valueOf(randomNumber);

        // Return the generated number as a string
        return Formnumber;
    }
}