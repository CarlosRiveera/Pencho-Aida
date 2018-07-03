package com.ionicframework.penchoyaida233650.portada.ws;

/**
 * Created by Alex on 09/05/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



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
import com.ionicframework.penchoyaida233650.db.Portada;
import com.ionicframework.penchoyaida233650.db.PortadaDao;


/**
 * Created by Alex on 09/03/2016.
 */
public class Get_TraerPortada {

   public  String song,artist,album, albumArt;
    BufferedReader in = null;
    String url = "http://penchoyaida.fm/dev/search.php";
    public int error;
    public void Get_TraerPortada(Context context){
        url = context.getResources().getString(R.string.url)+"/dev/search.php";
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "shopping.sqlite", null);
        SQLiteDatabase db  = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        PortadaDao portadaDao = daoSession.getPortadaDao();
        try {
            HttpClient client = new DefaultHttpClient(timeOut(5000,5000));
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            Log.i("GET SHOPPING", url);
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");

            in.close();
            String page = sb.toString();
            JSONObject jsonObject = new JSONObject(page);
            Log.i("PAGE", "" + page);
            JSONArray msgJson = jsonObject.getJSONArray("data");
            for (int i =0; i<msgJson.length(); i++){
                JSONObject json_data = msgJson.getJSONObject(i);
                song = json_data.getString("song");
                artist = json_data.getString("artist");
                album = json_data.getString("album");
                albumArt = json_data.getString("albumArt");
                Portada portada = new Portada();
                portada.setId(Long.valueOf("1"));
                portada.setSong(song);
                portada.setArtist(artist);
                portada.setAlbum(album);
                portada.setAlbumArt(albumArt);
                portadaDao.insertOrReplace(portada);

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