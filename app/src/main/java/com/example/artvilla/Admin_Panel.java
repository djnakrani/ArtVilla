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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class Admin_Panel extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin_panel);
        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_user);
        nav_user = findViewById(R.id.user_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String curr_usermail = fAuth.getCurrentUser().getEmail();
        System.out.println(curr_usermail);
        View nav = nav_user.getHeaderView(0);
        TextView useremail = nav.findViewById(R.id.user_email);
        useremail.setText(curr_usermail);

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
                        Toast.makeText(Admin_Panel.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aCpassword:
                        startActivity(new Intent(Admin_Panel.this, Admin_cpassword.class));
                        finish();
                        break;
                    case R.id.aItem:
                        startActivity(new Intent(Admin_Panel.this, Admin_additem.class));
                        finish();
                        break;

                    case R.id.aManage:
                        startActivity(new Intent(Admin_Panel.this, Admin_manageActivty.class));
                        finish();
                        break;
                    case R.id.aLogout:
                        Toast.makeText(Admin_Panel.this, "Logout",Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(Admin_Panel.this,MainActivity.class));
        finish();
    }
}