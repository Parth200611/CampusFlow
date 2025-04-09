package com.hyperX.campusflow;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class LeaveRequest extends Fragment {
    EditText etName, etBranch, etSem, etEnroll, etStartDate, etEndDate, etTitle, etDescription;
     ImageView ivNoticeImage;
     Button btnAddImage, btnPostNotice;
     String startdate,enddate,name,branch,sem,enroll,title,dis;
    Bitmap bitmap;
    Uri filepath;
    private  int pick_image_request=789;

    private static final int PICK_IMAGE_REQUEST_PASS = 1;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_leave_request, container, false);
        etName = view.findViewById(R.id.et_name);
        etBranch = view.findViewById(R.id.et_barnch);
        etSem = view.findViewById(R.id.et_sem);
        etEnroll = view.findViewById(R.id.et_enroll);
        etStartDate = view.findViewById(R.id.et_startdate);
        etEndDate = view.findViewById(R.id.et_enddate);
        etTitle = view.findViewById(R.id.et_title);
        etDescription = view.findViewById(R.id.et_dis);
        btnPostNotice = view.findViewById(R.id.btnPostNotice);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();

            }
        });
        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker2();

            }
        });



        btnPostNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()){
                    etName.setError("Enter Name");
                }else  if (etBranch.getText().toString().isEmpty()){
                    etBranch.setError("Enter Branch");
                }else  if (etSem.getText().toString().isEmpty()){
                    etSem.setError("Enter Sem");
                }else  if (etEnroll.getText().toString().isEmpty()){
                    etEnroll.setError("Enter Enrollment No");
                }else{
                    name=etName.getText().toString().trim(); 
                    branch=etBranch.getText().toString().trim(); 
                    sem=etSem.getText().toString().trim();
                    title=etTitle.getText().toString().trim();
                    dis=etDescription.getText().toString().trim();
                    enroll=etEnroll.getText().toString().trim();
                    progressDialog=new ProgressDialog(getActivity());
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    
                    postdata();
                }
            }
        });
        return view;
    }

    private void postdata() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        client.setTimeout(30000); // 30 seconds

        params.put("name", name);
        params.put("branch", branch);
        params.put("sem", sem);
        params.put("enroll", enroll);
        params.put("sdate", startdate);
        params.put("edate", enddate);
        params.put("title", title);
        params.put("dis", dis);

        client.post(urls.addleavedata, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String status = response.getString("success");
                    if (status.equals("1")) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Request Posted!", Toast.LENGTH_SHORT).show();
                        clear();
                    } else {
                        Toast.makeText(getActivity(), "Failed to Add Notice", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.e("JSON_ERROR", "Parsing error: " + e.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong. Please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Log.e("HTTP_ERROR", "Request failed: " + (throwable != null ? throwable.getMessage() : "Unknown error"));
                if (errorResponse != null) {
                    Log.e("HTTP_ERROR", "Response: " + errorResponse.toString());
                }
                Toast.makeText(getActivity(), "Failed to post data. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clear() {
        etName.setText("");
        etBranch.setText("");
        etStartDate.setText("");
        etSem.setText("");
        etEndDate.setText("");
        etEnroll.setText("");
        etTitle.setText("");
        etDescription.setText("");


    }


    private void SelectUserProfileimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image For Profil"),pick_image_request);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==pick_image_request && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                ivNoticeImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void UserImageSaveTodatabase(Bitmap bitmap, String strTitle) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.image, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(getActivity(), "Image Save as Notice "+strTitle, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(getActivity(), "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", strTitle); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap(bitmap)));

                return parms;

            }

        };
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void showDatePicker2() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, month, dayOfMonth) -> {
                    enddate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    etEndDate.setText(enddate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, month, dayOfMonth) -> {
                    startdate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    etStartDate.setText(startdate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}