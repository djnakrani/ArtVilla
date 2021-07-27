package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class User_favroite extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    RecyclerView listitems;
    SearchView searchView;
    DatabaseReference iData;
    Query fData;
    ArrayList<items> list;
    user_itemadapter user_itemadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favroite);
        fAuth = FirebaseAuth.getInstance();
        initilization();
        listitems = findViewById(R.id.User_items);
        searchView = findViewById(R.id.usearchView);
        showall();

        nav_user.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.Main:
                        startActivity(new Intent(User_favroite.this, MainActivity.class));
                        finish();
                        break;

                    case R.id.uProfile:
                        startActivity(new Intent(User_favroite.this, User_Activity.class));
                        finish();
                        break;
                    case R.id.uCpassword:
                        startActivity(new Intent(User_favroite.this, User_Cpassword.class));
                        finish();
                        break;
                    case R.id.uFav:
                        Toast.makeText(User_favroite.this, "You Are Already in This Page...",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.uLogout:
                        Toast.makeText(User_favroite.this, "Logout",Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(User_favroite.this,MainActivity.class));
        finish();
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
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void showall() {
        iData= FirebaseDatabase.getInstance().getReference("Items");
        fData = FirebaseDatabase.getInstance().getReference().child("Favroite");
        listitems.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        listitems.setHasFixedSize(true);
//        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fUser = fAuth.getCurrentUser();
        if(fData!=null)
        {
            fData.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        list = new ArrayList<>();
                        if(list.size()<=snapshot.getChildrenCount()) {
                            for (DataSnapshot sp : snapshot.getChildren()) {
                                if(sp.child("UserId").getValue().toString().equals(fAuth.getUid()))
                                {
                                    System.out.println(sp.child("ItemId").getValue().toString());
                                    String Itemid = sp.child("ItemId").getValue().toString();
                                    iData.child(Itemid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NotNull DataSnapshot snapshot2) {
                                            if (snapshot2.exists()) {
                                                list.add(snapshot2.getValue(items.class));
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                user_itemadapter = new user_itemadapter(list);
                                listitems.setAdapter(user_itemadapter);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NotNull DatabaseError error) {

                }
            });
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
        } else {
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
    }

    private void searchdata(String str) {
        ArrayList<items> myitems=new ArrayList<>();
        for(items obj: list)
        {
            if(obj.getArtist_name().toLowerCase().contains(str.toLowerCase()) | obj.getItem_name().toLowerCase().contains(str.toLowerCase()))
            {
                myitems.add(obj);
            }
            user_itemadapter itemAdapter2= new user_itemadapter(myitems);
            listitems.setAdapter(itemAdapter2);
        }
    }

}