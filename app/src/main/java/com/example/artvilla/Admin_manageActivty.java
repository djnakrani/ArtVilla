package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Admin_manageActivty extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    RecyclerView listitems;
    SearchView searchView;
    DatabaseReference iData;
    ArrayList<items> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_activty);
        fAuth = FirebaseAuth.getInstance();
        drawerLayout = findViewById(R.id.drawer_user);
        nav_user = findViewById(R.id.user_nav);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listitems = findViewById(R.id.Aitem_lists);
        searchView = findViewById(R.id.searchView);

        iData= FirebaseDatabase.getInstance().getReference().child("Items");
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        listitems.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        listitems.setHasFixedSize(true);

        nav_user.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.aHome:
                        startActivity(new Intent(Admin_manageActivty.this, Admin_Panel.class));
                        finish();
                        break;
                    case R.id.aCpassword:
                        startActivity(new Intent(Admin_manageActivty.this, Admin_cpassword.class));
                        finish();
                        break;
                    case R.id.aItem:
                        startActivity(new Intent(Admin_manageActivty.this, Admin_additem.class));
                        finish();
                        break;

                    case R.id.aManage:
                        Toast.makeText(Admin_manageActivty.this, "You Are Already In Home Page...",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.aLogout:
                        Toast.makeText(Admin_manageActivty.this, "Logout", Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(Admin_manageActivty.this,MainActivity.class));
            finish();
        }

    @Override
    protected void onStart() {
        super.onStart();
        if(iData!=null)
        {
            iData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        list = new ArrayList<>();
                        for(DataSnapshot sp:snapshot.getChildren())
                        {
                            list.add(sp.getValue(items.class));
                        }
                        ItemAdapter itemAdapter = new ItemAdapter(list);
                        listitems.setAdapter(itemAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String str) {
                    searchdata(str);
                    return false;
                }
            });
        }
    }

    private void searchdata(String str) {
        ArrayList<items> myitems=new ArrayList<>();
        for(items obj: list)
        {
            if(obj.getItem_name().toLowerCase().contains(str.toLowerCase()))
            {
                myitems.add(obj);
            }
            ItemAdapter itemAdapter2= new ItemAdapter(myitems);
            listitems.setAdapter(itemAdapter2);
        }
    }
}