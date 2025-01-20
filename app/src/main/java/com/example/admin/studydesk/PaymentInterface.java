package com.example.admin.studydesk;

import android.util.Log;
import android.webkit.JavascriptInterface;

class PaymentInterface {
    @JavascriptInterface
    public void success(String data) {
        // Handle payment success
        Log.d("PaymentInterface", "Payment Success: " + data);
    }

    @JavascriptInterface
    public void error(String data) {
        // Handle payment failure
        Log.d("PaymentInterface", "Payment Error: " + data);
    }
}
