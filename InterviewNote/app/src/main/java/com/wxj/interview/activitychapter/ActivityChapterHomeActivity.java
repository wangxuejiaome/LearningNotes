package com.wxj.interview.activitychapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.wxj.interview.R;

public class ActivityChapterHomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ChapterHomeActivity";
    private Button btnGoAActivity;
    private Button btnGoCSingleTaskActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_home);
        initView();
        Log.d(TAG, "onCreate: Task Id:" + getTaskId());
    }

    private void initView() {
        btnGoAActivity = findViewById(R.id.btn_go_a_activity);
        btnGoCSingleTaskActivity = findViewById(R.id.btn_go_c_single_task_activity);
        btnGoAActivity.setOnClickListener(this);
        btnGoCSingleTaskActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_go_a_activity:
                startActivity(new Intent(this, AActivity.class));
                break;
            case R.id.btn_go_c_single_task_activity:
                startActivity(new Intent(this, CSingleTaskActivity.class));
                break;
        }
    }
}