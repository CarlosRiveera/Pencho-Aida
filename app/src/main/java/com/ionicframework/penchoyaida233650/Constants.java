package com.ionicframework.penchoyaida233650;

/**
 * Created by Santiago on 11/05/2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "imoves.com.pruebanosirve.action.main";
        public static String INIT_ACTION = "imoves.com.pruebanosirve.action.init";
        public static String PREV_ACTION = "imoves.com.pruebanosirve.action.prev";
        public static String PLAY_ACTION = "imoves.com.pruebanosirve.action.play";
        public static String NEXT_ACTION = "imoves.com.pruebanosirve.action.next";
        public static String STARTFOREGROUND_ACTION = "imoves.com.pruebanosirve.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "imoves.com.pruebanosirve.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultAlbumArt(Context context) {
        Bitmap bm = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bm = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.default_album_art, options);
        } catch (Error ee) {
        } catch (Exception e) {
        }
        return bm;
    }

}