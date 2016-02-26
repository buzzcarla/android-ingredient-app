package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.emmanbraulio.final_thesis2.R;

/**
 * Created by Derek on 1/27/2016.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final TextView tv = (TextView) findViewById(R.id.anim_fiit);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.appear);


        tv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener()
        {
           @Override
           public  void onAnimationStart(Animation animation)
           {

           }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                finish();
                Intent intent = new Intent (Splash.this,HomeScreen.class);
                startActivity(intent);
            }

            @Override
            public  void onAnimationRepeat(Animation animation)
            {

            }

        });
    }
}
