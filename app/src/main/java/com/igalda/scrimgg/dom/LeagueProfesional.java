package com.igalda.scrimgg.dom;

import java.io.Serializable;

public class LeagueProfesional implements Serializable {
    private String id;
    private String img_url;
    private String name;

    public LeagueProfesional(String id, String imgUrl, String name){
        this.id=id;
        this.img_url=imgUrl;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LeagueProfesional{" +
                "id='" + id + '\'' +
                ", img_url='" + img_url + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
