package wxj.me.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import wxj.me.practice.datastore.DataStoreActivity;
import wxj.me.practice.fragment.ContainerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDataStore;
    private Button btnGoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnDataStore = findViewById(R.id.btn_store_data);
        btnGoFragment = findViewById(R.id.btn_fragment);
        btnDataStore.setOnClickListener(this);
        btnGoFragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_store_data:
                startActivity(new Intent(this, DataStoreActivity.class));
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(this, ContainerActivity.class));
                break;
        }
    }
}
