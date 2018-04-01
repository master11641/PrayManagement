package com.example.alireza.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.alireza.myapplication.BuildConfig;
import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {
    TextView txtVersion;
    TextView txtAppName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtVersion = findViewById(R.id.txtVersion);
        txtAppName = findViewById(R.id.txtAppName);

        BuildConfig bf = new BuildConfig();
        String versionName = bf.VERSION_NAME;
        int versionCode = bf.VERSION_CODE;
        txtVersion.setText("VersionName : "+versionName +"\n" +"VersionCode : "+versionCode);



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            Animation anim1= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.pdlg_anim_fade_in);
           anim1.setDuration(3000);
            txtAppName.startAnimation(anim1);
       /*     Animation anim2= AnimationUtils.loadAnimation(SplashActivity.this,R.anim.splash_animation);
            anim2.setDuration(3000);
            txtVersion.startAnimation(anim2);   */

        }
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

   /* @Override
    public void initSplash(ConfigSplash configSplash) {
*//* you don't have to override every property *//*

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(2000); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
        configSplash.setLogoSplash(R.mipmap.ic_launcher_round); //or any other drawable
        configSplash.setAnimLogoSplashDuration(2000); //int ms
        configSplash.setAnimLogoSplashTechnique(Techniques.DropOut); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
       // configSplash.setPathSplash(); //set path String
  *//*      configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(3000);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.Blue); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(3000);
        configSplash.setPathSplashFillColor(R.color.colorPrimaryDark); //path object filling color*//*


        //Customize Title
        configSplash.setTitleSplash("نرم افـزار راز و نیاز");
        configSplash.setTitleTextColor(R.color.White);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
       configSplash.setTitleFont("font/BZar.ttf"); //provide string to your font located in a
    }

    @Override
    public void animationsFinished() {
        Intent i = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(i);
    }*/


}
