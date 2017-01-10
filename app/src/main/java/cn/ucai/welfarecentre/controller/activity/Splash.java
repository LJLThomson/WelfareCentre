package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class Splash extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MFGT.startAcitivity(Splash.this,MainActivity.class);
                MFGT.finish(Splash.this);
            }
        },2000);
    }
}
