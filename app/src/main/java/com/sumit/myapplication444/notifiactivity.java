package com.sumit.myapplication444;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class notifiactivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myref = database.getReference();
    TextView mtvnotify;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiactivity);
        mtvnotify = findViewById(R.id.tvnotify);




       myref.child("notification message").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Log.d("FETCHING DATA","DATA IS FETCHED"+snapshot.getValue().toString());
               Toast.makeText(notifiactivity.this, "HERE IS A MESSAGE FOR YOU!", Toast.LENGTH_SHORT).show();
               if (snapshot.getValue().toString().equals("")) {
                   mtvnotify.setText("NOTHING HERE!");
               }
               else {
                  String notice =  snapshot.getValue().toString();
                  mtvnotify.setText(notice);
               }






           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


    }
}