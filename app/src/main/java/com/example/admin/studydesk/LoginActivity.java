package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    String status;
    String token;
    String message;
    String URL;
    TextView tvURL;
    public static Boolean shadow=false;
    public static Boolean screen=false;
    // UI references.
    private EditText ETusername;
    private EditText ETpassword;
    Button BtnLogin;
    ProgressBar progressBar;
    TextView tvRegistration,tvForgot;
    String deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final AlphaAnimation buttonClick = new AlphaAnimation(1F,.5F);
        // Set up the login form.
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        tvURL=(TextView) findViewById(R.id.lblurl);
        deviceid= Settings.Secure.getString(LoginActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        ETusername=(EditText)findViewById(R.id.etUser);
        ETpassword=(EditText)findViewById(R.id.etPass);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        BtnLogin=(Button)findViewById(R.id.btnLogin);
        tvForgot=(TextView)findViewById(  R.id.lblForgot);
        tvRegistration=(TextView)findViewById(  R.id.lblRegistration);
        tvForgot.setPaintFlags(tvRegistration.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvRegistration.setPaintFlags(tvRegistration.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse("https://arstudydesk.com/beta/Signup"));
                startActivity(browser);
                */
                SecondDtl.link="https://arstudydesk.com/Signup";
                Intent intent = new Intent(LoginActivity.this, SecondDtl.class);
                startActivity(intent);
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncCallSoap_Forgot(ETusername.getText().toString().trim()).execute();
            }
        });
        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncCallSoap(ETusername.getText().toString().trim(),ETpassword.getText().toString().trim()).execute();

            }
        });
        ETusername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ETusername.setHint("");
                } else {
                    ETusername.setHint("USER NAME");
                }
            }
        });
        ETpassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ETpassword.setHint("");
                } else {
                    ETpassword.setHint("PASSWORD");
                }
            }
        });

        Window window =  getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
       //finish();
        //SharedPreferences prefs = getSharedPreferences("TOKEN", MODE_PRIVATE);
        //String token = prefs.getString("token", null);
        //new AsyncCallSoap_LOgout(token).execute();
       //System.exit(0);
        super.onBackPressed();
        //return;
        //this.finishAffinity();
    }


    public class AsyncCallSoap extends AsyncTask<String,Void,String>
    {
        private final String Username;
        private final String Password;

        AsyncCallSoap(String email, String password) {
            Username = email;
            Password = password;
        }
        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Client/auth");


                try {
                    json.put("email",Username);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("password", Password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    json.put("device_id", deviceid);
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
                   // editor.putString("token", token);
                    VerifyOtp.token=token;
                    editor.putString("email", Username);
                    editor.putString("pwd", Password);
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
                Intent intent = new Intent(LoginActivity.this, VerifyOtp.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }

        }

    }






    public class AsyncCallSoap_Forgot extends AsyncTask<String,Void,String>
    {
        private final String Username;

        AsyncCallSoap_Forgot(String email) {
            Username = email;

        }
        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Login/forgotPassword");


            try {
                json.put("email",Username);
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

                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();


        }

    }




    public class AsyncCallSoap_LOgout extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_LOgout(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        @Override
        protected String doInBackground (String...params){

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = null;
            JSONObject json=new JSONObject();
            HttpPost post = new HttpPost("https://arstudydesk.com/Client/logout");
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
            finishAffinity();
            System.exit(0);

        }

    }

}

