package com.mountreachsolution.campusflow;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;


public class profilpage extends Fragment {

    TextView tvname, tvage, tvadhareno, tvenrollment, tvMobileNo;
    CircleImageView cvimage;
    Button btnlogout;
    SharedPreferences sharedPreferences;
    public static final String PREFS_NAME = "LoginPrefs";
    String savedUsername,studentName,email,adhrno,image,mobileno;
    TextView tvroom;
    String room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profilpage, container, false);
        btnlogout = view.findViewById(R.id.btnlogout);tvname = view.findViewById(R.id.tvnameValue);
        cvimage = view.findViewById(R.id.profileImage);
        btnlogout = view.findViewById(R.id.btnlogout);
        tvname = view.findViewById(R.id.tvnameValue);
        tvage = view.findViewById(R.id.agevalue);
        tvadhareno = view.findViewById(R.id.adharvalue);
        tvenrollment = view.findViewById(R.id.addressValue);
        tvMobileNo = view.findViewById(R.id.Mobilenovalue);
        tvroom = view.findViewById(R.id.tvroomno);

        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Fetch username
         savedUsername = sharedPreferences.getString("loggedInUsername", "Guest");
        tvname.setText(savedUsername);

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });
        
        getdata();
        return view;
    }

    private void getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.studentprofil,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API_RESPONSE", "Response: " + response); // Debugging

                        try {
                            // Validate if response is JSON
                            if (!response.trim().startsWith("{")) {
                                Log.e("API_ERROR", "Invalid response: " + response);
                                return;
                            }

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("getAdmission");

                            if (jsonArray.length() > 0) {
                                JSONObject data = jsonArray.getJSONObject(0); // Get first item

                                studentName = data.optString("studentname", "N/A");
                                adhrno = data.optString("adhrno", "N/A");
                                email = data.optString("email", "N/A");
                                mobileno = data.optString("contactnumber", "N/A");
                                image = data.optString("passphoto", "");
                                room = data.optString("room", "");

                                // Update UI on main thread
                                tvname.setText(studentName);
                                tvadhareno.setText(adhrno);
                                tvage.setText(email);
                                tvMobileNo.setText(mobileno);
                                tvenrollment.setText(savedUsername);
                                tvroom.setText(room);

                                if (!image.isEmpty()) {
                                    Glide.with(getActivity())
                                            .load(urls.address + "images/" + image)
                                            .skipMemoryCache(true)
                                            .error(R.drawable.baseline_person_24) // Error placeholder
                                            .into(cvimage);
                                }
                            } else {
                                Log.e("API_ERROR", "No data found.");
                            }
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY_ERROR", "Error: " + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enrollment", savedUsername);  // Sending Enrollment Number
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void logoutUser() {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(LoginActivity.PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear(); // Clear login data
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear activity stack
        startActivity(intent);
        getActivity().finish();
    }
}