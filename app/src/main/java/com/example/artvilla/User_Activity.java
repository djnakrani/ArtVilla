package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class User_Activity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button navbarbtn,home;
    boolean draweropen = true;
    NavigationView nv;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawerlayout);
        navbarbtn = findViewById(R.id.navbar_menu);
        home =findViewById(R.id.home);
        nv = findViewById(R.id.nv);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_Activity.this, MainActivity.class));
                finish();
            }
        });

        navbarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(draweropen){
                    drawerLayout.openDrawer(GravityCompat.END);
                    draweropen=false;
                }else {
                    drawerLayout.closeDrawer(GravityCompat.END);
                    draweropen=true;
                }
            }
        });

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.uProfile:
                        Toast.makeText(User_Activity.this, "Profile",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.uCpassword:
                        Toast.makeText(User_Activity.this, "Change password",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.uOrder:
                        Toast.makeText(User_Activity.this, "My Order",Toast.LENGTH_SHORT).show();
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

    private void logOut() {
        fAuth.signOut();
        startActivity(new Intent(User_Activity.this,MainActivity.class));
        finish();
    }
}