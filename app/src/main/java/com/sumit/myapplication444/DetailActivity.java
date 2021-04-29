package com.sumit.myapplication444;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {


    private RequestQueue mRequestQueue;
    TextView mtvmatchid,mtvteam1,mtvteam2,mtvscore,mtvmatchstatus;
    private static final String TAG = "CRICKET SCORE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        //initializing the ids
        mtvmatchid = findViewById(R.id.tvmatchid);
        mtvteam1 = findViewById(R.id.tvteam1);
        mtvteam2 = findViewById(R.id.tvteam2);
        mtvscore = findViewById(R.id.tvscore);
        mtvmatchstatus = findViewById(R.id.tvmatchstatus);


        //getting the intent
        Intent ut = getIntent();
        String uide = ut.getStringExtra("uid");
        Log.d("INTENT UT","THE ID IS"+uide);
        mtvmatchid.setText(uide);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJson(uide);

    }


    public void parseJson(String matchid) {

        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("PLEASE WAIT!");
        pd.show();
        String url = "https://cricapi.com/api/cricketScore?apikey=EjR0y17mdGQS912lWWMJiqxcqfQ2&unique_id=" + matchid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
           // Toast.makeText(this, "respose is"+response, Toast.LENGTH_LONG).show();
            pd.dismiss();

            try {
                JSONObject jsonObject = new JSONObject(response);
                String score = jsonObject.getString("score");
                String team1 = jsonObject.getString("team-1");
                String team2 = jsonObject.getString("team-2");
                String matchstatus = jsonObject.getString("matchStarted");
                //Toast.makeText(DetailActivity.this, "checking"+score, Toast.LENGTH_SHORT).show();
                mtvteam1.setText(team1);
                mtvteam2.setText(team2);
                mtvscore.setText(score);
                if (matchstatus.equals("true")) {
                    mtvmatchstatus.setText("MATCH IS LIVE");
                }
                 else {
                     Log.d(TAG,"SOMETHING WENT WRONG");
                     mtvmatchstatus.setText("MATCH NOT STARTED");

                }

             } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(this, "SOMETHING WENT WRONG"+e.getMessage(), Toast.LENGTH_LONG).show();

            }

            Log.d(TAG,"RESONSE IS"+response);
            Log.d(TAG,"RESONSE IS"+response);




        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                Toast.makeText(DetailActivity.this, "PLEASE CHECK YOUR CONNECTION", Toast.LENGTH_SHORT).show();

            }
        });
        mRequestQueue.add(stringRequest);







    }
}