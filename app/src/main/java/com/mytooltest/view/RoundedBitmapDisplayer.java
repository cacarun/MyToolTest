package com.mytooltest.view;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 * 基于大众化图片加载库Universal-Image-Loader中的RoundedBitmapDisplayer源码进行拓展，
 * 自定义了一个FlexibleRoundedBitmapDisplayer，
 * 可以用简单快捷的代码指定图片的某几个角为圆角，某几个角不为圆角
 *
 */

public class RoundedBitmapDisplayer implements BitmapDisplayer {
    protected int cornerRadius;
    protected int corners;

    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    /**
     * 构造方法说明：设定圆角像素大小，所有角都为圆角
     * @param cornerRadiusPixels 圆角像素大小
     */

    public RoundedBitmapDisplayer(int cornerRadiusPixels){
        this.cornerRadius = cornerRadiusPixels;
        this.corners = CORNER_ALL;
    }
    /**
     * 构造方法说明：设定圆角像素大小，指定角为圆角
     * @param cornerRadiusPixels 圆角像素大小
     * @param corners 自定义圆角<br>
     * CORNER_NONE　无圆角<br>
     * CORNER_ALL 全为圆角<br>
     * CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT　指定圆角（选其中若干组合  ） <br>
     */
    public RoundedBitmapDisplayer(int cornerRadiusPixels, int corners){
        this.cornerRadius = cornerRadiusPixels;
        this.corners = corners;
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }
        imageAware.setImageDrawable(new FlexibleRoundedDrawable(bitmap,cornerRadius,corners));
    }
    public static class FlexibleRoundedDrawable extends Drawable {
        protected final float cornerRadius;

        protected final RectF mRect = new RectF(), mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;
        private int corners;

        public FlexibleRoundedDrawable(Bitmap bitmap, int cornerRadius, int corners) {
            this.cornerRadius = cornerRadius;
            this.corners = corners;

            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            mBitmapRect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(0, 0, bounds.width(), bounds.height());

            Matrix shaderMatrix = new Matrix();

            // 1) 会变形
//            shaderMatrix.setRectToRect(mBitmapRect, mRect, Matrix.ScaleToFit.FILL);
//            bitmapShader.setLocalMatrix(shaderMatrix);

            // 2)
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            float scale = Math.max(bounds.width() * 1.0f / mBitmapRect.right, bounds.height()
                    * 1.0f / mBitmapRect.bottom);
            // shader的变换矩阵，我们这里主要用于放大或者缩小
            shaderMatrix.setScale(scale, scale);
            bitmapShader.setLocalMatrix(shaderMatrix);
        }

        @Override
        public void draw(Canvas canvas) {
            //先画一个圆角矩形将图片显示为圆角
            canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
            int notRoundedCorners = corners ^ CORNER_ALL;
            //哪个角不是圆角我再把你用矩形画出来
            if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
                canvas.drawRect(0, 0, cornerRadius, cornerRadius, paint);
            }
            if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
                canvas.drawRect(mRect.right - cornerRadius, 0, mRect.right, cornerRadius, paint);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
                canvas.drawRect(0, mRect.bottom - cornerRadius, cornerRadius, mRect.bottom, paint);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
                canvas.drawRect(mRect.right - cornerRadius, mRect.bottom - cornerRadius, mRect.right, mRect.bottom, paint);
            }
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }
}
