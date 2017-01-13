package cn.ucai.welfarecentre.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.activity.BoutiqueActivity;
import cn.ucai.welfarecentre.controller.activity.CatagoryActivity;
import cn.ucai.welfarecentre.controller.activity.GoodsDetailsAcitivity;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void finish(AppCompatActivity context){
        context.finish();
    }
    public static void startAcitivity(AppCompatActivity context,Class<?> clz){
        context.startActivity(new Intent(context,clz));
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    public static void startActivity(Activity context,Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
    /**
     * 二级页面，
     * @param context activity
     * @param boutiqueBean
     */
    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean){
        Intent intent = new Intent(context, BoutiqueActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,boutiqueBean.getId());
        intent.putExtra(I.Boutique.NAME,boutiqueBean.getTitle());
        startActivity((AppCompatActivity)context,intent);//将context强制转换
//        context.startActivity(intent);
    }
    public static void gotoGoodsDetailsActitivity(Context context,int goodsId){
//         Intent intent = new Intent(context,GoodsA)
        Intent intent = new Intent(context, GoodsDetailsAcitivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,goodsId);
        startActivity((AppCompatActivity)context,intent);
    }
    public static void gotoCatagoryActivity(Context context,int picId){
        Intent  intent= new Intent(context, CatagoryActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID,picId);
//        context.startActivity(intent);
        startActivity((AppCompatActivity)context,intent);
    }
}
