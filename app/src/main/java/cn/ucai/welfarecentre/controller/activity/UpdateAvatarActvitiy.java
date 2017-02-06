package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/1/18 0018.
 */

public class UpdateAvatarActvitiy extends AppCompatActivity {
    PopupWindow mpopupWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actovity_boutique);
        initPopupWindow();
    }

    private void initPopupWindow() {
        View layout = View.inflate(this,R.layout.popup_window,null);
//        创建PopupWindow对象，设置布局
        mpopupWindow = new PopupWindow();
        mpopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mpopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mpopupWindow.setContentView(layout);
//        上面三条等价于mpopupWindow = new PopupWindow(layout，ViewGroup.LayoutParams.MATCH_PARENT，ViewGroup.LayoutParams.MATCH_PARENT);
        mpopupWindow.setFocusable(true);
        mpopupWindow.setOutsideTouchable(true);//点击外部关闭
        mpopupWindow.setAnimationStyle(R.style.style_popup_window);//有进入属性，和出去属性
        mpopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(UpdateAvatarActvitiy.this, "关闭弹出框", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
