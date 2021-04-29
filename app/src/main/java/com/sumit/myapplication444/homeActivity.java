package com.sumit.myapplication444;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.common.internal.Objects;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homeActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ExampleAdapter mExampleAdapter;
    private ArrayList<ExampleItem> mExampleList;
    private RequestQueue mRequestQueue;
    BottomNavigationView btmnav;
    private InterstitialAd mInterstitialAd;
    private final String TAG = "homeActivity";
    ImageView mnotifi;


    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mnotifi = findViewById(R.id.notificationbell);


        //notification bell
        mnotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(homeActivity.this,notifiactivity.class);
                startActivity(intent);
            }
        });


        //intiazalizing variables
        btmnav = findViewById(R.id.bottomnav);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        
        

        //intestrial ads matter





        //ending


        //btmnav on selected listner
        btmnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.live) {

                    Intent intent = new Intent(homeActivity.this, liveActivity.class);
                    startActivity(intent);


                } else if (item.getItemId() == R.id.analysis) {
                    Intent i = new Intent(homeActivity.this, analysisActivity.class);
                    startActivity(i);

                }

                return false;
            }
        });


    }


    private void parseJSON() {
        String url = "https://cricapi.com/api/cricket?apikey=EjR0y17mdGQS912lWWMJiqxcqfQ2";
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("LOADING LIVE MATCHES!! ");
        pd.show();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            pd.dismiss();
                            Log.d("GETTING DATA", "DATA IS FETCHED: AND THE DATA IS    " + response);
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);
                                String creatorName = hit.getString("description");
                                String imageUrl = hit.getString("title");

                                String likeCount = hit.getString("unique_id");
                                mExampleList.add(new ExampleItem(imageUrl, creatorName, likeCount));
                            }
                            mExampleAdapter = new ExampleAdapter(homeActivity.this, mExampleList);
                            mRecyclerView.setAdapter(mExampleAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(homeActivity.this, "SOMETHING WENT WRONG!!", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        Log.d("item clicked","item is clicked");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}