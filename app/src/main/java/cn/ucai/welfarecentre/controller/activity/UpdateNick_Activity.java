package cn.ucai.welfarecentre.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ucai.welfarecentre.Model.Dao.UserDao;
import cn.ucai.welfarecentre.Model.bean.Result;
import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.net.IModelUser;
import cn.ucai.welfarecentre.Model.net.ModelUser;
import cn.ucai.welfarecentre.Model.net.OnCompleteListener;
import cn.ucai.welfarecentre.Model.utils.CommonUtils;
import cn.ucai.welfarecentre.Model.utils.ConvertUtils;
import cn.ucai.welfarecentre.Model.utils.DisplayUtils;
import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.ResultUtils;
import cn.ucai.welfarecentre.Model.utils.SharePrefrenceUtils;
import cn.ucai.welfarecentre.R;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;
import cn.ucai.welfarecentre.view.MFGT;

/**
 * Created by Administrator on 2017/1/18 0018.
 */

public class UpdateNick_Activity extends AppCompatActivity {
    @BindView(R.id.backClickArea)
    LinearLayout backClickArea;
    @BindView(R.id.tv_common_Title)
    TextView tvCommonTitle;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.bt4)
    Button bt4;
    IModelUser model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_nick);
        ButterKnife.bind(this);
        DisplayUtils.initBackWithTitle(this, "修改昵称");//并点击backClickArea结束该页面
        initData();
    }

    private void initData() {
     User user = FuLiCentreApplication.getInstance().getUser();
        if (user==null){
            finish();
        }else{
            et1.setText(user.getMuserNick());
        }
    }

    @OnClick(R.id.bt4)
    public void onClick() {//保存
        checkInput();
        MFGT.finish(this);
    }

    private void checkInput() {
        String nick = et1.getText().toString().trim();
        String userName = FuLiCentreApplication.getUser().getMuserName();
        if (TextUtils.isEmpty(nick)){
            CommonUtils.showLongToast(R.string.nick_name_connot_be_empty);
            return;
        }else if (nick.equals(FuLiCentreApplication.getUser().getMuserNick())){
            CommonUtils.showLongToast(R.string.update_fail);
            return;
        }
        Intent intent = getIntent();
        intent.putExtra(I.User.NICK,nick);
        setResult(RESULT_OK,intent);
        updateNick(userName,nick);
    }

    private void updateNick(final String userName, final String nick) {
        final ProgressDialog dialog = new ProgressDialog(this);
        model = new ModelUser();
        model.UpdateNick(this, userName, nick, new OnCompleteListener<String>() {
            @Override
            public void onSuccess(String s) {
                int msg = R.string.update_fail;
                if (s !=null){
                    Result result = ResultUtils.getResultFromJson(s,User.class);
                    if (result!=null){
                        if (result.isRetMsg()){
//                            解析数据
                            msg = R.string.update_user_nick_success;
                            String json = result.getRetData().toString();
                            Gson gson = new Gson();
                            User user = gson.fromJson(json,User.class);
//                            数据库保存数据
                            boolean savaUser = UserDao.getInstance().SavaUser(user);
//                            采用首选项保存数据
                            savaUserNameAndPassword(userName, nick);
                        }else{
                            if (result.getRetCode() ==I.MSG_USER_SAME_NICK
                                    || result.getRetCode() == I.MSG_USER_UPDATE_NICK_FAIL){
                                msg = R.string.update_nick_fail_unmodify;
                            }
                        }
                    }
                }
                CommonUtils.showLongToast(msg);
                dialog.dismiss();
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void savaUserNameAndPassword(String userName, String nick) {
        SharePrefrenceUtils sharePrefrenceUtils = SharePrefrenceUtils.getInstance(UpdateNick_Activity.this);
        sharePrefrenceUtils.savaUser(userName);
    }
}
