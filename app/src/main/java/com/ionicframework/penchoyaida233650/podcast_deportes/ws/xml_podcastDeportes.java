package com.ionicframework.penchoyaida233650.podcast_deportes.ws;

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
import java.util.Vector;


import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.db.DaoMaster;
import com.ionicframework.penchoyaida233650.db.DaoSession;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.DetallePodcastPrincipalDeportes;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.DetallePodcastPrincipalDeportesDao;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.PostcadDeportes;
import com.ionicframework.penchoyaida233650.podcast_deportes.db.PostcadDeportesDao;

import static org.json.XML.toJSONObject;

/**
 * Created by Alex on 16/05/2016.
 */
public class xml_podcastDeportes {
    Context contexto;
    public Vector<String> title = new Vector<String>();
    public Vector<String> content = new Vector<String>();
    public Vector<String> link = new Vector<String>();
    public Vector<String> pubDate = new Vector<String>();
    public xml_podcastDeportes(Context context){
        contexto = context;
    }
    public String resultadoactiveStream ;
    BufferedReader in = null;

    String url = "http://penchoyaida.com/dev/castDep20.php";
    public int error, errorcode;
    public void Get_buscar(Context context){
        url = context.getResources().getString(R.string.url)+"/dev/castDep20.php";
        try {
            HttpClient client = new DefaultHttpClient(timeOut(30000,30000));
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            System.out.println("URL "+url);
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
            Log.i("PAGE", "" + page);
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(contexto, "penchoaida.sqlite", null);
            SQLiteDatabase db  = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            PostcadDeportesDao postcastDao = daoSession.getPostcadDeportesDao();
            DetallePodcastPrincipalDeportesDao detallePodcastDao = daoSession.getDetallePodcastPrincipalDeportesDao();
            JSONObject msgJson = jsonObject.getJSONObject("response");

            errorcode = msgJson.getInt("errorCode");
            if(errorcode==0){
                JSONObject podcast = msgJson.getJSONObject("msg");
                detallePodcastDao.deleteAll();
                DetallePodcastPrincipalDeportes detallePodcast = new DetallePodcastPrincipalDeportes();
                detallePodcast.setTitulo("Pencho & Aida");
                detallePodcast.setSegundoTitulo("Podcast Exclusivos");
                detallePodcast.setDescripcion(podcast.getString("description"));
                detallePodcast.setImagenPodcast(podcast.getString("image"));
                detallePodcastDao.insertInTx(detallePodcast);
                JSONArray ListaPodcastEspeciales = podcast.getJSONArray("podcasts");
                postcastDao.deleteAll();
                for (int i = 0; i<ListaPodcastEspeciales.length(); i++){
                    JSONObject jsonData = ListaPodcastEspeciales.getJSONObject(i);
                    PostcadDeportes postcast = new PostcadDeportes();
                    postcast.setTitle(jsonData.getString("title"));
                    postcast.setDescription(jsonData.getString("description"));
                    //Obtener imagen
                    String imagen = jsonData.getString("link");
                    String urlImagenDetallePodcast = imagen.replace("http://www.penchoyaida.com/audios/deportes/","http://www.penchoyaida.com/imgpodcastpya/");
                    String ImagenTerminacion = urlImagenDetallePodcast.replace(".mp3",".jpg");
                    postcast.setLink(jsonData.getString("link"));
                    postcast.setImagen("http://www.penchoyaida.com/imgpodcastpya/Pencho_y_Aida_-_Deportes_Palomo.jpg");
                    postcast.setPubDate(jsonData.getString("pubdate"));
                    postcast.setAuthor(jsonData.getString("author"));
                    postcastDao.insertInTx(postcast);
                }
                error = 3;
            }else{
                error = 5;
            }


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
