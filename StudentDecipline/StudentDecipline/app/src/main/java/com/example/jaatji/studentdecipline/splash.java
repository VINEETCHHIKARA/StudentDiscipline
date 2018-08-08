package com.example.jaatji.studentdecipline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences sp=getSharedPreferences("login", Context.MODE_PRIVATE);
                String s=sp.getString("name","a");
                /* Create an Intent that will start the Menu-Activity. */
                if(s=="a"){
                    Intent mainIntent = new Intent(splash.this,Login.class);
                    splash.this.startActivity(mainIntent);
                    splash.this.finish();
                }
                else{
                    Intent i=new Intent(splash.this,Option.class);
                    startActivity(i);
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
