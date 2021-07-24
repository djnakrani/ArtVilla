package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseUser fUser;
    DatabaseReference uData;
    FirebaseAuth fAuth;
    Button loginbtn;
    RecyclerView listitems;
    SearchView searchView;
    DatabaseReference iData;
    ArrayList<items> list;
    user_itemadapter user_itemadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginbtn = findViewById(R.id.gotologin);
        uData = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        listitems = findViewById(R.id.User_items);
        searchView = findViewById(R.id.usearchView);
        showall();
        if(fAuth.getUid() != null){
            loginbtn.setBackgroundResource(R.drawable.ic_baseline_person_24);
            loginbtn.setWidth(20);
            loginbtn.setHeight(20);
            loginbtn.setText(" ");
        }


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fAuth.getUid() != null) {
                    uData.child("User").child(fAuth.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                            System.out.println(snapshot.child("userType"));
                            if (snapshot.child("userType").exists()) {
                                if(snapshot.child("userType").getValue().equals("Admin")){
                                    startActivity(new Intent(MainActivity.this,Admin_Panel.class));
                                    finish();
                                }
                                else {
                                    startActivity(new Intent(MainActivity.this,User_Activity.class));
                                    finish();
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });
                }
                else {
                    startActivity(new Intent(MainActivity.this, login.class));
                    finish();
                }
            }
        });
    }

    private void showall() {
        iData= FirebaseDatabase.getInstance().getReference().child("Items");
        listitems.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false));
        listitems.setHasFixedSize(true);
        onStart();
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
                        user_itemadapter = new user_itemadapter(list);
                        listitems.setAdapter(user_itemadapter);

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
            user_itemadapter itemAdapter2= new user_itemadapter(myitems);
            listitems.setAdapter(itemAdapter2);
        }
    }
}
