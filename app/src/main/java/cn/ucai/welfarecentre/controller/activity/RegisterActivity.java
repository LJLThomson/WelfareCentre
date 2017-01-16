package cn.ucai.welfarecentre.controller.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.bean.Result;
import cn.ucai.welfarecentre.Model.net.IModelUser;
import cn.ucai.welfarecentre.Model.net.ModelUser;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.CommonUtils;
import cn.ucai.welfarecentre.Model.utils.ResultUtils;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etNick)
    EditText etNick;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.etConfirmPassword)
    EditText etConfirmPassword;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_avatar)
    TextView tvAvatar;
    @BindView(R.id.layout_user_avatar)
    RelativeLayout layoutUserAvatar;
    IModelUser model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accunt_registration);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivReturn, R.id.btnRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivReturn:
                break;
            case R.id.btnRegister:
                checkOutInput();

                break;
        }
    }



    private void checkOutInput() {
        String userName = etUserName.getText().toString();
        String userNick = etNick.getText().toString();
        String password = etPassword.getText().toString();
        String confirm = etConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(userName)){
            etUserName.setError(getResources().getString(R.string.user_name_connot_be_empty));
            etUserName.requestFocus();
            return;
        }else if (!userName.matches("[a-zA-Z]\\w{5,15}]")){
            etUserName.setError(getResources().getString(R.string.illegal_user_name));
            etUserName.requestFocus();
            return;
        }else if (TextUtils.isEmpty(userNick)){
            etNick.setError(getResources().getString(R.string.nick_name_connot_be_empty));
            etNick.requestFocus();
            return;
        }else if (TextUtils.isEmpty(password)){
            etPassword.setError(getResources().getString(R.string.password_connot_be_empty));
            etPassword.requestFocus();
            return;
        }else if (TextUtils.isEmpty(confirm)){
            etConfirmPassword.setError(getResources().getString(R.string.confirm_password_connot_be_empty));
            etConfirmPassword.requestFocus();
            return;
        }else if(!password.equals(confirm)){
            etConfirmPassword.setError(getResources().getString(R.string.two_input_password));
            etConfirmPassword.requestFocus();
            return;
        }
        register( userName, userNick, password);
    }

    private void register(String userName, String userNick, String password) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.register));
        model = new ModelUser();
        model.RegisterEnter(this,userName,userNick,password,new OnCompleteListener<String>(){
            @Override
            public void onSuccess(String s) {
                if (s != null){
                    Result result = ResultUtils.getResultFromJson(s, Result.class);
                    if (result!=null){
                        if (result.isRetMsg()){
                            CommonUtils.showLongToast(R.string.register_success);
                            MFGT.finish(RegisterActivity.this);
                        }else{
                            CommonUtils.showLongToast(R.string.register_fail_exists);
                        }
                    }else{
                        CommonUtils.showLongToast(R.string.register_fail);
                    }
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
}
