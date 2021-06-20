package com.igalda.scrimgg.dom;

import java.io.Serializable;

public class Equipo implements Serializable {

    private String idCapitan;
    private String tid; // Este es el nombre del equipo también.
    private String imgPath;
    private privacidadEquipo tipo;

    /*
        Constructor para nuevos equipos, poniendo los atributos generales a 0
     */
    public Equipo(String idCapitan, String tid) {
        this.tid = tid;
        this.idCapitan = idCapitan;
        this.imgPath = "";
        this.tipo = privacidadEquipo.PRIVADO;
    }

    public Equipo(String idUsuarioCapitan, String idEquipo, String imagePath, privacidadEquipo tipoPrivacidad) {
        this.idCapitan = idUsuarioCapitan;
        this.tid = idEquipo;
        this.imgPath = imagePath;
        this.tipo = tipoPrivacidad;
    }

    public String getIdCapitan(){
        return this.idCapitan;
    }
    public void setIdCapitan(String idCapitan){
        this.idCapitan=idCapitan;
    }

    public String getTid(){
        return this.tid;
    }
    public void setTid(String tid){
        this.tid = tid;
    }

    public String getImgPath(){
        return this.imgPath;
    }
    public void setImgPath(String imgPath){
        this.imgPath=imgPath;
    }

    public privacidadEquipo getTipo(){
        return this.tipo;
    }
    public void setTipo(privacidadEquipo tipo){
        this.tipo=tipo;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "idCapitan='" + idCapitan + '\'' +
                ", id='" + tid + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
