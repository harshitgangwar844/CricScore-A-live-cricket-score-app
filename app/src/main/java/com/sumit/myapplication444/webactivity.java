package com.sumit.myapplication444;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.google.android.exoplayer2.Player.STATE_BUFFERING;


public class webactivity extends AppCompatActivity {





    PlayerView playerView;
    ProgressBar loading;
    String video;
    String intial;
    String ht;
    ImageView videosettings;
    SimpleExoPlayer player;
    String initialplayback;
    String ht2;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("LIVE ACTIVITY DATA");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_webactivity);
        firebasestuff();
        Intent into = getIntent();
        intial =into.getStringExtra("name");
        myRef.child("240p").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ht = snapshot.getValue().toString();
                Log.d("value of 720p","fdfs"+ht);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Toast.makeText(this, "the data is"+intial, Toast.LENGTH_SHORT).show();

        myRef.child("720p").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ht2 = snapshot.getValue().toString();
                Log.d("value of 720p","fdfs"+ht);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // Toast.makeText(webactivity.this, "df"+ht, Toast.LENGTH_SHORT).show();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_webactivity);
        Toast.makeText(this, "CHANGE THE QUALITY IF PROBLEM OCCURS", Toast.LENGTH_SHORT).show();

        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        playerView = findViewById(R.id.video_view);
        loading = findViewById(R.id.loading);
        videosettings = findViewById(R.id.video_settings);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(webactivity.this);
        player = new SimpleExoPlayer.Builder(webactivity.this).setTrackSelector(trackSelector).build();
        FullScreencall();
        playerView.setPlayer(player);
            video = intial;
                    Uri videoUri = Uri.parse(video);
        videosettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(webactivity.this, "360p recommended!", Toast.LENGTH_SHORT).show();
                Log.d("bnvnvn","BUTTON CLICKED");
                confirm();
            }
        });







        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(videoUri);
// Set the media item to be played.
        player.setMediaItem(mediaItem);
// Prepare the player.
        player.prepare();
// Start the playback.
        player.play();
        player.addListener(new Player.EventListener() {

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == STATE_BUFFERING) {
                    loading.setVisibility(View.VISIBLE);

                }

                else {
                    loading.setVisibility(View.GONE);
                }
            }
        });








    }

    public void FullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }

    }
    public void confirm () {
        FullScreencall();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        View v = this.getWindow().getDecorView();
        v.setSystemUiVisibility(View.GONE);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(webactivity.this);

        // set title

        alertDialogBuilder.setTitle("Quality options");
        alertDialogBuilder.setIcon(R.drawable.ic_baseline_play_arrow_24);

        // set dialog message
        alertDialogBuilder
                .setMessage("IF VIDEO BUFFERS CHANGE THE QUALITY")
                .setCancelable(true)
                .setPositiveButton("240p", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        FullScreencall();
                        //String video1 = "https://sony247channels.akamaized.net/hls/live/2011749/SABHD/hdntl=exp=1618986560~acl=%2f*~id=211692da-be30-46bf-b5ba-9e5b564afdf4~data=hdntl~hmac=2bc26d1cd35a92ca03a801582a1107ef659ddcf230adc0e31a09b3dad3935895/master_224.m3u8?originpath=/linear/hls/pb/event/UI4QFJ_uRk6aLxIcADqa_A/stream/fd1cde62-2d88-464d-b522-ce28780346dc:SIN/variant/f971db5f8b42ca4a531c71a0f93f3047/bandwidth/356224.m3u8";
                        Uri videourl = Uri.parse(ht);
                        // Build the media item.
                        MediaItem mediaItem = MediaItem.fromUri(videourl);
// Set the media item to be played.
                        player.setMediaItem(mediaItem);
// Prepare the player.
                        player.prepare();

// Start the playback.
                        player.play();

                    }
                })
                .setNeutralButton("360p", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FullScreencall();
                        //String video1 = "https://sony247channels.akamaized.net/hls/live/2011749/SABHD/hdntl=exp=1618990306~acl=%2f*~id=df3e78a1-bbec-4101-9e39-52305b49d7fa~data=hdntl~hmac=74d9a9e56188ab259f83bc5f4dd4ac4a327991d697bb4dd085d475e03ed6d742/master_664.m3u8?originpath=/linear/hls/pb/event/UI4QFJ_uRk6aLxIcADqa_A/stream/8bd3d09b-173c-4edc-8b8c-e42cb3c63799:SIN/variant/8cb05aaf4163194faf26536c89c5f179/bandwidth/917664.m3u8";
                        Uri videourl = Uri.parse(intial);
                        // Build the media item.
                        MediaItem mediaItem = MediaItem.fromUri(videourl);
// Set the media item to be played.
                        player.setMediaItem(mediaItem);
// Prepare the player.
                        player.prepare();

// Start the playback.
                        player.play();




                    }

                })
                .setNegativeButton("720p", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing



                        //String video1 = "https://sony247channels.akamaized.net/hls/live/2011749/SABHD/hdntl=exp=1618986560~acl=%2f*~id=211692da-be30-46bf-b5ba-9e5b564afdf4~data=hdntl~hmac=2bc26d1cd35a92ca03a801582a1107ef659ddcf230adc0e31a09b3dad3935895/master_224.m3u8?originpath=/linear/hls/pb/event/UI4QFJ_uRk6aLxIcADqa_A/stream/fd1cde62-2d88-464d-b522-ce28780346dc:SIN/variant/f971db5f8b42ca4a531c71a0f93f3047/bandwidth/356224.m3u8";

                                Uri videourl = Uri.parse(ht2);
                        //Toast.makeText(webactivity.this, "df"+ht, Toast.LENGTH_SHORT).show();
                        // Build the media item.
                        MediaItem mediaItem = MediaItem.fromUri(videourl);
// Set the media item to be played.
                        player.setMediaItem(mediaItem);
// Prepare the player.
                        player.prepare();

// Start the playback.
                        player.play();

                        FullScreencall();

                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();


        // show it
        alertDialog.show();


    }



    public void firebasestuff() {
        myRef.child("360p").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                initialplayback = snapshot.getValue().toString();
                Log.d("Tag","the value is"+initialplayback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
       Intent intent = new Intent(webactivity.this,liveActivity.class);
       startActivity(intent);

        player.stop();

    }


}
