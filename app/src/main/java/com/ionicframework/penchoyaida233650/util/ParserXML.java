package com.ionicframework.penchoyaida233650.util;

/**
 * Created by Alex on 09/05/2016.
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Xml;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.db.Postcast;
import com.ionicframework.penchoyaida233650.db.PostcastDao;

public class ParserXML {


    public  Context contexto;
    public ParserXML(Context context){
        contexto = context;
    }



    // Namespace general. null si no existe
    private static final String ns = null;

    // Constantes del archivo Xml
    private static  final String EtiquetaPrincipal = "rss";
    private static final String EtiquetaContenedora = "channel";

    private static final String EtiquetaObjetoPostCast = "item";


    //Contenido de Objeto PostCast
    private static final String Etiquetatitle = "title";
    private static final String Etiquetadescription = "description";
    private static final String Etiquetalink = "link";
    private static final String Etiquetaenclosure = "enclosure";
    private static final String Etiquetaguid = "guid";
    private static final String EtiquetapubDate = "pubDate";

    private static  final String Prefijo ="itunes";
    private static final String Etiquetasubtitle = "itunes:subtitle";
    private static final String Etiquetasummary = "itunes:summary";
    private static final String Etiquetaduration = "itunes:duration";
    private static final String Etiquetaauthor = "itunes:author";
    private static final String Etiquetakeywords = "itunes:keywords";
    private static final String Etiquetaexplicit = "itunes:explicit";
    private static final String Etiquetablock= "itunes:block";


    public List<podCastXML> parsear(InputStream in) throws XmlPullParserException, IOException {

        try {
            XmlPullParser parser = Xml.newPullParser();

            XMLtoJson classXML= new XMLtoJson(contexto);
            try {
                classXML.parseador( parser.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null; 
        } finally {
            in.close();
        }
    }


    private List<podCastXML> leerItems(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<podCastXML> ListaItems = new ArrayList<podCastXML>();

        parser.require(XmlPullParser.START_TAG, ns, EtiquetaContenedora);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nombreEtiqueta = parser.getName();
            // Buscar etiqueta <Channel>
            if (nombreEtiqueta.equals(EtiquetaObjetoPostCast)) {
                ListaItems.add(leerHotel(parser));
            } else {
                saltarEtiqueta(parser);
            }
        }
        return ListaItems;
    }


    private podCastXML leerHotel(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, EtiquetaObjetoPostCast);
        String title= null;
        String description= null;
        String link= null;
        String enclosure= null;
        String guid= null;
        String pubDate= null;
        String subtitle= null;
        String summary= null;
        String duration= null;
        String author= null;
        String keywords= null;
        String explicit= null;
        String block = null;
        HashMap<String, String> valoracion = new HashMap<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch (name) {
                case Etiquetatitle:
                    title = leerTitle(parser);
                    break;
                case Etiquetadescription:
                    description = leerdescription(parser);
                    break;
                case Etiquetalink:
                    link = leerlink(parser);
                    break;
               /* case Etiquetaenclosure:
                    enclosure = leerenclosure(parser);
                    break;
                case Etiquetaguid:
                    guid = leerguid(parser);
                    break;
                case EtiquetapubDate:
                    pubDate = leerpubDate(parser);
                    break;
                case Etiquetasubtitle:
                    subtitle = leersubtitle(parser);
                    break;
                case Etiquetasummary:
                    summary = leersummary(parser);
                    break;
                case Etiquetaduration:
                    duration = leerduration(parser);
                    break;
                case Etiquetaauthor:
                    author = leerauthor(parser);
                    break;
                case Etiquetakeywords:
                    keywords = leerkeywords(parser);
                    break;
                case Etiquetaexplicit:
                    explicit = leerexplicit(parser);
                    break;
                case Etiquetablock:
                    block = leerblock(parser);
                    break;*/
                default:
                    saltarEtiqueta(parser);
                    break;
            }
        }

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(contexto, "shopping.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        PostcastDao postcastDao = daoSession.getPostcastDao();
        Postcast postcast = new Postcast();
        postcast.setTitle(title);
        postcast.setDescription(description);
        postcast.setLink(link);
        postcast.setEnclosure(enclosure);
        postcast.setGuid(guid);
        postcast.setPubDate(pubDate);
        postcast.setSubtitle(subtitle);
        postcast.setSummary(summary);
        postcast.setDuration(duration);
        postcast.setAuthor(author);
        postcast.setKeywords(keywords);
        postcast.setExplicit(explicit);
        postcast.setBlock(block);
        postcastDao.insertInTx(postcast);

        return new podCastXML(
                title,
                description,
                link,
                enclosure,
                guid,
                pubDate,
                subtitle,
                summary,
                duration,
                author,
                keywords,
                explicit,
                block);
    }




    // Procesa la etiqueta <Title>
    private String leerTitle (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetatitle);
        String leerTitle = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetatitle);
        return leerTitle;
    }

    // Procesa la etiqueta <Description>
    private String leerdescription (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetadescription);
        String leerdescription = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetadescription);
        return leerdescription;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerlink (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetalink);
        String leerlink = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetalink);
        return leerlink;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerenclosure (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetaenclosure);
        String leerenclosure = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetaenclosure);
        return leerenclosure;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerguid (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetaguid);
        String leerguid = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetaguid);
        return leerguid;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerpubDate (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, EtiquetapubDate);
        String pubDate = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, EtiquetapubDate);
        return pubDate;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leersubtitle (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetasubtitle);
        String leersubtitle = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetasubtitle);
        return leersubtitle;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leersummary (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetasummary);
        String leersummary = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetasummary);
        return leersummary;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerduration (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetaduration);
        String leerduration = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetaduration);
        return leerduration;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerauthor(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetaauthor);
        String leerauthor = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetaauthor);
        return leerauthor;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerkeywords (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetakeywords);
        String leerkeywords = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetakeywords);
        return leerkeywords;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerexplicit (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetaexplicit);
        String leerexplicit = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetaexplicit);
        return leerexplicit;
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private String leerblock (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, Etiquetablock);
        String leerblock = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, Etiquetablock);
        return leerblock;
    }

    // Obtiene el texto de los atributos
    private String obtenerTexto(XmlPullParser parser) throws IOException, XmlPullParserException {
        String resultado = "";
        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }
        return resultado;
    }

    // Salta aquellos objeteos que no interesen en la jerarquía XML.
    private void saltarEtiqueta(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
