package com.mytooltest.canvas.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mytooltest.R;

/**
 * 刮刮乐效果
 */

public class EraserEffect2 extends View {

   private Paint paint;
   private Paint textPaint;
   private Path path;

   private Canvas dstCanvas;
   private Canvas canvas1;

   private Bitmap srcBitmap;
   private Bitmap dstBitmap;
   private Bitmap bitmap;

    private float eventX;
    private float eventY;

    public EraserEffect2(Context context) {
        this(context, null);
    }

    public EraserEffect2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        //初始化画笔
        paint = new Paint();
        //设置画笔颜色（不能为完全透明）
        paint.setColor(Color.RED);
        paint.setStrokeWidth(100);
        paint.setStyle(Paint.Style.STROKE);

        // 源图像
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer3, null);

        //目标图像
        dstBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        dstCanvas = new Canvas(dstBitmap);

        //中奖信息
        bitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        canvas1 = new Canvas(bitmap);

        //路径（贝塞尔曲线）
        path = new Path();
        //绘制中奖信息文字的画笔
        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(50);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        String text = "别傻了，宝贝，洗洗睡去吧！";
        //获取文字宽度
        float textWidth = textPaint.measureText(text);
        //居中绘制文字，这里没有考虑高度居中
        canvas1.drawText(text, (bitmap.getWidth() - textWidth) / 2, bitmap.getHeight() / 2, textPaint);

        canvas.drawBitmap(bitmap, 0, 0, paint);

        //使用离屏绘制
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);


        //先将路径绘制到 bitmap上
        dstCanvas.drawPath(path, paint);

        //绘制 目标图像
        canvas.drawBitmap(dstBitmap, 0, 0, paint);
        //设置 模式 为 SRC_OUT
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        //绘制源图像
        paint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                eventX = event.getX();
                eventY = event.getY();
                path.moveTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_MOVE:

                getParent().requestDisallowInterceptTouchEvent(true);

                float endX = (event.getX() - eventX) / 2 + eventX;
                float endY = (event.getY() - eventY) / 2 + eventY;
                path.quadTo(eventX, eventY, endX, endY);
                eventX = event.getX();
                eventY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                getParent().requestDisallowInterceptTouchEvent(false);

                break;

        }
        invalidate();
        return true;
    }

}
