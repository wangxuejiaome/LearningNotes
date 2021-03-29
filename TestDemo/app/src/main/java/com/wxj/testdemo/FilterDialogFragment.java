package com.wxj.testdemo;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class FilterDialogFragment extends DialogFragment {

    public static final String TAG = "FilterDialogFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FilterDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_filter, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.75);
        Log.d("FilterDialogFragment", "width: " + layoutParams.width);
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.END;
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xff23ffff));
        getDialog().getWindow().setAttributes(layoutParams);

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getFragmentManager().beginTransaction().hide(FilterDialogFragment.this).commit();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((MainActivity)getActivity()).getFramentCounts();
                        }
                    },1000);

                    return true;
                }
                return false;
            }
        });
    }
}
