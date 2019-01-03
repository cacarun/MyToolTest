package com.mytooltest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.mytooltest.R;

public class RoundShaderView extends View {

    private static final int RECT_SIZE = 400;// 矩形尺寸的一半

    private Paint mPaint;// 画笔

    private RectF rectF;

    public RoundShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setColor(Color.BLACK);

        // 获取位图
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.layer2);
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        rectF = new RectF(0, 0, RECT_SIZE, RECT_SIZE);

        // 设置着色器
        mPaint.setShader(bitmapShader);

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        Matrix shaderMatrix = new Matrix();
        float scale = Math.max(rectF.right * 1.0f / bitmap.getWidth(), rectF.bottom * 1.0f / bitmap.getHeight());
        shaderMatrix.setScale(scale, scale);
        bitmapShader.setLocalMatrix(shaderMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制圆角矩形
        canvas.drawRoundRect(rectF, 10, 10, mPaint);
    }
}
