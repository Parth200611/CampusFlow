package com.mountreachsolution.campusflow;

import static com.mountreachsolution.campusflow.profilpage.PREFS_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class studentViewLeaveRequest extends AppCompatActivity {
    RecyclerView rvlist;
    List<POJOGetDta> pojoGetDtas;
    SharedPreferences sharedPreferences;
    String strenrollment;
    Adptergetdatastu adptergetdatastu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_view_leave_request);
        getWindow().setStatusBarColor(ContextCompat.getColor(studentViewLeaveRequest.this,R.color.blue));
        getWindow().setNavigationBarColor(ContextCompat.getColor(studentViewLeaveRequest.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Fetch username
        strenrollment = sharedPreferences.getString("loggedInUsername", "Guest");
        Log.d("SharedPreferences", "Logged in username: " + strenrollment);
        rvlist=findViewById(R.id.rvleavelist);
        pojoGetDtas=new ArrayList<>();
        rvlist.setLayoutManager(new LinearLayoutManager(studentViewLeaveRequest.this));

        geData();

    }

    private void geData() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("enroll", strenrollment);
        Log.d("API_REQUEST", "Sending enrollment: " + strenrollment); // Debug request

        client.post(urls.getDataEnroll, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("API_RESPONSE", "Response: " + response.toString()); // Debug response

                try {
                    if (!response.has("getrequest")) {
                        Log.e("API_ERROR", "Missing 'getrequest' in response!");
                        return;
                    }

                    JSONArray jsonArray = response.getJSONArray("getrequest");
                    pojoGetDtas.clear(); // Clear previous data

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String id = jsonObject1.optString("id", "N/A");
                        String name = jsonObject1.optString("name", "N/A");
                        String branch = jsonObject1.optString("branch", "N/A");
                        String sem = jsonObject1.optString("sem", "N/A");
                        String enroll = jsonObject1.optString("enroll", "N/A");
                        String strat = jsonObject1.optString("startdate", "N/A");
                        String end = jsonObject1.optString("enddate", "N/A");
                        String title = jsonObject1.optString("title", "N/A");
                        String dis = jsonObject1.optString("discription", "N/A");
                        String status = jsonObject1.optString("stauts", "N/A");

                        Log.d("API_DATA", "Parsed: " + id + ", " + name + ", " + enroll); // Debug each entry

                        pojoGetDtas.add(new POJOGetDta(id, name, branch, sem, enroll, strat, end, title, dis, status));
                    }

                    adptergetdatastu = new Adptergetdatastu(pojoGetDtas, studentViewLeaveRequest.this);
                    rvlist.setAdapter(adptergetdatastu);

                } catch (JSONException e) {
                    Log.e("JSON_ERROR", "JSONException: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("API_ERROR", "Request failed! Status: " + statusCode + ", Error: " + throwable.getMessage());
            }
        });
    }

}