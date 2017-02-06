package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.Model.Dao.UserDao;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.utils.SharePrefrenceUtils;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
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
//                一开始就调用，看看用户是否有已经存储的数据，ljl123456
                String username = SharePrefrenceUtils.getInstance(Splash.this).getUser();
                if (username !=null){
//                    反问数据库，查找是否已经注册，已经注册，则为后面不需要注册跳转页面
                    User user = UserDao.getInstance().getUser(username);
                    if (user!=null){
                        FuLiCentreApplication.setUser(user);
                    }
                }
                MFGT.startAcitivity(Splash.this,MainActivity.class);
                MFGT.finish(Splash.this);
            }
        },2000);
    }
}
