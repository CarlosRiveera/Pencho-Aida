package com.ionicframework.penchoyaida233650.db;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table POSTCAST.
 */
public class Postcast {

    private Long id;
    private String title;
    private String description;
    private String link;
    private String imagen;
    private String enclosure;
    private String guid;
    private String pubDate;
    private String subtitle;
    private String summary;
    private String duration;
    private String author;
    private String keywords;
    private String explicit;
    private String block;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Postcast() {
    }

    public Postcast(Long id) {
        this.id = id;
    }

    public Postcast(Long id, String title, String description, String link, String imagen, String enclosure, String guid, String pubDate, String subtitle, String summary, String duration, String author, String keywords, String explicit, String block) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.imagen = imagen;
        this.enclosure = enclosure;
        this.guid = guid;
        this.pubDate = pubDate;
        this.subtitle = subtitle;
        this.summary = summary;
        this.duration = duration;
        this.author = author;
        this.keywords = keywords;
        this.explicit = explicit;
        this.block = block;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getExplicit() {
        return explicit;
    }

    public void setExplicit(String explicit) {
        this.explicit = explicit;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}