package com.mytooltest.canvas.path;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class PathTestView extends View {

    private Path mPath;
    private Paint mPaint;

    public PathTestView(Context context) {
        this(context, null);
    }

    public PathTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPath = new Path();

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(0,0); // TODO 这行去掉的话画出来的是一个三角形？
        mPath.lineTo(300, 300);
        mPath.moveTo(0,0);
        mPath.lineTo(100, 300);
        canvas.drawPath(mPath, mPaint);


//        mPath.addRect(new RectF(0, 0, 200, 200), Path.Direction.CW); // 从左上角开始 顺时针方向
//        canvas.drawPath(mPath, mPaint);

//        mPath.addRect(0, 0, 200, 200, Path.Direction.CW);
//        mPath.setLastPoint(0, 100);
//        canvas.drawPath(mPath, mPaint);

//        mPath.addRect(0, 0, 200, 200, Path.Direction.CCW);
//        mPath.setLastPoint(100,0);
//        canvas.drawPath(mPath, mPaint);


//        mPath.addCircle(200, 200, 100, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);


//        RectF rect = new RectF(50,50,250,150);
//        mPath.addRoundRect(rect, 50, 50, Path.Direction.CW); // 创建一个圆角矩形
//        canvas.drawPath(mPath, mPaint);


//        RectF rect = new RectF(0,0,400,300); // 矩形
////        RectF rect = new RectF(0,0,400,400); // 正方形
//        mPaint.setColor(Color.GRAY);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(rect, mPaint);
//        mPaint.setColor(Color.BLACK);
//        mPath.addOval(rect, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);


//        RectF rect = new RectF(100,100,400,400);
//        mPaint.setColor(Color.GRAY);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(rect, mPaint);
//        mPaint.setColor(Color.BLACK);
//        mPath.addArc(rect, 90, 180); // 圆弧（扇形）
//        canvas.drawPath(mPath, mPaint);


        // arcTo 添加一个圆弧到path，如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
//        RectF rect = new RectF(200,200,400,300);
//        mPaint.setColor(Color.GRAY);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(rect, mPaint);
//        mPaint.setColor(Color.BLACK);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPath.moveTo(0, 0);
//        mPath.lineTo(100, 50); // 用path画一条从(0,0)到(100,50)的直线
////        mPath.arcTo(rect, 90, 180); // 用arcTo方法画一段圆弧（直线终点(100,50)与圆弧起点会连成一条直线）
//        mPath.arcTo(rect, 90, 180, true); // 用arcTo方法画一段圆弧（直线终点(100,100)与圆弧起点不会连成一条直线）
//        canvas.drawPath(mPath, mPaint);


//        mPath.addRect(0, 0, 400, 400, Path.Direction.CW); // 宽高为400的矩形
//        Path src = new Path();
//        src.addCircle(200, 200, 100, Path.Direction.CW); // 圆心为(200,200)半径为100的正圆
////        mPath.addPath(src);
//        mPath.addPath(src, 200, 0); // 位移
//        canvas.drawPath(mPath, mPaint);

//        mPath.addRect(0, 0, 400, 400, Path.Direction.CW);
//        Path src = new Path();
//        src.addCircle(200, 200, 100, Path.Direction.CW);
//        mPath.set(src); // 相当于 path = src;
//        canvas.drawPath(mPath, mPaint);

//        mPath.addRect(0, 0, 400, 400, Path.Direction.CW);
//        canvas.drawPath(mPath, mPaint);
//        mPaint.setColor(Color.RED); // 将画笔变成红色
//        mPath.offset(100,0);  // 将path向右平移
//        canvas.drawPath(mPath, mPaint);

//        mPath.addRect(0, 0, 100, 100, Path.Direction.CW); //path添加矩形
//        Path dst = new Path();
//        dst.addCircle(50,50, 50, Path.Direction.CW); //dst添加圆形
//        mPath.offset(50,0, dst); //将平移后的path存储到dst
//        canvas.drawPath(dst, mPaint);


//        mPath.moveTo(100, 100);
//        mPath.quadTo(180, 30, 300, 100); // (x1,y1) 为控制点，(x2,y2)为结束点。
//        canvas.drawPath(mPath, mPaint);

////        mPath.moveTo(100, 100); // 加这行后效果跟上面的一致（二次贝塞尔曲线），不加这行会变成三次贝塞尔曲线
//        mPath.cubicTo(100, 100, 180, 30, 300, 100); // (x1,y1) 为控制点，(x2,y2)为控制点，(x3,y3) 为结束点
//        canvas.drawPath(mPath, mPaint);






    }
}
