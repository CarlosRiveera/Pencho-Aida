package com.ionicframework.penchoyaida233650.play.db;

/**
 * Created by Santiago on 11/05/2016.
 */
public class DBEstado {

    Long idestado;
    String estado;
    String detalle;

    public DBEstado(Long idestado, String estado, String detalle) {
        this.idestado = idestado;
        this.estado = estado;
        this.detalle = detalle;
    }

    public Long getIdestado() {
        return idestado;
    }

    public void setIdestado(Long idestado) {
        this.idestado = idestado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
