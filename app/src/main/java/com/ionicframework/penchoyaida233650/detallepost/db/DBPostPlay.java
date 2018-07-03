package com.ionicframework.penchoyaida233650.detallepost.db;

/**
 * Created by Santiago on 13/05/2016.
 */
public class DBPostPlay {

    Long id;
    String titulo;
    String link;
    String imagen;
    String artista;
    String subtitle;

    public DBPostPlay(Long id, String titulo, String link, String imagen, String artista, String subtitle) {
        this.id = id;
        this.titulo = titulo;
        this.link = link;
        this.imagen = imagen;
        this.artista = artista;
        this.subtitle = subtitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
