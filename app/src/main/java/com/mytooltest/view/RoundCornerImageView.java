package com.mytooltest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;

/**
 * 圆角图片
 */
public class RoundCornerImageView extends android.support.v7.widget.AppCompatImageView {

    private static final int CORNER_TYPE_SINGLE = 0;
    private static final int CORNER_TYPE_EACH = 1;

    private Context context;

    private int mCornerType;
    private float mRadius;
    private float[] mFloatRadii;

    private Path mClipPath = new Path();
    private RectF mRect = new RectF();

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);

        mCornerType = ta.getInt(R.styleable.RoundCornerImageView_corner_type, CORNER_TYPE_SINGLE);
        if (mCornerType == CORNER_TYPE_EACH) {

            float ltRadius = ta.getDimension(R.styleable.RoundCornerImageView_corner_lt_radius, 0);
            float rtRadius = ta.getDimension(R.styleable.RoundCornerImageView_corner_rt_radius, 0);
            float rbRadius = ta.getDimension(R.styleable.RoundCornerImageView_corner_rb_radius, 0);
            float lbRadius = ta.getDimension(R.styleable.RoundCornerImageView_corner_lb_radius, 0);
            mFloatRadii = new float[] { ltRadius, ltRadius, rtRadius, rtRadius, rbRadius, rbRadius, lbRadius, lbRadius };
        } else {

            mRadius = ta.getDimension(R.styleable.RoundCornerImageView_corner_radius, DeviceUtil.dip2px(context, 18));
            Log.d("RoundCornerImageView", "mRadius: " + mRadius);
        }

        ta.recycle();
    }

    public void setRadiusDp(float dp) {
        mRadius = DeviceUtil.dip2px(context, dp);
        postInvalidate();
    }

    public void setRadiusPx(int px) {
        mRadius = px;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRect.set(0, 0, this.getWidth(), this.getHeight());
        mClipPath.reset(); // 每次绘制都切割一次圆角 记得调用Path.reset()方法

        if (mCornerType == CORNER_TYPE_EACH) {

            Log.d("RoundCornerImageView", "onDraw mFloatRadii: " + mFloatRadii);
            mClipPath.addRoundRect(mRect, mFloatRadii, Path.Direction.CW); // 沿顺时针方向绘制
        } else {

            Log.d("RoundCornerImageView", "onDraw mRadius: " + mRadius);
            mClipPath.addRoundRect(mRect, mRadius, mRadius, Path.Direction.CW);
        }

        canvas.clipPath(mClipPath);
        super.onDraw(canvas);
    }

}
