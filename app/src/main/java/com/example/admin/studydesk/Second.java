package com.example.admin.studydesk;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.admin.studydesk.R;
import com.framgia.android.emulator.EmulatorDetector;

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

public class Second extends AppCompatActivity {
    Button btnSubUser, btnRegUser, btnNewUser, btnFreeDemo, btnMore, btnRefferal, btnReferandEarn, btnContact;
    String status, message, token, deviceid;
    TextView btnHow, btnFAQ;
    private RequestQueue requestQueue;
    private static Second mInstance;
    String imei;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //String networkOperator =tm.getSimSerialNumber()+"";
      /*  AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("info");
        alertDialog.setMessage(Build.RADIO);
        alertDialog.show();
        Toast.makeText(this, Build.FINGERPRINT, Toast.LENGTH_SHORT).show();
*/
        EmulatorDetector.with(this)
                .setCheckTelephony(true)
                .addPackageName("com.bluestacks")
                .setDebug(true)
                .detect(new EmulatorDetector.OnEmulatorDetectorListener() {
                    @Override
                    public void onResult(boolean isEmulator) {
                        if (isEmulator) {

                            finish();
                        }else{

                        }
                    }
                });
        mInstance = this;
        if (isEmulator(this)) {
            Toast.makeText(this, "This app will not work on emulator", Toast.LENGTH_SHORT).show();
            finish();
        }
        btnSubUser=(Button)findViewById(R.id.btnSubUser);
        btnRegUser=(Button)findViewById(R.id.btnRegUser);
        btnNewUser=(Button)findViewById(R.id.btnNewUser);
        btnFreeDemo=(Button)findViewById(R.id.btnFreeDemo);
        btnContact=(Button)findViewById(R.id.btnContact);
        btnMore=(Button)findViewById(R.id.btnMore);
        //btnRefferal=(Button)findViewById(R.id.btnRefferal);
        btnReferandEarn=(Button)findViewById(R.id.btnReferandEarn);
        btnHow=(TextView)findViewById(R.id.btnHow) ;
        btnFAQ=(TextView)findViewById(R.id.btnFAQ) ;
        deviceid= Settings.Secure.getString(Second.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        btnHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vTAkfcLPibPZmeFAX6AfHWR6T7wCzrUtR_B4i2AKXVBLMCq0ViH2PKrdlua_d903Ste35pEbso7uqr5/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Second.this, RefferalDtl.class);
                startActivity(intent);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second.this, Contact.class);
                startActivity(intent);
            }
        });

        btnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vQX2gQyxPCam5kwHm3lTsxWUG6OWfdnhpmBeXWCeQmNLQpmBO_hols6RCZaRTp9aLiW44b2-B-B0MGI/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(Second.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
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
       /* btnRefferal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Second.this, Refferal.class);
                startActivity(intent);
            }
        });*/
        btnSubUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(Second.this, LoginActivity.class);
                //startActivity(intent);
                new AsyncCallSoap().execute();
            }
        });
        btnRegUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondDtl.link="https://rayaanaravind.in/user/login/";
                Intent intent = new Intent(Second.this, SecondDtl.class);
                startActivity(intent);
               /* String url = "https://rayaanaravind.in/Login";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/
            }
        });
        btnNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondDtl.link="https://rayaanaravind.in/user/register/";
                Intent intent = new Intent(Second.this, SecondDtl.class);
                startActivity(intent);
                /*String url = "https://rayaanaravind.in/Signup";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/
            }
        });
        btnFreeDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Second.this, Demo.class);
                startActivity(intent);
            }
        });
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*SecondDtl.link="https://www.arstudydesk.com/";
                Intent intent = new Intent(Second.this, SecondDtl.class);
                startActivity(intent);*/
                /*String url = "https://www.arstudydesk.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);*/
                Intent intent = new Intent(Second.this, More.class);
                startActivity(intent);
            }
        });
        btnReferandEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Second.this, Contact.class);
                startActivity(intent);*/

                /*SecondDtl.from= "REFER & EARN";
                SecondDtl.link="https://docs.google.com/forms/d/e/1FAIpQLSf3YcxSkA0owuBP3yFBY2es9AZYmo18QUZlxtkcL4xuuSua0w/viewform?usp=sf_link";
                Intent intent = new Intent(Second.this, SecondDtl.class);
                startActivity(intent);*/
                Intent intent = new Intent(Second.this, Refferal.class);
                startActivity(intent);
            }
        });
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

            BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


            if (m_BluetoothAdapter.getAddress() == null && m_BluetoothAdapter.getAddress().isEmpty() && m_BluetoothAdapter.getAddress().equals("null")){
                isEmulator =  Boolean.valueOf(true);
            }
            else if (getProperty(SystemProperties, "ro.secure").equalsIgnoreCase("0"))
                isEmulator =  Boolean.valueOf(true);
            else if (getProperty(SystemProperties, "ro.kernel.qemu").equalsIgnoreCase("1"))
                isEmulator =  Boolean.valueOf(true);
            else if (Build.PRODUCT.contains("sdk"))
                isEmulator =  Boolean.valueOf(true);
            else if (Build.FINGERPRINT.contains("android"))
                isEmulator =  Boolean.valueOf(true);
            else if(localTelephonyManager.getSimOperatorName().equals("Android"))
                isEmulator =  Boolean.valueOf(true);
            else if(localTelephonyManager.getNetworkOperatorName().equals("Android"))
                isEmulator =  Boolean.valueOf(true);
            else
                isEmulator =  Boolean.valueOf(false);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return isEmulator;
    }
    public static synchronized Second getInstance() {
        return mInstance;
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }
    public void addToRequestQueue(Request request, String tag) {
        request.setTag(tag);
        getRequestQueue().add(request);
    }
    /*
    Cancel all the requests matching with the given tag
    */
    public void cancelAllRequests(String tag) {
        getRequestQueue().cancelAll(tag);
    }
    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {


        AsyncCallSoap() {

        }


         private final ProgressDialog dialog = new ProgressDialog(Second.this);
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
                Intent intent = new Intent(Second.this, GetCourse.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(Second.this, LoginActivity.class);
                startActivity(intent);
            }

        }

    }

}
