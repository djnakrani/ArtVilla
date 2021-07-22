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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Admin_additem extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    EditText itemname,artistname,price,file;
    Button btnAdd;
    DatabaseReference iData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_additem);
        fAuth = FirebaseAuth.getInstance();
        itemname = findViewById(R.id.editIname);
        artistname = findViewById(R.id.editAname);
        price = findViewById(R.id.editPrice);
        file = findViewById(R.id.editFileName);
        btnAdd = findViewById(R.id.btnAddItem);

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
                switch (id) {
                    case R.id.aHome:
//                        Toast.makeText(Admin_cpassword.this, "You Are Already In Home Page...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_additem.this, Admin_Panel.class));
                        finish();
                        break;
                    case R.id.aCpassword:
//                        Toast.makeText(Admin_additem.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_additem.this, Admin_cpassword.class));
                        finish();
                        break;
                    case R.id.aItem:
                        Toast.makeText(Admin_additem.this, "You Are Already In Home Page...", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Admin_additem.this, Admin_additem.class));
//                        finish();
                        break;

                    case R.id.aManage:
//                        Toast.makeText(Admin_Panel.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Admin_additem.this, Admin_manageActivty.class));
                        finish();
                        break;
                    case R.id.aLogout:
//                        Toast.makeText(Admin_Panel.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        Toast.makeText(Admin_additem.this, "Logout", Toast.LENGTH_SHORT).show();
                        logOut();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items i1=new items();
                i1.setItem_name(itemname.getText().toString());
                i1.setArtist_name(artistname.getText().toString());
                i1.setPrice(price.getText().toString());
                i1.setPhotoPath("default");
                iData = FirebaseDatabase.getInstance().getReference("Items").child(i1.getItem_name());
                iData.setValue(i1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Admin_additem.this,"Item Added Successfully...",Toast.LENGTH_SHORT).show();
                            itemname.setText(" ");
                            artistname.setText(" ");
                            price.setText(" ");
                            file.setText(" ");
                        }
                        else {
                            Toast.makeText(Admin_additem.this,"Something Went Wrong,Please Try After Sometime",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
    }
    private void logOut() {
        fAuth.signOut();
        startActivity(new Intent(Admin_additem.this,MainActivity.class));
        finish();
    }
}