package com.hyperX.campusflow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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
import java.util.List;

public class ViewWorker extends AppCompatActivity
{
    RecyclerView rvlist;
    TextView tvnodata;
    List<POJOwork> pojOworks;
    Adpterwork adpterwork;
    String selectedStatus, selectedGender, fullname, adharNo, mobileNo, address, dob, joiningDate, qualification,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_worker);
        getWindow().setStatusBarColor(ContextCompat.getColor(ViewWorker.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ViewWorker.this,R.color.white));

        rvlist=findViewById(R.id.rvlist);
        tvnodata =findViewById(R.id.noworker);
        pojOworks = new ArrayList<>();
        rvlist.setLayoutManager(new LinearLayoutManager(ViewWorker.this));
        getData();
        



    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(ViewWorker.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getworker, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("getallworker");
                    if (jsonArray.length() == 0) {
                        // No data found, show tvnoadmision and hide rvlist
                        tvnodata.setVisibility(View.VISIBLE);
                        rvlist.setVisibility(View.GONE);
                        return; // Exit function as there's no data to display
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        id=jsonObject1.getString("id");
                        fullname=jsonObject1.getString("name");
                        adharNo=jsonObject1.getString("adhrno");
                        mobileNo=jsonObject1.getString("mobileno");
                        address=jsonObject1.getString("address");
                        dob=jsonObject1.getString("dob");
                        joiningDate=jsonObject1.getString("joinningdate");
                        selectedStatus=jsonObject1.getString("status");
                        selectedGender=jsonObject1.getString("gender");
                        qualification=jsonObject1.getString("qulification");
                        pojOworks.add(new POJOwork(selectedStatus,selectedGender,fullname,adharNo,mobileNo,address,dob,joiningDate,qualification,id));

                    }
                    adpterwork = new Adpterwork(pojOworks,ViewWorker.this);
                    rvlist.setAdapter(adpterwork);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewWorker.this, "server Error", Toast.LENGTH_SHORT).show();
            }


        });

        requestQueue.add(stringRequest);



    }
}