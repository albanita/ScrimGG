/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.igalda.scrimgg.dom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.igalda.scrimgg.pers.*;

/**
 *
 * @author Alin Emanuel Banita
 */
public class Usuario implements Serializable {
    private String id;
    private String cuentaRiot;
    private String email;
    private int expScrim; // Valor entre 0 y 99. Al alcanzar 100, vuelve a 0.
    private String imgPath;
    private String nickName;
    private int nivelScrim;

    // para usuario nuevo que se da de alta
    public Usuario(String email, String nickName){
        this.email = email;
        this.nickName = nickName;
        this.id = "";
        this.cuentaRiot="";
        this.expScrim=0;
        this.imgPath="";
        this.nivelScrim =0;
        
    }

    // para un usuario que ya está dado de alta en la base de datos y será útil para la persistencia
    public Usuario(String id, String cuentaRiot, String email, int expScrim, String imgpath, String nickName, int nivelScrim){
        this.id = id;
        this.cuentaRiot = cuentaRiot;
        this.email=email;
        this.expScrim = expScrim;
        this.imgPath = imgpath;
        this.nickName=nickName;
        this.nivelScrim = nivelScrim;
    }

    public String getId(){
        return this.id;
    }

    public String getCuentaRiot(){
        return this.cuentaRiot;
    }
    public void setCuentaRiot(String cuentaRiot){
        this.cuentaRiot = cuentaRiot;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public int getExpScrim(){
        return this.expScrim;
    }
    public void setExpScrim(int expScrim){
        this.expScrim=expScrim;
    }

    public String getImgPath(){
        return this.imgPath;
    }
    public void setImgPath(String imgPath){
        this.imgPath=imgPath;
    }

    public String getNickName(){
        return this.nickName;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }

    public int getNivelScrim(){
        return this.nivelScrim;
    }
    public void setNivelScrim(int nivelScrim){
        this.nivelScrim = nivelScrim;
    }

    public String toString(){
        return "ID: " + this.getId() + " Cuenta Riot: " + this.getCuentaRiot() + " Email: " + this.getEmail() + " ExpScrim: " + this.getExpScrim() + " ImgPath: " + this.getImgPath() +
                " NickName: " + this.getNickName() + " NivelScrim: " + this.getNivelScrim() + "\n";
    }
}
