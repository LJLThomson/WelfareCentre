package cn.ucai.welfarecentre.Model.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class SharePrefrenceUtils {
    private static final String SHARE_PREFRENCE_NAME = "cn.ucai.fulicenter_user";
    private static final String SHARE_PREFRENCE_NAME_USERNAME = "cn.ucai.fulicenter_user_username";
    private static SharePrefrenceUtils instance;
    private static SharedPreferences Preference;
    public SharePrefrenceUtils(Context context){
        this.Preference = context.getSharedPreferences(SHARE_PREFRENCE_NAME, context.MODE_PRIVATE);
    }
    public static SharePrefrenceUtils getInstance(Context context){
        if (instance == null){
            instance = new SharePrefrenceUtils(context);
        }
        return instance;
    }
    public static void savaUser(String username){
        Preference.edit().putString(SHARE_PREFRENCE_NAME_USERNAME,username).commit();
    }
    public static String getUser(){
        return  Preference.getString(SHARE_PREFRENCE_NAME_USERNAME,null);
    }
}
