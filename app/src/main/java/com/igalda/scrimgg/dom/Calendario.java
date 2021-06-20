package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Date;
import java.util.List;

public class Calendario implements Serializable {
    private HashMap<String, Date> fechas;

    private List<Liga> ligas;
    private List<Torneo> torneos;

    public Calendario(){

    }

    public Calendario(List<Liga> ligas, List<Torneo> torneos){
        /*this.enfrentamientos=enfrentamientos;
        this.ligas=ligas;
        this.torneos=torneos;

        for(int i=0; i<this.ligas.size(); i++){
            this.fechas.put(this.ligas.get(i).getId(), this.ligas.get(i).getFechaInicio());
            this.fechas.put(this.ligas.get(i).getId(), this.ligas.get(i).getFechaFinal());
        }

        for(int i=0; i<this.torneos.size(); i++){
            Torneo t = this.torneos.get(i);
            this.fechas.put(t.getId(), t.getFechaInicio());
            this.fechas.put(t.getId(), t.getFechaFinal());
        }

        for(int i=0; i<this.enfrentamientos.size(); i++){
            Enfrentamiento e = this.enfrentamientos.get(i);
            this.fechas.put(e.getId(), e.getFecha());
        }*/
    }

    public List<Liga> getLigas(){return this.ligas;}
    public List<Torneo> getTorneos(){return this.torneos;}

    public HashMap<String, Date> getFechas() {
        return this.fechas;
    }

    public void setFechas(HashMap<String, Date> fechas){
        this.fechas=fechas;
    }

}
