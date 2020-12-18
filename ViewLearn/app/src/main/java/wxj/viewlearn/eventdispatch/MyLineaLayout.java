package wxj.viewlearn.eventdispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 文件名： MyLineaLayout
 * 创建者： 18113881
 * 创建日期： 2020/12/18 9:35
 * 描述：
 */
public class MyLineaLayout extends LinearLayout {


    public MyLineaLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            return false;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }
}
