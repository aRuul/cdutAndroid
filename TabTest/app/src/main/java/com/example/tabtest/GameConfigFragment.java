package com.example.tabtest;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tabtest.StorageCore.SharedPreferenceTool;
import com.example.tabtest.entity.playMusic;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameConfigFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameConfigFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameConfigFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameConfigFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameConfigFragment newInstance(String param1, String param2) {
        GameConfigFragment fragment = new GameConfigFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    //--------------------------------------------------------------------------------------------------

    private Context mContext;
    private Switch aSwitch;
    SharedPreferenceTool sharedPreferenceTool;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_game_config, container, false);
        mContext=getContext();

        sharedPreferenceTool=new SharedPreferenceTool(mContext);
        aSwitch=view.findViewById(R.id.switch_background);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Log.d("开关","开");
                    sharedPreferenceTool.save("on");
                }
                else {
                    Log.d("开关","关");
                    sharedPreferenceTool.save("off");
                }
                Toast.makeText(mContext, "个人偏好设置将在app重启后生效", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


}