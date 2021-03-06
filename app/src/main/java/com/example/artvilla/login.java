package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {
    TextView signup,reset;
    EditText uname,upwd;
    Button login;
    ProgressBar loginprogBar;
//    DatabaseReference logindb;
    FirebaseAuth fAuth;
    DatabaseReference uData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();
        signup = findViewById(R.id.linksignup);
        uname = findViewById(R.id.signinemail);
        upwd = findViewById(R.id.signinpassword);
        login = findViewById(R.id.loginbtn);
        reset = findViewById(R.id.forgotPassword);
        loginprogBar = findViewById(R.id.loginprogress);
//        logindb = FirebaseDatabase.getInstance().getReference().child("User");
        uData = FirebaseDatabase.getInstance().getReference();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginprogBar.setVisibility(View.VISIBLE);
                if(isvalid(uname,upwd))
                {
                    String uName=uname.getText().toString();
                    String uPwd = upwd.getText().toString();

                    fAuth.signInWithEmailAndPassword(uName,uPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                String uId=fAuth.getCurrentUser().getUid();
                                uData.child("User").child(uId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()){
                                            if(task.getResult().child("userType").exists())
                                            {
                                                User obj = task.getResult().getValue(User.class);
                                                if((obj.getUserType()).equals("Admin")){
                                                    startActivity(new Intent(login.this, Admin_Panel.class));
                                                    finish();
                                                }
                                                else {
                                                    startActivity(new Intent(login.this, MainActivity.class));
                                                    finish();
                                                }

                                            }
                                        }else {
                                            Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(login.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, activity_sign_up.class));
                finish();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, forgotpassword.class));
            }
        });
    }

    private boolean isvalid(EditText uname, EditText upwd) {
        boolean isuname,ispwd;
        if(uname.getText().toString().isEmpty())
        {
            uname.setError("Email Not Blank Consider..");
            isuname = false;
        }
        else
            isuname = true;

        if(upwd.getText().toString().isEmpty())
        {
            upwd.setError("Email Not Blank Consider..");
            ispwd = false;
        }
        else
            ispwd = true;

        if(isuname & ispwd)
            return true;
        else
            return false;
    }
}
