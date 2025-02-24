package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ModuleSelection extends AppCompatActivity {
    RecyclerView recyclerView;
    String status;
    String message;
    Button btnlogout;
    JSONArray jsonArray;
    JSONObject jsonObj;
    public static String courseid;
    SimpleAdapterModule simpleAdapterModule;
    public List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_selection);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
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
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        btnlogout=(Button)findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
                String token = prefs.getString("token", null);
                new AsyncCallSoap_LOgout(token).execute();


            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(ModuleSelection.this));
       /* list.add(String.format("%100s", " Module - I (Projections of Lines)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vSumgMnaMOkOOz5XFZvSaGL0tSERwjc6QiMjG0a5A-v6Ch2HvkUUOMCDpkhB2TT-r3653LtcN8naEdY/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " Module - II (Projections of Solids)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vQiH8mjjqg26GtD0g7xqmTGVF_l4e2ts40q0ZF5qEESsXAB2yPDY8VbB6IP9QVlhK3BPQMrrksm3qkN/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " Module - III (Isometric Projection)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vRMGMgGQgN0Z7np8HI28LGt0E0oukTLna8SHFcPCOdR9pxhj_2l2w3YKujGBcwJQOrQd35gbuVBEogI/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " Module - V (Sections of Solids)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vRFt_c4kwObwEQ2Eer9xMp-jE8_yQIuD-_IpL-OVuyI_Ok4skyrxi95DbQtlHQaG_dwoDmcPDM25vph/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " Module - V (Development of Surfaces)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vQtuLkDgynmv3AZcmWUw876plpmg-Bom0rA1csvD9pud1KfOCfo7GkOGinvjtOtOTT88zOh_SFMtFqg/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " Module - VI (Perspective Projection)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vSz42eFfcNUCIJ_R6LrKen5BS2G_TETJzC8zOIuwmv5n5M77zCUTDPqWaUqO3t5ait5qlf69wUg3Ipb/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " Module - VI (Intersection of Surfaces)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vSkF-oaXa2LE8bOWLWUuPmwu97XwPLW-fbcd_bgyOxs7QNMuMuqPb80k2U_i7mahCQNYkq1X9p97NcB/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (Jan 2016)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vTjMZzE_kvd5P9TRCZ2r7a2y43OJDT3dXAajkHVK2FSQNx_qYbeUQAKR3Ik5iFIt9tfQ2SzjF7TTyED/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (May June 2016)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vSsRy3yy8t1kvCk2NN5Uh1sr638J-N6gaa82oFl0YUMTmNPuKKWqp6D1Vq1wEvecFkbhsIcJ1Og0iZy/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (July 2016)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vQdb3hddPp2Ce-T_fgzt9H73SE38AStPpZm9CFW65keepHMhYsgiO7dbqO8PdgGdmuqeSjKAfjNeyoo/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (Jan 2017)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vRL7lNt9bjMDmW4EX1VLLl0CPZyk_6mMYp840Gc6LcWk5t4I8aH7Pf_0C_QrGMHfTKmxOhvpd2LVvba/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (May 2017)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vSaH6kQSHeRVJ0oH7HDy6_cZClAtjKS0DElUtTQz9VqFYD78b_UrfkfjmTeWZ3tNtHBzk35XpI01QnZ/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (July 2017)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vQMjwziQctLBbg1UcNUqH9Z3hJOWyox-h4_-JgCnIMHQ78KKSh1Tu2pCBaVIPbRmWfOPFiSOrDrB9eE/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (Dec 2017)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vQnIuK8JXaDcBacBsdEoeCRca16DVffU06MGI27dTegaWHkp_uxHM8n_N5Nt1eeKJ08Fb0U8undMAqU/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (April 2018)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vTozSAAwILLSkcx_XiPGmZh_ClvJH1qmZhjJjp3VnvJf4C7lh9_67rg_2y6u1nCH4ocI923J8fZQ9lT/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " APJKTU Solved QP (July 2018)") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vSPmupvjxoB4OOhiP7db7yYnmkwm7_zT2wBAX4fZgT8ArlYotWA2971mVGwbE1qenRDpnhZA35hfLNq/pub?start=false&loop=false&delayms=3000"));
        list.add(String.format("%100s", " References") + String.format("%1000s", "https://docs.google.com/presentation/d/e/2PACX-1vQ7jvXu6Y755mZw1ToXe0FUWDIoLy8RybiFxgEWRi_614423Ty0ixx0fVYJ6MAkztALaJ1KdG_ISjUz/pub?start=false&loop=false&delayms=3000"));
        simpleAdapterModule=new SimpleAdapterModule(list,ModuleSelection.this);
        recyclerView.setAdapter(simpleAdapterModule);
        */
        SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        new AsyncCallSoap(token).execute();
        new AsyncCallSoap_Log(token).execute();
    }
    @Override
    public void onBackPressed() {
       /* SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        new AsyncCallSoap_LOgout(token).execute();
       */
        //finish();
        //System.exit(0);
        super.onBackPressed();
        //this.finishAffinity();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public class AsyncCallSoap_LOgout extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_LOgout(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(ModuleSelection.this);
        @Override
        protected String doInBackground (String...params){

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
            Intent intent = new Intent(ModuleSelection.this, Second.class);
            startActivity(intent);

        }

    }
    public class AsyncCallSoap_Log extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_Log(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(ModuleSelection.this);
        @Override
        protected String doInBackground (String...params){

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
    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {

        private final String token;

        AsyncCallSoap(String tk) {

            token = tk;
        }
        private final ProgressDialog dialog = new ProgressDialog(ModuleSelection.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Client/getcoursemodules/");


            try {
                json.put("course_id",courseid);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            StringEntity stringEntity = null;
            try {
                stringEntity = new StringEntity(json.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            post.addHeader("authorization",token);
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
            jsonObj = null;
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
            if (status.equals("success")){
                try {
                    String tutorials="";
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        tutorials = decodeJWT(jsonObj.getString("tutorials"));
                    }
                    JSONObject jsonObject = new JSONObject(tutorials);
                    jsonArray = jsonObject.getJSONArray("modules");
                    for (int i=0;i<jsonArray.length();i++){
                        list.add(jsonArray.get(i).toString().trim());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return status;
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
            if (status.equals("success")){

                simpleAdapterModule=new SimpleAdapterModule(jsonArray,ModuleSelection.this);
                recyclerView.setAdapter(simpleAdapterModule);

                //Intent intent = new Intent(GetCourse.this, ModuleSelection.class);
                // startActivity(intent);
            }else{
                Toast.makeText(ModuleSelection.this, message, Toast.LENGTH_SHORT).show();
            }

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

}
