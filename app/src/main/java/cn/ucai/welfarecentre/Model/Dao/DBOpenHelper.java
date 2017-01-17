package cn.ucai.welfarecentre.Model.Dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import cn.ucai.welfarecentre.Model.utils.I;

/**
 * Created by Administrator on 2017/1/17 0017.
 * 创建数据库
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static  String FULICENTER_USER_TABLE_CREATE;
    private static DBOpenHelper instance;
    public static DBOpenHelper getInstance(Context context){
        if (instance == null){
            instance = new DBOpenHelper(context);
        }
        return instance;
    }
    public DBOpenHelper(Context context) {
        super(context, getUserDataBaseName(), null, DATABASE_VERSION);
    }
    private static String getUserDataBaseName(){
        return "cn.ucai.fulicenter.db";
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        FULICENTER_USER_TABLE_CREATE = "create table " + UserDao.USER_TABLE_NAME +"("
                + UserDao.USER_COLUMN_NAME + " text primary key, "
                + UserDao.USER_COLUMN_NICK + " text,"
                + UserDao.USER_COLUMN_AVATAR + " integer,"
                + UserDao.USER_COLUMN_AVATAR_PATH +" text,"
                + UserDao.USER_COLUMN_AVATAR_TYPE + " integer,"
                + UserDao.USER_COLUMN_AVATAR_SUFFIX + " text,"
                + UserDao.USER_COLUMN_AVATAR_UPDATA_TIME + " text);";
        sqLiteDatabase.execSQL(FULICENTER_USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersionCode, int newVersionCode) {
        Log.i("main", "数据库的老版本:" + oldVersionCode + ",新版本:" + newVersionCode);
    }
}
