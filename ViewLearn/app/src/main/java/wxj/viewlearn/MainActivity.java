package wxj.viewlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import wxj.viewlearn.layoutinflater.LayoutInflaterActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLayoutInflater = findViewById(R.id.btn_layoutInflater);
        btnLayoutInflater.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_layoutInflater:
                startActivity(new Intent(this,LayoutInflaterActivity.class));
                break;
        }
    }
}
