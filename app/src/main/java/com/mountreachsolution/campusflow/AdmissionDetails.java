package com.mountreachsolution.campusflow;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdmissionDetails extends AppCompatActivity {
    ImageView ivArcImage, ivCast, ivAadharCard, ivMarksheet;
    ImageView  cvImage;
    TextView tvName, tvExam, tvPercentage, tvFromNo, tvBranch, tvAdmissionYear, tvDOB, tvAadharNo, tvContactNo, tvEmail;
     Button btnAccept, btnReject;
     String id;
     String enrollement,password,roomnumber;
    String fromno,name,dateofbirth,adhrno,cast,nationality,address,contactnumber,barnch,admissionyear,lastexamination,lastexamppersentage,fathername,parentcontactno,addressparent,bloodgroup,arcrecipt,passphoto,adhrcardphoto,periousyearmarksheet,castcetificate,email;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admission_details);
        getWindow().setStatusBarColor(ContextCompat.getColor(AdmissionDetails.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AdmissionDetails.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        id=getIntent().getStringExtra("id");
        cvImage = findViewById(R.id.cvimage);
        // TextViews
        tvName = findViewById(R.id.tvname);
        tvExam = findViewById(R.id.tvExam);
        tvPercentage = findViewById(R.id.tvPercentage);
        tvFromNo = findViewById(R.id.tvFromno);
        tvBranch = findViewById(R.id.tvBranch);
        tvAdmissionYear = findViewById(R.id.tvAdmissionYear);
        tvDOB = findViewById(R.id.tvdob);
        tvAadharNo = findViewById(R.id.tvadhrno);
        tvContactNo = findViewById(R.id.tvcontactno);
        tvEmail = findViewById(R.id.tvEmail);

        // ImageViews
        ivArcImage = findViewById(R.id.ivarcimage);
        ivCast = findViewById(R.id.ivcast);
        ivAadharCard = findViewById(R.id.ivadhrcard);
        ivMarksheet = findViewById(R.id.ivmarsheet);

        // Buttons
        btnAccept = findViewById(R.id.btnaccept);
        btnReject = findViewById(R.id.btnreject);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 enrollement = generateEnrollmentNumber();
                 password = generateRandomPassword();
                generateRoomNumber();
                passdat();
                

                if (!email.isEmpty()) {
                    sendEmailConfirmation(email, enrollement, password,roomnumber);
                } else {
                    Toast.makeText(AdmissionDetails.this, "Email not available!", Toast.LENGTH_SHORT).show();
                }            }
        });
        
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAdmissionData(id);
            }
        });

        getdata(id);

    }

    private void passdat() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("formno", fromno);
        params.put("studentname", name);
        params.put("dateofbirth", dateofbirth);
        params.put("adhrno", adhrno);
        params.put("cast", cast);
        params.put("nationality", nationality);
        params.put("address", address);
        params.put("contactnumber", contactnumber);
        params.put("barnch", barnch);
        params.put("admission_year", admissionyear);
        params.put("lastexamination", lastexamination);
        params.put("lastexamppersentage",lastexamppersentage);
        params.put("fathername", fathername);
        params.put("parentcontactno", parentcontactno);
        params.put("addressparent",addressparent);
        params.put("bloodgroup", bloodgroup);
        params.put("arcrecipt", arcrecipt);
        params.put("passphoto", passphoto);
        params.put("adhrcardphoto", adhrcardphoto);
        params.put("periousyearmarksheet", periousyearmarksheet);
        params.put("castcetificate", castcetificate);
        params.put("email",email);
        params.put("enrollment",enrollement);
        params.put("password",password);
        params.put("room",roomnumber);



        client.post(urls.admissionconfirm,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status=response.getString("success");
                    if (status.equals("1")){
                        deleteAdmissionData(id);
                        Toast.makeText(AdmissionDetails.this, "Admission Confirm", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AdmissionDetails.this, "Data Already Exist", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(AdmissionDetails.this, "server error", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void deleteAdmissionData(String id) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.removeuser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                Toast.makeText(AdmissionDetails.this, "Admission rejected successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AdmissionDetails.this, "Failed to reject admission", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AdmissionDetails.this, "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AdmissionDetails.this, "Server Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        // Add request to queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private String generateEnrollmentNumber() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);  // 6-digit random number
        return "ENR" + number;
    }
    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 8; i++) {  // Generate 8-character password
            password.append(characters.charAt(random.nextInt(characters.length())));
        }

        return password.toString();
    }
    private void sendEmailConfirmation(String email, String enrollment, String password,String roomnumber) {
        String subject = "Admission Confirmation - Enrollment Details";
        String message = "Dear Student,\n\n"
                + "Your admission has been confirmed.\n\n"
                + "Enrollment Number: " + enrollment + "\n"
                + "Password: " + password + "\n\n" + "Room No: " + roomnumber + "\n\n"
                + "Use these credentials to log in.\n\n"
                + "Best Regards,\nYour Institution";

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        } catch (Exception e) {
            Toast.makeText(this, "No email app found!", Toast.LENGTH_SHORT).show();
        }
    }



    private void getdata(String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(AdmissionDetails.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getdatabyid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("getAdmission");

                    for (int i =0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id1=jsonObject1.getString("id");
                         fromno=jsonObject1.getString("formno");
                         name=jsonObject1.getString("studentname");
                         dateofbirth=jsonObject1.getString("dateofbirth");
                         adhrno=jsonObject1.getString("adhrno");
                         cast=jsonObject1.getString("cast");
                         nationality=jsonObject1.getString("nationality");
                         address=jsonObject1.getString("address");
                         contactnumber=jsonObject1.getString("contactnumber");
                         barnch=jsonObject1.getString("barnch");
                         admissionyear=jsonObject1.getString("admissionyear");
                         lastexamination=jsonObject1.getString("lastexamination");
                         lastexamppersentage=jsonObject1.getString("lastexamppersentage");
                         fathername=jsonObject1.getString("fathername");
                         parentcontactno=jsonObject1.getString("parentcontactno");
                         addressparent=jsonObject1.getString("addressparent");
                         bloodgroup=jsonObject1.getString("bloodgroup");
                         arcrecipt=jsonObject1.getString("arcrecipt");
                         passphoto=jsonObject1.getString("passphoto");
                         adhrcardphoto=jsonObject1.getString("adhrcardphoto");
                         periousyearmarksheet=jsonObject1.getString("periousyearmarksheet");
                         castcetificate=jsonObject1.getString("castcetificate");
                         email=jsonObject1.getString("email");

                    }
                    tvName.setText(name);
                    tvDOB.setText(dateofbirth);
                    tvAdmissionYear.setText(admissionyear);
                    tvPercentage.setText(lastexamppersentage);
                    tvBranch.setText(barnch);
                    tvExam.setText(lastexamination);
                    tvEmail.setText(email);
                    tvFromNo.setText(fromno);
                    tvContactNo.setText(contactnumber);
                    tvAadharNo.setText(adhrno);
                    Glide.with(AdmissionDetails.this)
                            .load(urls.address + "images/"+passphoto)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(cvImage);
                    Glide.with(AdmissionDetails.this)
                            .load(urls.address + "images/"+arcrecipt)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivArcImage);
                    Glide.with(AdmissionDetails.this)
                            .load(urls.address + "images/"+adhrcardphoto)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivAadharCard);
                    Glide.with(AdmissionDetails.this)
                            .load(urls.address + "images/"+periousyearmarksheet)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivMarksheet);
                    Glide.with(AdmissionDetails.this)
                            .load(urls.address + "images/"+castcetificate)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivCast);





                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdmissionDetails.this, "Server Down", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", id); // Send cast as a POST parameter
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void generateRoomNumber() {
        Random random = new Random();
        int number = random.nextInt(900) + 100; // Generate a number between 100 and 999
        roomnumber = "Room " + number; // Store in variable
    }
}