package cn.ucai.welfarecentre.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import cn.ucai.welfarecentre.R;

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
}
