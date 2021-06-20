package com.igalda.scrimgg.act.notif;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.dom.notif.Notification;
import com.igalda.scrimgg.neg.Negocio;

import java.util.List;

public class Notificaciones extends BaseActivity {

    private Negocio n = Negocio.getNegocio();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();

    private RecyclerView notif;

    private AdapterNotifications adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notificaciones);
        View rootView = getLayoutInflater().inflate(R.layout.activity_notificaciones, frameLayout);

        notif = (RecyclerView) findViewById(R.id.rvNotificationsBox);

        adapter = new AdapterNotifications(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        notif.setLayoutManager(l);
        notif.setAdapter(adapter);

        List<Notification> lnotif = n.nNotification().listNotifications(currentUser.getUid());

        for (Notification n : lnotif) {
            adapter.addNotification(n);
        }
    }

    //@Override
    public HolderNotifications onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this).inflate(R.layout.cv_notification, parent, false);
        return new HolderNotifications(v);
    }

    public void wipeNotifications(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                n.nNotification().wipeNotifications(currentUser.getUid());
                adapter.wipeNotifications();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.setTitle(getString(R.string.notifWipeAskTitle));
        dialog.setMessage(getString(R.string.notifWipeAskMessage));
        dialog.show();
    }
}