package com.example.tabtest.myBroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.tabtest.RankListActivity;
import com.example.tabtest.StorageCore.dataBaseTool;
import com.example.tabtest.StorageCore.dbOperation;

public class myReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("李相孺","收到广播");

        Bundle bundle = intent.getExtras();
        int score=bundle.getInt("score");
        String level=bundle.getString("level");
        dataBaseTool db=new dataBaseTool(context,"rank",null,1);
        dbOperation dbTool=new dbOperation(db);
        dbTool.insertData("测试1号",level,score);

//        int score=intent.getIntExtra("score",0);
        System.out.println("广播中的数据: "+score+" "+level);
        Toast.makeText(context,"游戏结束！",Toast.LENGTH_LONG).show();

        Intent intent2=new Intent(context, RankListActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtras(bundle);
        context.startActivity(intent2);
    }
}
