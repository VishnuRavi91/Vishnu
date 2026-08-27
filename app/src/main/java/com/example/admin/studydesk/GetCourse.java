package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.studydesk.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GetCourse extends AppCompatActivity {
    private static final String TAG = "GetCourse";
    String status,message;
    RecyclerView recyclerView;
    JSONArray jsonArray;
    SimpleAdapterCourse simpleAdapterCourse;
    JSONObject jsonObj;
    Button btnLogout;
    String jwtKey= "ar*(}[+=)study@#$%desk";
    public List<String> list = new ArrayList<String>();

    /**
     * Records a failure for onPostExecute to report. Every early return in doInBackground goes
     * through here so a network or payload problem shows a message instead of crashing on a null.
     */
    private String fail(String reason) {
        status = "error";
        message = reason;
        return status;
    }

    /** Dismissing a dialog whose activity has already gone throws; swallow that. */
    private static void dismissDialog(ProgressDialog dialog) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (IllegalArgumentException ignored) {
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_course);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        /*if (!checkPermission()){
            requestPermission();
        }*/
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        Window window =getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        btnLogout=(Button)findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
                String token = prefs.getString("token", null);
                new AsyncCallSoap_LOgout(token).execute();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(GetCourse.this));
        new AsyncCallSoap(token).execute();
        new AsyncCallSoap_Log(token).execute();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decodeJWT(String jwtToken) {
        try {
            // Decode the JWT without verifying (if you just want to read the payload)
            String[] parts = jwtToken.split("\\.");
            if (parts.length != 3) {
                return "Invalid JWT Token";
            }

            // Decode payload (base64 decoding)
            byte[] decodedBytes = Base64.getUrlDecoder().decode(parts[1]);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Error decoding JWT: " + e.getMessage();
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {

        private final String token;

        AsyncCallSoap(String tk) {

            token = tk;
        }
        private final ProgressDialog dialog = new ProgressDialog(GetCourse.this);
        @Override
        protected String doInBackground (String...params){
            try {

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Client/getcourses/");




            StringEntity stringEntity = null;
            try {
                stringEntity = new StringEntity(json.toString());
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, "Could not build the courses request", e);
            }
            if (stringEntity == null) {
                return fail("Could not prepare the request. Please try again.");
            }
            post.addHeader("authorization",token);
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");

            post.setEntity(stringEntity);

            try {
                response = client.execute(post);
            } catch (IOException e) {
                Log.e(TAG, "getcourses request failed", e);
            }
            if (response == null) {
                return fail("Could not reach the server. Please check your connection.");
            }

            String responseBody = null;
            try {
                responseBody = EntityUtils
                        .toString(response.getEntity());
            } catch (IOException e) {
                Log.e(TAG, "Could not read the courses response", e);
            }
            if (responseBody == null) {
                return fail("The server returned an empty response. Please try again.");
            }

            jsonObj = null;
            try {
                jsonObj = new JSONObject(responseBody);
                status = jsonObj.getString("status");
                message = jsonObj.optString("message", "");
            } catch (JSONException e) {
                Log.e(TAG, "Unexpected courses response: " + responseBody, e);
                return fail("The server sent an unexpected response.");
            }

            if ("success".equals(status)){
                try {
                    String tutorials="";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        tutorials = decodeJWT(jsonObj.getString("tutorials"));
                    }
                    JSONObject jsonObject = new JSONObject(tutorials);

                    // Get the 'modules' array
                    jsonArray = jsonObject.getJSONArray("modules");
//                    jsonArray =  tutorials .getJSONArray("tutorials");
                    for (int i=0;i<jsonArray.length();i++){
                        list.add(jsonArray.get(i).toString().trim());
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Could not read courses out of the payload", e);
                    return fail("Could not read the course list.");
                }
                if (jsonArray == null) {
                    return fail("No courses available right now.");
                }
            }
            return status;
                    } catch (Exception e) {
                // Containment: an uncaught throw here would kill the process.
                Log.e("GetCourse", "Background task failed", e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
            dismissDialog(dialog);
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // TV.setText(result);
            if (isFinishing() || isDestroyed()) {
                return;
            }
            if ("success".equals(status) && jsonArray != null){
                Date currentTime = Calendar.getInstance().getTime();
                SharedPreferences.Editor editor = getSharedPreferences("TOKEN", MODE_PRIVATE).edit();
                editor.putString("token", token);
                editor.putLong("lastloggedtime", currentTime.getTime());
                editor.apply();
                simpleAdapterCourse=new SimpleAdapterCourse(sort(jsonArray, new Comparator() {
                    @Override
                    public int compare(Object a, Object b) {
                        JSONObject    ja = (JSONObject)a;
                        JSONObject    jb = (JSONObject)b;
                        return ja.optString("name", "").toLowerCase().compareTo(jb.optString("name", "").toLowerCase());
                    }
                }),GetCourse.this);
                recyclerView.setAdapter(simpleAdapterCourse);

                //Intent intent = new Intent(GetCourse.this, ModuleSelection.class);
               // startActivity(intent);
            }else{
                Toast.makeText(GetCourse.this,
                        message == null || message.isEmpty() ? "Could not load the courses." : message,
                        Toast.LENGTH_LONG).show();
            }

        }

    }
    public static JSONArray sort(JSONArray array, Comparator c){
        List    asList = new ArrayList(array.length());
        for (int i=0; i<array.length(); i++){
            asList.add(array.opt(i));
        }
        Collections.sort(asList, c);
        JSONArray  res = new JSONArray();
        for (Object o : asList){
            res.put(o);
        }
        return res;
    }
    @Override
    public void onBackPressed() {
       /* SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        new AsyncCallSoap_LOgout(token).execute();
       */
        //finish();
        //System.exit(0);
        //super.onBackPressed();
        //this.finishAffinity();
        Intent intent = new Intent(GetCourse.this, Second.class);
        startActivity(intent);
    }
    public class AsyncCallSoap_LOgout extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_LOgout(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(GetCourse.this);
        @Override
        protected String doInBackground (String...params){
            try {

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Client/logout/");
            post.addHeader("authorization",token);


            StringEntity stringEntity = null;
            try {
                stringEntity = new StringEntity(json.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            post.setEntity(stringEntity);

            try {
                response = client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String responseBody = null;
            try {
                responseBody = EntityUtils
                        .toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String res= responseBody.toString();
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String Response = null;
            try {
                status = jsonObj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                message=jsonObj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
                    } catch (Exception e) {
                // Containment: an uncaught throw here would kill the process.
                Log.e("GetCourse", "Background task failed", e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
            dialog.dismiss();
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // TV.setText(result);
            SharedPreferences preferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(GetCourse.this, Second.class);
            startActivity(intent);

        }

    }

    public class AsyncCallSoap_Log extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_Log(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(GetCourse.this);
        @Override
        protected String doInBackground (String...params){
            try {

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Client/activitylog/");
            post.addHeader("authorization",token);


            StringEntity stringEntity = null;
            try {
                stringEntity = new StringEntity(json.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            post.setEntity(stringEntity);

            try {
                response = client.execute(post);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String responseBody = null;
            try {
                responseBody = EntityUtils
                        .toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String res= responseBody.toString();
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String Response = null;
            try {
                status = jsonObj.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                message=jsonObj.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return status;
                    } catch (Exception e) {
                // Containment: an uncaught throw here would kill the process.
                Log.e("GetCourse", "Background task failed", e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
            dialog.dismiss();
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // TV.setText(result);


        }

    }
}
