package cn.ucai.welfarecentre.Model.net;

import android.content.Context;

import cn.ucai.welfarecentre.Model.utils.OkHttpUtils;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public interface IModelUser {
    void RegisterEnter(Context context, String userName, String NickName, String password, OnCompleteListener<String> OnCompleteListener);
    void LoginEnter(Context context, String userName,  String password, OnCompleteListener listener);
    void UpdateNick(Context context,String userName,String nick,OnCompleteListener listener);

}
