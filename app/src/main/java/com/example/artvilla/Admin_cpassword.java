package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Admin_cpassword extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cpassword);

        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_user);
        nav_user = findViewById(R.id.user_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        nav_user.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.aHome:
//                        Toast.makeText(Admin_cpassword.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_cpassword.this, Admin_Panel.class));
                        finish();
                        break;
                    case R.id.aCpassword:
                        Toast.makeText(Admin_cpassword.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aItem:
                        Toast.makeText(Admin_cpassword.this, "My Order",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aLogout:
                        Toast.makeText(Admin_cpassword.this, "Logout",Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(Admin_cpassword.this,MainActivity.class));
        finish();
    }

}