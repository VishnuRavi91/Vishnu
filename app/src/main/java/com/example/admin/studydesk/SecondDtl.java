package com.example.admin.studydesk;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.studydesk.R;

import im.delight.android.webview.AdvancedWebView;

public class SecondDtl extends AppCompatActivity {
    Button btnHome;
    AdvancedWebView webView;
    TextView tvTitle;
    private ProgressDialog dialog;
    public static String link,from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_dtl);
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
        tvTitle=(TextView)findViewById(R.id.title);
        if (from != null && !from.isEmpty() && !from.equals("null")){
            tvTitle.setText(from);
        }
        webView=(AdvancedWebView) findViewById(R.id.webView);
        btnHome=(Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondDtl.this, Second.class);
                startActivity(intent);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                 /*   dialog = new ProgressDialog(SecondDtl.this);
                    dialog.setMessage("Loading..");
                if (dialog.isShowing()) {
                    dialog.dismiss();

                }
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
        webView.loadUrl(link,true);

    }
/*    boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        // Log.i(TAG, "onBackPressed");

        if (doubleBackToExitPressedOnce) {
            //  Log.i(TAG, "double click");
           /* new AlertDialog.Builder(this)
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
          /*  finish();
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
