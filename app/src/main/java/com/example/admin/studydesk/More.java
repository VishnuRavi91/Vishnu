package com.example.admin.studydesk;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.studydesk.R;

public class More extends AppCompatActivity {
    Button btnWebsite,btnAbout,btnEgyan,btnComing,btnAnnouncement,btnTerms,btnHome,btnFeedback,btnContact,btnSponsorship;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
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
        btnSponsorship=(Button)findViewById(R.id.btnSponsorship);
        btnWebsite=(Button)findViewById(R.id.btnWebsite);
        btnAbout=(Button)findViewById(R.id.btnAbout);
        btnEgyan=(Button)findViewById(R.id.btnEGYAN);
        btnComing=(Button)findViewById(R.id.btnComingSoon);
        btnAnnouncement=(Button)findViewById(R.id.btnAnnouncement);
        btnTerms=(Button)findViewById(R.id.btnTerms);
        btnHome=(Button)findViewById(R.id.btnHome);
        btnContact=(Button)findViewById(R.id.btnContact);
        btnFeedback=(Button)findViewById(R.id.btnFeed);

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SecondDtl.link="https://docs.google.com/forms/d/e/1FAIpQLSfqdy6n99xSgJ2N2iYtSbrMQBxJ5zYs6WRGgNCZ7Y0R83goAA/viewform?usp=sf_link";
                SecondDtl.from= "FEEDBACK";
                Intent intent = new Intent(More.this, SecondDtl.class);
                startActivity(intent);
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, Contact.class);
                startActivity(intent);
            }
        });

        btnWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.arstudydesk.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vTXOCYVNQFeLZ5St0WfnKY4WjiUrcPIXTV3T_X-pcUqmv3_15Eq6CEau9lp5DnMM-iQIpDqxFYLtn7z/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(More.this, RefferalDtl.class);
                startActivity(intent);
            }
        });

        btnEgyan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vR1UE44jY0G7ZvDtVFicyb8JdZEhKJ8-yvaDTG06nZPf8M_yc2biSI1QKKlKNSk-NV0IQBoRR1KJyK0/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(More.this, RefferalDtl.class);
                startActivity(intent);
            }
        });

        btnSponsorship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vQo50HAMZzw638_UymYoHBB33FAi-Frd9lY6ROlrZJsY2yRCIT1PUdgFWR-Yy9raU9-RLNbeL0hgOcJ/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(More.this, RefferalDtl.class);
                startActivity(intent);
            }
        });
        btnComing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vQ2nWuBgmZEX-gWHRHObRpjQlEUya5dqbWlMcPwF9jHeDmgaPotFIAKgSHZDJnlct-IDevFeJY4HWw3/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(More.this, RefferalDtl.class);
                startActivity(intent);
            }
        });

        btnAnnouncement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vQtvN8lsc3Pch2cWMv2OotvOhiv3WDI_6xD25UDTi9quIx2lA7IsdH3PLRUs2sf_ehG5htY89HWGiqO/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(More.this, RefferalDtl.class);
                startActivity(intent);
            }
        });

        btnTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefferalDtl.link="https://docs.google.com/presentation/d/e/2PACX-1vS-HC5_E2fx-WNDjIrWgTOn-rKnOkfk1nJuZgBTH4CPdJv45AdnoIHgxpisoaWjffLaIY9VoMwLWUsE/pub?start=false&loop=false&delayms=3000";
                RefferalDtl.demo=false;
                Intent intent = new Intent(More.this, RefferalDtl.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(More.this, Second.class);
                startActivity(intent);
            }
        });
    }
}
