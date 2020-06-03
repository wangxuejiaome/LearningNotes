package com.wxj.interview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRvContents;
    private ContentsAdapter mContentAdapter;
    private List<String> mContentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mRvContents = findViewById(R.id.rv_contents);
        mRvContents.setLayoutManager(new LinearLayoutManager(this));
        mContentAdapter = new ContentsAdapter(this, mContentList);
        mRvContents.setAdapter(mContentAdapter);
    }


    private void initData() {
        mContentList.add("Activity");
        mContentAdapter.notifyDataSetChanged();
    }
}