package com.wxj.testdemo;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;
    private FilterDialogFragment filterDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (getSupportFragmentManager().findFragmentByTag("FilterFragmentDialog") == null) {
            filterDialogFragment = new FilterDialogFragment();
            transaction.add(filterDialogFragment, "FilterFragmentDialog");
        } else {
            transaction.show(filterDialogFragment);
        }
        transaction.commit();

    }

    public void getFramentCounts() {
        Log.d("MainActivity", "getFragments count: " + getSupportFragmentManager().getFragments().size());

    }
}