/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author albanita
 */

// TODO borrar esta clase
public class Clasificacion implements Serializable {
    private String id;
    HashMap<String, Integer> puntosEquipo = new HashMap<String, Integer>();
    private Liga liga;

    public Clasificacion() {
    }

    public Clasificacion(String id, HashMap<String, Integer> puntos, Liga liga) {
        this.id = id;
        this.puntosEquipo  = puntos;
        this.liga = liga;
    }

    public String getId() {
        return id;
    }

    public HashMap<String, Integer> getPuntos() {
        return puntosEquipo ;
    }

    public Liga getLiga() {
        return liga;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPuntos(HashMap<String, Integer> puntos) {
        this.puntosEquipo  = puntos;
    }

    public void setLiga(Liga liga) {
        this.liga = liga;
    }

    @Override
    public String toString() {
        return "Clasificacion{" + "id=" + id + ", puntos=" + puntosEquipo  + ", liga=" + liga + '}';
    }
    
    @Override
    public boolean equals(Object o){
        boolean b = false;
        if(o instanceof Clasificacion && o != null){
            Clasificacion cla = (Clasificacion) o;
            if(cla.getId().equals(this.id)){
                b=true;
            }
        }
        return b;
    }
}
