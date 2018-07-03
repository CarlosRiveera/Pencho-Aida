package com.ionicframework.penchoyaida233650.club.ws;

import android.content.Context;
import android.util.Log;

import com.ionicframework.penchoyaida233650.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by imoves on 12-15-15.
 */
public class PostClub {


    BufferedReader in = null;
    String url = "";
    public int result;
    String dispositivo;
    public String nombre,nacimiento,email,pais,profesion,premios,promociones,comentarios,telefono;
    public String idafiliado;

    public PostClub(String nombre,String nacimiento, String email,String pais,String profesion,String premios,String promociones,String comentarios,String telefono) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.email = email;
        this.pais = pais;
        this.profesion = profesion;
        this.premios = premios;
        this.promociones = promociones;
        this.comentarios = comentarios;
        this.telefono = telefono;
    }

    public int PostSolicitud(Context context){

        String WS = context.getResources().getString(R.string.url) + "/dev/clubForm";

        try {



            JSONObject jsonObj = new JSONObject();
            jsonObj.put("nombre",nombre);
            jsonObj.put("nacimiento",nacimiento);
            jsonObj.put("email",email);
            jsonObj.put("pais",pais);
            jsonObj.put("profesion",profesion);
            jsonObj.put("premios",premios);
            jsonObj.put("promociones",promociones);
            jsonObj.put("comentarios",comentarios);
            jsonObj.put("telefono",telefono);

            Log.i("jsonObj***", "" + jsonObj);

            // Create the POST object and add the parameters
            HttpPost httpPost = new HttpPost(WS);

            StringEntity entity;

            entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);

            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            HttpClient client = new DefaultHttpClient(timeOut(30000, 30000));

            HttpResponse response = client.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            String page = sb.toString();
            Log.i("page", "" + page);
            JSONObject jsonObject = new JSONObject(page);

            JSONObject msgJson = jsonObject.getJSONObject("response");
            int error = msgJson.getInt("errorCode");
            //Log.d("errorMessage", "" + msgJson.getString("errorMessage"));
            if(error == 0){
                //token 		= msgJson.getString("token");
                idafiliado	= msgJson.getString("idafiliado");
                result = 0;
            }else if(error == 1){
                result = 1;
            }else if(error == 2){
                result = 2;
            }else if(error == 3){
                result = 3;
            }else if(error == 4){
                result = 4;
            }else if(error == 5){
                result = 5;
            }else if(error == 6){
                result = 6;
            }else if(error == 7){
                result = 7;
            }else if(error == 8){
                result = 8;
            }


        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            result = 9;
            e.printStackTrace();
           // Toast.makeText(context, context.getResources().getString(R.string.noWS), Toast.LENGTH_LONG).show();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            result = 9;
            e.printStackTrace();
          //  Toast.makeText(context, context.getResources().getString(R.string.noWS), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            result = 9;
            e.printStackTrace();
           // Toast.makeText(context, context.getResources().getString(R.string.noWS), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            result = 9;
           // Toast.makeText(context, context.getResources().getString(R.string.noWS), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } finally{
          //  result = 9;
           // Toast.makeText(context, context.getResources().getString(R.string.noWS), Toast.LENGTH_LONG).show();

        }
        return result;
    }

    public HttpParams timeOut(int timeout, int timesocket) {

        HttpParams httpParameters = new BasicHttpParams();

        int timeoutConnection = timeout;
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);

        int timeoutSocket = timesocket;
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        return httpParameters;
    }
}
