package com.mountreachsolution.campusflow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Doc extends AppCompatActivity {
    private  int pick_image_request=789;
    Bitmap bitmap,bitmapp,bitmappp,bitmapppp,bitmappppp,bitmapppppp;
    Uri filepath;

    private static final int PICK_IMAGE_REQUEST_PASS = 1;
    private static final int PICK_IMAGE_REQUEST_COLLEGE = 2;
    private static final int PICK_IMAGE_REQUEST_CAST = 3;
    private static final int PICK_IMAGE_REQUEST_ARC = 4;
    private static final int PICK_IMAGE_REQUEST_MARKSHEET = 5;
    private static final int PICK_IMAGE_REQUEST_ADHR = 6;

    Button btnpass,btnadhr,btnarc,btncollege,btnmarksheet,btncast;
    AppCompatButton btndone;
    String Formnumber;
    ImageView ivpassimage,ivadharcard,ivarcimage,ivcollegerecepit,ivmarksheet,ivcastcertificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doc);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Formnumber=getIntent().getStringExtra("formnumber");

        btnadhr=findViewById(R.id.btnaddadhrcardimage);
        btnmarksheet=findViewById(R.id.btnmarksheet);
        btnarc=findViewById(R.id.btnaddarcrecipt);
        btnpass=findViewById(R.id.btnaddprofilimage);
        btncast=findViewById(R.id.btncastcertificate);

        ivadharcard=findViewById(R.id.ivadhrimage);
        ivcastcertificate=findViewById(R.id.ivcastcertificate);
        ivmarksheet=findViewById(R.id.ivmarksheet);

        ivarcimage=findViewById(R.id.ivarcrecipt);
        ivpassimage=findViewById(R.id.ivprofilimage);

        btndone=findViewById(R.id.btnsubmitform);

        btnpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(PICK_IMAGE_REQUEST_PASS);
            }
        });


        btncast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(PICK_IMAGE_REQUEST_CAST);
            }
        });

        btnarc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(PICK_IMAGE_REQUEST_ARC);
            }
        });

        btnmarksheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(PICK_IMAGE_REQUEST_MARKSHEET);
            }
        });

        btnadhr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery(PICK_IMAGE_REQUEST_ADHR);
            }
});
btndone.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i = new Intent(Doc.this, LoginActivity.class);
        startActivity(i);
    }
});

    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                switch (requestCode) {
                    case PICK_IMAGE_REQUEST_PASS:
                        ivpassimage.setImageBitmap(bitmap);
                        PassImage(bitmap, Formnumber);
                        break;
                    case PICK_IMAGE_REQUEST_CAST:
                        ivcastcertificate.setImageBitmap(bitmap);
                        PassImage3(bitmap, Formnumber);
                        break;
                    case PICK_IMAGE_REQUEST_ARC:
                        ivarcimage.setImageBitmap(bitmap);
                        PassImage4(bitmap, Formnumber);
                        break;
                    case PICK_IMAGE_REQUEST_MARKSHEET:
                        ivmarksheet.setImageBitmap(bitmap);
                        PassImage5(bitmap, Formnumber);
                        break;
                    case PICK_IMAGE_REQUEST_ADHR:
                        ivadharcard.setImageBitmap(bitmap);
                        PassImage6(bitmap, Formnumber);
                        break;
                    default:
                        Toast.makeText(this, "Unknown request code", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






    private void PassImage6(Bitmap bitmapppppp, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.addadhrcard, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap5(bitmapppppp)));
                return parms;

            }

        };
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap6(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }




    private void PassImage8(Bitmap bitmappppp, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.address, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap8(bitmappppp)));
                return parms;

            }

        };
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap8(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void PassImage5(Bitmap bitmappppp, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.addmarksheet, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap5(bitmappppp)));
                return parms;

            }

        };
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap5(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }











    private void PassImage4(Bitmap bitmapppp, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.addarc, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap4(bitmapppp)));
                return parms;

            }

        };
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap4(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }







    private void PassImage3(Bitmap bitmappp, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.addcast, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap3(bitmappp)));
                return parms;

            }

        };
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap3(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }





    private void PassImage2(Bitmap bitmapp, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.address, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
                return parms;
            }

            @Override
            protected Map<String, DataPart> getByteData() throws AuthFailureError {
                Map<String,VolleyMultipartRequest.DataPart> parms = new HashMap<>();
                long imagename = System.currentTimeMillis();
                parms.put("pic",new VolleyMultipartRequest.DataPart(imagename+".jpeg",getfiledatafromBitmap2(bitmapp)));
                return parms;

            }

        };
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap2(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }






    private void PassImage(Bitmap bitmap, String formnumber) {
        VolleyMultipartRequest volleyMultipartRequest =  new VolleyMultipartRequest(Request.Method.POST, urls.adpassimage, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(Doc.this, "Image Save  ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Doc.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                String errorMsg = error.getMessage();
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    errorMsg = new String(error.networkResponse.data);
                }
                Log.e("UploadError", errorMsg);
                Toast.makeText(Doc.this, "Upload Error: " + errorMsg, Toast.LENGTH_LONG).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("tags", formnumber); // Adjusted to match PHP parameter name
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
        Volley.newRequestQueue(Doc.this).add(volleyMultipartRequest);
    }

    private byte[] getfiledatafromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}