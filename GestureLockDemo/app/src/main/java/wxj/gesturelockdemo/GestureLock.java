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

    public GestureLock(Context context) {
        super(context);
        init();
    }

    public GestureLock(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GestureLock);
        mBitmapNormal = (BitmapDrawable) typedArray.getDrawable(R.styleable.GestureLock_bitmap_normal);
        mBitmapPress = (BitmapDrawable) typedArray.getDrawable(R.styleable.GestureLock_bitmap_press);
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

        mGestureLockSize =  Math.max(widthSpecSize,heightSpecSize);
        int bitmapSize = mBitmapNormal.getIntrinsicWidth();
        int space = (mGestureLockSize - bitmapSize * 3) / 4;
        mSpace = space > 0 ? space : 0;

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(mGestureLockSize,widthSpecMode),MeasureSpec.makeMeasureSpec(mGestureLockSize,heightSpecMode));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmapNormal = mBitmapNormal.getBitmap();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                //int top =
            }
        }

        //super.onDraw(canvas);
        canvas.drawBitmap(mBitmapNormal.getBitmap(),0,0,new Paint());
    }

    private void init() {

    }
}
