package com.example.tabtest.StorageCore;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferenceTool {
    private Context mContext;

    public SharedPreferenceTool(Context mContext) {
        this.mContext = mContext;
    }

    //保存数据
    public void save(String bgPreference) {
        SharedPreferences sp = mContext.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bgPreference", bgPreference);
        editor.commit();
        Toast.makeText(mContext, "SharedPreference信息设为"+bgPreference, Toast.LENGTH_SHORT).show();
    }

    //读取SP文件
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        data.put("bgPreference", sp.getString("bgPreference", ""));
        return data;
    }
}
