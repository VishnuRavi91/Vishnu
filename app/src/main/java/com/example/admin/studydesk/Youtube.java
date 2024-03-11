package com.example.admin.studydesk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.studydesk.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.net.URL;

public class Youtube extends AppCompatActivity {
    WebView webView;
    public static String link;
    Button btnHome;
    public static Boolean demo;
    TextView tvTitle;
    private ProgressDialog dialog;
    LinearLayout topbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube);
        webView=(WebView)findViewById(R.id.webView);
        btnHome=(Button)findViewById(R.id.btnHome);
        tvTitle=(TextView)findViewById(R.id.title) ;
        topbar=(LinearLayout)findViewById(R.id.topbar);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Youtube.this, Second.class);
                startActivity(intent);
            }
        });
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
        //      WindowManager.LayoutParams.FLAG_SECURE);
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

            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            tvTitle.setVisibility(View.GONE);
            topbar.setVisibility(View.GONE);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


       /* webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, android.os.Message resultMsg)
            {
                WebView.HitTestResult result = view.getHitTestResult();
                String data = result.getExtra();
                Context context = view.getContext();
               // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data));
               // context.startActivity(browserIntent);

                Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + data));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + data));
                try {
                    context.startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    context.startActivity(webIntent);
                }

                return false;
            }
        });*/
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                dialog = new ProgressDialog(Youtube.this);
                dialog.setMessage("Loading..");
                dialog.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialog.isShowing()) {
                    dialog.dismiss();

                }
                super.onPageFinished(view, url);
            }

            @Override public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///android_asset/error.html");
            } });


        webView.loadUrl(link);
    }
    /* @Override
     public boolean onKeyDown(int keyCode, KeyEvent event) {
         // Check if the key event was the Back button and if there's history
         if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
             webView.goBack();
             return true;
         }
         // If it wasn't the Back key or there's no web page history, bubble up to the default
         // system behavior (probably exit the activity)
         return super.onKeyDown(keyCode, event);
     }*/
    boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        // Log.i(TAG, "onBackPressed");

        if (doubleBackToExitPressedOnce) {
            //  Log.i(TAG, "double click");
            new AlertDialog.Builder(this)
                    .setTitle("Go to previous screen")
                    .setMessage("Continue?")
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }

                            }).setNegativeButton("No", null).show();
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
            Toast.makeText(this, "Please click BACK again to go previous screen",
                    Toast.LENGTH_SHORT).show();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
