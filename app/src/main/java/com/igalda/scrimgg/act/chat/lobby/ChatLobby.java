package com.igalda.scrimgg.act.chat.lobby;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.act.chat.sala.ChatRoom;
import com.igalda.scrimgg.dom.chat.Room;
import com.igalda.scrimgg.neg.Negocio;

import java.util.List;

public class ChatLobby extends BaseActivity {

    private RecyclerView cajaChats;

    private AdapterRoom adapter;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();

    private Negocio n = Negocio.getNegocio();
    private List<Room> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat_lobby);
        View rootView = getLayoutInflater().inflate(R.layout.activity_chat_lobby, frameLayout);

        cajaChats = (RecyclerView) findViewById(R.id.cajaChats);

        adapter = new AdapterRoom(ChatLobby.this);
        LinearLayoutManager l = new LinearLayoutManager(ChatLobby.this);
        cajaChats.setLayoutManager(l);
        cajaChats.setAdapter(adapter);

        rooms = n.nChat().listRooms(currentUser.getUid());

        for (Room r : rooms) {
            adapter.addSala(r);
        }
    }

    // Obtiene el TextView que indica el nombre de usuario con el que se tiene un chat abierto,
    // inicia un intent para lanzar la actividad del chat, pasa el nombre de usuario obtenido como
    // parámetro del intent y lanza la actividad.
    public void openRoom(View v) {
        TextView nomChat = (TextView) v.findViewById(R.id.tvRoomName);
        Room rm = null;
        for (Room r : rooms){
            rm = r;
            if (r.getNombre().equals(nomChat.getText())) break;
        }
        Intent intent = new Intent(this, ChatRoom.class);
        intent.putExtra("rid", rm.getRid());
        startActivity(intent);
    }
}