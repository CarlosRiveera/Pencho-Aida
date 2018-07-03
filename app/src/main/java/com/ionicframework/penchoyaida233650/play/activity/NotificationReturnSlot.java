package com.ionicframework.penchoyaida233650.play.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ionicframework.penchoyaida233650.R;

/**
 * Created by Santiago on 09/05/2016.
 */
public class NotificationReturnSlot extends Activity {


    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);

        btn1 = (Button) findViewById(R.id.btn3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("NotificationReturnSlot","--------------");
                Toast.makeText(NotificationReturnSlot.this, "volume", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        Bundle extras = NotificationReturnSlot.this.getIntent().getExtras();
        String action = extras.getString("DO");
        Log.i("NotificationReturnSlot","--------------"+ action);
        if (action.equals("volume")) {
            Log.i("NotificationReturnSlot", "volume");
            Toast.makeText(NotificationReturnSlot.this, "volume", Toast.LENGTH_SHORT).show();
            //Your code


        } else if (action.equals("stop")) {
            //Your code
            Toast.makeText(NotificationReturnSlot.this, "stop", Toast.LENGTH_SHORT).show();
            Log.i("NotificationReturnSlot", "stopNotification");
        }else if(action.equals("play")) {
            Log.i("NotificationReturnSlot", "play");
            Toast.makeText(NotificationReturnSlot.this, "play", Toast.LENGTH_SHORT).show();
        }
        finish();

    }
}