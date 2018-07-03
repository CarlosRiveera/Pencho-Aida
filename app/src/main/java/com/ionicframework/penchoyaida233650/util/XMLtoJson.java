package com.ionicframework.penchoyaida233650.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.db.DetallePodcast;
import com.ionicframework.penchoyaida233650.db.DetallePodcastDao;
import com.ionicframework.penchoyaida233650.db.Postcast;
import com.ionicframework.penchoyaida233650.db.PostcastDao;

import static org.json.XML.toJSONObject;

/**
 * Created by Todociber on 10/05/2016.
 */
public class XMLtoJson {

   public Context contexto;
    public XMLtoJson(Context context){
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
        PostcastDao postcastDao = daoSession.getPostcastDao();
        DetallePodcastDao detallePodcastDao = daoSession.getDetallePodcastDao();
        JSONObject jsonObject = toJSONObject(jsonobtenido);
        String jsonpuro = jsonobtenido;
        JSONObject rss=   jsonObject.getJSONObject("rss");
        JSONObject channel = rss.getJSONObject("channel");
         try {
             TituloGenral= channel.getString("title");
             DescripcionGeneral= channel.getString("description");
             lastBuild=  channel.getString("lastBuildDate");
             JSONObject imgen =  channel.getJSONObject("image");
             urlImagen=  imgen.getString("url");
             detallePodcastDao.deleteAll();
             DetallePodcast detallePodcast = new DetallePodcast();
             String[] Titulos = TituloGenral.split("-");
             detallePodcast.setTitulo(Titulos[0]);
             detallePodcast.setSegundoTitulo(Titulos[1]);
             detallePodcast.setDescripcion(DescripcionGeneral);
             detallePodcast.setImagenPodcast(urlImagen);
             detallePodcastDao.insertInTx(detallePodcast);
        }catch (Exception e){

        }
        JSONArray item = channel.getJSONArray("item");
        int nvueltas =item.length();
        if(nvueltas>20){
            nvueltas=20;
        }
            postcastDao.deleteAll();
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
                Postcast postcast = new Postcast();
                postcast.setTitle(itemsName.get(i));
                postcast.setDescription(itemsdescription.get(i));
                //Obtener imagen
                String imagen = itemslink.get(i);
                String urlImagenDetallePodcast = imagen.replace("http://www.penchoyaida.com/audios/","http://www.penchoyaida.com/imgpodcastpya/");
                String ImagenTerminacion = urlImagenDetallePodcast.replace(".mp3",".jpg");
                postcast.setLink(itemslink.get(i));
                postcast.setImagen(ImagenTerminacion);
                postcast.setPubDate(itemspubDate.get(i));
                postcast.setSubtitle(itemsitunessubtitle.get(i));
                postcast.setSummary(itemsitunessummary.get(i));
                postcast.setDuration(itemsitunesduration.get(i));
                postcast.setAuthor(itemsitunesauthor.get(i));
                postcast.setKeywords(itemsituneskeywords.get(i));
                postcast.setExplicit(itemsitunesexplicit.get(i));
                postcast.setBlock(itemsitunesblock.get(i));
                postcastDao.insertInTx(postcast);
            }catch (Exception e){

            }



        }
        Log.v("JSON45 ", channel.toString());
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
