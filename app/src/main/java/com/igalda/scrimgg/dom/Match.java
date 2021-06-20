package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.Date;

public class Match implements Serializable {
        private String id;
        private String idAzul;
        private String idRojo;
        private Date fecha;
        private String resultado;
        private matchType tipo;

    public Match(String id, String idAzul, String idRojo, Date fecha, String resultado, matchType tipo) {
        this.id = id;
        this.idAzul = idAzul;
        this.idRojo = idRojo;
        this.fecha = fecha;
        this.resultado = resultado;
        this.tipo = tipo;
    }


    public String getIdAzul() {
        return idAzul;
    }

    public String getIdRojo() {
        return idRojo;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getResultado() {
        return resultado;
    }

    public void setIdAzul(String idAzul) {
        this.idAzul = idAzul;
    }

    public void setIdRojo(String idRojo) {
        this.idRojo = idRojo;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public matchType getTipo() {
        return tipo;
    }

    public void setTipo(matchType tipo) {
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", idAzul='" + idAzul + '\'' +
                ", idRojo='" + idRojo + '\'' +
                ", fecha=" + fecha +
                ", resultado='" + resultado + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
