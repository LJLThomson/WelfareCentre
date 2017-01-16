package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.utils.I;
import cn.ucai.welfarecentre.Model.utils.MD5;
import cn.ucai.welfarecentre.Model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class ModelUser implements IModelUser {
    @Override
    public void RegisterEnter(Context context, String userName, String NickName, String password, OnCompleteListener<String> listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))
                .targetClass(String.class)
                .execute(listener);
    }

    @Override
    public void LoginEnter(Context context, String userName, String password, OnCompleteListener listener) {
        OkHttpUtils<String> utils = new OkHttpUtils<>(context);
        utils.setRequestUrl(I.REQUEST_LOGIN)
                .addParam(I.User.USER_NAME,userName)
                .addParam(I.User.PASSWORD, MD5.getMessageDigest(password))//MD5简单的加密算法
                .post()
                .targetClass(String.class)
                .execute(listener);
    }
}
