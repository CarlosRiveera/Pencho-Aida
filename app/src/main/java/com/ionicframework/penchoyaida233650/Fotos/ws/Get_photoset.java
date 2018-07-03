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

import com.ionicframework.penchoyaida233650.Fotos.db.Photoset;
import com.ionicframework.penchoyaida233650.Fotos.db.PhotosetDao;
import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;


/**
 * Created by Alex on 17/05/2016.
 */
public class Get_photoset {

    public  String idAlbum,primary,secret,server,farm,photos,title,description,date_create,date_update;

    //public  String song,artist,album, albumArt;
    BufferedReader in = null;

    public int error;
    public void Get_photoset(Context context){
        String url = context.getString(R.string.JSONflickrAlbumPrincipal);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PhotosetDao photosetDao = daoSession.getPhotosetDao();
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
            JSONObject jsonObject1 = jsonObject.getJSONObject("photosets");
//            Log.i("PAGE", "" + page);
            JSONArray msgJson = jsonObject1.getJSONArray("photoset");
            photosetDao.deleteAll();

            for (int i =0; i<msgJson.length(); i++){
                JSONObject json_data = msgJson.getJSONObject(i);
                Photoset photoset = new Photoset();
                photoset.setIdAlbum(json_data.getString("id"));
                photoset.setPrimaryPhoto(json_data.getString("primary"));
                photoset.setSecret(json_data.getString("secret"));
                photoset.setServer(json_data.getString("server"));
                photoset.setFarm(json_data.getString("farm"));
                photoset.setPhotos(json_data.getString("photos"));
                JSONObject title =   json_data.getJSONObject("title");
                String Titulo =  title.getString("_content");
                photoset.setTitle(Titulo);
                JSONObject descripcion = json_data.getJSONObject("description");
                String descripcionImagen = descripcion.getString("_content");
                photoset.setDescription(descripcionImagen);
                photoset.setDate_create(json_data.getString("date_create"));
                photoset.setDate_update(json_data.getString("date_update"));
                String urlImagenALbum  = "https://farm"+json_data.getString("farm")+".staticflickr.com/"+json_data.getString("server")+"/"+json_data.getString("primary")+"_"+json_data.getString("secret")+".jpg";
                photoset.setUrlImagenAlbum(urlImagenALbum);
                photosetDao.insertInTx(photoset);
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