package com.igalda.scrimgg.act.chat.sala;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.igalda.scrimgg.BaseActivity;
import com.igalda.scrimgg.ImageDownloader;
import com.igalda.scrimgg.R;
import com.igalda.scrimgg.act.usuario.VerUsuario;
import com.igalda.scrimgg.dom.Usuario;
import com.igalda.scrimgg.dom.chat.Message;
import com.igalda.scrimgg.neg.Negocio;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoom extends BaseActivity {

    // Layout's fields
    private CircleImageView fotoPerf;
    private TextView nombre;

    private RecyclerView cajaMensajes;

    private EditText txtMensaje;
    private ImageButton btEnviar;

    private AdapterMessage adapter;

    // Firebase instances and references
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();

    // User objects
    Usuario curUsrObj;
    Usuario otherUsrObj;

    // Business layer reference
    private Negocio n = Negocio.getNegocio();
    private List<Message> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat_room);
        View rootView = getLayoutInflater().inflate(R.layout.activity_chat_room, frameLayout);

        Intent intent = getIntent();
        String currentUsr = currentUser.getUid();
        String rid = intent.getStringExtra("rid");
        Usuario curUsrObj = n.nUsuario().buscarUsuario(currentUsr);
        Usuario otherUsrObj = n.nUsuario().buscarUsuario(separateRid(rid,currentUsr));

        // Get the layout's elements references
        fotoPerf = (CircleImageView) findViewById(R.id.civProfilePicChatRoom);
        nombre = (TextView) findViewById(R.id.tvChatRoomName);

        cajaMensajes = (RecyclerView) findViewById(R.id.cajaMensajesSalaChat);

        txtMensaje = (EditText) findViewById(R.id.txtMensajeSalaChat);
        btEnviar = (ImageButton) findViewById(R.id.btEnviarSalaChat);

        // Initialize elements
        if(otherUsrObj.getImgPath().equals("")){
            fotoPerf.setImageResource(R.drawable.base_profile_pic);
        } else {
            ImageDownloader.downloadTheImage(otherUsrObj.getImgPath(), fotoPerf);
        }
        nombre.setText(otherUsrObj.getNickName());

        // Initialize the adapter
        adapter = new AdapterMessage(ChatRoom.this, rid);
        LinearLayoutManager l = new LinearLayoutManager(ChatRoom.this);
        cajaMensajes.setLayoutManager(l);
        cajaMensajes.setAdapter(adapter);

        // Get the room's messages from the DB and add them to the adapter.
        messages = n.nChat().listMessages(rid);
        Collections.sort(messages);
        for (Message m : messages){
            adapter.addMensaje(m);
        }

        // Set scrollBar at bottom
        setScrollBar();

        // Sets an OnClickListener to the send button. Creates a new message, adds it to the DB,
        // to the messages list and to the adapter, and clears the text box.
        btEnviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Message m = new Message(String.valueOf(n.nChat().generateMessageId(rid)), curUsrObj.getNickName(), txtMensaje.getText().toString(), curUsrObj.getImgPath(), new Date());
                n.nChat().addMessage(m, rid);
                messages.add(m);
                adapter.addMensaje(m);
                txtMensaje.setText("");
            }
        });

        // Observer for maintaining the chat view focused at the last message.
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                super.onItemRangeChanged(positionStart, itemCount);
                setScrollBar();
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    // Método para mantener la vista en el último mensaje enviado utilizado por el observador
    private void setScrollBar(){
        if(adapter.getItemCount()!=0){
            cajaMensajes.scrollToPosition(adapter.getItemCount()-1);
        } else {
            cajaMensajes.scrollToPosition(cajaMensajes.getChildCount());
        }
    }

    public void openProfile(View view) {
        Intent intent = new Intent(this, VerUsuario.class);
        intent.putExtra("user", otherUsrObj.getId());
        startActivity(intent);
    }

    /**
     * Author: I. Jorquera.
     * Separates the room ID (rid) and returns the other user ID.
     * Parameter "rid" must contain "uid".
     * @param rid Room ID.
     * @param uid User ID.
     * @return An user ID.
     */
    private String separateRid(String rid, String uid){
        String [] split = rid.split("-");
        if(split[0].equals(uid)){
            return split[1];
        } else {
            return split[0];
        }
    }
}