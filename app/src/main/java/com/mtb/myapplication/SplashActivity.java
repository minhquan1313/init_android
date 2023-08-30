package com.mtb.myapplication;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class SplashActivity extends AppCompatActivity {
    Context context = SplashActivity.this;
    TextView txt_view;
    ImageView iv_top, iv_heart, iv_beat, iv_bottom;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bindComponents();

        bindData();
    }

    private void bindComponents() {
        iv_top = findViewById(R.id.iv_top);
        iv_heart = findViewById(R.id.iv_heart);
        txt_view = findViewById(R.id.txt_view);
        iv_beat = findViewById(R.id.iv_beat);
        iv_bottom = findViewById(R.id.iv_bottom);

        runnable = new Runnable() {
            @Override
            public void run() {
                // when runable is run
                // set text
                txt_view.setText(charSequence.subSequence(0, index++));
                // check condition
                if (index <= charSequence.length()) {
                    // when index is equal to text lengh
                    // run handle
                    handler.postDelayed(runnable, delay);

                }
            }
        };
    }

    private void bindData() {
        // if (ActivityCompat.checkSelfPermission(context,
        // Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
        // Utils.askPermission(context, Manifest.permission.BLUETOOTH, 1);
        // return;
        // }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animationTop = AnimationUtils.loadAnimation(context, R.anim.top_wave);
        iv_top.startAnimation(animationTop);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(iv_heart,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        animator.setDuration(500);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();

        Glide.with((Context) context)
                .asGif()
                .load("https://i.giphy.com/lo9OHBZzaM7W8Lg5L0.gif")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv_beat);

        Animation animationBot = AnimationUtils.loadAnimation(this, R.anim.bot_wave);
        iv_bottom.setAnimation(animationBot);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        }, 4000);
    }

//    public void animaText(CharSequence cs) {
//        // set text
//        charSequence = cs;
//        // clear index
//        index = 0;
//        // clear text
//        txt_view.setText("");
//        // remove call back
//        handler.removeCallbacks(runnable);
//        // run handler
//        handler.postDelayed(runnable, delay);
//
//    }
}