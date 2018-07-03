package com.ionicframework.penchoyaida233650.podcast.ws;

import android.content.Context;
import android.util.Log;

import com.ionicframework.penchoyaida233650.R;

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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Vector;

/**
 * Created by Alex on 11/05/2016.
 */
public class Get_PostcatBuscadosPrincipal {
    public String textoBuscado;
    Context contexto;
    public Vector<String> title = new Vector<String>();
    public Vector<String> content = new Vector<String>();
    public Vector<String> link = new Vector<String>();
    public Vector<String> pubDate = new Vector<String>();



    public Get_PostcatBuscadosPrincipal(Context context, String texto){
        contexto = context;
        textoBuscado = texto;
    }
    public String resultadoactiveStream ;
    BufferedReader in = null;

    String url = "http://penchoyaida.fm/dev/podcast.php?q=";
    public int error;
    public void Get_buscar(Context context){
        url = context.getResources().getString(R.string.url)+"/dev/podcast.php?q=";
        try {
            textoBuscado = URLEncoder.encode(textoBuscado, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpClient client = new DefaultHttpClient(timeOut(5000,5000));
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            request.setURI(new URI(url+textoBuscado));
            HttpResponse response = client.execute(request);
            System.out.println("URL "+url+textoBuscado);

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            JSONArray jsonObject = new JSONArray(page);

            Log.i("PAGE", "" + page);
            for (int i = 0; i<jsonObject.length(); i++){
                JSONObject jsonData = jsonObject.getJSONObject(i);
                title.addElement( jsonData.getString("title"));
               content.addElement( jsonData.getString("content"));
               link.addElement( jsonData.getString("link"));
                pubDate.addElement(jsonData.getString("pubDate"));
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
            error = 1;
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
