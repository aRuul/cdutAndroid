package com.example.tabtest;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabtest.StorageCore.dataBaseTool;
import com.example.tabtest.StorageCore.dbOperation;
import com.example.tabtest.adapter.myAdapter;
import com.example.tabtest.entity.TimeTickLoad;
import com.example.tabtest.entity.clickRecord;
import com.example.tabtest.entity.itemPhoto;
import com.example.tabtest.entity.playMusic;
import com.example.tabtest.entity.reSetData;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameMainFragment newInstance(String param1, String param2) {
        GameMainFragment fragment = new GameMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//-------------------------------------------------------------------------------------
    private Context mContext;
    private GridView grid_photo;
    private TextView score;
    private TextView gameLevel;
    private myAdapter mAdapter=null;
    private ArrayList<itemPhoto> mData=null;
    private int ImgIdArray[]=new int[73];           //存储生成的小图标的id信息
    private int kindNumber=10;
    private clickRecord record;   //用于记录上次点击的entity类
    private reSetData setData;
    private playMusic music;
    private ProgressBar progressBar;
    private TextView timeLeft;
    private boolean isBegin=false;   //是否点击了开始游戏
    private MainActivity mainActivity;



    public GameMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //mContext=MainActivity.this;
        mContext=getContext();
        View view =inflater.inflate(R.layout.fragment_game_main, container, false);
        mainActivity=(MainActivity)getActivity();


        record=new clickRecord();
        record.setLevel("简单模式");  //默认
        music=new playMusic(mContext);

        //进度条
        progressBar=view.findViewById(R.id.Async_progressBar);
        timeLeft=view.findViewById(R.id.Async_txt1);


        grid_photo=(GridView)view.findViewById(R.id.grid_photo);
        score=view.findViewById(R.id.gameScore);
        gameLevel=view.findViewById(R.id.gameLevel);
        mData=new ArrayList<itemPhoto>();

        setData=new reSetData(kindNumber);
        ImgIdArray=setData.getImgIdArray();
        mData=setData.getmData();

        mAdapter=new myAdapter(mData,mContext);
        grid_photo.setAdapter(mAdapter);


        //长按游戏模式--弹出菜单
        gameLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence level=gameLevel.getText();
                PopupMenu popupMenu=new PopupMenu(mContext,v);
                popupMenu.getMenuInflater().inflate(R.menu.game_pop_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("点击了弹出菜单");

                        if(level.equals("简单模式")){
                            Log.d("点击事件","简单模式重拍");
                            setData=new reSetData(10);
                        }
                        if(level.equals("普通模式")){
                            Log.d("点击事件","普通模式重拍");
                            setData=new reSetData(15);
                        }
                        if(level.equals("困难模式")){
                            Log.d("点击事件","困难模式重拍");
                            setData=new reSetData(25);
                        }
                        mData=setData.getmData();
                        ImgIdArray=setData.getImgIdArray();
                        mAdapter.setmData(mData);
                        record.reClearImgIdArray();
                        mAdapter.notifyDataSetChanged();
                        return true;
                    }
                });
                //弹出菜单
                popupMenu.show();
            }
        });

        //点击监听器
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //debug输出
                Log.d("click","点击事件");

                if(isBegin){
                    System.out.println("本次点击的位置position: "+position);
                    music.play(2,false);
                    ImageView tempImg=view.findViewById(R.id.minPhoto);
                    boolean isSame=record.isSame(ImgIdArray[position],position,tempImg);
                    if(isSame){
                        music.play(3,false);
                    }
                    score.setText("分数： "+String.valueOf(record.getScore()));
                }
                else {
                    Toast.makeText(mContext,"请点击开始游戏",Toast.LENGTH_LONG).show();
                }

            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eggs++;
                if(eggs==3){
                    Toast.makeText(mContext,unicodeToString(testshow),Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.game_meau,menu);

        menu.add(1,6,0,"开始");
        menu.add(1,1,0,"简单");
        menu.add(1,2,0,"普通");
        menu.add(1,3,0,"困难");

        //menu.add(2,4,0,"背景音乐");
        SubMenu bgSwitch=menu.addSubMenu("背景音乐");
        bgSwitch.add(1,4,0,"开");
        bgSwitch.add(1,5,0,"关");
    }

    @Override
    //选项菜单--点击事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 6:
                Log.d("菜单点击事件","点击了开始");
                TimeTickLoad timeTickLoad=new TimeTickLoad(progressBar,timeLeft,mContext,record);
                timeTickLoad.execute(1000);
                isBegin=true;
                break;
            case 1:
                Log.d("菜单点击事件","点击了简单");
                gameLevel.setText("简单模式");
                record.setLevel("简单模式");
                setData=new reSetData(10);
                break;
            case 2:
                Log.d("菜单点击事件","点击了普通");
                gameLevel.setText("普通模式");
                record.setLevel("普通模式");
                setData=new reSetData(15);
                break;
            case 3:
                Log.d("菜单点击事件","点击了困难");
                gameLevel.setText("困难模式");
                record.setLevel("困难模式");
                setData=new reSetData(25);
                break;
            case 4:
                Log.d("菜单点击事件","点击了开背景音乐");
                mainActivity.getMediaPlayer().setLooping(true);
                mainActivity.getMediaPlayer().start();
                //music.play(1,true);
                return true;
            case 5:
                Log.d("菜单点击事件","点击了关背景音乐");
                mainActivity.getMediaPlayer().pause();
                //music.release();
                return true;
            default:
                Log.d("菜单点击事件","...");
                break;
        }
        mData=setData.getmData();
        ImgIdArray=setData.getImgIdArray();
        mAdapter.setmData(mData);
        record.reClearImgIdArray();
        record.setScore(0);
        score.setText("分数：0");
        mAdapter.notifyDataSetChanged();
        return true;
    }

    String testshow="\\u6d4b\\u8bd5";
    int eggs=0;
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
