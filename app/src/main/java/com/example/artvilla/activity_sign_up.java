package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class activity_sign_up extends AppCompatActivity {
    TextView linklogin;
    Button signup;
    ProgressBar progressBar;
    EditText name,mail,mono,pwd;
    boolean isVaild=false;
    DatabaseReference uData;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fAuth = FirebaseAuth.getInstance();
        linklogin = findViewById(R.id.linklogin);
        signup = findViewById(R.id.signupbtn);
        name = findViewById(R.id.signupname);
        mail = findViewById(R.id.signupemail);
        mono = findViewById(R.id.signupmono);
        pwd = findViewById(R.id.signuppwd);
        progressBar = findViewById(R.id.progressBar);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(name,mail,mono,pwd);
                if(isVaild)
                {
                    String uName = name.getText().toString();
                    String uMail = mail.getText().toString();
                    String uMono = mono.getText().toString();
                    String uPwd = pwd.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);
//                    Toast.makeText(activity_sign_up.this,"Created....",Toast.LENGTH_SHORT).show();
                    fAuth.createUserWithEmailAndPassword(uMail,uPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(activity_sign_up.this,"Created....",Toast.LENGTH_SHORT).show();
                                FirebaseUser user = fAuth.getCurrentUser();
                                String uId = user.getUid();
                                uData =FirebaseDatabase.getInstance().getReference("User").child(uId);
                                HashMap<String,String> data=new HashMap<>();
                                data.put("U_Id",uId);
                                data.put("Name",uName);
                                data.put("Mail",uMail);
                                data.put("Mobile",uMono);
                                data.put("ImageURL","Default");
                                data.put("UserType","Admin");
                                uData.setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(activity_sign_up.this,"You Are Register Successfully...",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(activity_sign_up.this, MainActivity.class));
                                            finish();
                                        }else{
                                            Toast.makeText(activity_sign_up.this,"Something Went Wrong,Please Try After Sometime",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(activity_sign_up.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
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
        String pattern1 ="[a-zA-Z0-9_.+-]+@[a-zA-Z]+\\.[a-zA-Z.]{1,3}";
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

        if(pwd.getText().toString().length() < 8){
            pwd.setError("Password Should Be Minimum 8 Character..");
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