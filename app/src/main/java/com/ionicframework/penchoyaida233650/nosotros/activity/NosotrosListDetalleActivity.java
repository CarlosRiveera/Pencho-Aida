package com.ionicframework.penchoyaida233650.nosotros.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ionicframework.penchoyaida233650.R;

/**
 * Created by JosueIMOVES on 17/05/2016.
 */
public class NosotrosListDetalleActivity extends AppCompatActivity {

    RelativeLayout li1,li2,li3,li4,li5;
    TextView texttwitter,btnweb;
    int[] draw = {R.drawable.staff_1,R.drawable.staff_2,R.drawable.staff_3,R.drawable.staff_4,R.drawable.staff_5};
    String[] twitt = {"@EvelynLinares","@chomitojr_ok","@beatrizvasquez3","@taniarucks","@penchoyaida"};
    String[] nombres ={"Evelyn Linares","Roberto 'Chomito' Reyes","Beatriz Vásquez","Tania Henríquez","Gabriela Campos"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nosotros_list_detalle_activity);

        getSupportActionBar().setTitle(getResources().getString(R.string.staff));
        texttwitter = (TextView) findViewById(R.id.texttwitter);
        btnweb = (TextView) findViewById(R.id.btnweb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        texttwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://twitter.com/PenchoyAida");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.penchoyaida.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        li1 = (RelativeLayout)findViewById(R.id.li1);
        li1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(0, 0);
            }
        });

        li2 = (RelativeLayout)findViewById(R.id.li2);
        li2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(1,1);
            }
        });

        li3 = (RelativeLayout)findViewById(R.id.li3);
        li3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(2,2);
            }
        });

        li4 = (RelativeLayout)findViewById(R.id.li4);
        li4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(3,3);
            }
        });

        li5 = (RelativeLayout)findViewById(R.id.li5);
        li5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showDialog(4,4);
            }
        });
    }

    public void showDialog(int texto, int img){
        final Dialog dialog = new Dialog(NosotrosListDetalleActivity.this);
        dialog.setContentView(R.layout.custom);
        TextView text = (TextView) dialog.findViewById(R.id.texttwitter);
        dialog.setTitle(nombres[texto]);
        text.setText(twitt[texto]);
        ImageView image = (ImageView) dialog.findViewById(R.id.imgperfil);
        image.setImageResource(draw[img]);
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
