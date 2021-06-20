package com.igalda.scrimgg.act.chat.lobby;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igalda.scrimgg.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderRoom extends RecyclerView.ViewHolder {

    private TextView nombre;
    private TextView ultimoMensaje;
    private TextView horaUltimoMensaje;
    private CircleImageView foto;

    public HolderRoom(View itemView){
        super(itemView);
        nombre = (TextView) itemView.findViewById(R.id.tvRoomName);
        ultimoMensaje = (TextView) itemView.findViewById(R.id.tvLastMsg);
        horaUltimoMensaje = (TextView) itemView.findViewById(R.id.tvLastMsgTime);
        foto = (CircleImageView) itemView.findViewById(R.id.fotoPerfilChat);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(TextView ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public TextView getHoraUltimoMensaje() {
        return horaUltimoMensaje;
    }

    public void setHoraUltimoMensaje(TextView horaUltimoMensaje) {
        this.horaUltimoMensaje = horaUltimoMensaje;
    }

    public CircleImageView getFoto() {
        return foto;
    }

    public void setFoto(CircleImageView foto) {
        this.foto = foto;
    }
}
