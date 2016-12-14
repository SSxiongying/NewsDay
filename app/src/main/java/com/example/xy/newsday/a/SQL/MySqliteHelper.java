package com.example.xy.newsday.a.SQL;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.xy.newsday.a.bean.NesData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/17.
 */
public class MySqliteHelper {
    MySqlite mySqlite=null;
    SQLiteDatabase sqLiteDatabase=null;

    public MySqliteHelper(Context context){
        mySqlite = new MySqlite(context);
        sqLiteDatabase = mySqlite.getReadableDatabase();
        Log.e("MySqliteHelper: ","nihao");
    }
    //添加
    public void addUser(String str,String str1,String str2,String str3,String str4){
        String sql = "insert into user_info(title,link,url,pubDate,desc,allPages) values('"+str+"','"+str1+"','"+str2+"','"+str3+"','"+str4+"','100')";
        Log.e("MySqliteHelper",sql);
        sqLiteDatabase.execSQL(sql);
    }
    //删除
    public void delete(String str){
//      DELETE FROM 表名称 WHERE 列名称 = 值
        String sql = "delete from user_info where title = '"+str+"'";
        sqLiteDatabase.execSQL(sql);
    }
    //修改
    public void update(String strqian,String strhou){
//       UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
        String sql = "update user_info set title = '"+strqian+"' where title = '"+strhou+"'";
        sqLiteDatabase.execSQL(sql);
    }

    //查询
    public ArrayList<NesData> fandUser(){
        ArrayList<NesData> list = new ArrayList<>();
//      SELECT 列名称 FROM 表名称 WHERE 列 运算符 值
        String sql = "select * from user_info";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToFirst();//把游标移到第一行
        while (!cursor.isAfterLast()){
            String title = cursor.getString(cursor.getColumnIndex("title"));
            Log.e("MySqliteHelper", title.toString());
            String link = cursor.getString(cursor.getColumnIndex("link"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String pubDate = cursor.getString(cursor.getColumnIndex("pubDate"));
            String content = cursor.getString(cursor.getColumnIndex("desc"));
            String pages=cursor.getString(cursor.getColumnIndex("allPages"));
            NesData myData = new NesData(title,content,pubDate,link,pages,url);
            //将数据加入集合
            list.add(myData);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    //查重
    public int chakan(String str){
        String sql = "select title from user_info where title = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,new String[]{str});
        cursor.moveToFirst();//把游标移到第一行
        cursor.close();
        return cursor.getCount();
    }
}
