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

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Queue;

public class user_itemadapter extends RecyclerView.Adapter<user_itemadapter.MyViewHolder> {

    ArrayList<items> list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView itemName,artistName,aMono;
        ImageView image;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.Name);
            artistName = itemView.findViewById(R.id.ArtistName);
            aMono = itemView.findViewById(R.id.Price);
            image = itemView.findViewById(R.id.ItemImage);
        }

    }
}
