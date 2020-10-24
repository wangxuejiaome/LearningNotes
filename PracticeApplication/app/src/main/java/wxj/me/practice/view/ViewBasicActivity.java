package wxj.me.practice.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;

import wxj.me.practice.R;

public class ViewBasicActivity extends AppCompatActivity {

    public static final String TAG = "ViewBasicActivity";

    Button mBtnCoordinate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_basic);
        initView();
    }

    private void initView() {
        mBtnCoordinate = findViewById(R.id.btn_coordinate);
        mBtnCoordinate.post(new Runnable() {
            @Override
            public void run() {
                printCoordinateInfo();
                translationButton();
            }
        });
    }



    private void translationButton() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBtnCoordinate,"translationX",-100);
        objectAnimator.start();
        Log.d(TAG, "translationButton: translationX:100");
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                printCoordinateInfo();

                getDisplayMetrics(ViewBasicActivity.this);
                printDisplayMetrics(ViewBasicActivity.this);
            }
        });
    }

    private void printCoordinateInfo() {
        Log.d(TAG,"left:" + mBtnCoordinate.getLeft() + ",right:" + mBtnCoordinate.getRight() + ",translationX:" + mBtnCoordinate.getTranslationX()
                + ",x:" + mBtnCoordinate.getX());
    }

    public static DisplayMetrics getDisplayMetrics(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            windowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        }else{
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public static void printDisplayMetrics(Context context){
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        Log.v(TAG,"---printDisplayMetrics---" +
                "widthPixels=" + displayMetrics.widthPixels
                + ", heightPixels=" + displayMetrics.heightPixels
                + ", density=" + displayMetrics.density
                + ", densityDpi="+displayMetrics.densityDpi);
    }


}
