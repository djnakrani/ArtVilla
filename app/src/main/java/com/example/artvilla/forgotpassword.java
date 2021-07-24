package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class forgotpassword extends AppCompatActivity {

    EditText resetmail;
    Button resetbtn;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        resetmail = findViewById(R.id.resetemial);
        resetbtn = findViewById(R.id.resetbtn);
        fAuth = FirebaseAuth.getInstance();

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resetmail.getText().toString().isEmpty())
                {
                    resetmail.setError("Enter Mail Id");
                }
                else
                {
                    fAuth.sendPasswordResetEmail(resetmail.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(forgotpassword.this,"Link Send In Mail...",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(forgotpassword.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
}