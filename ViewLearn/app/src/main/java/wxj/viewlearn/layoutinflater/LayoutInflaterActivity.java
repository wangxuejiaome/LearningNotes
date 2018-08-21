package wxj.viewlearn.layoutinflater;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import wxj.viewlearn.R;

public class LayoutInflaterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_inflater);
        FrameLayout flContainer = findViewById(R.id.fl_container);
        // 强转为 FrameLayout，证明返回的是 flContainer，并不是 button
        FrameLayout inflateResult = (FrameLayout) getLayoutInflater().inflate(R.layout.button_layout,flContainer);
    }
}
