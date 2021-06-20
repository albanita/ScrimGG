/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author igjorque
 */
public class Cuenta implements Serializable {
    private final String nombreCuenta;
    private String rankSolo;
    private String rankFlex;

    public Cuenta() {
        this.nombreCuenta = "";
        this.rankSolo = "";
        this.rankFlex = "";
    }

    public Cuenta(String nombreCuenta, String rankSolo, String rankFlex) {
        this.nombreCuenta = nombreCuenta;
        this.rankSolo = rankSolo;
        this.rankFlex = rankFlex;
    }

    public String getRankSolo() {
        return rankSolo;
    }

    public void setRankSolo(String rankSolo) {
        this.rankSolo = rankSolo;
    }

    public String getRankFlex() {
        return rankFlex;
    }

    public void setRankFlex(String rankFlex) {
        this.rankFlex = rankFlex;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "nombreCuenta=" + nombreCuenta + ", rankSolo=" + rankSolo + ", rankFlex=" + rankFlex + '}';
    }

    @Override
    public boolean equals(Object obj) {
        boolean b=false;
        if(obj instanceof Cuenta){
            Cuenta c = (Cuenta) obj;
            if(c.nombreCuenta.equals(this.nombreCuenta)){
                b=true;
            }
        }
        return b;
    }
    
    
    
    
}
