package com.mountreachsolution.campusflow;

import android.os.Bundle;
import android.view.PixelCopy;

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
import java.util.List;

public class ViewLeaveRequest extends AppCompatActivity {
    RecyclerView rvlist;
    List<POJORequest> pojoRequests;
    AdpterRequest adpterRequest;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_leave_request);
        getWindow().setStatusBarColor(ContextCompat.getColor(ViewLeaveRequest.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ViewLeaveRequest.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        
        rvlist=findViewById(R.id.rvleavelist);
        pojoRequests=new ArrayList<>();
        rvlist.setLayoutManager(new LinearLayoutManager(ViewLeaveRequest.this));
        
        getData();

    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(ViewLeaveRequest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getLeaverequest, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("getallrequest");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id=jsonObject1.getString("id");
                        String name=jsonObject1.getString("name");
                        String branch=jsonObject1.getString("branch");
                        String sem=jsonObject1.getString("sem");
                        String enroll=jsonObject1.getString("enroll");
                        String strat=jsonObject1.getString("startdate");
                        String end=jsonObject1.getString("enddate");
                        String title=jsonObject1.getString("title");
                        String dis=jsonObject1.getString("discription");
                        pojoRequests.add(new POJORequest(id,name,branch,sem,enroll,strat,end,title,dis));
                    }
                    adpterRequest=new AdpterRequest(pojoRequests,ViewLeaveRequest.this);
                    rvlist.setAdapter(adpterRequest);



                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }
}