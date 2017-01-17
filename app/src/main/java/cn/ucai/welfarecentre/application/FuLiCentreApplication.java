package cn.ucai.welfarecentre.application;

import android.app.Application;

import cn.ucai.welfarecentre.Model.bean.User;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class FuLiCentreApplication extends Application {
    private static FuLiCentreApplication instance;
    private static User user;
    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        FuLiCentreApplication.user = user;
    }
    
    public FuLiCentreApplication() {

    }
    public static FuLiCentreApplication getInstance(){
        if (instance == null){
            instance = new FuLiCentreApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
