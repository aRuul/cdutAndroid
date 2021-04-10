package com.example.tabtest.entity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TimeTickLoad extends AsyncTask<Integer,Integer,String> {
    private ProgressBar progressBar;
    private TextView textView;
    private Context mContext;
    clickRecord record;

    public TimeTickLoad(ProgressBar progressBar, TextView textView, Context mContext,clickRecord record) {
        super();
        this.progressBar=progressBar;
        this.textView=textView;
        this.mContext=mContext;
        this.record=record;
    }

    @Override
    //预处理
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(ProgressBar.VISIBLE);
    }

    @Override
    //对后台任务的结果处理
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText("时间到...");

        Intent intent=new Intent();
        intent.setComponent(new ComponentName("com.example.tabtest","com.example.tabtest.myBroadcastReceiver.myReceiver"));
        Bundle bundle = new Bundle();
        System.out.println("游戏结束，数据为："+record.getLevel()+" ,"+record.getScore());
        bundle.putString("level",record.getLevel());
        bundle.putInt("score",record.getScore());
        intent.putExtras(bundle);
        intent.setAction("goToList");
        mContext.sendBroadcast(intent);

    }

    @Override
    //对进度条控件根据进度值做出具体响应
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
        int lastTime=100-values[0];
        textView.setText("时间还剩"+lastTime+"s");
        super.onProgressUpdate(values);
    }


    @Override
    //关键方法
    protected String doInBackground(Integer... integers) {
        for(int i=0;i<=100;i++){
            publishProgress(i);

            try {
                Thread.sleep(integers[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "ok";
    }

}
