package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.admin.studydesk.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class Refferal extends AppCompatActivity {
    Button btnKTU,btnSNCST,btnHome;
    public static List<String> list = new ArrayList<String>();
    private static final String TAG = "Refferal";
    static JSONArray jsonArray;
    static String loadError;
    JSONObject jsonObj;
    RecyclerView recyclerView;
    SimpleAdapterReferrals simpleAdapterReferrals;
    private static Response response;
    public static final String WAURL="https://script.google.com/macros/s/AKfycbzcTECVWsvZZL6fSlMROhgWpyNIpVrDsCkkpB5qRUwPAny9lfTm/exec?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refferal);

       // btnKTU=(Button)findViewById(R.id.btnRefKTU);
        //btnSNCST=(Button)findViewById(R.id.btnRefSNCST);
        btnHome=(Button)findViewById(R.id.btnHome);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Refferal.this));
        new AsyncCallSoap().execute();
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Refferal.this, Second.class);
                startActivity(intent);
            }
        });
      /*  btnKTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vRcOQIuh7p3jEpE8I454iBAh1McxlwHr5VWK56CGxzC-UdlzXhqvVIb2gKKW_al83o1F_XYIfWXuVTr/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Refferal.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnSNCST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link=" https://docs.google.com/presentation/d/e/2PACX-1vQO7hYK2YrgqTd7V-DFRcxrO4w5bMvPuiiTei9XCk-zSubUFeF0am-6GAdXacWg1Kx_cyfbFpDGm4r8/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Refferal.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
*/
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//               WindowManager.LayoutParams.FLAG_SECURE);
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
    }
    public static JSONObject readAllData() {
        jsonArray = null;
        loadError = null;
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL+"action=readAll")
                    .build();
            response = client.newCall(request).execute();
            //return new JSONObject(response.body().string());
            String body = response.body() == null ? null : response.body().string();
            if (body == null) {
                loadError = "The server returned an empty response.";
                return null;
            }
            JSONObject jsonObj;
            try {
                jsonObj = new JSONObject(body);
            } catch (JSONException e) {
                // Not JSON at all - the endpoint is returning an error page.
                Log.e(TAG, "Referral feed is not JSON: " + body.substring(0, Math.min(300, body.length())), e);
                loadError = "The referral service is unavailable. Please try again later.";
                return null;
            }
            jsonArray = jsonObj.optJSONArray("records");
            if (jsonArray == null) {
                Log.e(TAG, "Referral feed has no 'records' array: " + body.substring(0, Math.min(300, body.length())));
                loadError = "No referral content available right now.";
                return null;
            }
            for (int i=0;i<jsonArray.length();i++){
                try {
                    list.add(jsonArray.get(i).toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (@NonNull IOException e) {
            Log.e(TAG, "Could not reach the referral service", e);
            loadError = "Could not reach the server. Please check your connection.";
        }
        return null;
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
    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {


        AsyncCallSoap() {

        }
        private final ProgressDialog dialog = new ProgressDialog(Refferal.this);
        @Override
        protected String doInBackground (String...params){
            try {
            readAllData();
            return null;
                    } catch (Exception e) {
                // Containment: an uncaught throw here would kill the process.
                Log.e("Refferal", "Background task failed", e);
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
            if (isFinishing() || isDestroyed()) {
                return;
            }
            if (jsonArray == null) {
                Toast.makeText(Refferal.this,
                        loadError == null ? "Could not load the referrals." : loadError,
                        Toast.LENGTH_LONG).show();
                return;
            }
            simpleAdapterReferrals=new SimpleAdapterReferrals(jsonArray,Refferal.this);
            recyclerView.setAdapter(simpleAdapterReferrals);
        }

    }

}
