package cn.ucai.welfarecentre.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import cn.ucai.welfarecentre.Model.bean.BoutiqueBean;
import cn.ucai.welfarecentre.Model.bean.CategoryChildBean;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.controller.activity.BoutiqueActivity;
import cn.ucai.welfarecentre.controller.activity.CatagoryActivity;
import cn.ucai.welfarecentre.controller.activity.GoodsDetailsAcitivity;
import cn.ucai.welfarecentre.controller.activity.LoginActivity;
import cn.ucai.welfarecentre.controller.activity.PersonalActivity;
import cn.ucai.welfarecentre.controller.activity.RegisterActivity;
import cn.ucai.welfarecentre.controller.activity.SettingsActivity;
import cn.ucai.welfarecentre.controller.activity.UpdateNick_Activity;

/**
 * Created by Administrator on 2017/1/10 0010.
 */

public class MFGT {
    public static void finish(AppCompatActivity context) {
        context.finish();
    }

    public static void startAcitivity(AppCompatActivity context, Class<?> clz) {
        context.startActivity(new Intent(context, clz));
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);//进入，出去anim属性
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 二级页面，
     *
     * @param context      activity
     * @param boutiqueBean
     */
    public static void gotoBoutiqueChild(Context context, BoutiqueBean boutiqueBean) {
        Intent intent = new Intent(context, BoutiqueActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, boutiqueBean.getId());
        intent.putExtra(I.Boutique.NAME, boutiqueBean.getTitle());
        startActivity((AppCompatActivity) context, intent);//将context强制转换
//        context.startActivity(intent);
    }

    public static void gotoGoodsDetailsActitivity(Context context, int goodsId) {
//         Intent intent = new Intent(context,GoodsA)
        Intent intent = new Intent(context, GoodsDetailsAcitivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, goodsId);
        startActivity((AppCompatActivity) context, intent);
    }

    public static void gotoCatagoryActivity(Context context, int picId, String groupName, ArrayList<CategoryChildBean> category_list) {
        Intent intent = new Intent(context, CatagoryActivity.class);
        intent.putExtra(I.NewAndBoutiqueGoods.CAT_ID, picId);
        intent.putExtra("groupName", groupName);
        intent.putExtra("category_list", category_list);
//        intent.put
//        context.startActivity(intent);
        startActivity((AppCompatActivity) context, intent);
    }

//    登录
    public static void gotoLoginActivity(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity((AppCompatActivity) context,intent);
    }
//    注册
    public static void gotoRegisterActivity(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity((AppCompatActivity) context,intent);
    }
//    个人中心
    public static void gotoPersonalActivity(Context context){
        Intent intent = new Intent(context, PersonalActivity.class);
        startActivity((AppCompatActivity) context,intent);
    }

    public static void gotoSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        startActivity((AppCompatActivity) context,intent);
    }
    public static void gotoUpdateNickActivity(Activity activity){
        Intent intent = new Intent(activity, UpdateNick_Activity.class);
        activity.startActivityForResult(intent,I.REQUEST_CODE_NICK);
    }
}
