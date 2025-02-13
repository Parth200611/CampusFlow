package com.mountreachsolution.campusflow;

import static com.mountreachsolution.campusflow.profilpage.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewAdmissiondetails extends AppCompatActivity {
    ImageView ivArcImage, ivCast, ivAadharCard, ivMarksheet;
    ImageView  cvImage;
    TextView tvName, tvExam, tvPercentage, tvFromNo, tvBranch, tvAdmissionYear, tvDOB, tvAadharNo, tvContactNo, tvEmail;
    String strenrollment;
    SharedPreferences sharedPreferences;
    String fromno,name,dateofbirth,adhrno,cast,nationality,address,contactnumber,barnch,admissionyear,lastexamination,lastexamppersentage,fathername,parentcontactno,addressparent,bloodgroup,arcrecipt,passphoto,adhrcardphoto,periousyearmarksheet,castcetificate,email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_admissiondetails);
        getWindow().setStatusBarColor(ContextCompat.getColor(ViewAdmissiondetails.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ViewAdmissiondetails.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Fetch username
        strenrollment = sharedPreferences.getString("loggedInUsername", "Guest");
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
        
        getData(strenrollment);


    }

    private void getData(String strenrollment) {
        RequestQueue requestQueue = Volley.newRequestQueue(ViewAdmissiondetails.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getAdmissiondata, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("getAdmissiondetails");

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
                        lastexamppersentage=jsonObject1.getString("lastexamppersentahe");
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
                    Glide.with(ViewAdmissiondetails.this)
                            .load(urls.address + "images/"+passphoto)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(cvImage);
                    Glide.with(ViewAdmissiondetails.this)
                            .load(urls.address + "images/"+arcrecipt)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivArcImage);
                    Glide.with(ViewAdmissiondetails.this)
                            .load(urls.address + "images/"+adhrcardphoto)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivAadharCard);
                    Glide.with(ViewAdmissiondetails.this)
                            .load(urls.address + "images/"+periousyearmarksheet)
                            .skipMemoryCache(true)
                            .error(R.drawable.baseline_person_24)// Resize the image to 800x800 pixels
                            .into(ivMarksheet);
                    Glide.with(ViewAdmissiondetails.this)
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
                Toast.makeText(ViewAdmissiondetails.this, "Server Down", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enroll", strenrollment); // Send cast as a POST parameter
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}