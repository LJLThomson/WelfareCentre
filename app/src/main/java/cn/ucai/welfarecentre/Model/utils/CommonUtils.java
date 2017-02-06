package cn.ucai.welfarecentre.Model.utils;

import android.widget.Toast;

import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.controller.activity.RegisterActivity;


public class CommonUtils {
    public static void showLongToast(String msg){
//         instance = new FuLiCentreApplication();
        Toast.makeText(FuLiCentreApplication.getInstance(),msg,Toast.LENGTH_LONG).show();
    }
    public static void showShortToast(String msg){
        Toast.makeText(FuLiCentreApplication.getInstance(),msg,Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(int rId){
        showLongToast(FuLiCentreApplication.getInstance().getString(rId));
    }
    public static void showShortToast(int rId){
        showShortToast(FuLiCentreApplication.getInstance().getString(rId));
    }
}
