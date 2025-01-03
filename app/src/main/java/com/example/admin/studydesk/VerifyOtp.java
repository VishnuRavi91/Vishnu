package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Calendar;
import java.util.Date;

public class VerifyOtp extends AppCompatActivity {
    Button btnCOntinue;
    EditText etOtp;
    String status;
    String message;
    public static String token;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        etOtp=(EditText)findViewById(R.id.etOTP);
        btnCOntinue=(Button)findViewById(R.id.btnConti);
        //SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        //token = prefs.getString("token", null);
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

        btnCOntinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncCallSoap(etOtp.getText().toString().trim(),token).execute();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        /*SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        String token = prefs.getString("token", null);
        new AsyncCallSoap_LOgout(token).execute();*/
        super.onBackPressed();

    }


    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {
        private final String otp;
        private final String token;

        AsyncCallSoap(String OTP, String tk) {
            otp = OTP;
            token = tk;
        }
        private final ProgressDialog dialog = new ProgressDialog(VerifyOtp.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://rayaanaravind.in/Client/verifyotp/");


            try {
                json.put("otp_code",otp);
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
            if (status.equals("success")){
                Date currentTime = Calendar.getInstance().getTime();
                SharedPreferences.Editor editor = getSharedPreferences("TOKEN", MODE_PRIVATE).edit();
                editor.putString("token", token);
                editor.putLong("lastloggedtime", currentTime.getTime());
                editor.apply();
                Intent intent = new Intent(VerifyOtp.this, GetCourse.class);
                startActivity(intent);
            }else{
                Toast.makeText(VerifyOtp.this, message, Toast.LENGTH_SHORT).show();
            }

        }

    }

    public class AsyncCallSoap_LOgout extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_LOgout(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(VerifyOtp.this);
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
            dialog.show();
        }

        @Override
        protected void onPostExecute (String result)
        {
            super.onPostExecute(result);
            dialog.dismiss();
            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            // TV.setText(result);
            Intent intent = new Intent(VerifyOtp.this, LoginActivity.class);
            startActivity(intent);

        }

    }

}
