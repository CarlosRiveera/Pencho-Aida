package com.ionicframework.penchoyaida233650.videos.adapter;

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
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;


/**
 * Created by Alex on 18/05/2016.
 */
public class adapterVideos_Listado extends BaseAdapter{
    private LayoutInflater inflater;
    private final Context context;
    Cursor cursor;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public adapterVideos_Listado(Context context, Cursor cursor) {

        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                // .showStubImage(R.drawable.logooo)
                // .showImageForEmptyUri(R.drawable.logooo)
                .cacheInMemory().cacheOnDisc()
                //.displayer(new RoundedBitmapDisplayer(50))
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
            view = inflater.inflate(R.layout.item_listado_videos, null);
            holder = new ViewHolder();
            holder.image = (ImageviewAdapter) view.findViewById(R.id.ImagenPreviewVideo);
            holder.textoDescripcion =(TextView) view.findViewById(R.id.TituloVideo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);

        holder.textoDescripcion.setText("   "+cursor.getString(5));
        imageLoader.displayImage(cursor.getString(9),holder.image, options);
        return view;
    }

    private class ViewHolder {

        public TextView textoDescripcion;
        public ImageviewAdapter image;
    }

}

