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
 * 进度条 + 进度头
 */

public class ProgressXfermodeView2 extends View {

    private static final String TAG = "ProgressXfermodeView2";

    private static final int PROGRESS_HEIGHT = 32;
    private static final int PROGRESS_COIN_HEIGHT = 44; // 指示头宽高
    private static final int PROGRESS_MARGIN = 6;

    private Paint mBitPaint;
    private Bitmap mBGBitmap;
    private Bitmap mDstBitmap;
    private Bitmap mCoinBitmap;
    private int mTotalWidth, mTotalHeight;
    private int mBitWidth, mBitHeight, mMarginHeight, mCoinHeight, mCoinHeightHalf;
    private Rect mSrcRect;
    private PorterDuffXfermode mXfermode;
    private RectF mDynamicRect;
    private RectF mDynamicCoinRect;

    public ProgressXfermodeView2(Context context) {
        super(context);
        init(context);
    }
    public ProgressXfermodeView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public ProgressXfermodeView2(Context context, AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 初始化Xfermode的模式
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 初始化画笔
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        // 获取背景图片
        mBGBitmap = ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.wallet_task_new_progress_back)).getBitmap();
        // 获取完整进度条图片
        mDstBitmap = ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.wallet_task_new_progress_font)).getBitmap();
        // 获取指示头
        mCoinBitmap = ((BitmapDrawable) ContextCompat.getDrawable(context, R.drawable.wallet_task_new_progress_coin)).getBitmap();

        mBitHeight = DeviceUtil.dip2px(context, PROGRESS_HEIGHT);
        mBitWidth = mDstBitmap.getWidth();

        mMarginHeight = DeviceUtil.dip2px(context, PROGRESS_MARGIN);
        mCoinHeight = DeviceUtil.dip2px(context, PROGRESS_COIN_HEIGHT);
        mCoinHeightHalf = (int)(mCoinHeight / 2f);

        // 初始化原矩形大小为给定的背景图的大小
        mSrcRect = new Rect(mCoinHeightHalf, mMarginHeight, mBitWidth, mBitHeight + mMarginHeight);
        Log.d(TAG, "cjw widthddd: " + mBitWidth);
        // 动态变化的矩形
        mDynamicRect = new RectF(mCoinHeightHalf, mMarginHeight, mCoinHeightHalf, mBitHeight + mMarginHeight);
        // 指示头
        mDynamicCoinRect = new RectF(0, 0, mCoinHeight, mCoinHeight);
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
        // 绘制指示头
        canvas.drawBitmap(mCoinBitmap, null, mDynamicCoinRect, null);

        // 清除混合模式
        mBitPaint.setXfermode(null);
        // 恢复保存的图层；
        canvas.restoreToCount(saveLayerCount);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 设置总宽高
        mTotalWidth = w;
        mTotalHeight = h;
    }

    /**
     * 设置进度条的数值变化这里就0～100
     * * @param value
     */
    public void setProgress(int value) {
        value = value < 0 ? 0 : value > 100 ? 100 : value;

        ValueAnimator va = ValueAnimator.ofFloat(mCoinHeightHalf, value * (mBitWidth / 100f));
        va.setDuration(5000);
        va.addUpdateListener(new
        ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation){
                float value = (float) animation.getAnimatedValue();
                Log.d(TAG, "cjw 进度条增长: " + value);
                mDynamicRect.right = value;

                mDynamicCoinRect.left = value - mCoinHeightHalf;
                mDynamicCoinRect.right = value + mCoinHeightHalf;

                postInvalidate();
            }
        });
        va.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "释放资源");
//        if (mBGBitmap != null && !mBGBitmap.isRecycled()) {
//            mBGBitmap.recycle();
//        }
//        if (mDstBitmap != null && !mDstBitmap.isRecycled()) {
//            mDstBitmap.recycle();
//        }
//        if (mCoinBitmap != null && !mCoinBitmap.isRecycled()) {
//            mCoinBitmap.recycle();
//        }
    }

}