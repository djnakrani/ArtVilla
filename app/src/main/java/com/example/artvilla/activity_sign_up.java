package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class activity_sign_up extends AppCompatActivity {
    TextView linklogin;
    Button signup;
    EditText name,mail,mono,pwd;
    boolean isVaild=false;
    DatabaseReference uData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        uData =FirebaseDatabase.getInstance().getReference().child("User");
        linklogin = findViewById(R.id.linklogin);
        signup = findViewById(R.id.signupbtn);
        name = findViewById(R.id.signupname);
        mail = findViewById(R.id.signupemail);
        mono = findViewById(R.id.signupmono);
        pwd = findViewById(R.id.signuppwd);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name,mail,mono,pwd);
                if(isVaild)
                {
                    User u1=new User();

                    u1.setUname(name.getText().toString());
                    u1.setMail(mail.getText().toString());
                    u1.setMono(mono.getText().toString());
                    u1.setPwd(pwd.getText().toString());
                    u1.setIsAdmin("1");

                    uData.push().setValue(u1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            startActivity(new Intent(activity_sign_up.this, MainActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(activity_sign_up.this,"Something Wrong,Please Try After Some Time.",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity_sign_up.this, login.class));
                finish();
            }
        });
    }

    private boolean validate(EditText name, EditText mail, EditText mono, EditText pwd) {
        boolean isname,ismail,ismono,ispwd;
        String pattern1 ="[a-zA-Z0-9_.+-]+@[a-zA-Z]+\\.[a-zA-Z.]";
        Pattern p1=Pattern.compile(pattern1);
        if(name.getText().toString().isEmpty()){
            name.setError("Name Is Required..");
            isname=false;
        }
        else {
            isname=true;
        }

        Matcher m1 = p1.matcher(mail.getText().toString());
        if(!m1.matches()){
            mail.setError("Enter Vaild Email..");
            ismail=false;
        }
        else {
            ismail = true;
        }

        if(mono.getText().toString().isEmpty()){
            mono.setError("Mobile Number Is Required..");
            ismono=false;
        }
        else {
            ismono=true;
        }

        if(pwd.getText().toString().isEmpty()){
            pwd.setError("Password Should Be Required..");
            ispwd=false;
        }
        else {
            ispwd=true;
        }

        if(isname & ismail & ismono & ispwd){
            isVaild=true;
        }else {
            isVaild=false;
        }
        return isVaild;
    }
}