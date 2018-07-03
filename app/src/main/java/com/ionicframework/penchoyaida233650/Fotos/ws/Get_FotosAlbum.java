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

import com.ionicframework.penchoyaida233650.Fotos.db.PhotoAlbum;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotoAlbumDao;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;


/**
 * Created by Alex on 17/05/2016.
 */
public class Get_FotosAlbum {
    public  String idAlbum,primary,secret,server,farm,photos,title,description,date_create,date_update;

    //public  String song,artist,album, albumArt;
    BufferedReader in = null;

    public int error;
    public void Get_FotosAlbum(Context context,String idAlbum){
        String url = "https://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=24a9f619ce0be3645ea52d3a72ffe4c9&photoset_id="+idAlbum+"&id&user_id=138964743%40N07&format=json&nojsoncallback=1";
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PhotoAlbumDao photoAlbumDao = daoSession.getPhotoAlbumDao();
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
            JSONObject jsonObject1 = jsonObject.getJSONObject("photoset");
//            Log.i("PAGE", "" + page);
            JSONArray msgJson = jsonObject1.getJSONArray("photo");
            photoAlbumDao.deleteAll();

            for (int i =0; i<msgJson.length(); i++){
                JSONObject json_data = msgJson.getJSONObject(i);
                PhotoAlbum photoAlbum = new PhotoAlbum();
                photoAlbum.setIdPhoto(json_data.getString("id"));
                photoAlbum.setSecret(json_data.getString("secret"));
                photoAlbum.setServer(json_data.getString("server"));
                photoAlbum.setFarm(json_data.getString("farm"));
                photoAlbum.setTitle(json_data.getString("title"));
                photoAlbum.setIspublic(json_data.getString("ispublic"));
                photoAlbum.setIdAlbum(idAlbum);
                String urlPhotoAlbum = "https://farm"+json_data.getString("farm")+".staticflickr.com/"+json_data.getString("server")+"/"+json_data.getString("id")+"_"+json_data.getString("secret")+".jpg";
                photoAlbum.setUrlImagenPhotoAlbum(urlPhotoAlbum);
                photoAlbumDao.insertInTx(photoAlbum);
            }
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