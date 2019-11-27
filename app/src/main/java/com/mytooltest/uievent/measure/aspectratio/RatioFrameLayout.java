package com.mytooltest.uievent.measure.aspectratio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioFrameLayout extends FrameLayout {

    // 宽高比
    private static final float RATIO = 16.f / 9;

    public RatioFrameLayout(@NonNull Context context) {
        super(context);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatioFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 父控件是否是固定值或者是match_parent
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            // 得到父容器的宽度
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            // 得到子控件的宽度
            int childWidth = parentWidth - getPaddingLeft() - getPaddingRight();
            // 计算子控件的高度
            int childHeight = (int) (childWidth / RATIO + 0.5f);
            // 计算父控件的高度
            int parentHeight = childHeight + getPaddingBottom() + getPaddingTop();

            // note 必须测量子控件,确定孩子的大小
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            // 测量
            setMeasuredDimension(parentWidth, parentHeight);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
