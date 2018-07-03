package com.ionicframework.penchoyaida233650.nosotros.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.alexzh.circleimageview.CircleImageView;

import com.ionicframework.penchoyaida233650.R;
import me.biubiubiu.justifytext.library.JustifyTextView;


/**
 * Created by JosueIMOVES on 17/05/2016.
 */
public class NosotrosDetalleActivity extends ActionBarActivity {

    ImageView img;
    Integer foto;
    Uri uri;
    String nombre, twitter, desc;
    TextView textnombre, texttwitter;
    JustifyTextView textdesc;
    CircleImageView imgperfil;
    LinearLayout Social;
    Bitmap bitmap;
    String[] twitters = {"https://twitter.com/PenchoDuque", "https://twitter.com/aidafarrar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nosotros_detalle_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        try {


            Bundle extra = NosotrosDetalleActivity.this.getIntent().getExtras();
            foto = (Integer) extra.get("foto");
            nombre = (String) extra.get("nombre");
            twitter = (String) extra.get("twitter");
            desc = (String) extra.get("desc");

            img = (ImageView) findViewById(R.id.back);
            textnombre = (TextView) findViewById(R.id.textnombre);
            texttwitter = (TextView) findViewById(R.id.texttwitter);
            textdesc = (JustifyTextView) findViewById(R.id.textdesc);
            imgperfil = (CircleImageView) findViewById(R.id.imgperfil);
            Social = (LinearLayout) findViewById(R.id.Social);

            if (nombre.equals("Pencho Duque")) {
                getSupportActionBar().setTitle(getResources().getString(R.string.penchos));
            } else {
                getSupportActionBar().setTitle(getResources().getString(R.string.aida));
            }
            img.setImageResource(foto);
            imgperfil.setImageResource(foto);
            textnombre.setText(nombre);
            texttwitter.setText(twitter);
            textdesc.setText(desc + "\n");
            texttwitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nombre.equals("Pencho Duque")) {
                        uri = Uri.parse("https://twitter.com/PenchoDuque");
                    } else {
                        uri = Uri.parse("https://twitter.com/aidafarrar");
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });

            BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            Bitmap blurred = blurRenderScript(bitmap, 20);//second parametre is radius
            img.setImageBitmap(blurred);
            Social.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            imgperfil.setEnabled(false);
            imgperfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        } catch (NullPointerException ex){
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private Bitmap blurRenderScript(Bitmap smallBitmap, int radius) {
        try {
            smallBitmap = RGB565toARGB888(smallBitmap);

            bitmap = Bitmap.createBitmap(
                    smallBitmap.getWidth(), smallBitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            RenderScript renderScript = RenderScript.create(NosotrosDetalleActivity.this);
            Allocation blurInput = Allocation.createFromBitmap(renderScript, smallBitmap);
            Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);
            ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript,
                    Element.U8_4(renderScript));
            blur.setInput(blurInput);
            blur.setRadius(radius); // radius must be 0 < r <= 25
            blur.forEach(blurOutput);
            blurOutput.copyTo(bitmap);
            renderScript.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    private Bitmap RGB565toARGB888(Bitmap img) throws Exception {
        int numPixels = img.getWidth() * img.getHeight();
        int[] pixels = new int[numPixels];
        img.getPixels(pixels, 0, img.getWidth(), 0, 0, img.getWidth(), img.getHeight());
        Bitmap result = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.ARGB_8888);
        result.setPixels(pixels, 0, result.getWidth(), 0, 0, result.getWidth(), result.getHeight());
        return result;
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
