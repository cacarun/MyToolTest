package com.mytooltest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import com.mytooltest.R;
import com.mytooltest.util.DeviceUtil;

/**
 * BitmapShader +  CENTER_CROP 实现圆角图片 或者 某一个角、某几个角是圆角的图片（无锯齿）
 *
 */
public class RoundCenterCropWithEachConnerShaderView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "RoundEachConner";

    private static final int CORNER_TOP_LEFT = 1;
    private static final int CORNER_TOP_RIGHT = 1 << 1;
    private static final int CORNER_BOTTOM_LEFT = 1 << 2;
    private static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    private static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;

    private float cornerRadius;
    private int corners;

    private int width;
    private int height;

    private Bitmap image;
    private Drawable drawable;

    private Paint paint;

    private RectF rectF = new RectF();

    public RoundCenterCropWithEachConnerShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCenterCropWithEachConnerShaderView);
        cornerRadius = typedArray.getDimension(R.styleable.RoundCenterCropWithEachConnerShaderView_cornerRadius, DeviceUtil.dip2px(context, 18));
        corners = typedArray.getInt(R.styleable.RoundCenterCropWithEachConnerShaderView_corners, CORNER_ALL);
        typedArray.recycle();

        Log.d(TAG, "cornerRadius=" + cornerRadius + " corners=" + corners);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        Log.d(TAG, "onSizeChanged... width=" + width + " height=" + height);
//        if (image != null)
//            updateShader();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.d(TAG, "onDraw... width=" + width + " height=" + height);

        loadBitmap();

        // Check if image isn't null
        if (image == null)
            return;

        //先画一个圆角矩形将图片显示为圆角
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
        int notRoundedCorners = corners ^ CORNER_ALL;

        Log.d(TAG, "onDraw onDraw notRoundedCorners=" + notRoundedCorners);

        //哪个角不是圆角我再把你用矩形画出来
        if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
            canvas.drawRect(0, 0, cornerRadius, cornerRadius, paint);
        }
        if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
            canvas.drawRect(rectF.right - cornerRadius, 0, rectF.right, cornerRadius, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
            canvas.drawRect(0, rectF.bottom - cornerRadius, cornerRadius, rectF.bottom, paint);
        }
        if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
            canvas.drawRect(rectF.right - cornerRadius, rectF.bottom - cornerRadius, rectF.right, rectF.bottom, paint);
        }
    }

    private void loadBitmap() {
        if (drawable == getDrawable())
            return;

        drawable = getDrawable();
        image = drawableToBitmap(drawable);

        updateShader();
    }

    private void updateShader() {

        if (image == null)
            return;

        Log.d(TAG, "updateShader... width=" + width + " height=" + height);

        rectF.set(0, 0, width, height);

        // Create Shader
        BitmapShader shader = new BitmapShader(image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        // Center Image in Shader
        float scale = 0;
        float dx = 0;
        float dy = 0;

        // CENTER_CROP
        if (image.getWidth() * getHeight() > getWidth() * image.getHeight()) {
            scale = getHeight() / (float) image.getHeight();
            dx = (getWidth() - image.getWidth() * scale) * 0.5f;
        } else {
            scale = getWidth() / (float) image.getWidth();
            dy = (getHeight() - image.getHeight() * scale) * 0.5f;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        matrix.postTranslate(dx, dy);
        shader.setLocalMatrix(matrix);

        // Set Shader in Paint
        paint.setShader(shader);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        } else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            // Create Bitmap object out of the drawable
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
