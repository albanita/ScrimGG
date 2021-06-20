package com.igalda.scrimgg.act.chat.sala;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.chat.Message;
import com.igalda.scrimgg.neg.Negocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressLint("SimpleDateFormat")

public class AdapterMessage extends RecyclerView.Adapter<HolderMessage> {

    private List<Message> listMensaje;
    private Context c;
    private ScheduledExecutorService exec;
    private Negocio n = Negocio.getNegocio();
    private String rid;

    public AdapterMessage(Context c, String rid) {
        this.listMensaje = new ArrayList<>();
        this.c = c;
        this.rid = rid;
        listener();
    }

    public void addMensaje (Message m) {
        listMensaje.add(listMensaje.size(), m);
        notifyItemInserted(listMensaje.size()+1);
    }

    @Override
    public HolderMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.cv_chat_msg, parent, false);
        return new HolderMessage(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMessage holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());

        Date hora = listMensaje.get(position).getHora();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm");
        holder.getHora().setText(sdf.format(hora));

        if(listMensaje.get(position).getFotoPerfil().equals("")){
            holder.getFoto().setImageResource(R.drawable.base_profile_pic);
        } else {
            ImageDownloader.downloadTheImage(listMensaje.get(position).getFotoPerfil(), holder.getFoto());
        }
    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

    public List<Message> getItemList(){ return listMensaje; }

    // Thread to check for new items
    private void listener(){
        exec = Executors.newSingleThreadScheduledExecutor();
        Runnable hilo = new Runnable(){
            public void run(){
                List<Message> messages = n.nChat().listMessages(rid);
                Collections.sort(messages);
                int diff = messages.size() - getItemCount();
                if(diff > 0){
                    for (int i = messages.size()-diff; i < messages.size(); i++){
                        addMensaje(n.nChat().getMessage(rid, String.valueOf(i+1)));
                    }
                }
            }
        };
        exec.scheduleAtFixedRate(hilo, 5000, 1000, TimeUnit.MILLISECONDS);
    }
}
