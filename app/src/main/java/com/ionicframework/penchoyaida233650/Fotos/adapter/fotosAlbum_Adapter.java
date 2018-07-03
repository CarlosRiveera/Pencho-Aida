package com.ionicframework.penchoyaida233650.Fotos.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import com.ionicframework.penchoyaida233650.R;
import com.ionicframework.penchoyaida233650.util.ImageviewAdapter;


/**
 * Created by Alex on 17/05/2016.
 */
public class fotosAlbum_Adapter extends BaseAdapter {
    private LayoutInflater inflater;
    private final Context contexto;
    Cursor cursor;
    DisplayImageOptions options;
    protected ImageLoader imageLoader = ImageLoader.getInstance();

    public fotosAlbum_Adapter(Context context, Cursor cursor) {
        this.contexto = context;
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .cacheInMemory().cacheOnDisc()
                .build();
        this.inflater = LayoutInflater.from(context);
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
            view = inflater.inflate(R.layout.item_foto, null);
            holder = new ViewHolder();
            holder.ImagenPrincipalAlbum = (ImageviewAdapter) view.findViewById(R.id.ImagenPrincipalAlbum);
            holder.TituloFotoAlbum = (TextView) view.findViewById(R.id.TituloFotoAlbum);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        cursor.moveToPosition(position);
        holder.TituloFotoAlbum.setVisibility(View.GONE);
        imageLoader.displayImage(cursor.getString(8), holder.ImagenPrincipalAlbum, options, new ImageLoadingListener(){
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });
        return view;
    }

    private class ViewHolder {
        public TextView TituloFotoAlbum;
        public ImageviewAdapter ImagenPrincipalAlbum;
    }

}
