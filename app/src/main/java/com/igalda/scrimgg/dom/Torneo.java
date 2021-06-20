package com.igalda.scrimgg.dom;
import com.igalda.scrimgg.pers.PersistenciaEquipo;
import com.igalda.scrimgg.pers.PersistenciaTorneo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
public class Torneo implements Serializable {
    private String id;
    private String nombre;

    public Torneo(String id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public Torneo() {
        this.id="";
        this.nombre="";
    }

    public String getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String toString(){
        return "ID: " + this.id + " Nombre: " + this.nombre;
    }

    public void setId(String id) {
        this.id = id;
    }
}
    /*private String id;
    private Date fechaInicio;
    private Date fechaFinal;
    
    private Equipo equipoGanador;
    private List<Equipo> equiposParticipantes;
    
    private List<Enfrentamiento> enfrentamientos;

    public Torneo() {
    }

    public Torneo(String id, Date fechaInicio, Date fechaFinal, Equipo equipoGanador, List<Equipo> equiposParticipantes, List<Enfrentamiento> enfrentamientos) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.equipoGanador = equipoGanador;
        this.equiposParticipantes = equiposParticipantes;
        this.enfrentamientos = enfrentamientos;
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

    public Equipo getEquipoGanador() {
        return equipoGanador;
    }

    public List<Equipo> getEquiposParticipantes() {
        return equiposParticipantes;
    }

    public List<Enfrentamiento> getEnfrentamientos() {
        return enfrentamientos;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setEquipoGanador(Equipo equipoGanador) {
        this.equipoGanador = equipoGanador;
    }

    public void setEquiposParticipantes(List<Equipo> equiposParticipantes) {
        this.equiposParticipantes = equiposParticipantes;
    }

    public void setEnfrentamientos(List<Enfrentamiento> enfrentamientos) {
        this.enfrentamientos = enfrentamientos;
    }

    @Override
    public String toString() {
        return "Torneo{" + "id=" + id + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal + ", equipoGanador=" + equipoGanador + ", equiposParticipantes=" + equiposParticipantes + ", enfrentamientos=" + enfrentamientos + '}';
    }
    
    @Override
    public boolean equals(Object o){
        boolean b = false;
        if(o instanceof Torneo && o != null){
            Torneo t = (Torneo) o;
            if(t.id.equals(this.id)){
                b=true;
            }
        }
        return b;
    }
}*/
