package com.mytooltest.canvas.xfermode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * https://blog.csdn.net/iispring/article/details/50472485
 */

public class XfermodeTestView extends View {

    private Paint paint;

    public XfermodeTestView(Context context) {
        this(context, null);
    }

    public XfermodeTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 开启硬件离屏缓存
         */
        setLayerType(LAYER_TYPE_HARDWARE, null);

        //设置背景色
        canvas.drawARGB(255, 139, 197, 186);

        int canvasHeight = canvas.getHeight();
        int canvasWidth = canvas.getWidth();
        int r = canvasWidth / 3;


        // 1) 绘制黄色的圆形
//        paint.setColor(0xFFFFCC44);
//        canvas.drawCircle(r, r, r, paint);
//        // 绘制蓝色的矩形
//        paint.setColor(0xFF66AAFF);
//        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);


        // 2) 绘制黄色的圆形
//        paint.setColor(0xFFFFCC44);
//        canvas.drawCircle(r, r, r, paint);
//        // 使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
//        paint.setColor(0xFF66AAFF);
//        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
//        //最后将画笔去除Xfermode
//        paint.setXfermode(null);


        // 3)
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        //正常绘制黄色的圆形
        paint.setColor(0xFFFFCC44);
        canvas.drawCircle(r, r, r, paint);
        //使用CLEAR作为PorterDuffXfermode绘制蓝色的矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setColor(0xFF66AAFF);
        canvas.drawRect(r, r, r * 2.7f, r * 2.7f, paint);
        //最后将画笔去除Xfermode
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);


    }
}
