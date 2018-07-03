package com.ionicframework.penchoyaida233650.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Todociber on 10/05/2016.
 */
public class XMlParser {

    // constructor
    public XMlParser() {

    }

    /**
     * Getting XML from URL making HTTP request
     * @param url string
     * */
    public String getXmlFromUrl(String url) {
        String xml = null;

        try {
            // defaultHttpClient




            HttpClient client = new DefaultHttpClient();
            client.getParams().setParameter(CoreProtocolPNames.USER_AGENT,"android");
            HttpGet request = new HttpGet(url);
            request.setHeader("Content-Type", "text/xml; charset=utf-8");
            request.setURI(new URI(url));
            HttpResponse httpResponse = client.execute(request);
            HttpEntity httpEntity = httpResponse.getEntity();
            xml = EntityUtils.toString(httpEntity,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // return XML
        return xml;
    }
}
