package wxj.viewlearn.eventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import wxj.viewlearn.R;

public class EventDispatchActivity extends Activity {

    private RelativeLayout mRelativeLayout;
    private LinearLayout mLinearLayout;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_dispatch);

        initView();
    }

    private void initView() {
        mRelativeLayout = findViewById(R.id.rl_container);
        mLinearLayout = findViewById(R.id.my_liner_layout);
        mButton = findViewById(R.id.my_button);

        mLinearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        mButton.getParent().requestDisallowInterceptTouchEvent(true);
                        // 手指按下
                        Log.e("ll", "downTest1");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 手指移动
                        Log.e("ll", "moveTest1");
                        break;
                    case MotionEvent.ACTION_UP:
                        // 手指抬起
                        Log.e("ll", "upTest1");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        // 事件被拦截
                        Log.e("ll", "cancelTest1");
                        break;
                }
                return false;
            }
        });

        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
//                        mButton.getParent().requestDisallowInterceptTouchEvent(true);
                        // 手指按下
                        Log.e("btn", "downTest1");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // 手指移动
                        Log.e("btn", "moveTest1");
                        break;
                    case MotionEvent.ACTION_UP:
                        // 手指抬起
                        Log.e("btn", "upTest1");
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        // 事件被拦截
                        Log.e("btn", "cancelTest1");
                        break;
                }
                return false;
            }
        });
    }

}