package cn.ucai.welfarecentre.application;

import android.app.Application;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class FuLiCentreApplication extends Application {
    private static FuLiCentreApplication instance;

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
        instance = getInstance();
    }
}
