package cn.ucai.welfarecentre.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ImageLoader;
import cn.ucai.welfarecentre.Model.utils.SharePrefrenceUtils;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/17 0017.
 */

public class SettingsActivity extends AppCompatActivity {
    @BindView(R.id.bt3)
    Button bt3;
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_common_Title)
    TextView tvCommonTitle;
    @BindView(R.id.tv10)
    TextView tv10;
    @BindView(R.id.line4)
    LinearLayout line4;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.line5)
    LinearLayout line5;
    @BindView(R.id.tv8)
    TextView tv8;
    @BindView(R.id.tv9)
    TextView tv9;
    @BindView(R.id.line6)
    LinearLayout line6;
    ImageView img_5;
    @BindView(R.id.tv11)
    TextView tv11;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_settings);
        ButterKnife.bind(this);
        tvCommonTitle.setText("个人设置");
        img_5 = (ImageView) findViewById(R.id.img5);
        downloadDefaultAvatar();
    }

    private void downloadDefaultAvatar() {
        //          图片下载，这里下载的是默认图片
        User user = FuLiCentreApplication.getUser();
        String avatar_img = ImageLoader.getAvatarUrl(user);
        String nick = user.getMuserNick();
        tv11.setText(user.getMuserName());//设置用户名
        tv9.setText(user.getMuserNick());//设置昵称
        ImageLoader.setAvatar(avatar_img, this, img_5);//设置用户头像
    }

    @OnClick({R.id.bt3, R.id.backClickArea, R.id.tv_common_Title, R.id.tv10, R.id.line4, R.id.textView3, R.id.line5, R.id.line6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt3://退出,访问数据库，删除user信息，并进入登录界面
//                然后根据用户名，删除user
                String username = SharePrefrenceUtils.getInstance(FuLiCentreApplication.getInstance()).getUser();
//                UserDao.getInstance().deleUser(username);//根据username删除数据库
                MFGT.gotoLoginActivity(this);
                finish();
                break;
            case R.id.backClickArea://返回，回到个人中心页面
                MFGT.finish(this);
                break;
            case R.id.line4://头像设置
                MFGT.gotoAvatarActivity(this);
                break;
            case R.id.line5://用户名更改
                break;
            case R.id.line6://昵称更改
                MFGT.gotoUpdateNickActivity(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case I.REQUEST_CODE_NICK:
                String username = data.getStringExtra(I.Avatar.USER_NAME);
                String nick = data.getStringExtra(I.User.NICK);
                tv9.setText(nick);
                break;
        }
    }
}
