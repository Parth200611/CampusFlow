package com.mountreachsolution.campusflow;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Admissionlist extends AppCompatActivity {
    RecyclerView rvlist;
    TextView tvnoadmision;
    String cast;
    AdpterAdmissioncast adpterAdmissioncast;
   List<POJOAdmissioncast> pojoAdmissioncasts;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admissionlist);
        getWindow().setStatusBarColor(ContextCompat.getColor(Admissionlist.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(Admissionlist.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        cast =getIntent().getStringExtra("cast");

        rvlist = findViewById(R.id.rvlistofstudent);
        tvnoadmision =findViewById(R.id.tvnostudent);
        rvlist.setLayoutManager(new LinearLayoutManager(Admissionlist.this));
        pojoAdmissioncasts = new ArrayList<>();

         progressDialog = new ProgressDialog(Admissionlist.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();
        
        getData(cast);


    }

    private void getData(String cast) {
        RequestQueue requestQueue = Volley.newRequestQueue(Admissionlist.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.admissionlistcast, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("getAdmission");
                    if (jsonArray.length() == 0) {
                        // No data found, show tvnoadmision and hide rvlist
                        tvnoadmision.setVisibility(View.VISIBLE);
                        rvlist.setVisibility(View.GONE);
                        return; // Exit function as there's no data to display
                    }
                    progressDialog.dismiss();
                    for (int i =0;i<jsonArray.length();i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id=jsonObject1.getString("id");
                        String form=jsonObject1.getString("formno");
                        String studentname=jsonObject1.getString("studentname");
                        String dateofbirth=jsonObject1.getString("dateofbirth");
                        String adhrno=jsonObject1.getString("adhrno");
                        String cast=jsonObject1.getString("cast");
                        String nationality=jsonObject1.getString("nationality");
                        String address=jsonObject1.getString("address");
                        String contactnumber=jsonObject1.getString("contactnumber");
                        String barnch=jsonObject1.getString("barnch");
                        String admissionyear=jsonObject1.getString("admissionyear");
                        String lastexamination=jsonObject1.getString("lastexamination");
                        String lastexamppersentage=jsonObject1.getString("lastexamppersentage");
                        String fathername=jsonObject1.getString("fathername");
                        String parentcontactno=jsonObject1.getString("parentcontactno");
                        String addressparent=jsonObject1.getString("addressparent");
                        String bloodgroup=jsonObject1.getString("bloodgroup");
                        String arcrecipt=jsonObject1.getString("arcrecipt");
                        String passphoto=jsonObject1.getString("passphoto");
                        String adhrcardphoto=jsonObject1.getString("adhrcardphoto");
                        String periousyearmarksheet=jsonObject1.getString("periousyearmarksheet");
                        String castcetificate=jsonObject1.getString("castcetificate");
                        String email=jsonObject1.getString("email");
                        pojoAdmissioncasts.add(new POJOAdmissioncast(id,form,studentname,dateofbirth,adhrno,cast,nationality,address,contactnumber,barnch,
                                admissionyear,lastexamination,lastexamppersentage,fathername,parentcontactno,addressparent,bloodgroup,arcrecipt,passphoto,adhrcardphoto,periousyearmarksheet,castcetificate,email));

                    }
                    tvnoadmision.setVisibility(View.GONE);
                    rvlist.setVisibility(View.VISIBLE);
                    adpterAdmissioncast = new AdpterAdmissioncast(pojoAdmissioncasts,Admissionlist.this);
                    rvlist.setAdapter(adpterAdmissioncast);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Admissionlist.this, "Server Down", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cast", cast); // Send cast as a POST parameter
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pojoAdmissioncasts.clear(); // Clear previous data to avoid duplication
        getData(cast); // Fetch data again when coming back
    }

}