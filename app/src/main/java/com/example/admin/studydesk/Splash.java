package com.example.admin.studydesk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.admin.studydesk.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class Splash extends AppCompatActivity {
    String status,message,token,oldtoken,email,pwd,deviceid;
    long lasttime;
    Date current,last;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        boolean connected = false;
        deviceid= Settings.Secure.getString(Splash.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        if (connected == false) {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }


        //  final LinearLayout layout =(LinearLayout)findViewById(R.id.linearsplash);
        SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        oldtoken = prefs.getString("token", null);
        lasttime=prefs.getLong("lastloggedtime",0);

            //call();
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {

                            Intent intent = new Intent(Splash.this, Second.class);
                            startActivity(intent);

                                //call();

                    }

                }

            };
            timerThread.start();

        Window window = getWindow();
        //new AsyncCallSoap().execute();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }


    }
    private static String getProperty(Class myClass, String propertyName) throws Exception {
        return (String) myClass.getMethod("get", String.class).invoke(myClass, propertyName);
    }
    public Boolean isEmulator(Context paramContext)
    {
        Boolean isEmulator = false;

        try {
            Class SystemProperties = Class.forName("android.os.SystemProperties");
            TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService(TELEPHONY_SERVICE);
            if (getProperty(SystemProperties, "ro.secure").equalsIgnoreCase("0"))
                isEmulator =  Boolean.valueOf(true);
            else if (getProperty(SystemProperties, "ro.kernel.qemu").equalsIgnoreCase("1"))
                isEmulator =  Boolean.valueOf(true);
            else if (Build.PRODUCT.contains("sdk"))
                isEmulator =  Boolean.valueOf(true);
            else if (Build.MODEL.contains("sdk"))
                isEmulator =  Boolean.valueOf(true);
            else if(localTelephonyManager.getSimOperatorName().equals("Android"))
                isEmulator =  Boolean.valueOf(true);
            else if(localTelephonyManager.getNetworkOperatorName().equals("Android"))
                isEmulator =  Boolean.valueOf(true);
            else
                isEmulator =  Boolean.valueOf(false);

            String msg = "ro.secure = " + getProperty(SystemProperties, "ro.secure") +
                    "\nro.kernel.qemu = " + getProperty(SystemProperties, "ro.kernel.qemu") +
                    "\nSimOperatorName = " + localTelephonyManager.getSimOperatorName() +
                    "\nNetworkOperatorName = " + localTelephonyManager.getNetworkOperatorName();
            Log.i("adb", msg);
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("info");
            alertDialog.setMessage(msg);
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEmulator;
    }
    public void call(){
        new AsyncCallSoap().execute();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {


        AsyncCallSoap() {

        }
       // private final ProgressDialog dialog = new ProgressDialog(Splash.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://rayaanaravind.in/Client/backgroundauth/");


            try {
                json.put("device_id",deviceid);
            } catch (JSONException e) {
                e.printStackTrace();
           }

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
            if (status.equals("success")){
                try {
                    token=jsonObj.getString("access_token");
                    SharedPreferences.Editor editor = getSharedPreferences("TOKEN", MODE_PRIVATE).edit();
                    editor.putString("token", token);
                    //editor.putString("email", );
                    //editor.putString("pwd", Password);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
            //dialog.show();
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
         //   dialog.dismiss();
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // TV.setText(result);
            if (status.equals("success")){
                Intent intent = new Intent(Splash.this, GetCourse.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Splash.this, Second.class);
                startActivity(intent);
            }

        }

    }

    public class AsyncCallSoap_LOgout extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_LOgout(String Tok) {
            token=Tok;
        }
        //private final ProgressDialog dialog = new ProgressDialog(Splash.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://rayaanaravind.in/Client/logout/");
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
           // dialog.show();
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
           // dialog.dismiss();
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // TV.setText(result);
            SharedPreferences preferences = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            Intent intent = new Intent(Splash.this, LoginActivity.class);
            startActivity(intent);

        }

    }

}



