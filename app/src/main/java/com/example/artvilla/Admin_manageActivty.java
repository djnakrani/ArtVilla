package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Admin_manageActivty extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    RecyclerView r1;
    DatabaseReference iData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_activty);
        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_user);
        nav_user = findViewById(R.id.user_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        r1 = findViewById(R.id.recycle1);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        nav_user.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.aHome:
//                        Toast.makeText(Admin_cpassword.this, "You Are Already In Home Page...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_manageActivty.this, Admin_Panel.class));
                        finish();
                        break;
                    case R.id.aCpassword:
//                        Toast.makeText(Admin_additem.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_manageActivty.this, Admin_cpassword.class));
                        finish();
                        break;
                    case R.id.aItem:
//                        Toast.makeText(Admin_additem.this, "You Are Already In Home Page...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_manageActivty.this, Admin_additem.class));
                        finish();
                        break;

                    case R.id.aManage:
                        Toast.makeText(Admin_manageActivty.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Admin_additem.this, Admin_manageActivty.class));
//                        finish();
                        break;
                    case R.id.aLogout:
//                        Toast.makeText(Admin_Panel.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Admin_manageActivty.this, "Logout", Toast.LENGTH_SHORT).show();
                        logOut();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        iData= FirebaseDatabase.getInstance().getReference("Items");

        iData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot sp:snapshot.getChildren())
                {
                    System.out.println(sp);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
        private void logOut() {
            fAuth.signOut();
            startActivity(new Intent(Admin_manageActivty.this,MainActivity.class));
            finish();
        }
}