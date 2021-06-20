package com.igalda.scrimgg.dom;
import com.igalda.scrimgg.pers.PersistenciaEquipo;
import com.igalda.scrimgg.pers.PersistenciaLiga;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author albanita
 */
public class Liga implements Serializable {
    private String id;
    private Date fechaInicio;
    private Date fechaFinal;
    private String nombre;

    public Liga() {
        this.id="";
        this.fechaFinal=null;
        this.fechaFinal=null;
        this.nombre="";
    }

    public Liga(String id, String nombre, Date fechaInicio, Date fechaFinal) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }

    public void setID(String id){
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinal(Date fechaFinal) { this.fechaFinal = fechaFinal; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return "Liga{" +
                "id='" + id + '\'' +
                ", fechaInicio=" + sdf.format(fechaInicio) +
                ", fechaFinal=" + sdf.format(fechaFinal) +
                ", nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        boolean b = false;
        if(o instanceof Liga && o != null){
            Liga l = (Liga) o;
            if(l.getId().equals(this.id)){
                b=true;
            }
        }
        return b;
    }
}
