package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class User_Cpassword extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    FirebaseUser user;
    Button cpassword;
    EditText old,newp,cnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cpassword);

        initilization();

        nav_user.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.uProfile:
                        startActivity(new Intent(User_Cpassword.this, User_Activity.class));
                        finish();
                        break;
                    case R.id.uCpassword:
                        Toast.makeText(User_Cpassword.this, "You Are Already in This Page...",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.uFav:
                        startActivity(new Intent(User_Cpassword.this, User_Activity.class));
                        finish();
                        break;
                    case R.id.uLogout:
                        Toast.makeText(User_Cpassword.this, "Logout",Toast.LENGTH_SHORT).show();
                        logOut();
                        break;
                    default:
                        return true;
                }

                return true;

            }
        });

        cpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid(old,newp,cnew))
                {
                    changepassword();
                }
            }
        });
    }

    private void initilization() {
        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_user);
        nav_user = findViewById(R.id.user_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String curr_usermail = fAuth.getCurrentUser().getEmail();
        View nav = nav_user.getHeaderView(0);
        TextView useremail = nav.findViewById(R.id.user_email);
        TextView username = nav.findViewById(R.id.user_name);
        useremail.setText(curr_usermail);
        username.setText("User");
        cpassword = findViewById(R.id.ubtnCapassword);
        old = findViewById(R.id.ueditOldpwd);
        newp = findViewById(R.id.ueditNewpwd);
        cnew =findViewById(R.id.ueditNewCpwd);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void logOut() {
        fAuth.signOut();
        startActivity(new Intent(User_Cpassword.this,MainActivity.class));
        finish();
    }

    private boolean isValid(EditText old, EditText newp, EditText cnew) {
        boolean isold,isnew,iscnew;
        if(old.getText().toString().isEmpty())
        {
            old.setError("Password Not Null");
            isold=false;
        }
        else {
            isold = true;
        }

        if(newp.getText().toString().isEmpty())
        {
            newp.setError("Password Not Null");
            isnew=false;
        }
        else {
            isnew = true;
        }
        if(!newp.getText().toString().equals(cnew.getText().toString()))
        {
            cnew.setError("Conform Password Not Match With New Password");
            iscnew=false;
        }
        else {
            iscnew = true;
        }

        if(isold & isnew & iscnew)
        {
            return true;
        }
        else
            return false;
    }

    private void changepassword() {
        user = FirebaseAuth.getInstance().getCurrentUser();
//        System.out.println(user);
        if (user != null)
        {
            String email = user.getEmail();
            String pass=old.getText().toString();
            String Newpw = cnew.getText().toString();
            AuthCredential cred = EmailAuthProvider.getCredential(email,pass);
            System.out.println(email + pass + cred);
            user.reauthenticate(cred).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    user.updatePassword(Newpw).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(User_Cpassword.this,"Successfully Changed Password,Please Login Again...",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(User_Cpassword.this,login.class));
                                finish();

                            }
                            else{
                                Toast.makeText(User_Cpassword.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NotNull Exception e) {
                    Toast.makeText(User_Cpassword.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}