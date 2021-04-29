package com.sumit.myapplication444;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class analysisActivity extends AppCompatActivity {

    BottomNavigationView btmnav2;
    TextView fbtv;
    TextView analysistv;
    TextView analysis;
    //Button btnexo;
    LottieAnimationView manimation;


     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference mydata = database.getReference("analysis activity");
     Button btnstats;


    private AdView mAdView1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        fbtv = findViewById(R.id.fbtv);


        btmnav2 = findViewById(R.id.bottomnav2);
        analysistv = findViewById(R.id.analysisdata);
        analysis = findViewById(R.id.analysisgt);
        manimation = findViewById(R.id.animationView);
        btnstats = findViewById(R.id.btnana);
        btnstats.setVisibility(View.GONE);
        // banner ads intialization
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest);

        btmnav2.setSelectedItemId(R.id.analysis);
        loadfirebasedataanalysis();


        //btmnav on selected listner
        btmnav2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    Intent intent = new Intent(analysisActivity.this,homeActivity.class);
                    startActivity(intent);


                }
                else if(item.getItemId() == R.id.live) {
                    Intent i = new Intent(analysisActivity.this,liveActivity.class);
                    startActivity(i);

                }

                return false;
            }
        });
        btnstats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manimation.setVisibility(View.GONE);

                String notice;
                notice = analysistv.getText().toString();
               analysis.setText(notice);
            }
        });

    }




    private void loadfirebasedataanalysis() {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("PLEASE WAIT");
        pd.show();


        mydata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue().toString().equals("")) {
                    pd.dismiss();
                    fbtv.setText("PLEASE WAIT TILL MATCHES FOR ANALYSIS DATA");
                    btnstats.setVisibility(View.GONE);

                }
                  else {
                      btnstats.setVisibility(View.VISIBLE);
                    Log.d("EXTRACTING DATA","ABLE TO GET THE DATA of analysis activity"+snapshot+",,,,,,,,");
                     pd.dismiss();

                     String something = snapshot.getValue().toString();
                    fbtv.setText("CLICK THE BELOW BUTTON TO SEE THE IN DEPTH ANALYSIS");
                     analysistv.setText(snapshot.getValue().toString());




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("EXTRACTING DATA","FAILED TO GET THE DATA");
            }
        });



    }
}