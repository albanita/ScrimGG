package com.igalda.scrimgg.act.notif;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.notif.Notification;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterNotifications extends RecyclerView.Adapter<HolderNotifications> {

    private List<Notification> listNotif = new ArrayList<>();
    private Context c;


    public AdapterNotifications(Context c) {
        this.c = c;
    }

    public void addNotification (Notification not) {
        listNotif.add(not);
        notifyItemInserted(listNotif.size());
    }

    public void wipeNotifications (){
        int size = listNotif.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listNotif.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    private void deleteItem(int position) {
        listNotif.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listNotif.size());
    }

    @Override
    public HolderNotifications onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.cv_notification, parent, false);
        return new HolderNotifications(v);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull HolderNotifications holder, int position) {
        holder.getNotifTitle().setText(listNotif.get(position).getTitle());
        holder.getNotifText().setText(listNotif.get(position).getText());

        Long codigoHora = listNotif.get(position).getDate().getTime();
        Date d = new Date(codigoHora);
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm-dd/MMM");
        holder.getNotifTime().setText(sdf.format(d));
    }
    @Override
    public int getItemCount() {
        return listNotif.size();
    }
}
