package com.igalda.scrimgg.dom.chat;

import java.util.Date;

public class Message implements Comparable<Message> {
    private String mid;
    private String nombre;
    private String mensaje;
    private String fotoPerfil;
    private Date hora;

    public Message() {}

    public Message(String mid, String username, String mensaje, String fotoPerfil, Date hora) {
        this.mid = mid;
        this.mensaje = mensaje;
        this.nombre = username;
        this.fotoPerfil = fotoPerfil;
        this.hora = hora;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Date getHora(){ return hora; }

    public void setHora(Date hora){ this.hora = hora; }

    @Override
    public int compareTo(Message m) {
        return Integer.parseInt(this.getMid()) - Integer.parseInt(m.getMid());
    }
}
