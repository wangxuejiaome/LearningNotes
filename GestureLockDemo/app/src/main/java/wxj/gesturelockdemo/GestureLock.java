package wxj.gesturelockdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Measure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author wxj
 */
public class GestureLock extends View {

    public static final String TAG = "GestureLock";

    private BitmapDrawable mBitmapNormal;
    private BitmapDrawable mBitmapPress;
    /**
     * GestureLock 设计为宽与高相等，为正方形
     */
    private int mGestureLockSize;
    /**
     * 图形之间的间隔，每行或每列有 4 个间隔
     */
    private int mSpace;
    /**
     * 设置的图片大小
     */
    private int mBitmapSize;

    public GestureLock(Context context) {
        super(context);
        init();
    }

    public GestureLock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GestureLock);
        mBitmapNormal = (BitmapDrawable) typedArray.getDrawable(R.styleable.GestureLock_bitmap_normal);
        mBitmapPress = (BitmapDrawable) typedArray.getDrawable(R.styleable.GestureLock_bitmap_press);
        mSpace = typedArray.getInteger(R.styleable.GestureLock_space,0);
        typedArray.recycle();
        init();
    }

    public GestureLock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        mBitmapSize = mBitmapNormal.getIntrinsicWidth();

        if(mSpace == 0){
            // 如果没有设置 space 属性，则根据系统测量的大小来自己测算space
            mGestureLockSize =  Math.min(widthSpecSize,heightSpecSize);
            int space = (mGestureLockSize - mBitmapSize * 3) / 4;
            mSpace = space > 0 ? space : 0;
        }else {
            // 根据给定的 space 来决定 GestureLock 的大小
            mGestureLockSize = mBitmapSize * 3 + mSpace * 4;
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(mGestureLockSize,widthSpecMode),MeasureSpec.makeMeasureSpec(mGestureLockSize,heightSpecMode));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        for (int row = 1; row <= 3; row++) {
            for (int col = 1; col <= 3; col++) {
                int left = (col - 1) * mBitmapSize + col * mSpace;
                int top = (row -1) * mBitmapSize + row * mSpace;
                canvas.drawBitmap(mBitmapNormal.getBitmap(),left,top,new Paint());
            }
        }
    }

    private void init() {

    }
}
