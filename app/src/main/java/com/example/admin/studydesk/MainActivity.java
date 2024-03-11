package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.studydesk.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.scottyab.aescrypt.AESCrypt;

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
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    private ProgressDialog dialog;
    public static String link;
    String status;
    byte[] decryptedlink;
    String message;
    byte[] bytesEncoded;
    String messageAfterDecrypt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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



        webView = (WebView)findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

               /* dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Loading..");
                dialog.show();*/
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
               /* if (dialog.isShowing()) {
                    dialog.dismiss();

                }*/
                super.onPageFinished(view, url);
            }

            @Override public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/error.html");
            } });

        if (!LoginActivity.shadow) {
            /*Target listObjetivo = new ViewTarget(R.id.webView, this);

            new ShowcaseView.Builder(this, false)
                    .setTarget(listObjetivo)
                    .setContentTitle("")
                    .setContentText("Touch/swipe left to forward (Preferably on the upper half of the screen).Swipe right to backward.")
                    .setStyle(4)
                    .build();
            LoginActivity.shadow=true;*/
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
   private String decrypt(String opstring,String pwd) throws Exception{
       SecretKeySpec key= generateKey(pwd);
      // byte[] keyData = pwd.getBytes("UTF-8");
       //SecretKey key = new SecretKeySpec(keyData, "AES");
       Cipher c= Cipher.getInstance("AES");
       c.init(Cipher.DECRYPT_MODE,key);
       byte[] decodeValue= Base64.decode(opstring,Base64.DEFAULT);
       byte[] decValue=c.doFinal(decodeValue);
       String decryptedValue=new String(decValue);
       return decryptedValue;
   }

    private SecretKeySpec generateKey(String pwd) throws Exception{
        final MessageDigest digest=MessageDigest.getInstance("AES");
        byte[] bytes=pwd.getBytes("UTF-8");
        //byte[] bytes=pwd.getBytes();
        digest.update(bytes,0,bytes.length);
        byte[] key=digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }

    public class AsyncCallSoap_LOgout extends AsyncTask<String,Void,String>
    {

        private final String token;
        AsyncCallSoap_LOgout(String Tok) {
            token=Tok;
        }
        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
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

   /* boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        // Log.i(TAG, "onBackPressed");

        if (doubleBackToExitPressedOnce) {
            //  Log.i(TAG, "double click");
            /*new AlertDialog.Builder(this)
                    .setTitle("Go to previous screen")
                    .setMessage("Continue?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }

                            }).setNegativeButton("No", null).show();*/
           /* finish();
            return;
        } else {
            //  Log.i(TAG, "single click");
            if (webView.canGoBack()) {
                //  Log.i(TAG, "canGoBack");
                webView.goBack();
            } else {
                // Log.i(TAG, "nothing to canGoBack");
            }
        }

        this.doubleBackToExitPressedOnce = true;
        if (getApplicationContext() == null) {
            return;
        } else {
            //Toast.makeText(this, "Please click BACK again to go previous screen",
            //        Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }*/


}
