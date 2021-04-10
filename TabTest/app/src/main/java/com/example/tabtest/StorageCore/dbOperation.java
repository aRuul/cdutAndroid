package com.example.tabtest.StorageCore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class dbOperation {
    private dataBaseTool db;
    private SQLiteDatabase dbTool;

    public dbOperation(dataBaseTool db) {
        this.db = db;
        dbTool = db.getWritableDatabase();
    }

    public void insertData(String name,String level,int score){
        ContentValues theValues = new ContentValues();
        theValues.put("Name",name);
        theValues.put("Level",level);
        theValues.put("Score",score);
        dbTool.insert("user",null,theValues);
    }

    public String findAll(){
        Cursor cursor = dbTool.query("user", new String[]{"Name","Level","Score"}, null, null, null, null, null);
        //利用游标遍历所有数据对象
        //为了显示全部，把所有对象连接起来
        String textview_data = "";
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("Name"));
            String level = cursor.getString(cursor.getColumnIndex("Level"));
            int score=cursor.getInt(cursor.getColumnIndex("Score"));
            textview_data = textview_data + "\n" + name+"\t"+level+"\t"+score;
        }
        System.out.println(textview_data);
        return textview_data;
    }
}
