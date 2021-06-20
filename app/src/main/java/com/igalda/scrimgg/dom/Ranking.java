/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author igjorque
 */
public class Ranking implements Serializable {
    private String temporada;
    private List<Usuario> listado;

    public Ranking() {
    }

    public Ranking(String temporada, List<Usuario> listado) {
        this.temporada = temporada;
        this.listado = listado;
    }

    public List<Usuario> getListado() {
        return listado;
    }

    public void setListado(List<Usuario> listado) {
        this.listado = listado;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    @Override
    public String toString() {
        return "Ranking{" + "temporada=" + temporada + '}';
    }

    @Override
    public boolean equals(Object obj) {
        boolean b = false;
        if(obj instanceof Ranking){
            Ranking r = (Ranking) obj;
            if(r.getTemporada().equals(this.temporada)){
                b=true;
            }
        }
        return b;
    }
    
    
}
