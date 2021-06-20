package com.igalda.scrimgg.dom.notif;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    private String nid; // id único de la notificación
    private String title; // Título de la notificación
    private String text; // Cuerpo de la notificación
    private Date date;
    private String uid; // id usuario destino

    public Notification() {}

    public Notification(String nid,  String uid, String asunto, String texto, Date fecha) {
        this.nid = nid;
        this.title = asunto;
        this.text = texto;
        this.date = fecha;
        this.uid = uid;
    }

    public String getNid() {
        return this.nid;
    }


    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public Date getDate() {
        return this.date;
    }

    public String getUid(){ return this.uid; }
}
