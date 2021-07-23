package com.example.artvilla;

import android.app.AlertDialog;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Queue;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    ArrayList<items> list;
    public ItemAdapter(ArrayList<items> list) {
        this.list = list;
    }
    public OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onDeleteClick(int position);
    }
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        return new MyViewHolder(v,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int pos) {
        holder.itemName.setText(list.get(pos).getItem_name());
        holder.artistName.setText(list.get(pos).getArtist_name());
        holder.aMono.setText(list.get(pos).getartist_mono());
        Picasso.get().load(Uri.parse(list.get(pos).getPhotoPath())).into(holder.image);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName,artistName,aMono;
        ImageView image;
        Button remove;
        public MyViewHolder(@NonNull @NotNull View itemView, OnItemClickListner listener) {
            super(itemView);

            itemName = itemView.findViewById(R.id.Name);
            artistName = itemView.findViewById(R.id.ArtistName);
            aMono = itemView.findViewById(R.id.Price);
            image = itemView.findViewById(R.id.ItemImage);
            remove = itemView.findViewById(R.id.RemoveItem);

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Items").child(itemName.getText().toString());
                        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for ( DataSnapshot ds:snapshot.getChildren()){
                                    ds.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });
                }
            });

//            remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null)
//                    {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION)
//                            listener.onDeleteClick(position);
//                    }
//                }
//            });
        }

    }
}
