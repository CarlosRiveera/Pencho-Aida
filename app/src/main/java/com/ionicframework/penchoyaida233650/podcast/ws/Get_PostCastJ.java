package com.ionicframework.penchoyaida233650.podcast.ws;

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
import com.ionicframework.penchoyaida233650.db.DetallePodcastPrincipal;
import com.ionicframework.penchoyaida233650.db.DetallePodcastPrincipalDao;
import com.ionicframework.penchoyaida233650.db.Postcast_Principales;
import com.ionicframework.penchoyaida233650.db.Postcast_PrincipalesDao;

/**
 * Created by Santiago on 19/05/2016.
 */
public class Get_PostCastJ {

    public String errorCode, strError;
    BufferedReader in = null;
    String url = "http://penchoyaida.com/dev/cast20.php";
    public int error;
    int cont = 0;
    public String  nic, token;

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

    public int Get_PostCastJ(Context context) {
        try {
            url = context.getResources().getString(R.string.url)+"/dev/cast20.php";
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "penchoaida.sqlite", null);
            SQLiteDatabase db  = helper.getWritableDatabase();
            DaoMaster daoMaster = new DaoMaster(db);
            DaoSession daoSession = daoMaster.newSession();
            Postcast_PrincipalesDao postcast_principalesDao = daoSession.getPostcast_PrincipalesDao();
            DetallePodcastPrincipalDao detallePodcastPrincipalDao = daoSession.getDetallePodcastPrincipalDao();

            HttpClient client = new DefaultHttpClient(timeOut(30000, 30000));

            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
                    "android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            Log.i("url", "" + url);

            request.setURI(new URI(url));

            HttpResponse response = client.execute(request);
            Log.i("response", ""+response);
            in = new BufferedReader(new InputStreamReader(response.getEntity()
                    .getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            JSONObject jsonObject = new JSONObject(page);
            Log.i("page",""+jsonObject);
            JSONObject msgJson = jsonObject.getJSONObject("response");
            error       = msgJson.getInt("errorCode");
           // strError    = msgJson.getString("errorMessage");
            postcast_principalesDao.deleteAll();
            detallePodcastPrincipalDao.deleteAll();
            if (error == 0) {
                JSONObject podcast = msgJson.getJSONObject("msg");


                TituloGenral= podcast.getString("author");
                DescripcionGeneral= podcast.getString("description");
                urlImagen = podcast.getString("image");

                DetallePodcastPrincipal detallePodcastPrincipal = new DetallePodcastPrincipal();
                detallePodcastPrincipal.setTitulo(TituloGenral);
                detallePodcastPrincipal.setSegundoTitulo("");
                DescripcionGeneral = DescripcionGeneral.replaceAll("<br />","");
                detallePodcastPrincipal.setDescripcion(DescripcionGeneral);
                detallePodcastPrincipal.setImagenPodcast(urlImagen);
                detallePodcastPrincipalDao.insertInTx(detallePodcastPrincipal);
                JSONArray ListaPodcastEspeciales = podcast.getJSONArray("podcasts");
                for (int i = 0; i < ListaPodcastEspeciales.length(); i++) {
                        JSONObject json_data4 = ListaPodcastEspeciales.getJSONObject(i);
                        itemsName.addElement(json_data4.getString("title"));
                        itemsdescription.addElement(json_data4.getString("description"));
                        itemslink.addElement(json_data4.getString("link"));
                        itemspubDate.addElement(json_data4.getString("pubdate"));
                        itemsitunesauthor.addElement(json_data4.getString("author"));

                        Postcast_Principales postcast_principales = new Postcast_Principales();
                        postcast_principales.setTitle(itemsName.get(i));
                        postcast_principales.setDescription(itemsdescription.get(i));
                        String imagen = itemslink.get(i);
                        String urlImagenDetallePodcast = imagen.replace("http://www.penchoyaida.com/audios/programas/","http://www.penchoyaida.com/imgpodcastpya/");
                        String ImagenTerminacion = urlImagenDetallePodcast.replace(".mp3",".jpg");

                        postcast_principales.setLink(itemslink.get(i));
                        postcast_principales.setImagen(ImagenTerminacion);
                        postcast_principales.setPubDate(itemspubDate.get(i));
                        postcast_principales.setSubtitle("");
                        postcast_principales.setSummary("");
                        postcast_principales.setDuration("");
                        postcast_principales.setAuthor(itemsitunesauthor.get(i));
                        postcast_principales.setKeywords("");
                        postcast_principales.setExplicit("");
                        postcast_principales.setBlock("");
                        postcast_principalesDao.insertInTx(postcast_principales);

                }

                    /*idpublicidad.add(json_data1.getString("idpublicidad"));
                    imagen.add(json_data1.getString("imagen"));
                    estado.add(json_data1.getString("estado"));*/

                error = 0;
            } else if (error == 4) {
                error = 4;
            } else if (error == 5) {
                error = 5;
            }

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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally {

        }
        return error;
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
