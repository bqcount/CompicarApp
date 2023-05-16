package com.example.compicar.models;

import java.util.Date;

public class Post {
    private String idPost;
    private String imagen;
    private String fecha;
    private String destino;
    private String description;
    private String idUser;


    public Post() {

    }

    public Post(String idPost, String imagen, String fecha, String destino, String description, String idUser) {
        this.idPost = idPost;
        this.imagen = imagen;
        this.fecha = fecha;
        this.destino = destino;
        this.description = description;
        this.idUser = idUser;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}