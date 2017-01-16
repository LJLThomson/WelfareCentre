package cn.ucai.welfarecentre.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.Result;
import cn.ucai.welfarecentre.Model.net.IModelUser;
import cn.ucai.welfarecentre.Model.net.ModelUser;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.CommonUtils;
import cn.ucai.welfarecentre.Model.utils.ResultUtils;
import cn.ucai.welfarecentre.Model.utils.SharePrefrenceUtils;
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
    IModelUser modle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accunt_login);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.ivReturn, R.id.btnLogin, R.id.btnRegister, R.id.btnUrl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivReturn:
                break;
            case R.id.btnLogin:
                checkOutInput();
                break;
            case R.id.btnRegister:
                MFGT.gotoRegisterActivity(this);
                break;
            case R.id.btnUrl:
                break;
        }
    }

    private void checkOutInput() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        login(userName,password);
    }

    private void login(final String userName, final String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        modle = new ModelUser();
        modle.LoginEnter(this, userName, password, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                if (s !=null){
                    Result result = ResultUtils.getResultFromJson(s,Result.class);
                    if (result.isRetMsg()){
                        savaUserNameAndPassword(userName,password);
                        MFGT.finish(LoginActivity.this);
                    }else{
                        CommonUtils.showLongToast(getString(R.string.login_fail));
                    }
                }else{
                    CommonUtils.showLongToast(getString(R.string.login_fail));
                }
                dialog.dismiss();

            }

            @Override
            public void onError(String error) {
                dialog.dismiss();
                CommonUtils.showLongToast(error);
            }
        });
    }

    private void savaUserNameAndPassword(String userName,String password) {
        SharePrefrenceUtils sharePrefrenceUtils = SharePrefrenceUtils.getInstance(LoginActivity.this);
        sharePrefrenceUtils.savaUser(userName);
    }
}
