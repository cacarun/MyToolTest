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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.mytooltest.R;

/**
 * 橡皮擦效果
 */

public class EraserEffect extends View {

    private Paint paint;

    private Bitmap srcBitmap;
    private Bitmap dstBitmap;

    private Path path;
    private Canvas dstCanvas;

    private float eventX;
    private float eventY;

    public EraserEffect(Context context) {
        this(context, null);
    }

    public EraserEffect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        //初始化画笔
        paint = new Paint();
        //设置画笔颜色（不能为完全透明）
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(100);
        // 源图像
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer2, null);
        //目标图像
        dstBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        dstCanvas = new Canvas(dstBitmap);
        //路径（贝塞尔曲线）
        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //使用离屏绘制
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);

        //先将路径绘制到 bitmap上
        dstCanvas.drawPath(path, paint);

        //绘制 目标图像
        canvas.drawBitmap(dstBitmap, 0, 0, paint);
        //设置 模式 为 SRC_OUT
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        //绘制源图像
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerID);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        Log.d("EraserEffect", "onTouchEvent...");
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
                Log.d("EraserEffect", "endX: " + endX + " endY: " + endY);
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
