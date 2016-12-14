package com.example.xy.newsday.a.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MySqlite extends SQLiteOpenHelper{


    public MySqlite(Context context) {

        super(context, "user", null,3);
        //参数一：上下文对象；参数二：数据库名称；参数三：游标工厂；参数四：版本号；
    }
    //创建表
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建表
        String sql = "create table user_info (" +
                "title text ," +
                "link text," +
                "url text," +
                "pubDate text," +
                "desc text," +
                "allPages text)";
        sqLiteDatabase.execSQL(sql);//执行
    }
    //数据库版本改变时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
