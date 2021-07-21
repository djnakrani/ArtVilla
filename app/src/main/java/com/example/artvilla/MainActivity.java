package com.example.artvilla;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            Toast.makeText(MainActivity.this,fAuth.getUid(),Toast.LENGTH_LONG).show();
            loginbtn.setBackgroundResource(R.drawable.ic_baseline_person_24);
            loginbtn.setWidth(10);
            loginbtn.setHeight(30);
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
                if(fAuth.getUid() != null){
//                    startActivity(new Intent(MainActivity.this, User_Activity.class));
                    startActivity(new Intent(MainActivity.this,Admin_Panel.class));
                    finish();
                }
                else {
                    startActivity(new Intent(MainActivity.this, login.class));
                    finish();
                }
            }
        });
    }
}