package com.example.admin.studydesk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

/**
 * Created by Vishnu on 01-05-2019.
 */
public class MyWebViewClient extends WebViewClient {
    public boolean shuldOverrideKeyEvent (WebView view, KeyEvent event) {

        return true;
    }

    public boolean shouldOverrideUrlLoading (WebView view, String url) {
        if (!url.contains("youtube")) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }

        // reject anything other

        Intent intent = new Intent(view.getContext(), Youtube.class);
        Youtube.link=url;
        view.getContext().startActivity(intent);
        return false;
      //  view.loadUrl(url);
        //return false;
    }
}