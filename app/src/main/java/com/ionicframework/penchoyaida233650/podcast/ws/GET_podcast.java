package com.ionicframework.penchoyaida233650.podcast.ws;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.db.DetallePodcastPrincipal;
import com.ionicframework.penchoyaida233650.db.DetallePodcastPrincipalDao;
import com.ionicframework.penchoyaida233650.db.Postcast_Principales;
import com.ionicframework.penchoyaida233650.db.Postcast_PrincipalesDao;

import static org.json.XML.toJSONObject;

/**
 * Created by Alex on 12/05/2016.
 */
public class GET_podcast {




    public Context contexto;
    public GET_podcast(Context context){
        contexto = context;
    }
    public String TituloGenral,DescripcionGeneral,lastBuild, urlImagen;


    public Vector<String> itemsName = new Vector<String>();
    public Vector<String> itemsdescription = new Vector<String>();
    public Vector<String> itemslink = new Vector<String>();
    public Vector<String> itemspubDate = new Vector<String>();
    public Vector<String> itemsitunessummary = new Vector<String>();
    public Vector<String> itemsitunessubtitle = new Vector<String>();
    public Vector<String> itemsitunesduration = new Vector<String>();
    public Vector<String> itemsitunesauthor = new Vector<String>();
    public Vector<String> itemsituneskeywords = new Vector<String>();
    public Vector<String> itemsitunesexplicit = new Vector<String>();
    public Vector<String> itemsitunesblock = new Vector<String>();


    public void parseador(String jsonobtenido )throws JSONException {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(contexto, "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        Postcast_PrincipalesDao postcast_principalesDao = daoSession.getPostcast_PrincipalesDao();
        DetallePodcastPrincipalDao detallePodcastPrincipalDao = daoSession.getDetallePodcastPrincipalDao();

        JSONObject jsonObject = toJSONObject(jsonobtenido);

        JSONObject rss=   jsonObject.getJSONObject("rss");
        JSONObject channel = rss.getJSONObject("channel");
        try {



            TituloGenral= channel.getString("title");
            DescripcionGeneral= channel.getString("description");
            lastBuild=  channel.getString("lastBuildDate");
            JSONObject imgen =  channel.getJSONObject("image");
            urlImagen=  imgen.getString("url");
            detallePodcastPrincipalDao.deleteAll();
            DetallePodcastPrincipal detallePodcastPrincipal = new DetallePodcastPrincipal();
           // String[] Titulos = TituloGenral.split("-");
            detallePodcastPrincipal.setTitulo(TituloGenral);
            detallePodcastPrincipal.setSegundoTitulo("");
           DescripcionGeneral = DescripcionGeneral.replaceAll("<br/>","");
            detallePodcastPrincipal.setDescripcion(DescripcionGeneral);
            detallePodcastPrincipal.setImagenPodcast(urlImagen);
            detallePodcastPrincipalDao.insertInTx(detallePodcastPrincipal);
        }catch (Exception e){
            System.out.println("ERROR PARSEANDO DETALLES PRINCIPAL ");
        }
        JSONArray item = channel.getJSONArray("item");
        int nvueltas =item.length();
        if(nvueltas>20){
            nvueltas=20;
        }
        postcast_principalesDao.deleteAll();
        for (int i = 0; i<nvueltas; i++){
            try{
                    JSONObject itemElement = item.getJSONObject(i);
                itemsName.addElement(itemElement.getString("title"));
                itemsdescription.addElement(itemElement.getString("description"));
                itemslink.addElement(itemElement.getString("link"));
                itemspubDate.addElement(itemElement.getString("pubDate"));
                itemsitunessubtitle.addElement(itemElement.getString("itunes:subtitle"));
                itemsitunessummary.addElement(itemElement.getString("itunes:summary"));
                itemsitunesduration.addElement(itemElement.getString("itunes:duration"));
                itemsitunesauthor.addElement(itemElement.getString("itunes:author"));
                itemsituneskeywords.addElement(itemElement.getString("itunes:keywords"));
                itemsitunesexplicit.addElement(itemElement.getString("itunes:explicit"));
                itemsitunesblock.addElement(itemElement.getString("itunes:block"));
                Postcast_Principales postcast_principales = new Postcast_Principales();
                postcast_principales.setTitle(itemsName.get(i));
                postcast_principales.setDescription(itemsdescription.get(i));
                //Obtener imagen
                String imagen = itemslink.get(i);
                String urlImagenDetallePodcast = imagen.replace("http://www.penchoyaida.com/audios/","http://www.penchoyaida.com/imgpodcastpya/");
                String ImagenTerminacion = urlImagenDetallePodcast.replace(".mp3",".jpg");
                postcast_principales.setLink(itemslink.get(i));
                postcast_principales.setImagen(ImagenTerminacion);
                postcast_principales.setPubDate(itemspubDate.get(i));
                postcast_principales.setSubtitle(itemsitunessubtitle.get(i));
                postcast_principales.setSummary(itemsitunessummary.get(i));
                postcast_principales.setDuration(itemsitunesduration.get(i));
                postcast_principales.setAuthor(itemsitunesauthor.get(i));
                postcast_principales.setKeywords(itemsituneskeywords.get(i));
                postcast_principales.setExplicit(itemsitunesexplicit.get(i));
                postcast_principales.setBlock(itemsitunesblock.get(i));
                postcast_principalesDao.insertInTx(postcast_principales);
            }catch (Exception e){

            }
        }
//        Log.v("JSON12 ", channel.toString());
        // System.out.println("JSON45" +itemsName.toString());

    }

    public static String convertToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
}


