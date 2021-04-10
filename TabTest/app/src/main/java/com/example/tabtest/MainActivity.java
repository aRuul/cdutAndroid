package com.example.tabtest;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.tabtest.StorageCore.SharedPreferenceTool;
import com.example.tabtest.adapter.myAdapter;
import com.example.tabtest.entity.clickRecord;
import com.example.tabtest.entity.itemPhoto;
import com.example.tabtest.entity.playMusic;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabtest.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;

    private MediaPlayer mediaPlayer; //背景音乐
    private Handler handler=new Handler();
    private SharedPreferenceTool sharedPreferenceTool;

    Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            Log.d("背景音乐",": "+sharedPreferenceTool.read().get("bgPreference"));
            if(sharedPreferenceTool.read().get("bgPreference").equals("on")){
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
                Log.d("背景音乐","再线程中启动了音乐");
            }
            if(sharedPreferenceTool.read().get("bgPreference").equals("off")){
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    Log.d("背景音乐","再线程中关闭了音乐");
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        sharedPreferenceTool=new SharedPreferenceTool(getApplicationContext());
        mediaPlayer=MediaPlayer.create(this, R.raw.background);
        Toast.makeText(this,unicodeToString(testshow),Toast.LENGTH_LONG).show();
        //启动两秒后
        handler.postAtTime(mRunnable, SystemClock.uptimeMillis()+2*1000);

    }


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    String testshow="\\u674e\\u76f8\\u5b7a\\u6d4b\\u8bd5";

    public static String unicodeToString(String unicode) {
        StringBuffer sb = new StringBuffer();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }



}