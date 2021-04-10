package com.example.tabtest.entity;

import com.example.tabtest.R;

import java.util.ArrayList;
import java.util.Random;

public class reSetData {
    private ArrayList<itemPhoto> mData;
    private int ImgIdArray[]=new int[73];           //存储生成的小图标的id信息

    public reSetData(int kindNumber) {
        mData=new ArrayList<itemPhoto>();
        int minImg[]={R.drawable.p1,R.drawable.p2,R.drawable.p3,R.drawable.p4, R.drawable.p5,
                R.drawable.p6,R.drawable.p7,R.drawable.p8, R.drawable.p9,R.drawable.p10,
                R.drawable.p11,R.drawable.p12, R.drawable.p13,R.drawable.p14,R.drawable.p15,
                R.drawable.p16, R.drawable.p17,R.drawable.p18,R.drawable.p19,R.drawable.p20,
                R.drawable.p21,R.drawable.p22,R.drawable.p23,R.drawable.p24,R.drawable.p25};

        Random r = new Random();
        for(int i=0;i<72;i++){
            //生成随机数
            int randomNumber=r.nextInt(kindNumber);
            int pid=minImg[randomNumber];
            //将随机生成的图片id记录在ImgArray中
            ImgIdArray[i]=pid;
            System.out.println("初始化图片-onInitImg："+pid+"随机数："+randomNumber);
            mData.add(new itemPhoto(pid));
        }
    }


    public ArrayList<itemPhoto> getmData() {
        return mData;
    }

    public int[] getImgIdArray() {
        return ImgIdArray;
    }
}
