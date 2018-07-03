package com.ionicframework.penchoyaida233650.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table DETALLE_PODCAST_PRINCIPAL.
 */
public class DetallePodcastPrincipal {

    private Long id;
    private String Titulo;
    private String SegundoTitulo;
    private String ImagenPodcast;
    private String Descripcion;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public DetallePodcastPrincipal() {
    }

    public DetallePodcastPrincipal(Long id) {
        this.id = id;
    }

    public DetallePodcastPrincipal(Long id, String Titulo, String SegundoTitulo, String ImagenPodcast, String Descripcion) {
        this.id = id;
        this.Titulo = Titulo;
        this.SegundoTitulo = SegundoTitulo;
        this.ImagenPodcast = ImagenPodcast;
        this.Descripcion = Descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getSegundoTitulo() {
        return SegundoTitulo;
    }

    public void setSegundoTitulo(String SegundoTitulo) {
        this.SegundoTitulo = SegundoTitulo;
    }

    public String getImagenPodcast() {
        return ImagenPodcast;
    }

    public void setImagenPodcast(String ImagenPodcast) {
        this.ImagenPodcast = ImagenPodcast;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}