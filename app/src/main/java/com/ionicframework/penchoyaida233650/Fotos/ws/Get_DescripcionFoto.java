package com.ionicframework.penchoyaida233650.Fotos.ws;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import com.ionicframework.penchoyaida233650.Fotos.db.PhotoDetalle;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoDetalleDao;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;


/**
 * Created by Alex on 17/05/2016.
 */
public class Get_DescripcionFoto {
    public  String idAlbum,primary,secret,server,farm,photos,title,description,date_create,date_update;

    //public  String song,artist,album, albumArt;
    BufferedReader in = null;
    public String descripcion;
    public int error;
    public void Get_DescripcionFoto(Context context, String idFoto){
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=24a9f619ce0be3645ea52d3a72ffe4c9&photo_id="+idFoto+"&user_id=138964743%40N07&format=json&nojsoncallback=1";
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PhotoDetalleDao photoDetalleDao = daoSession.getPhotoDetalleDao();
        try {
            HttpClient client = new DefaultHttpClient(timeOut(5000,5000));
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            JSONObject jsonObject = new JSONObject(page);
            JSONObject jsonObject1 = jsonObject.getJSONObject("photo");
             photoDetalleDao.deleteAll();

                //JSONObject json_data = jsonObject1.getJSONObject(i);
                PhotoDetalle photoDetalle = new PhotoDetalle();
                photoDetalle.setIdPhoto(jsonObject1.getString("id"));
                photoDetalle.setSecret(jsonObject1.getString("secret"));
                photoDetalle.setServer(jsonObject1.getString("server"));
                photoDetalle.setFarm(jsonObject1.getString("farm"));
                photoDetalle.setDateuploaded(jsonObject1.getString("dateuploaded"));
                photoDetalle.setOriginalsecret(jsonObject1.getString("originalsecret"));
                photoDetalle.setOriginalformat(jsonObject1.getString("originalformat"));
                JSONObject title = jsonObject1.getJSONObject("title");
                String titulo = title.getString("_content");
                photoDetalle.setTitle(titulo);
                JSONObject description = jsonObject1.getJSONObject("description");
                descripcion = description.getString("_content");
                photoDetalle.setDescription(descripcion);
                JSONObject dates = jsonObject1.getJSONObject("dates");
                String posted = dates.getString("posted");
                String taken = dates.getString("taken");
                String LastUpdate = dates.getString("lastupdate");
                photoDetalle.setPosted(posted);
                photoDetalle.setTaken(taken);
                photoDetalle.setLastupdate(LastUpdate);
                JSONObject urls = jsonObject1.getJSONObject("urls");
                JSONArray urlinterna = urls.getJSONArray("url");
               JSONObject urlCOntenida = urlinterna.getJSONObject(0);
                String link=   urlCOntenida.getString("_content");
               // String link = urlinterna.getString(1);
                photoDetalle.setUrl(link);
                photoDetalleDao.insertInTx(photoDetalle);


            error = 3;
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            error = 5;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            error = 5;
            e.printStackTrace();
        } catch (IOException e) {
            // ERROR DEL SOCKET TIMEOUT
            error = 5;
            e.printStackTrace();
        } catch (JSONException e) {
            // ERROR DEL JSON DOCTYPE!
            error = 5;
            e.printStackTrace();
        }finally{

        }
    }
    public HttpParams timeOut(int timeout, int timesocket) {
        HttpParams httpParameters = new BasicHttpParams();
        int timeoutConnection = timeout;
        HttpConnectionParams.setConnectionTimeout(httpParameters,
                timeoutConnection);
        int timeoutSocket = timesocket;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
        return httpParameters;
    }
}
