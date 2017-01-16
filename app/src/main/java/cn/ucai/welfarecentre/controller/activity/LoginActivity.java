package cn.ucai.welfarecentre.controller.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.ivReturn)
    ImageView ivReturn;
    @BindView(R.id.layout_title)
    RelativeLayout layoutTitle;
    @BindView(R.id.iv_username)
    ImageView ivUsername;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.rl_username)
    RelativeLayout rlUsername;
    @BindView(R.id.iv_password)
    ImageView ivPassword;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.rl_password)
    RelativeLayout rlPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnUrl)
    Button btnUrl;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accunt_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegister)
    public void onClick() {
        MFGT.gotoRegisterActivity(this);
    }
}
