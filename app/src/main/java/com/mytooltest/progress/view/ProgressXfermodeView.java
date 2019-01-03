package com.mytooltest.progress.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;

/**
 * 进度条
 */

public class ProgressXfermodeView extends View {

    private static final String TAG = "ProgressXfermodeView";
    private Paint mBitPaint;
    private Bitmap mBGBitmap;
    private Bitmap mDstBitmap;
    private int mTotalWidth, mTotalHeight;
    private int mBitWidth, mBitHeight;
    private Rect mSrcRect;
    private PorterDuffXfermode mXfermode;
    private RectF mDynamicRect;

    public ProgressXfermodeView(Context context) {
        super(context);
        init(context);
    }
    public ProgressXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public ProgressXfermodeView(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //初始化Xfermode的模式
        // 源图像在运算时，只是在源图像所在区域与对应区域的目标图像做运算，所以目标图像与源图像不相交的地方是不会参与运算的
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        //初始化画笔
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        //获取背景图片
        mBGBitmap = ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.progress_bg_back)).getBitmap();
        //获取完整进度条图片
        mDstBitmap = ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.progress_bg_font)).getBitmap();

        int screenWidth = DeviceUtil.getScreenWidth(context);
        int screenHeight = DeviceUtil.getScreenHeight(context);
        mBitHeight = Math.min(mDstBitmap.getHeight(), screenHeight);
        mBitWidth = Math.min(mDstBitmap.getWidth(), screenWidth);

        //初始化原矩形大小为给定的背景图的大小
        mSrcRect = new Rect(0, 0, mBitWidth, mBitHeight);
        //动态变化的矩形
        mDynamicRect = new RectF(0, 0, 0, mBitHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 创建存为新图层
        canvas.drawBitmap(mBGBitmap, null, mSrcRect, null);

        int saveLayerCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mBitPaint, Canvas.ALL_SAVE_FLAG);
        // 绘制目标图为最终的完全进度条
        canvas.drawRoundRect(mDynamicRect, mBitHeight, mBitHeight, mBitPaint);
        // 设置混合模式
        mBitPaint.setXfermode(mXfermode);
        // 绘制源图形
        canvas.drawBitmap(mDstBitmap, null, mSrcRect, mBitPaint);
        // 清除混合模式
        mBitPaint.setXfermode(null);
        // 恢复保存的图层；
        canvas.restoreToCount(saveLayerCount);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置总宽高
        mTotalWidth = w;
        mTotalHeight = h;
    }

    /**
     * 设置进度条的数值变化这里就0～100
     * * @param value
     */
    public void setProgress(int value) {
        value = value < 0 ? 0 : value > 100 ? 100 : value;
        Log.i(TAG, "进度条增长" + value * (mBitWidth / 100));
        ValueAnimator va = ValueAnimator.ofFloat(mDynamicRect.right, value * (mBGBitmap.getWidth() / 100));
        va.setDuration(5000);
        va.addUpdateListener(new
        ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                float value = (float) animation.getAnimatedValue();
                mDynamicRect.right = value;
                postInvalidate();
            }
        });
        va.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(TAG, "释放资源");
//        if (mBGBitmap != null && !mBGBitmap.isRecycled()) {
//            mBGBitmap.recycle();
//        }
//        if (mDstBitmap != null && !mDstBitmap.isRecycled()) {
//            mDstBitmap.recycle();
//        }
    }

}