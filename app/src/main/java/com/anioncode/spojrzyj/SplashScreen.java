package com.anioncode.spojrzyj;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView=findViewById(R.id.imagecon);
//        SplashScreen.this.getSupportActionBar().hide();


        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade);
        imageView.startAnimation(animation);
//Remove notification bar
      //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
    //    this.setContentView(R.layout.activity_splash_screen);

     new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
             Intent intent= new Intent(SplashScreen.this,DashboardActivity.class);
             startActivity(intent);
             finish();
         }
     },1000);

    }
}
