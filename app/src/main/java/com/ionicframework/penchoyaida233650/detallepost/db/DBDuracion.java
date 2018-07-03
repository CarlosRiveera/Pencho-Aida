package com.ionicframework.penchoyaida233650.detallepost.db;

/**
 * Created by Santiago on 13/05/2016.
 */
public class DBDuracion {

    Long id;
    String current;
    String duration;
    String progreso;
    String detalle;

    public DBDuracion(Long id, String current, String duration, String progreso, String detalle) {
        this.id = id;
        this.current = current;
        this.duration = duration;
        this.progreso = progreso;
        this.detalle = detalle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getProgreso() {
        return progreso;
    }

    public void setProgreso(String progreso) {
        this.progreso = progreso;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
