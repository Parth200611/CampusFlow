package com.hyperX.campusflow;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ViewAllAdmission extends AppCompatActivity {
     RecyclerView rvList;
     TextView tvNoStudents;
     List<POJOALLStudent> studentList; // Sample student list
     StudentAdapter adapter; // Custom adapter for RecyclerView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_all_admission);
        getWindow().setStatusBarColor(ContextCompat.getColor(ViewAllAdmission.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ViewAllAdmission.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        rvList = findViewById(R.id.rvList);
      tvNoStudents=findViewById(R.id.tvNostu);


        studentList = new ArrayList<>();


        rvList.setLayoutManager(new LinearLayoutManager(this));
        getData();

    }

    private void getData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(urls.getAllAdmisson,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("API_SUCCESS", "Response: " + response.toString());

                try {
                    if (!response.has("getAdmission")) {
                        Log.d("API_SUCCESS", "Response: " + response.toString());

                        return;
                    }
                    studentList.clear();

                    JSONArray jsonArray = response.getJSONArray("getAdmission");

                    if (jsonArray.length() == 0) {
                        rvList.setVisibility(View.GONE);
                        tvNoStudents.setVisibility(View.VISIBLE);
                    } else {
                        rvList.setVisibility(View.VISIBLE);
                        tvNoStudents.setVisibility(View.GONE);
                    }


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String id = obj.optString("id", "N/A");
                        String studentName = obj.optString("studentname", "N/A");
                        String branch = obj.optString("barnch", "N/A");
                        String contact = obj.optString("contactnumber", "N/A");
                        String parentContact = obj.optString("parentcontactno", "N/A");
                        String room = obj.optString("room", "N/A");

                        studentList.add(new POJOALLStudent(id, studentName, branch, contact, parentContact, room));
                    }

                    if (adapter == null) {
                        adapter = new StudentAdapter(studentList, ViewAllAdmission.this);
                        rvList.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Log.e("API_ERROR", "JSON Parsing Error: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("API_FAILURE", "Status Code: " + statusCode);

                if (errorResponse != null) {
                    Log.e("API_FAILURE", "Error Response: " + errorResponse.toString());
                } else {
                    Log.e("API_FAILURE", "Error Message: " + throwable.getMessage());
                }
            }
        });
    }
}