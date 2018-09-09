package wxj.me.practice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import wxj.me.practice.datastore.DataStoreActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnDataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        btnDataStore = findViewById(R.id.btn_store_data);
        btnDataStore.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_store_data:
                startActivity(new Intent(this, DataStoreActivity.class));
                break;
        }
    }
}
