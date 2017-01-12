package cn.ucai.welfarecentre.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import cn.ucai.welfarecentre.R;

/**
 * Created by Administrator on 2017/1/12 0012.
 */

public class FlowIndicator extends View {
    int mCount;//指示器中实心圆数量
    int mRadius;//实心圆半径
    int mSpace;//实心圆之前的距离
    int mFocus;//当前实心圆的颜色
    int mNormalColor;//非焦点实心圆的颜色
    int mFocusColor;//焦点实心圆的颜色

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
        requestLayout();//通知重新布局，回调onMeasure、onLayout和onDraw
    }

    public int getmFocus() {
        return mFocus;
    }

    public void setmFocus(int mFocus) {
        this.mFocus = mFocus;
        invalidate();//通知回调onDraw方法
    }

    Paint mPaint;
    public FlowIndicator(Context context) {
        super(context);
    }

    public FlowIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FlowIndicator);
        mNormalColor = array.getColor(R.styleable.FlowIndicator_normal_color,
                0xffffff);
        mFocusColor = array.getColor(R.styleable.FlowIndicator_focus_color,
                0xffff07);
        mSpace = array.getDimensionPixelOffset(R.styleable.FlowIndicator_space,
                6);
        mRadius = array.getDimensionPixelOffset(
                R.styleable.FlowIndicator_r, 9);
        mCount=array.getInt(R.styleable.FlowIndicator_count,1);
        mFocus=array.getInt(R.styleable.FlowIndicator_focus, 0);

        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        Log.i("main", "onMeasure()");
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result=size;
        if (mode != MeasureSpec.EXACTLY) {
            size=2*mRadius+getPaddingTop()+getPaddingBottom();
            size = Math.min(size, result);
        }
        return size;
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode != MeasureSpec.EXACTLY) {
            int result=size;
            size=getPaddingLeft()+getPaddingRight()+2*mRadius*mCount+(mCount-1)*mSpace;
            size = Math.min(size, result);
        }
        return size;
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("main", "onLayout()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("main", "onDraw()");
        super.onDraw(canvas);
        int left=(getWidth()-(getPaddingLeft()+getPaddingRight()+2*mRadius*mCount+(mCount-1)*mSpace))/2;
        for(int i=1;i<=mCount;i++) {
            int color=mFocus==i-1?mFocusColor:mNormalColor;
            mPaint.setColor(color);
            int x=left+(i-1)*(2*mRadius+mSpace)+mRadius;//以圆中心为准
            canvas.drawCircle(x,mRadius,mRadius,mPaint);//x,y，半径
        }
    }

}
