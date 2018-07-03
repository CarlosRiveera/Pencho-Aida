package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;

import de.greenrobot.daogenerator.Schema;

public class Main {
    public  static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "imoves.com.panchoaida.db");
        schema.enableKeepSectionsByDefault();
        createDataBase(schema);
        DaoGenerator generador = new DaoGenerator();
        generador.generateAll(schema,args[0]);
    }


    private static void createDataBase(Schema schema) {

            Entity Portada = schema.addEntity("Portada");
        Portada.addIdProperty();
        Portada.addStringProperty("song");
        Portada.addStringProperty("artist");
        Portada.addStringProperty("album");
        Portada.addStringProperty("albumArt");

        Entity Postcast = schema.addEntity("Postcast");
        Postcast.addIdProperty();
        Postcast.addStringProperty("title");
        Postcast.addStringProperty("description");
        Postcast.addStringProperty("link");
        Postcast.addStringProperty("imagen");
        Postcast.addStringProperty("enclosure");
        Postcast.addStringProperty("guid");
        Postcast.addStringProperty("pubDate");
        Postcast.addStringProperty("subtitle");
        Postcast.addStringProperty("summary");
        Postcast.addStringProperty("duration");
        Postcast.addStringProperty("author");
        Postcast.addStringProperty("keywords");
        Postcast.addStringProperty("explicit");
        Postcast.addStringProperty("block");


        Entity detallePodcast = schema.addEntity("DetallePodcast");
        detallePodcast.addIdProperty();
        detallePodcast.addStringProperty("Titulo");
        detallePodcast.addStringProperty("SegundoTitulo");
        detallePodcast.addStringProperty("ImagenPodcast");
        detallePodcast.addStringProperty("Descripcion");


        Entity detallePodcAstPrincipal = schema.addEntity("DetallePodcastPrincipal");
        detallePodcAstPrincipal.addIdProperty();
        detallePodcAstPrincipal.addStringProperty("Titulo");
        detallePodcAstPrincipal.addStringProperty("SegundoTitulo");
        detallePodcAstPrincipal.addStringProperty("ImagenPodcast");
        detallePodcAstPrincipal.addStringProperty("Descripcion");


        Entity busquedasTemporales = schema.addEntity("BusquedasTemporales");
        busquedasTemporales.addIdProperty();
        busquedasTemporales.addStringProperty("Titulo");
        busquedasTemporales.addStringProperty("content");
        busquedasTemporales.addStringProperty("link");
        busquedasTemporales.addStringProperty("pubdate");

        Entity PostcastN = schema.addEntity("Postcast_Principales");
        PostcastN.addIdProperty();
        PostcastN.addStringProperty("title");
        PostcastN.addStringProperty("description");
        PostcastN.addStringProperty("link");
        PostcastN.addStringProperty("imagen");
        PostcastN.addStringProperty("enclosure");
        PostcastN.addStringProperty("guid");
        PostcastN.addStringProperty("pubDate");
        PostcastN.addStringProperty("subtitle");
        PostcastN.addStringProperty("summary");
        PostcastN.addStringProperty("duration");
        PostcastN.addStringProperty("author");
        PostcastN.addStringProperty("keywords");
        PostcastN.addStringProperty("explicit");
        PostcastN.addStringProperty("block");


    }





}
