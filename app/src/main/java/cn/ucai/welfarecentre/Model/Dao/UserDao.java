package cn.ucai.welfarecentre.Model.Dao;

import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.application.FuLiCentreApplication;

/**
 * Created by Administrator on 2017/1/17 0017.
 * 操作数据，从数据库中取数据
 */

public class UserDao {
    public  static final String USER_TABLE_NAME = "t_fulicenter_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR = "m_user_avatar_id";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avatar_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avatar_SUFFIX";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avatar_type";
    public static final String USER_COLUMN_AVATAR_UPDATA_TIME = "m_user_update_time";
    private static UserDao instance;
    public UserDao(){
        DBManager.onInit(FuLiCentreApplication.getInstance());
    }
    public static UserDao getInstance(){
        if (instance == null){
            instance = new UserDao();
        }
        return instance;
    }
    public boolean SavaUser(User user){
        return DBManager.getInstance().savaUser(user);
    }
}
