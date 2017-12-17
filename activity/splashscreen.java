package com.example.user.bloodconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.user.bloodconnect.R;

public class splashscreen extends Activity implements Animation.AnimationListener {
    Animation animFadeIn,animblink;
    LinearLayout linearLayout;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);


        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);

        animFadeIn.setAnimationListener(splashscreen.this);

        linearLayout = (LinearLayout) findViewById(R.id.linear);

        linearLayout.setVisibility(View.VISIBLE);
        linearLayout.startAnimation(animFadeIn);
        animblink = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink2);

        animblink.setAnimationListener(this);

        logo = (ImageView) findViewById(R.id.logo);

        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(animblink);

    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    @Override
    public void onAnimationStart(Animation animation) {


    }

    public void onAnimationEnd(Animation animation) {

        Intent i = new Intent(splashscreen.this, MainActivity.class);
        startActivity(i);
        this.finish();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}

