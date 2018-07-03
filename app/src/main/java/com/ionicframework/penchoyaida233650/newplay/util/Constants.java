package com.ionicframework.penchoyaida233650.newplay.util;

/**
 * Created by Santiago on 11/05/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Constants {


    public interface ACTION {
        public static String MAIN_ACTION = "imoves.com.panchoaida.action.main";
        public static String INIT_ACTION = "imoves.com.panchoaida.action.init";
        public static String PREV_ACTION = "imoves.com.panchoaida.action.prev";
        public static String PLAY_ACTION = "imoves.com.panchoaida.action.play";
        public static String NEXT_ACTION = "imoves.com.panchoaida.action.next";
        public static String STARTFOREGROUND_ACTION = "imoves.com.panchoaida.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "imoves.com.panchoaida.action.stopforeground";


    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context,String src) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {

            bm = decodeBase64(src);


        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}