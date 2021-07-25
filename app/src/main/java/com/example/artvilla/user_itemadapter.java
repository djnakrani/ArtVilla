package com.example.artvilla;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class user_itemadapter extends RecyclerView.Adapter<user_itemadapter.MyViewHolder> {

    ArrayList<items> list;
    String genId;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();

    DatabaseReference df = FirebaseDatabase.getInstance().getReference("Favroite");
    public user_itemadapter(ArrayList<items> list) {
        this.list = list;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_user,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int pos) {
        holder.itemName.setText(list.get(pos).getItem_name());
        holder.artistName.setText(list.get(pos).getArtist_name());
        holder.aMono.setText(list.get(pos).getartist_mono());
        Picasso.get().load(Uri.parse(list.get(pos).getPhotoPath())).into(holder.image);
        System.out.println(fUser);
        if(fUser != null) {
            genId = fUser.getUid() + holder.itemName.getText().toString();
            df.child(genId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    System.out.println(snapshot);
                    if (snapshot.exists()) {
                        holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    } else {
                        holder.fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName,artistName,aMono;
        ImageView image,fav;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.Name);
            artistName = itemView.findViewById(R.id.ArtistName);
            aMono = itemView.findViewById(R.id.Price);
            image = itemView.findViewById(R.id.ItemImage);
            fav = itemView.findViewById(R.id.myFav);

            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(fUser != null) {
                        genId = fUser.getUid() + itemName.getText().toString();
                        df.child(genId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                System.out.println(snapshot);
                                if (snapshot.exists()) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        ds.getRef().removeValue();
                                    }
                                    fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                                } else {
                                    HashMap<String, String> mv = new HashMap<>();
                                    mv.put("UserId", fAuth.getUid());
                                    mv.put("ItemId", itemName.getText().toString());
                                    df.child(genId).setValue(mv).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                                            } else {
                                                Toast.makeText(fav.getContext(), "Something Error...", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(fav.getContext(), "Please Login First.", Toast.LENGTH_LONG).show();
                        fav.getContext().startActivity(new Intent(fav.getContext(),login.class));
                    }
                }
            });


        }

    }
}
