package com.mytooltest.view;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * TextView 渐变
 */
public class GradientColorTextView extends android.support.v7.widget.AppCompatTextView {

//    private LinearGradient mLinearGradient;
//    private Paint mPaint;
//    private int mViewWidth = 0;
//    private Rect mTextBound = new Rect();

    public GradientColorTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        mViewWidth = getMeasuredWidth();
//        mPaint = getPaint();
//        String mTipText = getText().toString();
//        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
//        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{0xFFFF7701, 0xFFFE0526}, null, Shader.TileMode.REPEAT);
//        mPaint.setShader(mLinearGradient);
//        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
//    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            getPaint().setShader(new LinearGradient(0, 0, 0, getHeight(), new int[]{0xFFFF7701, 0xFFFE0526}, null, Shader.TileMode.CLAMP));
        }

    }
}
