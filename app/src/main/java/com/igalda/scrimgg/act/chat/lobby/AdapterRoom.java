package com.igalda.scrimgg.act.chat.lobby;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.chat.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")

public class AdapterRoom extends RecyclerView.Adapter<HolderRoom> {

    private List<Room> listRoom;
    private Context c;

    public AdapterRoom(Context c) {
        this.c = c;
        this.listRoom = new ArrayList<>();
    }

    public void addSala (Room r) {
        listRoom.add(listRoom.size(), r);
        notifyItemInserted(listRoom.size()+1);
    }

    @Override
    public HolderRoom onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.cv_chat_room, parent, false);
        return new HolderRoom(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRoom holder, int position) {
        holder.getNombre().setText(listRoom.get(position).getNombre());
        holder.getUltimoMensaje().setText(listRoom.get(position).getUltimoMensaje());

        Date d = listRoom.get(position).getHoraUltimoMensaje();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        holder.getHoraUltimoMensaje().setText(sdf.format(d));

        if (listRoom.get(position).getFotoPerfil().equals("")){
            holder.getFoto().setImageResource(R.drawable.base_profile_pic);
        } else {
            ImageDownloader.downloadTheImage(listRoom.get(position).getFotoPerfil(), holder.getFoto());
        }

    }

    @Override
    public int getItemCount() {
        return listRoom.size();
    }
}
