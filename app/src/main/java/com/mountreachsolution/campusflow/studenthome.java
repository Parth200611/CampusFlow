package com.mountreachsolution.campusflow;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class studenthome extends Fragment {
    TextView tvnodat;
    RecyclerView rvlist;
     List<POJOnotice> pojOnotice;
     AdpterNotice adpterNotice;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_studenthome, container, false);
        tvnodat=view.findViewById(R.id.tvnodata);
        rvlist=view.findViewById(R.id.rvnoticelist);
        pojOnotice=new ArrayList<>();
        rvlist.setLayoutManager(new LinearLayoutManager(getContext()));

        getData();

        return view;
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.getnotice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray =jsonObject.getJSONArray("getallnotice");
                    if (jsonArray.length() == 0) {
                        // No data found, show tvnoadmision and hide rvlist
                        tvnodat.setVisibility(View.VISIBLE);
                        rvlist.setVisibility(View.GONE);
                        return; // Exit function as there's no data to display
                    }
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
                        String id=jsonObject1.getString("id");
                        String date=jsonObject1.getString("date");
                        String time=jsonObject1.getString("time");
                        String title=jsonObject1.getString("title");
                        String discription=jsonObject1.getString("description");
                        String image=jsonObject1.getString("image");
                        pojOnotice.add(new POJOnotice(id,date,time,title,discription,image));
                    }
                    adpterNotice = new AdpterNotice(pojOnotice,getActivity());
                    rvlist.setAdapter(adpterNotice);
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