package com.wxj.interview.activitychapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.wxj.interview.R;

public class DSingleTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "DSingleTaskActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_single_task);
        initView();
        Log.d(TAG, "onCreate: Task Id:" + getTaskId());
    }

    private void initView() {
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ESingleTaskActivity.class));
    }

}