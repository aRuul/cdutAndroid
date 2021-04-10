package com.example.tabtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.tabtest.StorageCore.dataBaseTool;
import com.example.tabtest.StorageCore.dbOperation;

public class RankListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

        dataBaseTool db=new dataBaseTool(RankListActivity.this,"rank",null,1);
        dbOperation dbTool=new dbOperation(db);
        String data=dbTool.findAll();

        TextView dataText=findViewById(R.id.rank_data);
        dataText.setText(data);

    }
}