package com.sumit.myapplication444;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class liveActivity extends AppCompatActivity {



    RelativeLayout mrellayout;
    Button mbtnwatch;

    // firebase variables
     FirebaseDatabase database = FirebaseDatabase.getInstance();
     DatabaseReference myRef = database.getReference("LIVE ACTIVITY DATA");

     TextView mlinktv1,mlinktv2;
     String link1;




     AdView madviewBanner;
   


    private final String TAG = liveActivity.class.getSimpleName();



    BottomNavigationView btmnav1;

    TextView tv1;
    //reward aad variables
    RewardedAd mRewardedAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        mbtnwatch = findViewById(R.id.watch);
        mbtnwatch.setVisibility(View.GONE);





        btmnav1 = findViewById(R.id.bottomnav1);
        tv1 = findViewById(R.id.tv1);
        mlinktv1 = findViewById(R.id.linktv1);
        mrellayout = findViewById(R.id.relayoutlive);
        madviewBanner = findViewById(R.id.adView2);
        mlinktv2 = findViewById(R.id.linktv2);
        btmnav1.setSelectedItemId(R.id.live);
        mlinktv1.setVisibility(View.GONE);
        mlinktv2.setVisibility(View.GONE);
        mrellayout.setVisibility(View.VISIBLE);


        //initalizing the admobs
        MobileAds.initialize(liveActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Log.d("LIVE ACTIVITY"," ADMOB ININTALIZATION COMPLETED");
            }
        });



        AdRequest adRequest = new AdRequest.Builder().build();
        madviewBanner.loadAd(adRequest);

        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-7544291206603406/4424821745");
// TODO: Add adView to your view hierarchy.
        ProgressDialog pd = new ProgressDialog(liveActivity.this);
        pd.setMessage("PLEASE WAIT LOADING FIREBASE DATA");
        pd.show();

        RewardedAd.load(this, "ca-app-pub-7544291206603406/2564945160",
                adRequest, new RewardedAdLoadCallback(){



                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        mbtnwatch.setVisibility(View.VISIBLE);
                        Log.d(TAG, loadAdError.getMessage());
                        pd.dismiss();
                        mRewardedAd = null;
                        Toast.makeText(liveActivity.this, "FAILED TO LOAD", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mbtnwatch.setVisibility(View.VISIBLE);
                        mRewardedAd = rewardedAd;
                        pd.dismiss();
                        Log.d(TAG, "onAdFailedToLoad");
                        if (mRewardedAd != null) {
                            Activity activityContext = liveActivity.this;
                            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                                @Override
                                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                                    // Handle the reward.
                                    Log.d("TAG", "The user earned the reward.");
                                    int rewardAmount = rewardItem.getAmount();
                                    String rewardType = rewardItem.getType();
                                    Toast.makeText(activityContext, "adloaded", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Log.d("TAG", "The rewarded ad wasn't ready yet.");
                        }

                    }
                });
        try {

            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                }


            });

        } catch (Exception e) {
            Log.d("GETTING ERROR","THE ERROR IS"+e.getMessage());
        }




        intentforlinks();




        //btmnav on selected listner
        btmnav1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {

                    Intent intent = new Intent(liveActivity.this,homeActivity.class);
                    startActivity(intent);


                }
                else if(item.getItemId() == R.id.analysis) {
                    Intent i = new Intent(liveActivity.this,analysisActivity.class);
                    startActivity(i);

                }

                return false;
            }
        });


        loadfirebasedata();










    }

























    private void intentforlinks() {
        mbtnwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String testLink = link1;
                Intent into = new Intent(liveActivity.this,webactivity.class);
                into.putExtra("name",testLink);
                startActivity(into);


               // Log.d("TRASMITTING THE INTENT:","THE DATA IS:  "+testLink);
            }
        });







    }

    private void loadfirebasedata() {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("PLEASE WAIT LOADING FIREBASE DATA");
        pd.show();


        myRef.child("MATCH STATUS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("EXTRACTING DATA","ABLE TO GET THE DATA"+snapshot+",,,,,,,,");

                if (snapshot.getValue().toString().equals("false")) {

                    tv1.setText("PLEASE WAIT TILL THE MATCH STARTS");
                }

                else {
                    mlinktv1.setVisibility(View.VISIBLE);
                    mlinktv2.setVisibility(View.VISIBLE);
                    String notice = snapshot.getValue().toString();
                    myRef.child("360p").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Log.d("TAG","GETTING REPONSE"+snapshot);
                           tv1.setText(notice);
                           link1 = snapshot.getValue().toString();
                           mlinktv1.setText("WATCH IPL");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    myRef.child("LINK 2").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String link2  = snapshot.getValue().toString();
                            mlinktv2.setText(link2);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }



                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("EXTRACTING DATA","FAILED TO GET THE DATA");
            }
        });




    }





}