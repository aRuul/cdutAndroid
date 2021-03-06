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
    private int ImgIdArray[]=new int[73];           //???????????????????????????id??????
    private int kindNumber=10;
    private clickRecord record;   //???????????????????????????entity???
    private reSetData setData;
    private playMusic music;
    private ProgressBar progressBar;
    private TextView timeLeft;
    private boolean isBegin=false;   //???????????????????????????
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
        record.setLevel("????????????");  //??????
        music=new playMusic(mContext);

        //?????????
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


        //??????????????????--????????????
        gameLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence level=gameLevel.getText();
                PopupMenu popupMenu=new PopupMenu(mContext,v);
                popupMenu.getMenuInflater().inflate(R.menu.game_pop_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        System.out.println("?????????????????????");

                        if(level.equals("????????????")){
                            Log.d("????????????","??????????????????");
                            setData=new reSetData(10);
                        }
                        if(level.equals("????????????")){
                            Log.d("????????????","??????????????????");
                            setData=new reSetData(15);
                        }
                        if(level.equals("????????????")){
                            Log.d("????????????","??????????????????");
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
                //????????????
                popupMenu.show();
            }
        });

        //???????????????
        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //debug??????
                Log.d("click","????????????");

                if(isBegin){
                    System.out.println("?????????????????????position: "+position);
                    music.play(2,false);
                    ImageView tempImg=view.findViewById(R.id.minPhoto);
                    boolean isSame=record.isSame(ImgIdArray[position],position,tempImg);
                    if(isSame){
                        music.play(3,false);
                    }
                    score.setText("????????? "+String.valueOf(record.getScore()));
                }
                else {
                    Toast.makeText(mContext,"?????????????????????",Toast.LENGTH_LONG).show();
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

        menu.add(1,6,0,"??????");
        menu.add(1,1,0,"??????");
        menu.add(1,2,0,"??????");
        menu.add(1,3,0,"??????");

        //menu.add(2,4,0,"????????????");
        SubMenu bgSwitch=menu.addSubMenu("????????????");
        bgSwitch.add(1,4,0,"???");
        bgSwitch.add(1,5,0,"???");
    }

    @Override
    //????????????--????????????
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 6:
                Log.d("??????????????????","???????????????");
                TimeTickLoad timeTickLoad=new TimeTickLoad(progressBar,timeLeft,mContext,record);
                timeTickLoad.execute(1000);
                isBegin=true;
                break;
            case 1:
                Log.d("??????????????????","???????????????");
                gameLevel.setText("????????????");
                record.setLevel("????????????");
                setData=new reSetData(10);
                break;
            case 2:
                Log.d("??????????????????","???????????????");
                gameLevel.setText("????????????");
                record.setLevel("????????????");
                setData=new reSetData(15);
                break;
            case 3:
                Log.d("??????????????????","???????????????");
                gameLevel.setText("????????????");
                record.setLevel("????????????");
                setData=new reSetData(25);
                break;
            case 4:
                Log.d("??????????????????","????????????????????????");
                mainActivity.getMediaPlayer().setLooping(true);
                mainActivity.getMediaPlayer().start();
                //music.play(1,true);
                return true;
            case 5:
                Log.d("??????????????????","????????????????????????");
                mainActivity.getMediaPlayer().pause();
                //music.release();
                return true;
            default:
                Log.d("??????????????????","...");
                break;
        }
        mData=setData.getmData();
        ImgIdArray=setData.getImgIdArray();
        mAdapter.setmData(mData);
        record.reClearImgIdArray();
        record.setScore(0);
        score.setText("?????????0");
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
