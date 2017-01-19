package cn.ucai.welfarecentre.application;

import android.app.Application;

import java.util.HashMap;

import cn.ucai.welfarecentre.Model.bean.CartBean;
import cn.ucai.welfarecentre.Model.bean.User;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class FuLiCentreApplication extends Application {
    private static FuLiCentreApplication instance;
    private static User user;

    public static HashMap<Integer, CartBean> getMyCartList() {
        return myCartList;
    }

    public static void setMyCartList(HashMap<Integer, CartBean> myCartList) {
        FuLiCentreApplication.myCartList = myCartList;
    }

    private static HashMap<Integer,CartBean> myCartList;



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
