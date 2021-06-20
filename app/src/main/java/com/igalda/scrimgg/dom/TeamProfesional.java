package com.igalda.scrimgg.dom;

import java.io.Serializable;

public class TeamProfesional implements Serializable {
    private String id;
    private String acronym;
    private String imgUrl;
    private String location;
    private String name;

    public TeamProfesional(String id, String acronym, String imgUrl, String location, String name) {
        this.id = id;
        this.acronym = acronym;
        this.imgUrl = imgUrl;
        this.location = location;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeamProfesional{" +
                "id='" + id + '\'' +
                ", acronym='" + acronym + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
