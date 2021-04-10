package com.example.tabtest.entity;

import android.widget.ImageView;
import com.example.tabtest.R;

import java.util.Random;

public class clickRecord {
    //上一次图片资源id
    private int lastClickId;
    //位置
    private int lastPosition;
    //ImageView
    private ImageView lastImageView;
    //记录该位置的图标是否被消除 1被消除 0未消除
    private int ClearImgIdArray[]=new int[72];
    //分数
    private int score;
    //模式
    private String level;

    public clickRecord() {
        lastPosition=72;
        lastClickId=R.drawable.p26;
        score=0;
    }


    public boolean isSame(int theClickId,int thePosition,ImageView theImageView){
        //点击图片相同，且不是同一位置，且不是空白处的
        if(lastClickId==theClickId&&lastPosition!=thePosition&&ClearImgIdArray[lastPosition]!=1&&ClearImgIdArray[thePosition]!=1){

            int x1=to2dPosition(lastPosition)[0]; int y1=to2dPosition(lastPosition)[1];
            int x2=to2dPosition(thePosition)[0];  int y2=to2dPosition(thePosition)[1];
            System.out.println("上次坐标：("+x1+","+y1+")  本次坐标：("+x2+","+y2+")");

            if(vertical(x1,y1,x2,y2)||horizontal(x1,y1,x2,y2)||turnOne(x1,y1,x2,y2)||turnTwo(x1,y1,x2,y2)){
                System.out.println("通过规则判断...");
                theImageView.setImageDrawable(null);
                lastImageView.setImageDrawable(null);
                ClearImgIdArray[lastPosition]=1;
                ClearImgIdArray[thePosition]=1;
                lastClickId=R.drawable.p26;
                score=score+2;
                return true;
            }
            else {
                System.out.println("1-两次点击结果相同，但不符合规则，记录上次点击");
                lastClickId=theClickId;
                lastPosition=thePosition;
                lastImageView=theImageView;
                return false;
            }

        }
        else {
            System.out.println("2-两次点击结果不同，记录上次点击");
            lastClickId=theClickId;
            lastPosition=thePosition;
            lastImageView=theImageView;
            return false;
        }
    }

    //垂直检测-无bug
    public boolean vertical(int x1,int y1,int x2,int y2){
        if(y1==y2){
            int max_x=max(x1,x2);
            int min_x=min(x1,x2);
            for(int i=min_x+1;i<max_x;i++){
                //有障碍
                if(ClearImgIdArray[to1dPosition(i,y1)]==0){
                    System.out.println("1-没通过垂直检测...");
                    return false;
                }
            }
            System.out.println("2-通过垂直检测!!!");
            return true;
        }
        System.out.println("3-没通过垂直检测...");
        return false;
    }

    //水平检测
    public boolean horizontal(int x1,int y1,int x2,int y2){
        if(x1==x2){
            int max_y=max(y1,y2);
            int min_y=min(y1,y2);
            for(int i=min_y+1;i<max_y;i++){
                if(ClearImgIdArray[to1dPosition(x1,i)]==0){
                    System.out.println("1-没通过水平检测...");
                    return false;
                }
            }
            System.out.println("2-通过水平检测!!!");
            return true;
        }
        System.out.println("3-没通过水平检测...");
        return false;
    }

    //一次拐点
    public boolean turnOne(int x1,int y1,int x2,int y2){
        //先向上垂直检测，再向右水平检测
        if(vertical(x1,y1,x2,y1)&&horizontal(x2,y1,x2,y2)&&ClearImgIdArray[to1dPosition(x2,y1)]==1){
            System.out.println("1-通过一次拐点检测!!!");
            return true;
        }
        //向右水平检测,再向上垂直检测
        if(horizontal(x1,y1,x1,y2)&&vertical(x1,y2,x2,y2)&&ClearImgIdArray[to1dPosition(x1,y2)]==1){
            System.out.println("2-通过一次拐点检测!!!");
            return true;
        }
        System.out.println("3-没通过一次拐点检测...");
        return false;
    }

    //二次拐点
    public boolean turnTwo(int x1,int y1,int x2,int y2){
        int max_x=max(x1,x2); int max_y=max(y1,y2);
        int min_x=min(x1,x2); int min_y=min(y1,y2);
        for(int i=0;i<8;i++){
            for(int j=0;j<9;j++){
                if((i==x1&&j==y1)||(i==x2&&j==y2)){
                    continue;
                }
                if(i!=x1&&j!=y1&&i!=x2&&j!=y2){
                    continue;
                }
                if(ClearImgIdArray[to1dPosition(i,j)]==0){
                    continue;
                }
                if((horizontal(x1,y1,i,j)||vertical(x1,y1,i,j))&&turnOne(i,j,x2,y2)){
                    System.out.println("1-通过二次拐点检测！！！");
                    return true;
                }
                if(turnOne(x1,y1,i,j)&&(horizontal(i,j,x2,y2)||vertical(i,j,x2,y2))){
                    System.out.println("2-通过二次拐点检测！！！");
                    return true;
                }
            }
        }
        System.out.println("3-未通过二次拐点检测...");
        return false;
    }

    //将reClearImgIdArray[]全置为零
    public void reClearImgIdArray(){
        for(int i=0;i<ClearImgIdArray.length;i++){
            ClearImgIdArray[i]=0;
        }
    }

    //将一维数组坐标转化为8*9的二维坐标
    public int[] to2dPosition(int position){
        int position_2d[]=new int[2];
        int x=position/9;
        int y=position%9;
        position_2d[0]=x;
        position_2d[1]=y;

        return position_2d;
    }

    //将二维坐标转化为一维坐标
    public int to1dPosition(int x,int y){
        int position_1d;
        position_1d=x*9+y;
     return position_1d;
    }

    //比大小
    public int max(int x1,int x2){
        if(x1>x2){
            return x1;
        }
        return x2;
    }

    public int min(int x1,int x2){
        if(x1<x2){
            return x1;
        }
        return x2;
    }


    public int getLastClickId() {
        return lastClickId;
    }

    public void setLastClickId(int lastClickId) {
        this.lastClickId = lastClickId;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public ImageView getLastImageView() {
        return lastImageView;
    }

    public void setLastImageView(ImageView lastImageView) {
        this.lastImageView = lastImageView;
    }

    public int[] getClearImgIdArray() {
        return ClearImgIdArray;
    }

    public void setClearImgIdArray(int[] ClearImgId) {
        this.ClearImgIdArray=ClearImgIdArray;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
