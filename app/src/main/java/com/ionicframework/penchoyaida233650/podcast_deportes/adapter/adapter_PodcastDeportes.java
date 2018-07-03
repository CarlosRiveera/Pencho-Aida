package com.ionicframework.penchoyaida233650.podcast_deportes.adapter;

import android.content.Context;
import android.database.Cursor;
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

/**
 * Created by Alex on 11/05/2016.
 */
public class adapter_PodcastDeportes extends BaseAdapter {

    private LayoutInflater inflater;
    private final Context context;
    Cursor cursor;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public adapter_PodcastDeportes(Context context, Cursor cursor) {

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();

        this.inflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.item_podcast_deporte, null);
            holder = new ViewHolder();
            holder.image = (ImageView) view.findViewById(R.id.ImagenDetallePodcast);
            holder.textoDescripcion =(TextView) view.findViewById(R.id.DescripcionPodCast);
            holder.text = (TextView) view.findViewById(R.id.TituloPodCast);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);
        holder.text.setText(cursor.getString(1));
        holder.textoDescripcion.setText(cursor.getString(2)+"\n");
        imageLoader.displayImage("http://www.penchoyaida.com/imgpodcastpya/Pencho_y_Aida_-_Deportes_Palomo.jpg",holder.image, options);
        return view;
    }

    private class ViewHolder {
        public TextView text;
        public TextView textoDescripcion;
        public ImageView image;
    }

}
