package com.igalda.scrimgg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.igalda.scrimgg.act.chat.lobby.ChatLobby;
import com.igalda.scrimgg.act.chat.sala.ChatRoom;
import com.igalda.scrimgg.act.notif.Notificaciones;

public class principal extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_principal);
        View rootView = getLayoutInflater().inflate(R.layout.activity_principal, frameLayout);
    }

    public void launchChat(View view) {
        Intent intent = new Intent(principal.this, ChatLobby.class);
        startActivity(intent);
    }

    public void launchNotif(View view) {
        Intent intent = new Intent(principal.this, Notificaciones.class);
        startActivity(intent);
    }
}