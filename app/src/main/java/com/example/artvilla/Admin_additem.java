package com.example.artvilla;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Admin_additem extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 234;
    DrawerLayout drawerLayout;
    NavigationView nav_user;
    ActionBarDrawerToggle toggle;
    FirebaseAuth fAuth;
    EditText itemname,artistname,AMono;
    Button btnAdd;
    DatabaseReference iData;
    ImageView itemImage;
    private Uri filePath;
    StorageReference sR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_additem);
        fAuth = FirebaseAuth.getInstance();
        iData = FirebaseDatabase.getInstance().getReference();
        sR = FirebaseStorage.getInstance().getReference();
        itemname = findViewById(R.id.editIname);
        artistname = findViewById(R.id.editAname);
        AMono = findViewById(R.id.editAMono);
        btnAdd = findViewById(R.id.btnAddItem);
        itemImage = findViewById(R.id.editFileName);
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
                switch (id) {
                    case R.id.aHome:
                        startActivity(new Intent(Admin_additem.this, Admin_Panel.class));
                        finish();
                        break;
                    case R.id.aCpassword:
                        startActivity(new Intent(Admin_additem.this, Admin_cpassword.class));
                        finish();
                        break;
                    case R.id.aItem:
                        Toast.makeText(Admin_additem.this, "You Are Already In Home Page...", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.aManage:
                        startActivity(new Intent(Admin_additem.this, Admin_manageActivty.class));
                        finish();
                        break;
                    case R.id.aLogout:
                        Toast.makeText(Admin_additem.this, "Logout", Toast.LENGTH_SHORT).show();
                        logOut();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });

        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation(itemname,artistname,AMono,itemImage)){
                    iData.child("Items").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot snapshot) {
                            String id = itemname.getText().toString();
//                            System.out.println(snapshot.child(id));
                            if((!snapshot.child(id).exists()) & (id != ""))
                            {
//                                System.out.println(iData);
//                                System.out.println(snapshot.child(id).getValue());
                                if(snapshot.child(id).getValue() == null)
                                {
                                    items i1 = new items();
                                    i1.setItem_name(itemname.getText().toString());
                                    i1.setArtist_name(artistname.getText().toString());
                                    i1.setartist_mono(AMono.getText().toString());
                                    i1.setPhotoPath("default");
                                    DatabaseReference iDataChild = FirebaseDatabase.getInstance().getReference("Items").child(i1.getItem_name());
                                    iDataChild.setValue(i1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                UploadImage();
                                                Toast.makeText(Admin_additem.this, "Item Added Successfully...", Toast.LENGTH_SHORT).show();
                                                itemname.setText("");
                                                artistname.setText("");
                                                AMono.setText("");
                                                filePath = null;
                                            } else {
                                                Toast.makeText(Admin_additem.this, "Something Went Wrong,Please Try After Sometime", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                            else
                            {
                                Toast.makeText(Admin_additem.this,"Item Already Exist...",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private Uri UploadImage() {
        final String[] filepath = new String[1];
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Data Stored...");
        progressDialog.show();
        String path = itemname.getText().toString();
        StorageReference fileUpload=sR.child("Items/"+path+".jpg");
        fileUpload.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                fileUpload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        filepath[0] = String.valueOf(uri);
                        DatabaseReference change = iData.child("Items").child(path);
//                        System.out.println(change);
                        Map<String, Object> hopperUpdates = new HashMap<>();
                        hopperUpdates.put("photoPath",filepath[0]);

                        change.updateChildren(hopperUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Admin_additem.this,"Updated..",Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(Admin_additem.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }

                            }
                        });
//                        System.out.println(hopperUpdates);
                    }
                });

                itemImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_100));
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                double progess= ((100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount());
                progressDialog.setTitle((progess) + "% Uploaded..");
            }
        });
        return filePath;
    }

    private boolean checkValidation(EditText itemname, EditText artistname, EditText AMono, ImageView itemImage) {
        boolean isIname,isAname,isPrice,isFilepath;
        if(itemname.getText().toString().isEmpty()){
            itemname.setError("Item Name Is Required..");
            isIname=false;
        }
        else {
            isIname=true;
        }
        if(artistname.getText().toString().isEmpty()){
            artistname.setError("Artist Name Is Required..");
            isAname=false;
        }
        else {
            isAname=true;
        }
        if(AMono.getText().toString().isEmpty()){
            AMono.setError("Price Is Required..");
            isPrice=false;
        }
        else {
            isPrice=true;
        }
        if(filePath == null){
            Toast.makeText(Admin_additem.this,"Please Input Image...",Toast.LENGTH_LONG).show();
            isFilepath=false;
        }
        else {
            isFilepath=true;
        }

        if(isIname && isAname && isPrice && isFilepath){
            return true;
        }
        else {
            return false;
        }
    }

    private void showFileChooser() {
        Intent inImage=new Intent(Intent.ACTION_PICK);
        inImage.setType("image/*");
        inImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(inImage, "Select Picture"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        filePath = data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
            itemImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logOut() {
        fAuth.signOut();
        startActivity(new Intent(Admin_additem.this,MainActivity.class));
        finish();
    }

}