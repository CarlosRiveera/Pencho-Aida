package com.ionicframework.penchoyaida233650.podacast_exclusivos.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import com.ionicframework.penchoyaida233650.R;
import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by Alex on 11/05/2016.
 */
public class adapter_PodcastExclusivos extends BaseAdapter {

    private LayoutInflater inflater;
    private final Context context;
    Cursor cursor;
    String urlImagenDetallePodcast,valores;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public adapter_PodcastExclusivos(Context context, Cursor cursor) {
        try {
            imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            options = new DisplayImageOptions.Builder()
                    // .showStubImage(R.drawable.logooo)
                    // .showImageForEmptyUri(R.drawable.logooo)
                    .cacheInMemory().cacheOnDisc()
                    //.displayer(new RoundedBitmapDisplayer(50))
                    .build();
            this.inflater = LayoutInflater.from(context);
        }catch (Exception ex){

        }

            this.context = context;
            this.cursor = cursor;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return cursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (convertView == null) {
            view = inflater.inflate(R.layout.item_podcast_exlusivo, null);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.ImagenDetallePodcast);
            holder.textoDescripcion =(JustifyTextView) view.findViewById(R.id.DescripcionPodCast);
            holder.text = (TextView) view.findViewById(R.id.TituloPodCast);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);

        holder.text.setText(cursor.getString(1));
        holder.textoDescripcion.setText(cursor.getString(2)+"\n");

        urlImagenDetallePodcast = cursor.getString(3).replace("http://www.penchoyaida.com/audios/","http://www.penchoyaida.com/imgpodcastpya/");
        valores =urlImagenDetallePodcast.replace("mp3","jpg");
       // Log.i("imagen1","imagen"+ cursor.getString(3));
     //   Log.i("imagen2","---"+ cursor.getString(4));
        imageLoader.displayImage(cursor.getString(4),holder.image, options);
        return view;
    }

    private class ViewHolder {
        public TextView text;
        public JustifyTextView textoDescripcion;
        public ImageView image;
    }

}
