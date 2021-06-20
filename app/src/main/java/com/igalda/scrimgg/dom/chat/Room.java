package com.igalda.scrimgg.dom.chat;

import java.util.Date;

public class Room {
    // Room ID. Structure: user1uniqueid-user2uniqueid. user1-user2 or user2-user1 order
    // is defined alphabetically, so the order of each pair of users is always the same.
    private String rid;
    private String nombre; // Other user nickname
    private String fotoPerfil; // Other user profile pic
    private String ultimoMensaje; // Last message sent/received
    private Date horaUltimoMensaje; // Time of last message

    public Room() {}

    public Room(String rid, String nombre, String fotoPerfil, String ultimoMensaje, Date horaUltimoMensaje) {
        this.rid = rid;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.ultimoMensaje = ultimoMensaje;
        this.horaUltimoMensaje = horaUltimoMensaje;
    }

    public String getRid(){ return this.rid; }

    public void setRid(String rid){ this.rid = rid; }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public void setUltimoMensaje(String ultimoMensaje) {
        this.ultimoMensaje = ultimoMensaje;
    }

    public Date getHoraUltimoMensaje() {
        return horaUltimoMensaje;
    }

    public void setHoraUltimoMensaje(Date horaUltimoMensaje) {
        this.horaUltimoMensaje = horaUltimoMensaje;
    }
}
