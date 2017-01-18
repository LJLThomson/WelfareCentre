package cn.ucai.welfarecentre.Model.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import cn.ucai.welfarecentre.Model.bean.User;
import cn.ucai.welfarecentre.Model.utils.L;

/**
 * Created by Administrator on 2017/1/17 0017.
 * 将数据保存到数据库中
 */

public class DBManager {
    private static final String TAG = DBManager.class.getSimpleName();
    private static DBOpenHelper dbHelper;
    static DBManager dbMgr;

    public DBManager() {

    }

    public static void onInit(Context context) {
        dbHelper = new DBOpenHelper(context);
    }

    public static DBManager getInstance() {
        if (dbHelper != null) {
            dbMgr = new DBManager();
        } else {
            L.e(TAG, "没有调用onInit");
        }
        return dbMgr;
    }

    public boolean savaUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();
            values.put(UserDao.USER_COLUMN_NAME, user.getMuserName());
            values.put(UserDao.USER_COLUMN_NICK, user.getMuserNick());
            values.put(UserDao.USER_COLUMN_AVATAR, user.getMavatarId());
            values.put(UserDao.USER_COLUMN_AVATAR_PATH, user.getMavatarPath());
            values.put(UserDao.USER_COLUMN_AVATAR_TYPE, user.getMavatarType());
            values.put(UserDao.USER_COLUMN_AVATAR_SUFFIX, user.getMavatarSuffix());
            values.put(UserDao.USER_COLUMN_AVATAR_UPDATA_TIME, user.getMavatarLastUpdateTime());
            long count = db.replace(UserDao.USER_TABLE_NAME, null, values);//一个用户名
            return count != -1;
        }
        return false;
    }

    public User getUser(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        User user = null;
        Cursor c = db
                .query(UserDao.USER_TABLE_NAME, null, UserDao.USER_COLUMN_NAME + " like ?", new String[]{userName}, null, null, null);
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex(UserDao.USER_COLUMN_NAME));
            String nick = c.getString(c.getColumnIndex(UserDao.USER_COLUMN_NICK));
            int muserId = c.getInt(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR));
            String mpath = c.getString(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_PATH));
            String suffix = c.getString(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_SUFFIX));
            int mType = c.getInt(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_TYPE));
            String lastTime = c.getString(c.getColumnIndex(UserDao.USER_COLUMN_AVATAR_UPDATA_TIME));
            user = new User();
            user.setMuserName(name);
            user.setMuserNick(nick);
            user.setMavatarId(muserId);
            user.setMavatarPath(mpath);
            user.setMavatarSuffix(suffix);
            user.setMavatarType(mType);
            user.setMavatarLastUpdateTime(lastTime);
        }
        return user;
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = db.delete(UserDao.USER_TABLE_NAME, UserDao.USER_COLUMN_NAME + " like ?", new String[]{username});
        return count > 0;
    }
}
