package com.example.tabtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.tabtest.entity.itemPhoto;
import com.example.tabtest.entity.reSetData;
import com.example.tabtest.R;
import java.util.ArrayList;

public class myAdapter extends BaseAdapter {
    //itemPhoto链表
    private ArrayList<itemPhoto> mData;
    private Context mContext;

    public ArrayList<itemPhoto> getmData() {
        return mData;
    }

    public void setmData(ArrayList<itemPhoto> mData){
        this.mData=mData;
    }

    public Context getmContext() {
        return mContext;
    }

    public myAdapter(ArrayList<itemPhoto> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    //需要显示的数据数量
    public int getCount() {
        return mData.size();
    }

    @Override
    //指定的索引对应的数据项
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    //指定的索引对应的数据项ID
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_photo,parent,false);
        }

        ImageView minImg=(ImageView)convertView.findViewById(R.id.minPhoto);
        minImg.setImageResource(mData.get(position).getId());

        //System.out.println("myAdapter中图片id"+mData.get(position).getId());
        return convertView;
    }
}
