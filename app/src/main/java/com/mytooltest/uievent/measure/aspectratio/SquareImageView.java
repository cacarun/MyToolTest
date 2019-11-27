package com.mytooltest.uievent.measure.aspectratio;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.mytooltest.DTApplication;
import com.mytooltest.util.DeviceUtil;


public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    //屏幕宽度
    private int screenWidth = DeviceUtil.getScreenWidth(DTApplication.getInstance());
    private int ratio = 4;

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 第一种  比如开发一个相册界面: ImageView宽度为屏幕宽度的1/4, 然后通过内边距来控制间隔
//        setMeasuredDimension(screenWidth / ratio, screenWidth / ratio);

        // 第二种
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        setMeasuredDimension(widthSize, widthSize);

        // 第三种
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);

    }

}
