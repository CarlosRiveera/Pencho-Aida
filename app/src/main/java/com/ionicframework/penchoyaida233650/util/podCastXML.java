package com.ionicframework.penchoyaida233650.util;

/**
 * Created by Alex on 09/05/2016.
 */

import java.util.ArrayList;
import java.util.List;


public class podCastXML {
    public  String title;
    public  String description;
    public  String link;
    public  String enclosure;
    public  String guid;
    public  String pubDate;
    public  String subtitle;
    public  String summary;
    public  String duration;
    public  String author;
    public  String keywords;
    public  String explicit;
    public  String block;
    public static List<podCastXML> ITEMS = new ArrayList<>();




    public podCastXML(String title,
                      String description,
                      String link,
                      String enclosure,
                      String guid,
                      String pubDate,
                      String subtitle,
                      String summary,
                      String duration,
                      String author,
                      String keywords,
                      String explicit,
                      String block){
        this.title = title;
        this.description = description;
        this.link = description;
        this.link = link;
        this.enclosure = enclosure;
        this.guid = guid;
        this.pubDate = pubDate;
        this.subtitle = subtitle;
        this.subtitle = summary;
        this.duration = duration;
        this.author = author;
        this.keywords = keywords;
        this.explicit = explicit;
        this.block = block;

    }



    public  String getTitle(){
        return title;
    }
    public  String getDescription(){
        return description;
    }
    public  String getLink(){
        return link;
    }
    public  String getEnclosure(){
        return enclosure;
    }
    public  String getGuid(){
        return guid;
    }
    public  String getPubDate(){
        return pubDate;
    }
    public  String getSubtitle(){
        return subtitle;
    }
    public  String getSummary(){
        return summary;
    }
    public  String getDuration(){
        return duration;
    }
    public  String getAuthor(){
        return author;
    }
    public  String getKeywords(){
        return keywords;
    }
    public  String getExplicit(){
        return explicit;
    }
    public  String getBlock(){
        return block;
    }


}
