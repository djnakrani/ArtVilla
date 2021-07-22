package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    FirebaseUser fUser;
    DatabaseReference uData;
    FirebaseAuth fAuth;
    Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.gotologin);
        uData = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getUid() != null){
//            Toast.makeText(MainActivity.this,fAuth.getUid(),Toast.LENGTH_LONG).show();
            loginbtn.setBackgroundResource(R.drawable.ic_baseline_person_24);
            loginbtn.setWidth(20);
            loginbtn.setHeight(20);
            loginbtn.setText(" ");
        }
        else {
//            Toast.makeText(MainActivity.this,fAuth.getUid(),Toast.LENGTH_LONG).show();
//            loginbtn.setBackgroundResource(R.drawable.btn_bg);
//            loginbtn.setText("Login");
//            loginbtn.setWidth(70);
//            loginbtn.setHeight(35);
        }
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fAuth.getUid() != null) {
                    uData.child("User").child(fAuth.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            System.out.println(snapshot.child("userType"));
                            if (snapshot.child("userType").exists()) {
                                if(snapshot.child("userType").getValue().equals("Admin")){
                                    startActivity(new Intent(MainActivity.this,Admin_Panel.class));
                                    finish();
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this,User_Activity.class));
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });
                }
                else {
                    startActivity(new Intent(MainActivity.this, login.class));
                    finish();
                }
            }
        });
    }
}