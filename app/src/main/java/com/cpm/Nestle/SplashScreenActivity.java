package com.cpm.Nestle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cpm.Nestle.password.MPinActivity;
import com.cpm.Nestle.utilities.CommonString;

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 4000;
    Context context;
    private SharedPreferences preferences = null;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen_layout);
        context = this;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        StartAnimations();
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // close this activity
                String mpin = preferences.getString(CommonString.MPIN, null);
                if (mpin != null) {
                    Intent in = new Intent(context, MPinActivity.class);
                    in.putExtra(CommonString.IS_PASSWORD_CHECK, true);
                    startActivity(in);
                    finish();
                } else {
                    Intent i = new Intent(context, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);
        anim = AnimationUtils.loadAnimation(context, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }
}
