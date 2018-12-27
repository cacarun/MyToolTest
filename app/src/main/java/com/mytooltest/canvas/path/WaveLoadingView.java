package com.mytooltest.canvas.path;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class WaveLoadingView extends View {

    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    private Paint mCirclePaint; // 绘制圆
    private Canvas mCanvas; // 我们自己的画布
    private Bitmap mBitmap;
    private int mWidth;
    private int mHeight;

    private Paint mWavePaint;

    private Path mPath;
    private boolean isLeft;

    private int y;
    private int x;



    public WaveLoadingView(Context context) {
        this(context, null);
    }

    public WaveLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPath = new Path();

        mWavePaint = new Paint();
        mWavePaint.setColor(Color.parseColor("#33b5e5"));
        mWavePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.parseColor("#99cc00"));
        mCirclePaint.setAntiAlias(true);

        mBitmap = Bitmap.createBitmap(500,500, Bitmap.Config.ARGB_8888); // 生成一个 bitmap
        mCanvas = new Canvas(mBitmap); // 将 bitmap 放在我们自己的画布上，实际上 mCanvas.draw 的时候 改变的是这个bitmap对象
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        setMeasuredDimension(mWidth, mHeight);


    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (x > 50) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }

        if (y > -50) {  //大于-50是因为辅助点是50  为了让他充满整个屏幕
            y--;
        }

        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mPath.reset();
        mPath.moveTo(0, y);
        mPath.cubicTo(100 + x * 2, 50 + y, 100 + x * 2, y - 50, mWidth, y); //前两个参数是辅助点
        mPath.lineTo(mWidth, mHeight);//充满整个画布
        mPath.lineTo(0, mHeight);//充满整个画布
        mPath.close();


        mBitmap.eraseColor(Color.parseColor("#00000000"));
        //dst
        mCanvas.drawPath(mPath, mWavePaint);

        canvas.drawBitmap(mBitmap, 0, 0, null);
        postInvalidateDelayed(10);



        // dst
//        mCanvas.drawCircle(150,150,50, mCirclePaint);
//
//        mWavePaint.setXfermode(mMode);
//
//        // src
//        mCanvas.drawRect(100,100,200,200, mWavePaint);
//
//        canvas.drawBitmap(mBitmap,0,0,null);




    }
}
