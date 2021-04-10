package com.example.tabtest.entity;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.example.tabtest.R;

import java.util.HashMap;

public class playMusic {
    String MUSIC="音乐";

    private SoundPool mSoundPool=new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
    private HashMap<Integer, Integer> soundID = new HashMap<Integer, Integer>();
    private Context mContext;

    public playMusic(Context mContext) {
        this.mContext = mContext;
        onInitMusic();
    }

    public void onInitMusic(){
        Log.d(MUSIC,"OnInitMusic()...");
        //1-背景音乐 2-点击音效 3-消除音效
        soundID.put(1,mSoundPool.load(mContext,R.raw.background,1));
        soundID.put(2,mSoundPool.load(mContext,R.raw.click,1));
        soundID.put(3,mSoundPool.load(mContext,R.raw.success,1));
        soundID.put(4,mSoundPool.load(mContext,R.raw.fail,1));
    }

    public void play(int id,boolean loop){
        Log.d(MUSIC,"play");
        //true的时候循环播放
        if(loop){
            mSoundPool.play(soundID.get(id),1, 1, 0, -1, 1);
        }
        mSoundPool.play(soundID.get(id),1, 1, 0, 0, 1);
        //第一个参数soundID
        //第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
        //第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
        //第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
        //第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
        //第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
    }

    public void release(){
        mSoundPool.autoPause();
    }
}
