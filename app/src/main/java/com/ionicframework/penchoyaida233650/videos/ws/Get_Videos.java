package com.ionicframework.penchoyaida233650.videos.ws;

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

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.videos.db.DetalleVideo;
import com.ionicframework.penchoyaida233650.videos.db.DetalleVideoDao;


/**
 * Created by Alex on 18/05/2016.
 */
public class Get_Videos {

    public  String idAlbum,primary,secret,server,farm,photos,title,description,date_create,date_update;

    //public  String song,artist,album, albumArt;
    BufferedReader in = null;

    public int error;
    public void Get_photoset(Context context){
        String url = context.getString(R.string.urlVideos);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        DetalleVideoDao detalleVideoDao = daoSession.getDetalleVideoDao();

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
            JSONArray msgJson = jsonObject.getJSONArray("items");
            detalleVideoDao.deleteAll();
            for (int i =0; i<msgJson.length(); i++){
                JSONObject json_data = msgJson.getJSONObject(i);
                DetalleVideo detalleVideo = new DetalleVideo();
                detalleVideo.setEtag(json_data.getString("etag"));
                JSONObject videId = json_data.getJSONObject("id");
                String VideoID = videId.getString("videoId");
                detalleVideo.setKind(VideoID);
                JSONObject snippet = json_data.getJSONObject("snippet");
                detalleVideo.setPublishedAt(snippet.getString("publishedAt"));
                detalleVideo.setChannelId(snippet.getString("channelId"));
                detalleVideo.setTitle(snippet.getString("title"));
                detalleVideo.setDescription(snippet.getString("description"));
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                JSONObject defaultimg = thumbnails.getJSONObject("default");
                String defalutlimgURL = defaultimg.getString("url");
                detalleVideo.setDefaultImg(defalutlimgURL);
                JSONObject mediumimg = thumbnails.getJSONObject("medium");
                String mediumimgURL = mediumimg.getString("url");
                detalleVideo.setMedium(mediumimgURL);
                JSONObject higthimg = thumbnails.getJSONObject("high");
                String higtURL = higthimg.getString("url");
                detalleVideo.setHigh(higtURL);
                detalleVideo.setChannelTitle(snippet.getString("channelTitle"));
                detalleVideoDao.insertInTx(detalleVideo);
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