package com.ionicframework.penchoyaida233650.play.ws;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

/**
 * Created by Santiago on 11/05/2016.
 */
public class GET_Especiales {


    public String errorCode, strError;
    BufferedReader in = null;
    String url = "getTaxis";
    public int error;
    int cont = 0;
    public String id_usuario, nic, token;
    String latitud;
    String longitud;



    public Vector<String> latitude = new Vector<String>();

    public Vector<String> longitude = new Vector<String>();
    public Vector<String> distancia = new Vector<String>();



    public int GET_Especiales(Context context) {

        try {
            // String metodo = context.getResources().getString(R.string.url);
            HttpClient client = new DefaultHttpClient(timeOut(5000, 5000));

            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
                    "android");
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json; charset=utf-8");
            Log.i("url", "" + "http://www.penchoyaida.com/podcast/rss_itunes_especiales.xml");

            request.setURI(new URI("http://www.penchoyaida.com/podcast/rss_itunes_especiales.xml"));

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
            Log.i("page",""+sb.toString());
            String page = sb.toString();

            JSONObject jsonObject = XML.toJSONObject(page);
            Log.i("page",""+jsonObject);

            if (error == 0) {


                error = 0;
            } else if (error == 4) {
                error = 4;
            } else if (error == 5) {
                error = 5;
            }

        } catch (ClientProtocolException e) {
            error = 5;
            e.printStackTrace();
        } catch (ConnectTimeoutException e){
            error = 5;
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
        } catch (RuntimeException e){
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
