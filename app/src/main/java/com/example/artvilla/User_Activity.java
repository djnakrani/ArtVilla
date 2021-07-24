package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class User_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    FirebaseUser User;
    DatabaseReference uData;
    TextView name,email,mono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initilization();
        showData();
        nav_user.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.uProfile:
                        Toast.makeText(User_Activity.this, "You Are Already In This Page...",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.uCpassword:
                        startActivity(new Intent(User_Activity.this, User_Cpassword.class));
                        finish();
                        break;
                    case R.id.uFav:
                        startActivity(new Intent(User_Activity.this, User_favroite.class));
                        finish();
                        break;
                    case R.id.uLogout:
                        Toast.makeText(User_Activity.this, "Logout",Toast.LENGTH_SHORT).show();
                        logOut();
                        break;
                    default:
                        return true;
                }

                return true;

            }
        });

    }

    private void showData() {
        User = fAuth.getCurrentUser();
        if(User != null)
        {
            String uId = User.getUid();
//            System.out.println(uId);
            uData = FirebaseDatabase.getInstance().getReference("User").child(uId);
//            System.out.println(uData);
            uData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        email.setText(snapshot.child("mail").getValue().toString());
                        mono.setText(snapshot.child("mobile").getValue().toString());
                        name.setText(snapshot.child("name").getValue().toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    private void initilization() {
        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_user);
        nav_user = findViewById(R.id.user_nav);
        name = findViewById(R.id.userDisName);
        email = findViewById(R.id.userDisEmail);
        mono = findViewById(R.id.userDisMobile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String curr_usermail = fAuth.getCurrentUser().getEmail();
        View nav = nav_user.getHeaderView(0);
        TextView useremail = nav.findViewById(R.id.user_email);
        TextView username = nav.findViewById(R.id.user_name);
        useremail.setText(curr_usermail);
        username.setText("USER");
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

    }

    private void logOut() {
        fAuth.signOut();
        startActivity(new Intent(User_Activity.this,MainActivity.class));
        finish();
    }
}