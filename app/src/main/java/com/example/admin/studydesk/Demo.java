package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.studydesk.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Demo extends AppCompatActivity {
    Button btnDemoKtu,btnDemoSncst,btnSylKtu,btnSylSncst,btnHome,btnSubChargeKTU,btnSubChargeSNCST,btnComing;
    TextView lblSubamtKTU,lblSubAmtSncst;
    public static List<String> list = new ArrayList<String>();
    static JSONArray jsonArray;
    JSONObject jsonObj;
    RecyclerView recyclerView;
    SimpleAdapterDemo simpleAdapterDemo;
    private static Response response;
    public static final String WAURL="https://script.google.com/macros/s/AKfycbzWHR6SLzXJ5ok3NL0_Tz2Wm3Y1zZ4MVFLUA-CJAtdyhyabc-ue/exec?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(Demo.this));
        btnHome=(Button)findViewById(R.id.btnHome);
        btnComing=(Button)findViewById(R.id.btnComing);
       /* btnDemoKtu=(Button)findViewById(R.id.btndemoKTU);
        btnDemoSncst=(Button)findViewById(R.id.btnDemoSNCST);
        btnHome=(Button)findViewById(R.id.btnHome);
        btnSubChargeKTU=(Button)findViewById(R.id.btnSubChargeKTU);
        btnComing=(Button)findViewById(R.id.btnComing);
        btnSubChargeSNCST=(Button)findViewById(R.id.btnSubChargeSNCST);
        btnSylKtu=(Button)findViewById(R.id.btnSyllabusKTU);
        btnSylSncst=(Button)findViewById(R.id.btnSyllabusSNCST);
        lblSubamtKTU=(TextView)findViewById(R.id.lblSubAmtKTU);
        lblSubAmtSncst=(TextView) findViewById(R.id.lblSubAmtSNCST);
        lblSubamtKTU.append(" \u20B9"+"150");
        lblSubAmtSncst.append(" \u20B9"+"150");
        */
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
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Demo.this, Second.class);
                startActivity(intent);
            }
        });
        /*btnSylKtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vTZ1-x6Q42V9zPjTpNSX9AhS1MKEoK4ppUPURyIj4deaCFld4TcBsTypzkCvfe1CY7rzVhgrh2UqDHb/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnSylSncst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vT_9c3v-I7_snfbakXOfAjhI2iIBBvNGmB-kfJeW-5OhlBCCWUj60X-7PADgha3UAomZ2pQD--ID4qe/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnDemoKtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vQ4VzQ7Z9Z7NrBevGZJV2V3Hotvyv6Crfq8vE_4DyiDVjZRFhbjy6iD2Gbt6dNGgBQyAQW6vsADCwgP/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=true;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnDemoSncst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vT45-eS4FybM-CO9p47P-gR2eCzOVzX9D-GJnMI5qFOxsZk65vRYqubasKGoAmVFY3xfiuiWucyvKaB/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=true;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnSubChargeSNCST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vSBd7ahNcv1HxGsbz5srV1X1bJoZ5MI_UaDo6sYztfjIZzOUvbjY0YfbRvE6qTrZatijPJSvujwgJtv/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnSubChargeKTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vRrOsTzieFB-jQmSaI8UEKCwtF9SrYq9A7E8pftnNazXUSKDjfw3MRltMiw09S_2E_GZwbpgQ28oA36/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        */
        btnComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vQ2nWuBgmZEX-gWHRHObRpjQlEUya5dqbWlMcPwF9jHeDmgaPotFIAKgSHZDJnlct-IDevFeJY4HWw3/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=true;
                Intent intent = new Intent(Demo.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        new AsyncCallSoap().execute();
    }
    public static JSONObject readAllData() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL+"action=readAll")
                    .build();
            response = client.newCall(request).execute();
            //return new JSONObject(response.body().string());
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(response.body().string());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jsonArray = jsonObj.getJSONArray("records");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i=0;i<jsonArray.length();i++){
                try {
                    list.add(jsonArray.get(i).toString().trim());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (@NonNull IOException e) {
            Log.e("TAG", "" + e.getLocalizedMessage());
        }
        return null;
    }
    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {


        AsyncCallSoap() {

        }
        private final ProgressDialog dialog = new ProgressDialog(Demo.this);
        @Override
        protected String doInBackground (String...params){
            readAllData();
            return null;
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
            simpleAdapterDemo=new SimpleAdapterDemo(jsonArray,Demo.this);
            recyclerView.setAdapter(simpleAdapterDemo);
        }

    }

}
