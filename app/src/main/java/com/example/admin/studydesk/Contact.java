package com.example.admin.studydesk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.studydesk.R;

import java.net.URLEncoder;

public class Contact extends AppCompatActivity {
    Button btnwhatsapp,btncall,btnmail,btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
           //     WindowManager.LayoutParams.FLAG_SECURE);
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
        btnHome=(Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contact.this, Second.class);
                startActivity(intent);
            }
        });
        btnwhatsapp=(Button)findViewById(R.id.btnWhatsapp);
        btncall=(Button)findViewById(R.id.btnCall);
        btnmail=(Button)findViewById(R.id.btnEmail);
        btnwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsApp();
            }
        });
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:9995118306"));
                startActivity(intent);
            }
        });
        btnmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"arstudydesk@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                Contact.this.startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "arstudydesk@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                Contact.this.startActivity(Intent.createChooser(emailIntent, null));
            }
        });
    }
    private void openWhatsApp() {
        String smsNumber = "917907888019";
        boolean isWhatsappInstalled = whatsappInstalledOrNot( getApplicationContext(), "com.whatsapp");
        if (isWhatsappInstalled) {

//            Intent sendIntent = new Intent("android.intent.action.MAIN");
//            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
//            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
//            startActivity(sendIntent);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + smsNumber)); // Replace smsNumber with the phone number you want to chat with
            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }
    private boolean whatsappInstalledOrNot(Context context, String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
    public static boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
