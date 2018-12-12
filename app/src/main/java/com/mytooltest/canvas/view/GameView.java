package com.mytooltest.canvas.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jarvis on 2018/12/12.
 */

public class GameView extends View implements Runnable {

    /* 声明Paint对象 */
    private Paint mPaint = null;

    float arc;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        /* 构建对象 */
        mPaint = new Paint();

        /* 开启线程 */
        new Thread(this).start();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(arc>360)
            arc=0;
        /* 设置画布的颜色 */
//        canvas.drawColor(Color.WHITE);
        canvas.drawColor(Color.BLACK);

        /* 设置取消锯齿效果 */
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(255); // 不透明
        if(arc>200){

            mPaint.setColor(Color.RED);
        }else{

            mPaint.setColor(Color.GREEN);
        }
        mPaint.setStyle(Paint.Style.STROKE); // 设置画笔类型为轮廓
        mPaint.setStrokeWidth(5);

        // 绘制圆角矩形
        RectF rf = new RectF(10, 10, 90, 140);
        canvas.drawRoundRect(rf, 10, 10, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        mPaint.setAlpha(0); // 透明
        mPaint.setStyle(Paint.Style.FILL); // 设置画笔类型为填充

        /**
         *
         * 绘制圆弧
         *
         * oval       -    用于确定圆弧形状与尺寸的椭圆边界（即椭圆外切矩形）
         * startAngle -    圆弧起始角度，单位为度（以时钟3点的方向为0°，顺时针方向向下）
         * sweepAngle -    圆弧扫过的角度，顺时针方向，单位为度
         * useCenter  -    如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
         *
         */
        RectF arcRF = new RectF(-100, -75, 200, 225); // 确定外切矩形范围
        canvas.drawArc(arcRF, 240, arc, true, mPaint);

        arc+=2.5;

    }

    // 触笔事件
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    // 按键按下事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }

    // 按键弹起事件
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return true;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // 使用postInvalidate可以直接在线程中更新界面
            postInvalidate();
        }
    }
}
