package com.ionicframework.penchoyaida233650.play.db;

/**
 * Created by Santiago on 11/05/2016.
 */
public class DBCancion {

    Long id;
    String song;
    String artist;
    String album;
    String albumart;

    public DBCancion(Long id, String song, String artist, String album, String albumart) {
        this.id = id;
        this.song = song;
        this.artist = artist;
        this.album = album;
        this.albumart = albumart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumart() {
        return albumart;
    }

    public void setAlbumart(String albumart) {
        this.albumart = albumart;
    }
}
